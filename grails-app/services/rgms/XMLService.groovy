package rgms

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

import rgms.member.Member
import rgms.member.Orientation
import rgms.publication.Periodico
import rgms.publication.Publication

class XMLService {
    //Métodos públicos
    void importOrientations(Node xmlNode, String loggedUser)
    {
        Member user = Member.findByUsername(loggedUser)
        def orientations = (Node)xmlNode.children()[3]
        def concludedOrientations = (Node)orientations.children()[0]
        List<Object> values = concludedOrientations.children()
        Node node
        Orientation newOrientation
        String name
        Node children
        String natureza
        if(values != null && values.size() > 0)
        {
            for(int i = 0; i < values.size(); i++)
            {
                node = (Node)values[i]
                newOrientation = new Orientation()
                name = (String)node.name()
                if (name.toLowerCase().contains("mestrado"))
                {
                    fillOrientationData(node, newOrientation, user, "Mestrado")
                }
                else if (name.toLowerCase().contains("doutorado"))
                {
                    fillOrientationData(node, newOrientation, user, "Doutorado")
                }
                else
                {
                    children = (Node)(node.children()[0])
                    natureza = (String)children.attribute("NATUREZA")
                    if (natureza.toLowerCase().contains("iniciacao_cientifica"))
                    {
                        fillOrientationData(node, newOrientation, user, "Iniciação Científica")
                    }
                }

                newOrientation.save(flush: false)
            }
        }
    }

    void saveJournals(Node xmlFile) {
        Node artigosPublicados = (Node) ((Node)xmlFile.children()[1]).children()[1]
        List<Object> artigosPublicadosChildren = artigosPublicados.children()
        String tryingToParse
        Node dadosBasicos
        Node detalhamentoArtigo
        Periodico newJournal
        List<Object> firstArticle
        String tryingToParse2

        for (int i = 0; i < artigosPublicadosChildren.size(); ++i)
        {
            firstArticle = ((Node)artigosPublicadosChildren[i]).children()
            dadosBasicos = (Node) firstArticle[0]
            detalhamentoArtigo = (Node) firstArticle[1]
            newJournal = new Periodico()
            newJournal.title = getAttributeValueFromNode(dadosBasicos, "TITULO-DO-ARTIGO")

            if (Publication.findByTitle(newJournal.title) == null)
            {
                newJournal.publicationDate = new Date()

                tryingToParse = getAttributeValueFromNode(dadosBasicos, "ANO-DO-ARTIGO")
                if (tryingToParse.isInteger())
                    newJournal.publicationDate.set(year: tryingToParse.toInteger()- 1900)

                tryingToParse = getAttributeValueFromNode(detalhamentoArtigo, "VOLUME")
                if (tryingToParse.isInteger())
                    newJournal.volume = tryingToParse.toInteger()
                else
                    newJournal.volume = 1

                tryingToParse = getAttributeValueFromNode(detalhamentoArtigo, "FASCICULO")
                if (tryingToParse.isInteger())
                    newJournal.number = tryingToParse.toInteger()
                else
                    newJournal.number = 1

                tryingToParse =  getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-FINAL")
                tryingToParse2 = getAttributeValueFromNode(detalhamentoArtigo, "PAGINA-INICIAL")
                if (tryingToParse.isInteger() && tryingToParse2.isInteger())
                    newJournal.pages = tryingToParse.toInteger() - tryingToParse2.toInteger() + 1
                else
                    newJournal.pages = 1

                newJournal.journal = getAttributeValueFromNode(detalhamentoArtigo, "TITULO-DO-PERIODICO-OU-REVISTA")
                newJournal.file = 'emptyfile' + i.toString()

                newJournal.save(flush: false)
            }
        }
    }

    void fillMemberInfo(Node xmlFile, Member newMember)
    {
        Node dadosGerais = (Node) xmlFile.children()[0]
        List<Object> dadosGeraisChildren = dadosGerais.children()
        Node outrasInformacoes = (Node) dadosGeraisChildren[1]
        Node endereco = (Node) dadosGeraisChildren[2]
        Node enderecoProfissional = (Node) endereco.value()[0]
        Node formacaoAcademicaTitulacao = (Node) dadosGeraisChildren[3]

        newMember.name = getAttributeValueFromNode(dadosGerais, "NOME-COMPLETO")
        newMember.university = getAttributeValueFromNode(enderecoProfissional, "NOME-INSTITUICAO-EMPRESA")
        newMember.phone = getAttributeValueFromNode(enderecoProfissional, "DDD") +
                getAttributeValueFromNode(enderecoProfissional, "TELEFONE")
        newMember.website = getAttributeValueFromNode(enderecoProfissional, "HOME-PAGE")
        newMember.city = getAttributeValueFromNode(enderecoProfissional, "CIDADE")
        newMember.country = getAttributeValueFromNode(enderecoProfissional, "PAIS")
        newMember.email = getAttributeValueFromNode(enderecoProfissional, "E-MAIL")
    }
    //Métodos públicos

    //Métodos auxiliares
    private void fillOrientationData(Node node, Orientation newOrientation, Member user, String tipoOrientacao)
    {
        Node basicData = (Node)(node.children()[0])
        Node specificData = (Node)(node.children()[1])
        newOrientation.tipo = tipoOrientacao
        newOrientation.tituloTese = (String)basicData.attribute("TITULO")
        String ano = (String)basicData.attribute("ANO")
        newOrientation.anoPublicacao = Integer.parseInt(ano)
        newOrientation.curso = (String)specificData.attribute("NOME-DO-CURSO")
        newOrientation.instituicao = (String)specificData.attribute("NOME-DA-INSTITUICAO")
        newOrientation.orientador = user
        newOrientation.orientando = (String)specificData.attribute("NOME-DO-ORIENTADO")
    }

    private String getAttributeValueFromNode(Node n, String attribute)
    {
        n.attribute attribute
    }
	
	private Node parseReceivedFile(MultipartHttpServletRequest request)
	{
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
        CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("file");
        File file = new File("xmlimported.xml");
        f.transferTo(file)
        def records = new XmlParser()

        records.parse(file)
    }
    //Métodos auxiliares
}
