package rgms

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile
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

    //#if($Article)
    public static final JOURNAL_KEYS = ["title", "publicationDate", "authors", "journal", "volume", "number", "pages"]
    //#end
    public static final TOOL_KEYS = ["title", "publicationDate", "authors", "description"]
    public static final BOOK_KEYS = ["title", "publicationDate", "authors", "publisher", "volume", "pages"]
    public static final BOOK_CHAPTER_KEYS = ["title", "publicationDate", "authors", "publisher"]
    public static final DISSERTATION_KEYS = ["title", "publicationDate", "authors", "school"]
    public static final CONFERENCE_KEYS = ["title", "publicationDate", "authors", "booktitle", "pages"]
    //#if($researchLine)
    public static final RESEARCH_LINE_KEYS = ["name","description"]
    //#end
    //#if($researchProject)
    public static final RESEARCH_PROJECT_KEYS = ["projectName","description", "status", "responsible", "startYear", "endYear", "members", "funders"]
    //#end
    //#if($Orientation)
    public static final ORIENTATION_KEYS = ["tipo", "orientando", "tituloTese", "anoPublicacao", "instituicao", "curso"]
    //#end

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

        def masterDissertation = createDissertation(xmlFile, user.name)
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
            def newTool = createNewTool(currentNode, authorName)
            def result = checkToolStatus(newTool)
            if (result?.status && result?.status != XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newTool.properties.findAll{it.key in XMLService.TOOL_KEYS}
                tools += [obj: obj, status:result.status, id:result.id]
            }
        }

        return tools
    }

    private static createNewTool(Node currentNode, String authorName) {
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
        return [status:status, id:toolDB?.id]
    }

    def createBooks(Node xmlFile, String authorName) {
        def publishedBooks = xmlFile.depthFirst().findAll{ it.name() == 'LIVRO-PUBLICADO-OU-ORGANIZADO' }
        def booksList = []

        int i = 0
        for (Node currentNode : publishedBooks) {
            def newBook = createNewBook(currentNode, i, authorName)
            def result = checkBookStatus(newBook)
            if(result?.status && result?.status != PUB_STATUS_DUPLICATED) {
                def obj = newBook.properties.findAll{it.key in XMLService.BOOK_KEYS}
                booksList += [obj: obj, status:result.status, id:result.id]
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
        return [status:status, id:bookDB?.id]
    }

    def createBooksChapters(Node xmlFile, String authorName) {
        def publishedBookChapters = xmlFile.depthFirst().findAll{ it.name() == 'CAPITULO-DE-LIVRO-PUBLICADO' }
        def bookChaptersList = []

        for (int i = 0; i < publishedBookChapters?.size(); ++i) {
            def newBookChapter = createNewBookChapter(publishedBookChapters, i, authorName)
            def result = checkBookChapterStatus(newBookChapter)
            if(result?.status && result?.status != PUB_STATUS_DUPLICATED) {
                def obj = newBookChapter.properties.findAll{it.key in XMLService.BOOK_CHAPTER_KEYS}
                bookChaptersList += [obj: obj, status:result.status, id:result.id]
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
        return [status:status, id:bookChapterDB?.id]
    }

    private static Publication addAuthors(publication, newPublication) {
        publication.each{
            if(it.name() == "AUTORES"){
                newPublication.addToAuthors(getAttributeValueFromNode(it, "NOME-COMPLETO-DO-AUTOR"))
            }
        }
        return newPublication
    }

    def createDissertation(Node xmlFile, String authorName) {
        def newDissertation = createNewDissertation(xmlFile, authorName)
        if(!newDissertation) return null

        def dissertationDB = Dissertacao.findByTitle(newDissertation?.title)
        if(dissertationDB?.authors != newDissertation?.authors) dissertationDB = null

        def status = checkDissertationOrThesisStatus(dissertationDB, newDissertation)
        if(status == PUB_STATUS_DUPLICATED) return null
        def obj = newDissertation.properties.findAll{it.key in XMLService.DISSERTATION_KEYS}
        return [obj: obj, status:status, id:dissertationDB?.id]
    }

    private static createNewDissertation(Node xmlFile, String authorName){
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
        def newThesis = createNewThesis(xmlFile, authorName)
        if(!newThesis) return null

        def thesisDB = Tese.findByTitle(newThesis?.title)
        if(thesisDB?.authors != newThesis?.authors) thesisDB = null

        def status = checkDissertationOrThesisStatus(thesisDB, newThesis)
        if(status == PUB_STATUS_DUPLICATED) return null

        def obj = newThesis.properties.findAll{it.key in XMLService.DISSERTATION_KEYS}
        return [obj: obj, status:status, id:thesisDB?.id]
    }

    private static createNewThesis(Node xmlFile, String authorName){
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
            def newConference = createNewConferencia(currentNode, authorName)
            def result = checkConferenceStatus(newConference)

            if(result?.status && result?.status != PUB_STATUS_DUPLICATED){
                def obj = newConference.properties.findAll{it.key in XMLService.CONFERENCE_KEYS}
                conferences += [obj: obj, status:result.status, id:result.id]
            }
        }

        return conferences
    }

    private static createNewConferencia(conferenceNode, authorName) {
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
        return [status:status, id:conferenceDB?.id]
    }

    //#if($Article)
    def createJournals(Node xmlFile, String authorName) {
        def publishedArticles = xmlFile.depthFirst().findAll{ it.name() == 'ARTIGO-PUBLICADO' }
        def journals = []

        for (int i = 0; i < publishedArticles?.size(); ++i) {
            def newJournal = createNewJournal(publishedArticles, i, authorName)
            def result = checkJournalStatus(newJournal)

            if(result?.status && result?.status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newJournal.properties.findAll{it.key in XMLService.JOURNAL_KEYS}
                journals += [obj: obj, status:result.status, id:result.id]
            }
        }

        return journals
    }

    private static createNewJournal(List publishedArticlesChildren, int i, String authorName) {
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
        return [status:status, id:journalDB?.id]
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
            def result = checkOrientationStatus(newOrientation, user)

            if(result?.status && result?.status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newOrientation.properties.findAll{it.key in XMLService.ORIENTATION_KEYS}
                orientations += ["obj": obj, status:result.status, id:result.id]
            }
        }
        return orientations
    }

    static private createThesisOrientations(orientations, completedOrientationNode, user){
        def thesisOrientations = completedOrientationNode?.getAt("ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")
        for(Node orientation: thesisOrientations){
            def newOrientation = fillOrientationData(orientation, user, "Doutorado")
            def result = checkOrientationStatus(newOrientation, user)

            if(result?.status && result?.status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newOrientation.properties.findAll{it.key in XMLService.ORIENTATION_KEYS}
                orientations += ["obj": obj, status:result.status, id:result.id]
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
            def result = checkOrientationStatus(newOrientation, user)

            if(result?.status && result?.status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newOrientation.properties.findAll{it.key in XMLService.ORIENTATION_KEYS}
                orientations += [obj: obj, status:result.status, id:result.id]
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
        return [status:status, id:orientationDB?.id]
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
                def newResearchLine = createNewResearchLine(j)
                def result = checkResearchLineStatus(newResearchLine)

                if(result?.status && result?.status!=XMLService.PUB_STATUS_DUPLICATED) {
                    def obj = newResearchLine.properties.findAll{it.key in XMLService.RESEARCH_LINE_KEYS}
                    researchLinesList += [obj: obj, status:result.status, id:result.id]
                }
            }
        }
        return researchLinesList
    }

    private static createNewResearchLine(Node xmlFile) {
        ResearchLine newResearchLine = new ResearchLine()
        newResearchLine.members = []
        newResearchLine.publications = []
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
        return [status:status, id:rlDB?.id]
    }
    //#end

    //#if($researchProject)
    static createResearchProjects(Node xmlFile, String authorName) {
        def author = xmlFile.depthFirst().find{it.name() == 'DADOS-GERAIS'}.'@NOME-COMPLETO'
        if(author != authorName) return null

        def researchProjectsList = []
        def researchProjects = xmlFile.depthFirst().findAll{ it.name() == 'PROJETO-DE-PESQUISA' }

        for(Node project: researchProjects){
            def newProject = createNewResearchProject(project)
            def result = checkResearchProjectStatus(newProject)

            if(result?.status && result?.status!=XMLService.PUB_STATUS_DUPLICATED) {
                def obj = newProject.properties.findAll{ it.key in XMLService.RESEARCH_PROJECT_KEYS }
                researchProjectsList += [obj: obj, status:result.status, id:result.id]
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

        return [status:status, id:researchProjectDB?.id]
    }

    private static createNewResearchProject(Node xmlFile) {
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
    private static fillFunders(Node xmlFile, ResearchProject project) {
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

    static void saveMember(Node xmlFile, Member newMember) {
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

    private static extractDate(params,name,i,k){
        def dateString = params[name+i+".$k"]
        if(!dateString || dateString=="null") return null
        def date = new Date()
        date.set(year: dateString as int)
        return date
    }

    private static extractNamesSet(params,name,i,k){
        def authors = params.keySet()?.findAll{ it.contains(name+i+".$k")}?.sort()
        if(!authors || authors=="null") return null

        def result = [] as Set
        authors.each{ author ->
            result += params[author]
        }

        return result
    }

    private static extractFunders(params,name,i,k){
        def result = []
        def funders = params.keySet()?.findAll{ it.contains(name+i+".$k")}?.sort()
        if(!funders || funders=="null") return result

        def funderKeys = ["name", "code", "nature"]
        def n = funders.size()/funderKeys.size()
        for(j in 0..n-1) {
            def funder = new Funder()
            funderKeys.each{ fk ->
                def value = params[name+i+".$k"+j+".$fk"]
                if(value == "null") value = null
                funder."$fk" = value
            }
            result += funder
        }
        return result
    }

    private static calcImportedPublicationSize(importedKeys, keys){
        def authors = importedKeys.findAll{ it.contains("authors")}
        def authorsTotal = (authors) ? authors.size() : 0
        def n = (importedKeys.size()-authorsTotal)/(keys.size()-1)
    }

    private static getObject(name, properties){
        switch(name){
            case "journals":
                if(properties) return new Periodico(properties)
                else return new Periodico()
            case "tools":
                if(properties) return new Ferramenta(properties)
                else return new Ferramenta()
            case "books":
                if(properties) return new Book(properties)
                else return new Book()
            case "bookChapters":
                if(properties) return new BookChapter(properties)
                else return new BookChapter()
            case "conferences":
                if(properties) return new Conferencia(properties)
                else return new Conferencia()
            case "dissertation":
                if(properties) return new Dissertacao(properties)
                else return new Dissertacao()
            case "thesis":
                if(properties) return new Tese(properties)
                else return new Tese()
            //#if($researchLine)
            case "researchLines":
                if(properties) return new ResearchLine(properties)
                else return new ResearchLine()
            //#end
            //#if($researchProject)
            case "researchProjects":
                def project = new ResearchProject()
                if(properties){
                    project.properties = properties
                    return project
                }
                else return project
            //#end
            //#if($funder)
            case "funders":
                if(properties) return new Funder(properties)
                else return new Funder()
            //#end
            //#if($Orientation)
            case "orientations":
                if(properties) return new Orientation(properties)
                else return new Orientation()
            //#end
            default: return null
        }
    }

    //esse método deveria funcionar com ResearchProject, mas não funciona
    private static fullImportedPublication(keys, params, name, i){
        def pub = getObject(name, null)
        keys.each{ k ->
            switch (k){
                case "publicationDate": pub."$k" = XMLService.extractDate(params,name,i,k)
                    break
                case "authors": pub."$k" = XMLService.extractNamesSet(params,name,i,k)
                    break
                case "id":
                case "volume":
                case "number":
                case "startYear":
                case "endYear": pub."$k" = params[name+i+".$k"] as int
                    break
                //#if($researchProject)
                case "members": pub."$k" = XMLService.extractNamesSet(params,name,i,k)
                    break
                //#end
                //#if($funder)
                case "funders":
                    def funders = XMLService.extractFunders(params,name,i,k)
                    saveImportedFunders(funders, pub)
                    break
                //#end
                //#if($Orientation)
                case "anoPublicacao": pub."$k" = params[name+i+".$k"] as int
                    break
                //#end
                default: def value = params[name+i+".$k"]
                    if(value == "null") value = null
                    pub."$k" =  value
            }
        }
        return pub
    }

    private def extractImportedPublications(name, params, keys){
        def pubs = []
        def pubKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!pubKeySet || !keys) return pubs

        def n = XMLService.calcImportedPublicationSize(pubKeySet, keys)
        for(i in 0..n-1){
            String title = params[name+i+".title"]
            if(!title || title.isEmpty()) continue
            def pub = XMLService.fullImportedPublication(keys, params, name, i)
            pubs += pub
        }
        return pubs
    }

    private def extractDissertation(obj, name, params, keys){
        def dissertationKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(dissertationKeySet.isEmpty()) return null
        obj.title = params[name+"0.title"]
        if(!obj.title || obj.title.isEmpty()) return null

        keys?.each{ k -> //"title", "publicationDate", "authors", "school"
            switch (k){
                case "publicationDate": obj."$k" = XMLService.extractDate(params,name,0,k)
                    break
                case "authors": obj."$k" = XMLService.extractNamesSet(params,name,0,k)
                    break
                case "id": obj."$k" = params[name+"0.$k"] as int
                    break
                default:
                    def value = params[name+"0.$k"]
                    if(value == "null") value = null
                    obj."$k" = value
            }
        }

        return obj
    }

    //#if($researchLine)
    private def extractResearchLines(name, params, keys){
        def lines = []
        def linesKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!linesKeySet || !keys) return lines

        def n = linesKeySet.size()/keys.size()
        for(i in 0..n-1){
            String title = params[name+i+".name"]
            if(!title || title.isEmpty()) continue
            def pub = XMLService.fullImportedPublication(keys, params, name, i)
            pub.publications = []
            lines += pub
        }
        return lines
    }
    //#end

    //#if($researchProject)
    private def extractResearchProjects(name, params, keys){
        def projects = []
        def projectKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!projectKeySet || !keys) return projects

        def n = XMLService.calcImportedResearchProjects(projectKeySet,keys)
        for(i in 0..n-1){
            String title = params[name+i+".projectName"]
            if(!title || title.isEmpty()) continue

            def pub = new ResearchProject()
            keys.each{ k -> //"projectName","description", "status", "responsible", "startYear", "endYear", "members", "funders"
                switch (k){
                    case "id":
                    case "startYear":
                    case "endYear": pub."$k" = params[name+i+".$k"] as int
                        break
                    //#if($researchProject)
                    case "members": pub."$k" = XMLService.extractNamesSet(params,name,i,k)
                        break
                    //#end
                    //#if($funder)
                    case "funders":
                        def funders = XMLService.extractFunders(params,name,i,k)
                        saveImportedFunders(funders, pub)
                        break
                    //#end
                    default: def value = params[name+i+".$k"]
                        if(value == "null") value = null
                        pub."$k" =  value
                }
            }
            projects += pub
        }
        return projects
    }

    private static calcImportedResearchProjects(importedKeys, keys){
        def funders = importedKeys.findAll{ it.contains("funders")}
        def fundersTotal = (funders) ? funders.size() : 0
        def members = importedKeys.findAll{ it.contains("members")}
        def membersTotal = (members) ? members.size() : 0
        def n = (importedKeys.size()-fundersTotal-membersTotal)/(keys.size()-2)
    }
    //#end

    //#if(Orientation)
    private def extractOrientations(name, params, keys, user){
        def orientations = []
        def orientationKeySet = params.keySet()?.findAll{ it.contains(name) && it.contains(".")}

        if(!orientationKeySet || !keys) return orientations

        def n = orientationKeySet.size()/keys.size()
        for(i in 0..n-1){
            String title = params[name+i+".tituloTese"]
            if(!title || title.isEmpty()) continue
            def orientation = XMLService.fullImportedPublication(keys, params, name, i)
            orientation.orientador = user
            orientations += orientation
        }
        return orientations
    }
    //#end

    def saveImportedPublications(params, user) {
        //#if($Article)
        def journals = extractImportedPublications("journals", params, XMLService.JOURNAL_KEYS)
        println "total de journals importados = "+journals.size()
        save(journals)
        println "salvou journals!"
        //#end

        def tools = extractImportedPublications("tools", params, TOOL_KEYS)
        println "total de tools importados = "+tools.size()
        save(tools)
        println "salvou tools!"

        def books = extractImportedPublications("books", params, BOOK_KEYS)
        println "total de books importados = "+books.size()
        save(books)
        println "salvou books!"

        def bookChapters = extractImportedPublications("bookChapters", params, BOOK_CHAPTER_KEYS)
        println "total de bookChapters importados = "+bookChapters.size()
        save(bookChapters)
        println "salvou bookChapters!"

        def dissertation = extractDissertation(new Dissertacao(), "masterDissertation", params, DISSERTATION_KEYS)
        if (dissertation) save([dissertation])
        println "salvou dissertation!"

        def thesis = extractDissertation(new Tese(), "thesis", params, DISSERTATION_KEYS)
        if (thesis) save([thesis])
        println "salvou thesis!"

        def conferences = extractImportedPublications("conferences", params, CONFERENCE_KEYS)
        println "total de conferences importados = "+conferences.size()
        save(conferences)
        println "salvou conferences!"

        //#if($researchLine)
        def researchLines = extractResearchLines("researchLines", params, RESEARCH_LINE_KEYS)
        println "total de researchLines importados = "+researchLines.size()
        save(researchLines)
        println "salvou researchLines!"
        //#end

        //#if($researchProject)
        def researchProjects = extractResearchProjects("researchProjects", params, RESEARCH_PROJECT_KEYS)
        println "total de researchProjects importados = "+researchProjects.size()
        saveImportedResearchProjects(researchProjects)
        println "salvou researchProjects!"
        //#end

        //#if($Orientation)
        def orientations = extractOrientations("orientations", params, ORIENTATION_KEYS, user)
        println "total de orientations importados = "+orientations.size()
        saveImportedOrientations(orientations)
        println "salvou orientations!"
        //#end

        return 'default.xml.save.message'
    }

    def save(publications) {
        publications?.each{ pub ->
            if(!pub?.members) pub?.members = []
            if(pub.id){
                def originalPub = Publication.get(pub.id)
                originalPub.properties = pub.properties
                originalPub.save(flush:true)
            }
            else{
                pub.save(flush:true)
            }
        }
    }

    def saveImportedPubsOfType(pubs, type) {
        def objects = []
        pubs?.each{ p ->
            def obj = getObject(type, p)
            objects += obj
        }
        save(objects)
        return objects
    }

    //#if($researchProject)
    def saveImportedResearchProjects(researchProjects){
        researchProjects?.each { rp ->
            if(rp.id){
                def originalRP = ResearchProject.get(rp.id)
                originalRP.properties = rp.properties
                originalRP.save(flush:true)
            }
            else{
                rp.save(flush:true)
            }
        }
    }
    //#end

    //#if($funder)
    public def saveImportedFunders(funders, ResearchProject project){
        funders?.each(){ f ->
            Funder funder = Funder.findByCode(f.code)
            if (funder) {
                project.addToFunders(funder)
            } else {
                f.save(flush:true)
                project.addToFunders(f)
            }
        }
    }
    //#end

    //#if($Orientation)
    def saveImportedOrientations(orientations){
        orientations?.each{ orientation ->
            if(orientation.id){
                def originalOrientation = Orientation.get(orientation.id)
                originalOrientation.properties = orientation.properties
                originalOrientation.save(flush:true)
            }
            else{
                orientation.save(flush:true)
            }
        }
    }
    //#end

    //#if($researchLine)
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
}
