package rgms

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import rgms.member.Member
import rgms.member.Orientation
import rgms.publication.*
import rgms.researchProject.Funder
import rgms.researchProject.ResearchProject

class XMLService {

    /*
        saveEntity - closure que salva a classe de domínio que está usando a importação
     */

    static boolean Import(Closure saveEntity, Closure returnWithMessage,
                          String flashMessage, String controller,
                          javax.servlet.http.HttpServletRequest request) {
        boolean errorFound = false

        try {
            Node xmlFile = parseReceivedFile(request)
            saveEntity(xmlFile)
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
        catch (Exception) {
            flashMessage = 'default.xml.unknownerror.message'
            errorFound = true
        }

        returnWithMessage(flashMessage, controller)

        return !errorFound
    }

    static void createPublications(Node xmlFile, Member user) {
        createFerramentas(xmlFile)
        createBooksChapters(xmlFile)
        createDissertations(xmlFile)
        createConferencias(xmlFile)
        createOrientations(xmlFile, user)
        createJournals(xmlFile)
        //#if($researchLine)
        createResearchLines(xmlFile)
        //#end
        //#if($researchProject)
        createResearchProjects(xmlFile)
        //#end
        println "All imports done!"
    }


    static void createFerramentas(Node xmlFile) {
        Node producaoTecnica = (Node) xmlFile.children()[2]

        for (Node currentNode : producaoTecnica.children()) {
            if (currentNode.name().equals("SOFTWARE"))
                saveNewFerramenta(currentNode)
        }
    }

    private static void saveNewFerramenta(Node currentNode) {
        Node dadosBasicos = (Node) currentNode.children()[0]
        Node detalhamentoDoSoftware = (Node) currentNode.children()[1]
        Node informacoesAdicionais = getNodeFromNode(currentNode, "INFORMACOES-ADICIONAIS")

        Ferramenta newTool = new Ferramenta()

        fillPublicationDate(newTool, dadosBasicos, "ANO")
        newTool.file = 'no File'
        newTool.website = 'no Website'
        newTool.title = getAttributeValueFromNode(dadosBasicos, "TITULO-DO-SOFTWARE")

        String descricao = getAttributeValueFromNode(informacoesAdicionais, "DESCRICAO-INFORMACOES-ADICIONAIS")
        newTool.description = "País: " + getAttributeValueFromNode(dadosBasicos, "PAIS") + ", Ambiente: " + getAttributeValueFromNode(detalhamentoDoSoftware, "AMBIENTE") + (descricao.equals("") ? "" : ", Informacoes adicionais: " + descricao)
        newTool.save(flush: false)
    }

    static void createBooks(Node xmlFile) {
        Node books = (Node) ((Node) ((Node) xmlFile.children()[1]).children()[2]).children()[0]
        List<Object> bookChildren = books.children()

        int i = 0

        for (Node currentNode : bookChildren) {
            List<Object> book = currentNode.children()
            Node dadosBasicos = (Node) book[0]
            Node detalhamentoLivro = (Node) book[1]

            Book newBook = new Book()

            for (int j = 2; j < book.size() - 2; ++j) {
                newBook.addToAuthors(getAttributeValueFromNode(book[j], "NOME-COMPLETO-DO-AUTOR"))
            }

            createNewBook(newBook, dadosBasicos, detalhamentoLivro, i)
            ++i
        }
    }

    private static void createNewBook(Book newBook, Node dadosBasicos, Node detalhamentoLivro, int i) {

        newBook.title = getAttributeValueFromNode(dadosBasicos, "TITULO-DO-LIVRO")
        newBook.publisher = getAttributeValueFromNode(detalhamentoLivro, "NOME-DA-EDITORA")

        if (Publication.findByTitle(newBook.title) == null) {
            fillPublicationDate(newBook, dadosBasicos, "ANO")

            newBook.file = 'emptyfile' + i.toString()
            newBook.pages = getAttributeValueFromNode(detalhamentoLivro, "NUMERO-DE-PAGINAS")
            newBook.volume = getAttributeValueFromNode(detalhamentoLivro, "NUMERO-DE-VOLUMES").toInteger()
            newBook.save(flush: false)
        }
    }

    //#if($researchLine)
    static void createResearchLines(Node xmlFile) {
        //Nesse ponto eu já estou com a lista de Atuacoes Profissionais do XML
        List<Node> pro_perf = ((Node) ((Node) xmlFile.children()[0]).children()[4]).children()
        //Navega ate atuacoes profissionais e extrai a lista de atuacoes

        for (Node i : pro_perf) { //Atuaçao profissional
            for (Node j : i.children()) { //Atividades de pesquisa e desenvolvimento
                if (((String) j.name()).equals("ATIVIDADES-DE-PESQUISA-E-DESENVOLVIMENTO")) {
                    for (Node k : j.children()) { //Pesquisa e desenvolvimento
                        if (((String) k.name()).equals("PESQUISA-E-DESENVOLVIMENTO")) {
                            for (Node l : k.children()) { //Linha de pesquisa
                                saveResearchLine(l)
                            }
                        }
                    }
                }
            }
        }
    }
    //#end

    //#if($researchLine)
    private static void saveResearchLine(Node xmlFile) {
        ResearchLine newResearchLine = new ResearchLine()
        newResearchLine.name = getAttributeValueFromNode(xmlFile, "TITULO-DA-LINHA-DE-PESQUISA")
        newResearchLine.description = getAttributeValueFromNode(xmlFile, "OBJETIVOS-LINHA-DE-PESQUISA")
        newResearchLine.save(flush: false)
    }
    //#end

    //#if($researchProject)
    static void createResearchProjects(Node xmlFile) {
        //Nesse ponto eu já estou com a lista de Atuacoes Profissionais do XML
        List<Node> pro_perf = ((Node) ((Node) xmlFile.children()[0]).children()[4]).children()
        //Navega ate atuacoes profissionais e extrai a lista de atuacoes

        for (Node i : pro_perf) { //Atuaçao profissional
            for (Node j : i.children()) { //Atividades de pesquisa em projeto
                if (((String) j.name()).equals("ATIVIDADES-DE-PARTICIPACAO-EM-PROJETO")) {
                    for (Node k : j.children()) { //Participacao em projeto
                        if (((String) k.name()).equals("PARTICIPACAO-EM-PROJETO")) {
                            for (Node l : k.children()) { //Projeto de pesquisa
                                saveResearchProject(l)
                            }
                        }
                    }
                }
            }
        }
    }
    //#end

    //#if($researchProject)
    private static void saveResearchProject(Node xmlFile) {
        String name = getAttributeValueFromNode(xmlFile, "NOME-DO-PROJETO")
        ResearchProject project = ResearchProject.findByProjectName(name)

        if (project == null) { //Se o projeto de pesquisa ainda nao existe no sistema hora de adiciona-lo
            ResearchProject newProject = new ResearchProject()
            newProject.projectName = name
            newProject.description = getAttributeValueFromNode(xmlFile, "DESCRICAO-DO-PROJETO")
            newProject.status = getAttributeValueFromNode(xmlFile, "SITUACAO")
            newProject.startYear = getAttributeValueFromNode(xmlFile, "ANO-INICIO").toInteger()
            newProject.endYear = getAttributeValueFromNode(xmlFile, "ANO-FIM").equals("") ? 0 : getAttributeValueFromNode(xmlFile, "ANO-FIM").toInteger()
            fillProjectMembers(getNodeFromNode(xmlFile, "EQUIPE-DO-PROJETO"), newProject)
            fillFunders(getNodeFromNode(xmlFile, "FINANCIADORES-DO-PROJETO"), newProject)
            newProject.save(flush: false)
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
            } else {
                Funder newFunder = new Funder()
                newFunder.code = code
                newFunder.name = getAttributeValueFromNode(node, "NOME-INSTITUICAO")
                newFunder.nature = getAttributeValueFromNode(node, "NATUREZA")
                newFunder.save(flush: false)
                project.addToFunders(newFunder).save(flush: false)
            }
        }
    }
    //#end

    //#if($researchProject)
    private static void fillProjectMembers(Node xmlFile, ResearchProject project) {

        for (Node node : xmlFile?.children()) { //Para cada integrante presente no projeto
            String name = (String) (node.attribute("NOME-COMPLETO"))
            Boolean responsavel = ((String) (node.attribute("FLAG-RESPONSAVEL"))).equals("SIM")
            if (responsavel) project.responsible = name
            project.addToMembers(name).save(validate: true)
        }
    }
    //#end

    static void createBooksChapters(Node xmlFile) {
        Node bookChapters = (Node) ((Node) ((Node) xmlFile.children()[1]).children()[2]).children()[1]
        List<Object> bookChaptersChildren = bookChapters.children()

        for (int i = 0; i < bookChaptersChildren.size(); ++i) {
            List<Object> bookChapter = ((Node) bookChaptersChildren[i]).children()
            Node dadosBasicos = (Node) bookChapter[0]
            Node detalhamentoCapitulo = (Node) bookChapter[1]

            BookChapter newBookChapter = new BookChapter()

            newBookChapter = (BookChapter) addAuthors(bookChapter, newBookChapter)

            createNewBookChapter(newBookChapter, dadosBasicos, detalhamentoCapitulo, i)
        }
    }

    private
    static void createNewBookChapter(BookChapter newBookChapter, Node dadosBasicos, Node detalhamentoCapitulo, int i) {

        newBookChapter.title = getAttributeValueFromNode(dadosBasicos, "TITULO-DO-CAPITULO-DO-LIVRO")
        newBookChapter.publisher = getAttributeValueFromNode(detalhamentoCapitulo, "NOME-DA-EDITORA")

        if (Publication.findByTitle(newBookChapter.title) == null)
            fillBookChapterInfo(newBookChapter, dadosBasicos, i)
    }

    private static Publication addAuthors(publication, newPublication) {

        for (int j = 2; j < publication.size() - 4; ++j) {
            newPublication.addToAuthors(getAttributeValueFromNode(publication[j], "NOME-COMPLETO-DO-AUTOR"))
        }

        return newPublication
    }

    private static void fillBookChapterInfo(BookChapter newBookChapter, Node dadosBasicos, int i) {
        fillPublicationDate(newBookChapter, dadosBasicos, "ANO")

        newBookChapter.file = 'emptyfile' + i.toString()
        newBookChapter.chapter = 2
        newBookChapter.save(flush: false)
    }

    static void createDissertations(Node xmlFile) {
        Node dadosGerais = (Node) xmlFile.children()[0]

        Node formacaoAcademica = getNodeFromNode(dadosGerais, "FORMACAO-ACADEMICA-TITULACAO")
        Node mestrado = (Node) formacaoAcademica.children()[1]
        Node doutorado = (Node) formacaoAcademica.children()[2]

        createDissertation(mestrado)
        createDissertation(doutorado)
    }

    private static void createDissertation(Node xmlNode) {
        Dissertacao newDissertation = new Dissertacao()
        newDissertation.title = getAttributeValueFromNode(xmlNode, "TITULO-DA-DISSERTACAO-TESE")

        fillPublicationDate(newDissertation, xmlNode, "ANO-DE-OBTENCAO-DO-TITULO")
        newDissertation.school = getAttributeValueFromNode(xmlNode, "NOME-INSTITUICAO")
        newDissertation.file = 'no File'
        newDissertation.address = 'no Address'
        newDissertation.save(flush: false)
    }

    static void createConferencias(Node xmlFile) {
        Node trabalhosEmEventos = (Node) ((Node) xmlFile.children()[1]).children()[0]

        for (Node currentNode : trabalhosEmEventos.children()) {
            List<Node> nodeConferencia = currentNode.children()
            saveNewConferencia(nodeConferencia);
        }
    }

    private static void saveNewConferencia(List<Node> nodeConferencia) {
        Node dadosBasicos = (Node) nodeConferencia[0]
        Node detalhamento = (Node) nodeConferencia[1]
        String nomeEvento = ""
        if (((String) detalhamento.name()).equals("DETALHAMENTO-DO-TRABALHO"))
            nomeEvento = getAttributeValueFromNode(detalhamento, "NOME-DO-EVENTO")

        if (nomeEvento.contains("onferenc")) {
            Conferencia novaConferencia = new Conferencia()
            novaConferencia.title = nomeEvento

            if (Publication.findByTitle(novaConferencia.title) == null) {
                fillPublicationDate(novaConferencia, dadosBasicos, "ANO-DO-TRABALHO")

                String tryingToParse = getAttributeValueFromNode(dadosBasicos, "TITULO-DO-TRABALHO")
                novaConferencia.booktitle = tryingToParse;
                tryingToParse = getAttributeValueFromNode(detalhamento, "PAGINA-INICIAL")
                String tryingToParse2 = getAttributeValueFromNode(detalhamento, "PAGINA-FINAL")
                novaConferencia.pages = tryingToParse + " - " + tryingToParse2
                novaConferencia.file = 'emptyfile'
                novaConferencia.save(flush: false)
            }
        }
    }

    static void createOrientations(Node xmlFile, Member user) {
        List<Object> completedOrientations = findCompletedOrientations(xmlFile)

        if (!XMLService.isNullOrEmpty(completedOrientations))
            for (int i = 0; i < completedOrientations.size(); i++)
                saveNewOrientation(completedOrientations, i, user)
    }

    private static void saveNewOrientation(List<Object> completedOrientations, int i, Member user) {
        Node node = (Node) completedOrientations[i]
        Orientation newOrientation = new Orientation()
        String name = (String) node.name()

        fillNewOrientation(name, node, newOrientation, user)
        saveOrientation(newOrientation)
    }

    private static void fillNewOrientation(String name, Node node, Orientation newOrientation, Member user) {
        if (name.toLowerCase().contains("mestrado")) {
            fillOrientationData(node, newOrientation, user, "Mestrado")
        } else if (name.toLowerCase().contains("doutorado")) {
            fillOrientationData(node, newOrientation, user, "Doutorado")
        } else {
            Node children = (Node) (node.children()[0])
            String natureza = (String) children.attribute("NATUREZA")

            if (isUndergraduateResearch(natureza)) {
                fillOrientationData(node, newOrientation, user, "Iniciação Científica")
            }
        }
    }

    //Only saves if the orientation does not already exist
    private static void saveOrientation(Orientation newOrientation) {
        if (Orientation.findAll().find { it -> newOrientation.equals(it) } == null)
            newOrientation.save(flush: false)
    }

    private static boolean isUndergraduateResearch(String natureza) {
        natureza.toLowerCase().contains("iniciacao_cientifica")
    }

    private static boolean isNullOrEmpty(List<Object> completedOrientations) {
        completedOrientations == null || completedOrientations.size() == 0
    }

    private static List<Object> findCompletedOrientations(Node xmlFile) {
        def orientations = (Node) xmlFile.children()[3]
        def completedOrientations = (Node) orientations.children()[0]
        List<Object> values = completedOrientations.children()
        values
    }

    private static void fillOrientationData(Node node, Orientation newOrientation, Member user, String tipoOrientacao) {
        Node basicData = (Node) (node.children()[0])
        Node specificData = (Node) (node.children()[1])
        newOrientation.tipo = tipoOrientacao
        newOrientation.tituloTese = getAttributeValueFromNode(basicData, "TITULO")
        String ano = getAttributeValueFromNode(basicData, "ANO")
        newOrientation.anoPublicacao = Integer.parseInt(ano)
        newOrientation.curso = getAttributeValueFromNode(specificData, "NOME-DO-CURSO")
        newOrientation.instituicao = getAttributeValueFromNode(specificData, "NOME-DA-INSTITUICAO")
        newOrientation.orientador = user
        newOrientation.orientando = getAttributeValueFromNode(specificData, "NOME-DO-ORIENTADO")
    }

    //#if($Article)
    static void createJournals(Node xmlFile) {
        Node artigosPublicados = (Node) ((Node) xmlFile.children()[1]).children()[1]
        List<Object> artigosPublicadosChildren = artigosPublicados.children()

        for (int i = 0; i < artigosPublicadosChildren.size(); ++i)
            saveNewJournal(artigosPublicadosChildren, i)
    }

    private static void saveNewJournal(List artigosPublicadosChildren, int i) {
        List<Object> firstArticle = ((Node) artigosPublicadosChildren[i]).children()
        Node dadosBasicos = (Node) firstArticle[0]
        Node detalhamentoArtigo = (Node) firstArticle[1]
        Periodico newJournal = new Periodico()
        getJournalTitle(dadosBasicos, newJournal)

        newJournal = (Periodico) addAuthors(firstArticle, newJournal)

        if (Publication.findByTitle(newJournal.title) == null) {
            fillPublicationDate(newJournal, dadosBasicos, "ANO-DO-ARTIGO")
            getJournalVolume(detalhamentoArtigo, newJournal)
            getJournalNumber(detalhamentoArtigo, newJournal)
            getJournalNumberOfPages(detalhamentoArtigo, newJournal)
            getPeriodicTitle(detalhamentoArtigo, newJournal)
            newJournal.file = 'emptyfile' + i.toString() //files are not available on lattes
            newJournal.save(flush: false)
        }
    }

    private static void getJournalTitle(Node dadosBasicos, Periodico newJournal) {
        newJournal.title = getAttributeValueFromNode(dadosBasicos, "TITULO-DO-ARTIGO")
    }

    private static void getPeriodicTitle(Node detalhamentoArtigo, Periodico newJournal) {
        newJournal.journal = getAttributeValueFromNode(detalhamentoArtigo, "TITULO-DO-PERIODICO-OU-REVISTA")
    }

    private static void getJournalNumberOfPages(Node detalhamentoArtigo, Periodico newJournal) {
        String tryingToParse = getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-FINAL")
        String tryingToParse2 = getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-INICIAL")
        if (tryingToParse.isInteger() && tryingToParse2.isInteger())
            newJournal.pages = tryingToParse.toInteger() - tryingToParse2.toInteger() + 1
        else
            newJournal.pages = 1    //if not parsed successfully, least value possible
    }

    private static void getJournalNumber(Node detalhamentoArtigo, Periodico newJournal) {
        String tryingToParse = getAttributeValueFromNode(detalhamentoArtigo, "FASCICULO")
        if (tryingToParse.isInteger())
            newJournal.number = tryingToParse.toInteger()
        else
            newJournal.number = 1   //if not parsed successfully, least value possible
    }

    private static void getJournalVolume(Node detalhamentoArtigo, Periodico newJournal) {
        String tryingToParse = getAttributeValueFromNode(detalhamentoArtigo, "VOLUME")
        if (tryingToParse.isInteger())
            newJournal.volume = tryingToParse.toInteger()
        else
            newJournal.volume = 1   //if not parsed successfully, least value possible
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

    static Node parseReceivedFile(MultipartHttpServletRequest request) {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("file");
        File file = new File("xmlimported.xml");
        f.transferTo(file)
        def records = new XmlParser()

        if (file.length() > 0)
            records.parse(file)
    }

    static String getAttributeValueFromNode(Node n, String attribute) {
        n.attribute attribute
    }

    static Node getNodeFromNode(Node n, String nodeName) {
        for (Node currentNodeChild : n.children()) {
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

}
