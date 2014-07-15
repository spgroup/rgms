package rgms

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile
import org.xml.sax.SAXParseException
import rgms.member.Member
import rgms.member.Orientation
import rgms.publication.*
import rgms.researchProject.Funder
import rgms.researchProject.ResearchProject

class XMLService {

    def sessionFactory

    public static final String PUB_STATUS_STABLE = "stable"
    public static final String PUB_STATUS_TO_UPDATE = "to update"
    public static final String PUB_STATUS_CONFLICTED= "conflicted"
    public static final String PUB_STATUS_DUPLICATED = "duplicated"

    /*
        saveEntity - closure que salva a classe de domínio que está usando a importação
     */

    def boolean Import(Closure saveEntity, Closure returnWithMessage,
                          String flashMessage, String controller,
                          javax.servlet.http.HttpServletRequest request) {
        boolean errorFound = false
        def publications

        try {
            Node xmlFile = parseReceivedFile(request)
            publications = saveEntity(xmlFile)
        }
        //If file is not XML or if no file was uploaded
        catch (SAXParseException) {
            flashMessage = 'default.xml.parserror.message'
            errorFound = true
        }
        //If XML structure is not according to Lattes, it'll perform an invalid cast
        catch (NullPointerException) {
            flashMessage = 'default.xml.structure.message'
            errorFound = true
        }
        catch (Exception ex) {
            flashMessage = 'default.xml.unknownerror.message'
            errorFound = true
        }

        returnWithMessage(flashMessage, controller, publications)

        return !errorFound
    }

    def createPublications(Node xmlFile, Member user) {
        def publications = [:]
        if(!xmlFile) return publications

        //#if($Article)
        def journals = createJournals(xmlFile, user.name)
        if(journals) publications.put("journals", journals)
        //#end

        def tools = createTools(xmlFile, user.name)
        if(tools) publications.put("tools", tools)

        def books = createBooks(xmlFile, user.name)
        if(books) publications.put("books", books)

        def bookChapters = createBooksChapters(xmlFile, user.name)
        if(bookChapters) publications.put("bookChapters", bookChapters)

        def masterDissertation = createMasterDissertation(xmlFile, user.name)
        if(masterDissertation) publications.put("masterDissertation", masterDissertation)

        def thesis = createThesis(xmlFile, user.name)
        if(thesis) publications.put("thesis", thesis)

        def conferences = createConferencias(xmlFile, user.name)
        if(conferences) publications.put("conferences", conferences)

        //#if($researchLine)
        def researchLines = createResearchLines(xmlFile, user.name)
        if(researchLines) publications.put("researchLines", researchLines)
        //#end

        //#if($researchProject)
        def researchProjects = createResearchProjects(xmlFile, user.name)
        if(researchProjects) publications.put("researchProjects", researchProjects)
        //#end

        //#if($Orientation)
        def orientations = createOrientations(xmlFile, user)
        if(orientations) publications.put("orientations", orientations)
        //#end

        return publications
    }

    def createTools(Node xmlFile, String authorName) {
        def softwares = xmlFile.depthFirst().findAll{ it.name() == 'SOFTWARE' }
        def tools = []

        for (Node currentNode : softwares) {
            def newTool = saveNewTool(currentNode, authorName)
            def status = checkToolStatus(newTool)
            if (status && status != XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newTool.properties.findAll{it.key in ["title","publicationDate", "authors", "description"]}
                tools += ["obj": obj, "status": status]
            }
        }

        return tools
    }

    private static saveNewTool(Node currentNode, String authorName) {
        Node basicData = (Node) currentNode.children()[0]
        Node softwareDetails = (Node) currentNode.children()[1]
        Node additionalInfo = getNodeFromNode(currentNode, "INFORMACOES-ADICIONAIS")

        Ferramenta newTool = new Ferramenta()
        newTool.members = []
        newTool = (Ferramenta) addAuthors(currentNode, newTool)
        if(!newTool.authors.contains(authorName)) return null //the user is not author

        fillPublicationDate(newTool, basicData, "ANO")
        newTool.title = getAttributeValueFromNode(basicData, "TITULO-DO-SOFTWARE")
        String description = getAttributeValueFromNode(additionalInfo, "DESCRICAO-INFORMACOES-ADICIONAIS")
        newTool.description = "País: " + getAttributeValueFromNode(basicData, "PAIS") + ", Ambiente: " +
                getAttributeValueFromNode(softwareDetails, "AMBIENTE") +
                (description.equals("") ? "" : ", Informacoes adicionais: " + description)

        return newTool
    }

    static checkToolStatus(Ferramenta tool){
        if(!tool) return null
        def status = XMLService.PUB_STATUS_STABLE
        def toolDB = Ferramenta.findByTitle(tool.title)
        if(toolDB) status = checkPublicationStatus(toolDB, tool)
        return status
    }

    def createBooks(Node xmlFile, String authorName) {
        def publishedBooks = xmlFile.depthFirst().findAll{ it.name() == 'LIVRO-PUBLICADO-OU-ORGANIZADO' }
        def booksList = []

        int i = 0
        for (Node currentNode : publishedBooks) {
            def newBook = createNewBook(currentNode, i, authorName)
            def status = checkBookStatus(newBook)
            if(status && status != PUB_STATUS_DUPLICATED) {
                def obj = newBook.properties.findAll{it.key in ["title","publicationDate", "authors", "publisher", "volume", "pages"]}
                booksList += ["obj": obj, "status": status]
            }
            ++i
        }

        return booksList
    }

    private static createNewBook(Node currentNode, int i, String authorName) {
        List<Object> book = currentNode.children()
        Node basicData = (Node) book[0]
        Node bookDetails = (Node) book[1]
        Book newBook = new Book()

        newBook.members = []
        newBook = (Book) addAuthors(currentNode, newBook)
        if(!newBook.authors.contains(authorName)) return null //the user is not author

        newBook.title = getAttributeValueFromNode(basicData, "TITULO-DO-LIVRO")
        newBook.publisher = getAttributeValueFromNode(bookDetails, "NOME-DA-EDITORA")
        fillPublicationDate(newBook, basicData, "ANO")
        newBook.pages = getAttributeValueFromNode(bookDetails, "NUMERO-DE-PAGINAS")
        newBook.volume = getAttributeValueFromNode(bookDetails, "NUMERO-DE-VOLUMES").toInteger()

        return newBook
    }

    static checkBookStatus(Book book){
        if(!book) return null
        def status = PUB_STATUS_STABLE
        def bookDB = Book.findByTitleAndVolume(book.title, book.volume)
        if(bookDB) status = checkPublicationStatus(bookDB, book)
        return status
    }

    def createBooksChapters(Node xmlFile, String authorName) {
        def publishedBookChapters = xmlFile.depthFirst().findAll{ it.name() == 'CAPITULO-DE-LIVRO-PUBLICADO' }
        def bookChaptersList = []

        for (int i = 0; i < publishedBookChapters?.size(); ++i) {
            def newBookChapter = createNewBookChapter(publishedBookChapters, i, authorName)
            def status = checkBookChapterStatus(newBookChapter)
            if(status && status != PUB_STATUS_DUPLICATED) {
                def obj = newBookChapter.properties.findAll{it.key in ["title","publicationDate", "authors", "publisher"]}
                bookChaptersList += ["obj": obj, "status": status]
            }
        }

        return bookChaptersList
    }

    private static createNewBookChapter(List<Object> bookChaptersChildren, int i, String authorName) {
        List<Object> bookChapter = ((Node) bookChaptersChildren[i]).children()
        Node basicData = (Node) bookChapter[0]
        Node chapterDetails = (Node) bookChapter[1]

        BookChapter newBookChapter = new BookChapter()
        newBookChapter.members = []
        newBookChapter = (BookChapter) addAuthors(bookChapter, newBookChapter)
        if(!newBookChapter.authors.contains(authorName)) return null

        newBookChapter.title = getAttributeValueFromNode(basicData, "TITULO-DO-CAPITULO-DO-LIVRO")
        newBookChapter.publisher = getAttributeValueFromNode(chapterDetails, "NOME-DA-EDITORA")
        fillPublicationDate(newBookChapter, basicData, "ANO")

        return newBookChapter
    }

    static checkBookChapterStatus(BookChapter bookChapter){
        if(!bookChapter) return null
        def status = PUB_STATUS_STABLE
        def bookChapterDB = BookChapter.findByTitleAndChapter(bookChapter.title, bookChapter.chapter)
        if(bookChapterDB) status = checkPublicationStatus(bookChapterDB, bookChapter)
        return status
    }

    private static Publication addAuthors(publication, newPublication) {
        publication.each{
            if(it.name() == "AUTORES"){
                newPublication.addToAuthors(getAttributeValueFromNode(it, "NOME-COMPLETO-DO-AUTOR"))
            }
        }
        return newPublication
    }

    def createMasterDissertation(Node xmlFile, String authorName) {
        def newDissertation = saveMasterDissertation(xmlFile, authorName)
        if(!newDissertation) return null

        def dissertationDB = Dissertacao.findByTitle(newDissertation?.title)
        if(dissertationDB?.authors != newDissertation?.authors) dissertationDB = null

        def status = checkDissertationOrThesisStatus(dissertationDB, newDissertation)
        if(status == PUB_STATUS_DUPLICATED) return null
        def obj = newDissertation.properties.findAll{it.key in ["title","publicationDate", "authors", "school"]}
        return ["obj": obj, "status":status]
    }

    private static saveMasterDissertation(Node xmlFile, String authorName){
        def mestrado = xmlFile.depthFirst().find{ it.name() == 'MESTRADO' }
        if(!mestrado) return null

        String author = xmlFile.depthFirst().find{it.name() == 'DADOS-GERAIS'}.'@NOME-COMPLETO'
        if(author != authorName) return null

        Dissertacao newDissertation = new Dissertacao()
        newDissertation.members = []
        newDissertation = getDissertationOrThesisDetails(mestrado, newDissertation)
        newDissertation.addToAuthors(author)

        return newDissertation
    }

    static checkDissertationOrThesisStatus(TeseOrDissertacao pubDB, TeseOrDissertacao pub){
        if(!pub) return null
        def status = PUB_STATUS_STABLE
        if(pubDB) status = checkPublicationStatus(pubDB, pub)
        return status
    }

    def createThesis(Node xmlFile, String authorName) {
        def newThesis = saveThesis(xmlFile, authorName)
        if(!newThesis) return null

        def thesisDB = Tese.findByTitle(newThesis?.title)
        if(thesisDB?.authors != newThesis?.authors) thesisDB = null

        def status = checkDissertationOrThesisStatus(thesisDB, newThesis)
        if(status == PUB_STATUS_DUPLICATED) return null

        def obj = newThesis.properties.findAll{it.key in ["title","publicationDate", "authors", "school"]}
        return ["obj": obj, "status":status]
    }

    private static saveThesis(Node xmlFile, String authorName){
        def doutorado = xmlFile.depthFirst().find{ it.name() == 'DOUTORADO' }
        if(!doutorado) return null

        String author = xmlFile.depthFirst().find{it.name() == 'DADOS-GERAIS'}.'@NOME-COMPLETO'
        if(author != authorName) return null

        Tese newThesis = new Tese()
        newThesis.members = []
        newThesis = getDissertationOrThesisDetails(doutorado, newThesis)
        newThesis.addToAuthors(author)

        return newThesis
    }

    private static getDissertationOrThesisDetails(Node xmlNode, TeseOrDissertacao publication) {
        publication.title = getAttributeValueFromNode(xmlNode, "TITULO-DA-DISSERTACAO-TESE")
        fillPublicationDate(publication, xmlNode, "ANO-DE-OBTENCAO-DO-TITULO")
        publication.school = getAttributeValueFromNode(xmlNode, "NOME-INSTITUICAO")
        return publication
    }

    def createConferencias(Node xmlFile, String authorName) {
        def conferencePublications = xmlFile.depthFirst().findAll{ it.name() == 'TRABALHO-EM-EVENTOS' }
        def conferences = []

        for (Node currentNode : conferencePublications) {
            def newConference = saveNewConferencia(currentNode, authorName);
            def status = checkConferenceStatus(newConference)

            if(status && status != PUB_STATUS_DUPLICATED){
                def obj = newConference.properties.findAll{it.key in ["title","publicationDate", "authors", "booktitle", "pages"]}
                conferences += ["obj": obj, "status":status]
            }
        }

        return conferences
    }

    private static saveNewConferencia(conferenceNode, authorName) {
        def newConference = null
        def basicData = conferenceNode?.depthFirst()?.find{ it.name() == 'DADOS-BASICOS-DO-TRABALHO' }
        def details = conferenceNode?.depthFirst()?.find{ it.name() == 'DETALHAMENTO-DO-TRABALHO' }

        if (basicData && details) {
            def eventName = getAttributeValueFromNode(details, "NOME-DO-EVENTO")

            if (eventName.contains("onferenc")) {
                newConference = new Conferencia()
                newConference.members = []
                def authorsNode = conferenceNode.depthFirst().findAll{ it.name() == 'AUTORES'}
                newConference = (Conferencia) addAuthors(authorsNode, newConference)
                if(!newConference.authors.contains(authorName)) return null

                newConference.title = eventName
                fillPublicationDate(newConference, basicData, "ANO-DO-TRABALHO")
                newConference.booktitle = getAttributeValueFromNode(basicData, "TITULO-DO-TRABALHO")
                String initialPage = getAttributeValueFromNode(details, "PAGINA-INICIAL")
                String finalPage = getAttributeValueFromNode(details, "PAGINA-FINAL")
                newConference.pages = initialPage + "-" + finalPage
            }
        }
        return newConference
    }

    static checkConferenceStatus(Conferencia conference){
        if(!conference) return null
        def status = PUB_STATUS_STABLE
        def conferenceDB = Conferencia.findByTitleAndBooktitle(conference.title, conference.booktitle)
        if(conferenceDB) status = checkPublicationStatus(conferenceDB, conference)
        return status
    }

    //#if($Article)
    def createJournals(Node xmlFile, String authorName) {
        def publishedArticles = xmlFile.depthFirst().findAll{ it.name() == 'ARTIGO-PUBLICADO' }
        def journals = []

        for (int i = 0; i < publishedArticles?.size(); ++i) {
            def newJournal = saveNewJournal(publishedArticles, i, authorName)
            def status = checkJournalStatus(newJournal)

            if(status && status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newJournal.properties.findAll{it.key in ["title","publicationDate", "authors", "journal", "volume", "number", "pages"]}
                journals += ["obj": obj, "status":status]
            }
        }

        return journals
    }

    private static saveNewJournal(List publishedArticlesChildren, int i, String authorName) {
        List<Node> firstArticle = ((Node) publishedArticlesChildren[i]).children()
        Node basicData = (Node) firstArticle[0]
        Node articleDetails = (Node) firstArticle[1]
        Periodico newJournal = new Periodico()
        getJournalTitle(basicData, newJournal)
        newJournal.members = []
        newJournal = (Periodico) addAuthors(firstArticle, newJournal)
        if(!newJournal.authors.contains(authorName)) return null //the user is not author

        fillPublicationDate(newJournal, basicData, "ANO-DO-ARTIGO")
        getJournalVolume(articleDetails, newJournal)
        getJournalNumber(articleDetails, newJournal)
        getJournalNumberOfPages(articleDetails, newJournal)
        getPeriodicTitle(articleDetails, newJournal)

        return newJournal
    }

    static checkJournalStatus(Periodico journal){
        if(!journal) return null
        def status = XMLService.PUB_STATUS_STABLE
        def journalDB = Periodico.findByJournalAndTitle(journal.journal,journal.title)
        if(journalDB) status = checkPublicationStatus(journalDB, journal)
        return status
    }

    private static void getJournalTitle(Node basicData, Periodico newJournal) {
        newJournal.title = getAttributeValueFromNode(basicData, "TITULO-DO-ARTIGO")
    }

    private static void getPeriodicTitle(Node articleDetails, Periodico newJournal) {
        newJournal.journal = getAttributeValueFromNode(articleDetails, "TITULO-DO-PERIODICO-OU-REVISTA")
    }

    private static void getJournalNumberOfPages(Node articleDetails, Periodico newJournal) {
        String initialPage = getAttributeValueFromNode(articleDetails, "PAGINA-INICIAL")
        String finalPage = getAttributeValueFromNode(articleDetails, "PAGINA-FINAL")
        newJournal.pages =  initialPage + "-" + finalPage
    }

    private static void getJournalNumber(Node articleDetails, Periodico newJournal) {
        String number = getAttributeValueFromNode(articleDetails, "FASCICULO")
        if (number.isInteger())
            newJournal.number = number.toInteger()
        else
            newJournal.number = 1   //if not parsed successfully, least value possible
    }

    private static void getJournalVolume(Node articleDetails, Periodico newJournal) {
        String volume = getAttributeValueFromNode(articleDetails, "VOLUME")
        if (volume.isInteger())
            newJournal.volume = volume.toInteger()
        else
            newJournal.volume = 1   //if not parsed successfully, least value possible
    }
    //#end

    private static def calculateTotalMissingProperties(propertiesDB, properties){
        def missingPropertiesTotal = propertiesDB
        properties.each{
            if(!missingPropertiesTotal.contains(it)) missingPropertiesTotal += it
        }
        return missingPropertiesTotal
    }

    private static checkPublicationStatus(Publication pubDB, Publication pub){
        def status = XMLService.PUB_STATUS_DUPLICATED

        def missingPropertiesDB = pubDB.properties.findAll{it.key != 'id' && !it.value}.keySet()
        def missingProperties = pub.properties.findAll{it.key != 'id' && !it.value}.keySet()
        if(missingPropertiesDB != missingProperties){
            status = XMLService.PUB_STATUS_TO_UPDATE
        }

        def missingPropertiesTotal = calculateTotalMissingProperties(missingPropertiesDB, missingProperties)
        def detailsPropertiesDB = pubDB.properties.findAll{!(it.key in missingPropertiesTotal)}
        def detailsDB = detailsPropertiesDB.findAll{it.key!='id' && it.key!='publicationDate'}
        def detailsProperties = pub.properties.findAll{!(it.key in missingPropertiesTotal)}
        def details = detailsProperties.findAll{it.key!='id' && it.key!='publicationDate'}
        if(detailsDB != details){
            status = XMLService.PUB_STATUS_CONFLICTED
        }

        def date1 = pubDB.publicationDate?.toCalendar().get(Calendar.YEAR)
        def date2 = pub.publicationDate?.toCalendar().get(Calendar.YEAR)
        if(date1 && date2 && date1!=date2){
            status = XMLService.PUB_STATUS_CONFLICTED
        }

        return status
    }

    static Node parseReceivedFile(MultipartHttpServletRequest request) {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        MultipartFile f = (MultipartFile) mpr.getFile("file");
        File file = new File("xmlimported.xml");
        f.transferTo(file)
        def records = new XmlParser()
        records.parse(file)
    }

    static String getAttributeValueFromNode(Node n, String attribute) {
        n?.attribute attribute
    }

    static Node getNodeFromNode(Node n, String nodeName) {
        for (Node currentNodeChild : n?.children()) {
            if ((currentNodeChild.name() + "").equals((nodeName)))
                return currentNodeChild
        }
    }

    static void fillPublicationDate(Publication publication, Node currentNode, String field) {
        publication.publicationDate = new Date()
        String tryingToParse = getAttributeValueFromNode(currentNode, field)
        if (tryingToParse.isInteger())
            publication.publicationDate.set(year: tryingToParse.toInteger())
    }

    //#if($Orientation)
    static createOrientations(Node xmlFile, Member user) {
        def author = xmlFile.depthFirst().find{it.name() == 'DADOS-GERAIS'}.'@NOME-COMPLETO'
        if(author != user.name) return null

        def orientations = []
        Node completedOrientationNode = xmlFile.depthFirst().find{ it.name() == 'ORIENTACOES-CONCLUIDAS' }
        orientations = createMasterOrientations(orientations, completedOrientationNode, user)
        orientations = createThesisOrientations(orientations, completedOrientationNode, user)
        orientations = createUndergraduateResearch(orientations, completedOrientationNode, user)
        return orientations
    }

    static private createMasterOrientations(orientations, completedOrientationNode, user){
        def masterOrientations = completedOrientationNode?.getAt("ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")
        for(Node orientation: masterOrientations){
            def newOrientation = fillOrientationData(orientation, user, "Mestrado")
            def status = checkOrientationStatus(newOrientation, user)

            if(status && status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newOrientation.properties.findAll{it.key in ["tipo", "orientando", "tituloTese", "anoPublicacao", "instituicao", "curso", "orientador"]}
                orientations += ["obj": obj, "status":status]
            }
        }
        return orientations
    }

    static private createThesisOrientations(orientations, completedOrientationNode, user){
        def thesisOrientations = completedOrientationNode?.getAt("ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")
        for(Node orientation: thesisOrientations){
            def newOrientation = fillOrientationData(orientation, user, "Doutorado")
            def status = checkOrientationStatus(newOrientation, user)

            if(status && status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newOrientation.properties.findAll{it.key in ["tipo", "orientando", "tituloTese", "anoPublicacao", "instituicao", "curso", "orientador"]}
                orientations += ["obj": obj, "status":status]
            }
        }
        return orientations
    }

    static private createUndergraduateResearch(orientations, completedOrientationNode, user){
        def undergraduateResearch = completedOrientationNode?.getAt("OUTRAS-ORIENTACOES-CONCLUIDAS").findAll{
            it.children().get(0).'@NATUREZA' == "INICIACAO_CIENTIFICA"
        }

        for(Node orientation: undergraduateResearch){
            def newOrientation = fillOrientationData(orientation, user, "Iniciação Científica")
            def status = checkOrientationStatus(newOrientation, user)

            if(status && status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newOrientation.properties.findAll{it.key in ["tipo", "orientando", "tituloTese", "anoPublicacao", "instituicao", "curso"]}
                orientations += ["obj": obj, "status":status]
            }
        }
        return orientations
    }

    static checkOrientationStatus(Orientation orientation, Member user){
        if(!orientation) return null
        def status = XMLService.PUB_STATUS_STABLE
        def orientationDB = Orientation.findByOrientadorAndTituloTese(user, orientation.tituloTese)

        if(orientationDB){
            status = XMLService.PUB_STATUS_DUPLICATED

            def missingPropertiesDB = orientationDB.properties.findAll{it.key != 'id' && !it.value}.keySet()
            def missingProperties = orientation.properties.findAll{it.key != 'id' && !it.value}.keySet()
            if(missingPropertiesDB != missingProperties){
                status = XMLService.PUB_STATUS_TO_UPDATE
            }

            def missingPropertiesTotal = calculateTotalMissingProperties(missingPropertiesDB, missingProperties)
            def detailsPropertiesDB = orientationDB.properties.findAll{!(it.key in missingPropertiesTotal)}
            def detailsDB = detailsPropertiesDB.findAll{it.key!='id'}
            def detailsProperties = orientation.properties.findAll{!(it.key in missingPropertiesTotal)}
            def details = detailsProperties.findAll{it.key!='id'}
            if(detailsDB != details){
                status = XMLService.PUB_STATUS_CONFLICTED
            }
        }
        return status
    }

    private static fillOrientationData(Node node, Member user, String type) {
        Orientation newOrientation = new Orientation(tipo:type)
        Node basicData = (Node) (node.children()[0])
        Node specificData = (Node) (node.children()[1])
        newOrientation.tituloTese = getAttributeValueFromNode(basicData, "TITULO")
        String ano = getAttributeValueFromNode(basicData, "ANO")
        newOrientation.anoPublicacao = Integer.parseInt(ano)
        newOrientation.curso = getAttributeValueFromNode(specificData, "NOME-DO-CURSO")
        newOrientation.instituicao = getAttributeValueFromNode(specificData, "NOME-DA-INSTITUICAO")
        newOrientation.orientador = user
        newOrientation.orientando = getAttributeValueFromNode(specificData, "NOME-DO-ORIENTADO")
        return newOrientation
    }
    //#end

    //#if($researchLine)
    static createResearchLines(Node xmlFile, String authorName) {
        def author = xmlFile.depthFirst().find{it.name() == 'DADOS-GERAIS'}.'@NOME-COMPLETO'
        if(author != authorName) return null

        def researchLinesList = []
        def researchAndDevelopment = xmlFile.depthFirst().findAll{ it.name() == 'PESQUISA-E-DESENVOLVIMENTO' }

        for(Node i: researchAndDevelopment){
            def researchLines = i.getAt("LINHA-DE-PESQUISA")
            for(Node j:researchLines){
                def newResearchLine = saveResearchLine(j)
                def status = checkResearchLineStatus(newResearchLine)

                if(status && status!=XMLService.PUB_STATUS_DUPLICATED) {
                    def obj = newResearchLine.properties.findAll{it.key in ["name","description"]}
                    researchLinesList += ["obj": obj, "status":status]
                }
            }
        }
        return researchLinesList
    }

    private static saveResearchLine(Node xmlFile) {
        ResearchLine newResearchLine = new ResearchLine()
        newResearchLine.name = getAttributeValueFromNode(xmlFile, "TITULO-DA-LINHA-DE-PESQUISA")
        newResearchLine.description = getAttributeValueFromNode(xmlFile, "OBJETIVOS-LINHA-DE-PESQUISA")
        return newResearchLine
    }

    static checkResearchLineStatus(ResearchLine researchLine){
        if(!researchLine) return null
        def status = XMLService.PUB_STATUS_STABLE
        def rlDB = ResearchLine.findByName(researchLine.name)

        if(rlDB){
            status = XMLService.PUB_STATUS_DUPLICATED

            def missingPropertiesDB = rlDB.properties.findAll{it.key != 'id' && !it.value}.keySet()
            def missingProperties = researchLine.properties.findAll{it.key != 'id' && !it.value}.keySet()
            if(missingPropertiesDB != missingProperties){
                status = XMLService.PUB_STATUS_TO_UPDATE
            }

            def missingPropertiesTotal = calculateTotalMissingProperties(missingPropertiesDB, missingProperties)
            def detailsPropertiesDB = rlDB.properties.findAll{!(it.key in missingPropertiesTotal)}
            def detailsDB = detailsPropertiesDB.findAll{it.key!='id'}
            def detailsProperties = researchLine.properties.findAll{!(it.key in missingPropertiesTotal)}
            def details = detailsProperties.findAll{it.key!='id'}
            if(detailsDB != details){
                status = XMLService.PUB_STATUS_CONFLICTED
            }
        }
        return status
    }
    //#end

    //#if($researchProject)
    static createResearchProjects(Node xmlFile, String authorName) {
        def author = xmlFile.depthFirst().find{it.name() == 'DADOS-GERAIS'}.'@NOME-COMPLETO'
        if(author != authorName) return null

        def researchProjectsList = []
        def researchProjects = xmlFile.depthFirst().findAll{ it.name() == 'PROJETO-DE-PESQUISA' }

        for(Node project: researchProjects){
            def newProject = saveResearchProject(project)
            def status = checkResearchProjectStatus(newProject)

            if(status && status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newProject.properties.findAll{
                    it.key in ["projectName","description", "status", "responsible", "startYear", "endYear", "members", "funders"]
                }
                researchProjectsList += ["obj": obj, "status":status]
            }
        }
        return researchProjectsList
    }

    static checkResearchProjectStatus(ResearchProject researchProject){
        if(!researchProject) return null
        def status = XMLService.PUB_STATUS_STABLE
        def researchProjectDB = ResearchProject.findByProjectName(researchProject.projectName)

        if(researchProjectDB){
            status = XMLService.PUB_STATUS_DUPLICATED
            def missingPropertiesDB = researchProjectDB.properties.findAll{it.key != 'id' && !it.value}.keySet()
            def missingProperties = researchProject.properties.findAll{it.key != 'id' && !it.value}.keySet()
            if(missingPropertiesDB != missingProperties){
                status = XMLService.PUB_STATUS_TO_UPDATE
            }

            def missingPropertiesTotal = calculateTotalMissingProperties(missingPropertiesDB, missingProperties)
            def detailsPropertiesDB = researchProjectDB.properties.findAll{!(it.key in missingPropertiesTotal)}
            def detailsDB = detailsPropertiesDB.findAll{it.key!='id'}
            def detailsProperties = researchProject.properties.findAll{!(it.key in missingPropertiesTotal)}
            def details = detailsProperties.findAll{it.key!='id'}
            if(detailsDB != details){
                status = XMLService.PUB_STATUS_CONFLICTED
            }
        }

        return status
    }

    private static saveResearchProject(Node xmlFile) {
        ResearchProject newProject = new ResearchProject()
        newProject.projectName = getAttributeValueFromNode(xmlFile, "NOME-DO-PROJETO")
        newProject.description = getAttributeValueFromNode(xmlFile, "DESCRICAO-DO-PROJETO")
        newProject.status = getAttributeValueFromNode(xmlFile, "SITUACAO")
        newProject.startYear = getAttributeValueFromNode(xmlFile, "ANO-INICIO").toInteger()
        newProject.endYear = getAttributeValueFromNode(xmlFile, "ANO-FIM").equals("") ? 0 : getAttributeValueFromNode(xmlFile, "ANO-FIM").toInteger()
        fillProjectMembers(getNodeFromNode(xmlFile, "EQUIPE-DO-PROJETO"), newProject)
        //#if($funder)
        fillFunders(getNodeFromNode(xmlFile, "FINANCIADORES-DO-PROJETO"), newProject)
        //#end
        return newProject
    }

    private static void fillProjectMembers(Node xmlFile, ResearchProject project) {
        for (Node node : xmlFile?.children()) {
            String name = (String) (node.attribute("NOME-COMPLETO"))
            Boolean responsavel = ((String) (node.attribute("FLAG-RESPONSAVEL"))).equals("SIM")
            if (responsavel) project.responsible = name
            project.addToMembers(name)
        }
    }
    //#end

    //#if($funder)
    private static void fillFunders(Node xmlFile, ResearchProject project) {
        for (Node node : xmlFile?.children()) {
            String code = getAttributeValueFromNode(node, "CODIGO-INSTITUICAO")
            Funder funder = Funder.findByCode(code)

            if (funder) {
                project.addToFunders(funder)
            }
            def projectFunders = project.funders?.findAll{it.code = code}
            if(!projectFunders) {
                Funder newFunder = new Funder()
                newFunder.code = code
                newFunder.name = getAttributeValueFromNode(node, "NOME-INSTITUICAO")
                newFunder.nature = getAttributeValueFromNode(node, "NATUREZA")
                project.addToFunders(newFunder)
            }
         }
    }
    //#end

    static void createMember(Node xmlFile, Member newMember) {
        Node dadosGerais = (Node) xmlFile.children()[0]
        List<Object> dadosGeraisChildren = dadosGerais.children()

        Node endereco = (Node) dadosGeraisChildren[2]
        Node enderecoProfissional = (Node) endereco.value()[0]

        newMember.name = getAttributeValueFromNode(dadosGerais, "NOME-COMPLETO")
        newMember.university = getAttributeValueFromNode(enderecoProfissional, "NOME-INSTITUICAO-EMPRESA")
        newMember.phone = getAttributeValueFromNode(enderecoProfissional, "DDD") +
                getAttributeValueFromNode(enderecoProfissional, "TELEFONE")
        newMember.website = getAttributeValueFromNode(enderecoProfissional, "HOME-PAGE")
        newMember.city = getAttributeValueFromNode(enderecoProfissional, "CIDADE")
        newMember.country = getAttributeValueFromNode(enderecoProfissional, "PAIS")
        newMember.email = getAttributeValueFromNode(enderecoProfissional, "E-MAIL")

        newMember.save(flush: false)
    }



    private def extractImportedJournals(name, params, keys){
        def journals = []
        def journalKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!journalKeySet || !keys) return journals

        def n = journalKeySet.size()/keys.size()
        for(i in 0..n-1){
            def journal = [:]
            keys?.each{ k -> //"title", "publicationDate", "authors", "journal", "volume", "number", "pages"
                if(k=="publicationDate") journal.put("$k", extractDate(params,name,i,k))
                else if(k=="au thors") {
                    journal.put("$k", extractAuthors(params,name,i,k))
                }
                else if(k=="volume" || k=="number") journal.put("$k", params[name+i+".$k"] as int)
                else{
                    def value = params[name+i+".$k"]
                    if(value == "null") value = null
                    journal.put("$k", value)
                }
            }
            journals += journal
        }
        return journals
    }

    private def extractImportedPublications(name, params, keys){
        def pubs = []
        def pubKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!pubKeySet || !keys) return pubs

        def n = pubKeySet.size()/keys.size()
        for(i in 0..n-1){
            def pub = [:]
            keys.each{ k -> //"title", "publicationDate" e strings
                if(k=="publicationDate") pub.put("$k", extractDate(params,name,i,k))
                else if(k=="authors") pub.put("$k", extractAuthors(params,name,i,k))
                else{
                    def value = params[name+i+".$k"]
                    if(value == "null") value = null
                    pub.put("$k", value)
                }
            }
            pubs += pub
        }
        return pubs
    }

    //if(ResearchLine)
    private static checkContResearch(List researchLine, int i, String researchName) {
        List<Node> researchs = ((Node) researchLine[i]).children()
        Node basicData = (Node) researchs[0]
        List<String> lista = new ArrayList<String>()

        ResearchLine newResearch = new ResearchLine()

        while (!basicData.children().empty){
            newResearch = getResearchLine(basicData, researchName)
            lista.add(newResearch)
            researchs.add(this)
        }
        return lista
    }

    private static String getResearchLine(Node basicData, ResearchLine researchLine) {
        researchLine.name = getAttributeValueFromNode(basicData, "TITULO-DA-LINHA-DE-PESQUISA")
        return researchLine.name
    }
    //end



   private def extractBooks(name, params, keys){
        def books = []
        def bookKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!bookKeySet || !keys) return books

        def n = bookKeySet.size()/keys.size()
        for(i in 0..n-1){
            def book = [:]
            keys?.each{ k -> //"title", "publicationDate", "authors", "publisher", "volume", "pages"
                if(k=="publicationDate") book.put("$k", extractDate(params,name,i,k))
                else if(k=="authors") book.put("$k", extractAuthors(params,name,i,k))
                else if(k=="volume") book.put("$k", params[name+i+".$k"] as int)
                else{
                    def value = params[name+i+".$k"]
                    if(value == "null") value = null
                    book.put("$k", value)
                }
            }
            books += book
        }
        return books
    }

    private def extractDissertation(name, params, keys){
        def dissertationKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        def dissertation = [:]
        if(dissertationKeySet.isEmpty()) return dissertation

        keys?.each{ k -> //"title", "publicationDate", "authors", "school"
            if(k=="publicationDate") dissertation.put("$k", extractDate(params,name,0,k))
            else if(k=="authors") dissertation.put("$k", extractAuthors(params,name,0,k))
            else{
                def value = params[name+"0.$k"]
                if(value == "null") value = null
                dissertation.put("$k", value)
            }
        }

        return dissertation
    }


    private def extractDate(params,name,i,k){
        def dateString = params[name+i+".$k"]
        if(!dateString || dateString=="null") return null
        def date = new Date()
        def year = dateString?.substring(dateString?.length()-5,dateString?.length()) as int
        date.set(year: year)
        return date
    }

    private def extractAuthors(params,name,i,k){
        def authors = params[name+i+".$k"]
        if(!authors || authors=="null") return null
        def authorsList = authors?.substring(1,authors.length()-1)?.split(",") as List
        def result = []
        authorsList.each{
            if(it.startsWith(" ")){
                result += it.substring(1)
            }
            else result += it
        }
        if(result.isEmpty()) return authorsList
        else return result
    }



    //#if($researchLine)
    private def extractResearchLines(name, params, keys){
        def lines = []
        def linesKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!linesKeySet || !keys) return lines

        def n = linesKeySet.size()/keys.size()
        for(i in 0..n-1){
            def line = [:]
            keys.each{ k -> //"name", "description"
                def value = params[name+i+".$k"]
                if(value == "null") value = null
                line.put("$k", value)
            }
            lines += line
        }
        return lines
    }
    //#end

    //#if($researchProject && $funder)
    private def extractResearchProjects(name, params, keys){
        def projects = []
        def projectKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!projectKeySet || !keys) return projects

        def n = projectKeySet.size()/keys.size()
        for(i in 0..n-1){
            def project = [:]
            keys?.each{ k -> //"projectName","description", "status", "responsible", "startYear", "endYear", "members", "funders"
                if(k=="startYear" || k=="endYear")  project.put("$k", params[name+i+".$k"] as int)
                else if(k=="funders") project.put("$k", extractFunders(params,name,i,k))
                else if(k=="members") project.put("$k", extractAuthors(params,name,i,k))
                else{
                    def value = params[name+i+".$k"]
                    if(value == "null") value = null
                    project.put("$k", value)
                }
            }
            projects += project
        }
        return projects
    }

    private def extractFunders(params,name,i,k){
        String fundersString = params[name+i+".$k"]
        if(!fundersString || fundersString=="null") return []

        fundersString = fundersString.substring(1,fundersString.length()-1)
        def index1 = fundersString.indexOf("]")
        def index2 = fundersString.lastIndexOf("[")
        def code = fundersString.substring(1,index1)
        def funderName = fundersString.substring(index1+2,index2-1)
        def nature = fundersString.substring(index2+1,fundersString.length()-1)
        return [code:code, name:funderName, nature:nature]
    }
    //#end

    //#if(Orientation)
    private def extractOrientations(name, params, keys){
        def orientations = []
        def orientationKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!orientationKeySet || !keys) return orientations

        def n = orientationKeySet.size()/keys.size()
        for(i in 0..n-1){
            def orientation = [:]
            keys?.each{ k -> //"tipo", "orientando", "tituloTese", "anoPublicacao", "instituicao", "curso"
                if(k=="anoPublicacao") orientation.put("$k", params[name+i+".$k"] as int)
                else{
                    def value = params[name+i+".$k"]
                    if(value == "null") value = null
                    orientation.put("$k", value)
                }
            }
            orientations += orientation
        }
        return orientations
    }
    //#end

    def saveImportedPublications(params, user) {
        def flashMessage = 'default.xml.save.message'

        try {
            //#if($Article)
            def journalKeys = ["title", "publicationDate", "authors", "journal", "volume", "number", "pages"]
            def journals = extractImportedJournals("journals", params, journalKeys)
            saveImportedJournals(journals)
            //#end

            def toolKeys = ["title", "publicationDate", "authors", "description"]
            def tools = extractImportedPublications("tools", params, toolKeys)
            saveImportedTools(tools)

            def bookKeys = ["title", "publicationDate", "authors", "publisher", "volume", "pages"]
            def books = extractBooks("books", params, bookKeys)
            saveImportedBooks(books)

            def bookChapterKeys = ["title", "publicationDate", "authors", "publisher"]
            def bookChapters = extractImportedPublications("bookChapters", params, bookChapterKeys)
            saveImportedBookChapters(bookChapters)

            def dissertationKeys = ["title", "publicationDate", "authors", "school"]
            def dissertation = extractDissertation("masterDissertation", params, dissertationKeys)
            if (dissertation) saveImportedDissertation(dissertation)

            def thesis = extractDissertation("thesis", params, dissertationKeys)
            if (thesis) saveImportedThesis(thesis)

            def conferenceKeys = ["title", "publicationDate", "authors", "booktitle", "pages"]
            def conferences = extractImportedPublications("conferences", params, conferenceKeys)
            saveImportedConferences(conferences)

            //#if($researchLine)
            def researchLineKeys = ["name","description"]
            def researchLines = extractResearchLines("researchLines", params, researchLineKeys)
            saveImportedResearchLines(researchLines)
            //#end

            //#if($researchProject)
            def projectKeys = ["projectName","description", "status", "responsible", "startYear", "endYear", "members", "funders"]
            def researchProjects = extractResearchProjects("researchProjects", params, projectKeys)
            saveImportedResearchProjects(researchProjects)
            //#end

            //#if($Orientation)
            def orientationKeys = ["tipo", "orientando", "tituloTese", "anoPublicacao", "instituicao", "curso"]
            def orientations = extractOrientations("orientations", params, orientationKeys)
            saveImportedOrientations(orientations, user)
            //#end

        } catch(Exception ex){
            flashMessage = 'default.xml.saveerror.message'
            ex.printStackTrace()
        }

        return flashMessage
    }

    //#if($Article)
    def saveImportedJournals(journals) {
        journals.eachWithIndex(){ element, index ->
            Periodico p = new Periodico(element)
            p.members = []
            p.save(flush:true)
        }
    }
    //#end

    def saveImportedTools(tools) {
        tools?.eachWithIndex(){ element, index ->
            Ferramenta f = new Ferramenta(element)
            f.members = []
            f.save(flush:true)
        }
    }

    def saveImportedBooks(books) {
        books?.eachWithIndex(){ element, index ->
            Book b = new Book(element)
            b.members = []
            b.save(flush:true)
        }
    }

    def saveImportedBookChapters(bookChapters) {
        bookChapters?.eachWithIndex(){ element, index ->
            BookChapter bc = new BookChapter(element)
            bc.members = []
            bc.save(flush:true)
        }
    }

    def saveImportedDissertation(masterDissertation) {
        Dissertacao d = new Dissertacao(masterDissertation)
        d.members = []
        d.save(flush:true)
    }

    def saveImportedThesis(thesis) {
        Tese t = new Tese(thesis)
        t.members = []
        t.save(flush:true)
    }

    def saveImportedConferences(conferences) {
        conferences?.eachWithIndex(){ element, index ->
            Conferencia c = new Conferencia(element)
            c.members = []
            c.save(flush:true)
        }
    }

    //#if($researchLine)
    def saveImportedResearchLines(researchLines){
        researchLines?.eachWithIndex(){ element, index ->
            ResearchLine rl = new ResearchLine(element)
            rl.members = []
            rl.publications = []
            rl.save(flush:true)
        }
    }
    //#end

    //#if($researchProject)
    def saveImportedResearchProjects(researchProjects){
        researchProjects?.eachWithIndex(){ element, index ->
            //#if($funder)
            saveImportedFunders(element.funders)
            //#end
            def rp = new ResearchProject(element)
            rp.save(flush:true)
        }
    }
    //#end

    //#if($funder)
    def saveImportedFunders(project){
        project?.funders?.each(){ f ->
            f = Funder.findByCode(f.code)
            if(!f) f.save(flush:true)
            project.addToFunders(f).save(flush:true)
        }
    }
    //#end

    //#if($Orientation)
    def saveImportedOrientations(orientations, user){
        orientations?.eachWithIndex(){ element, index ->
            Orientation o = new Orientation(element)
            o.orientador = user
            o.save(flush:true)
        }
    }
    //#end
}
