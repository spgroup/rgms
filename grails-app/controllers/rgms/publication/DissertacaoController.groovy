package rgms.publication

import org.springframework.web.multipart.MultipartHttpServletRequest
import rgms.XMLService

import org.codehaus.groovy.grails.commons.ConfigurationHolder
class DissertacaoController extends ThesisOrDissertationController {


import javax.servlet.http.HttpServletRequest

import javax.servlet.http.HttpServletRequest


   
    def grailsApplication
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list() {
        listThesisOrDissertation("Dissertacao", params)
    }

    def create() {
        createThesisOrDissertation("Dissertacao", params)
    }

    def save() {
        saveThesisOrDissertation("Dissertacao", params)
    }
def getDissertacaoInstance(def id)
    {
        def dissertacaoInstance = Dissertacao.get(id)

        if (!dissertacaoInstance) {
            flash.message = messageGenerator('default.not.found.message', id)
            redirect(action: "list")
            return
        }

        [dissertacaoInstance: dissertacaoInstance]
    }
    def show() {
        getDissertacaoInstance(params.id)
    def edit (){
        getDissertacaoInstance(params.id)
    }

def show() {
        showOrEdit("Dissertacao", params.id)
    }
    
def edit() {
        showOrEdit("Dissertacao", params.id)
}

    def update() {
        updateThesisOrDissertation("Dissertacao", params)
    }

    def delete() {
        deleteThesisOrDissertation("Dissertacao", params)
    }
//#if($upXMLDissertacao)
    def uploadXMLDissertacao() {
        String flashMessage = 'The non existent dissertations were successfully imported'

        XMLService serv = new XMLService()
        Node xmlFile = serv.parseReceivedFile(request as MultipartHttpServletRequest)
        serv.Import(saveDissertations, returnWithMessage, flashMessage, xmlFile as HttpServletRequest)
    }

    Closure returnWithMessage = {
        String msg ->

            redirect(action: "list")
            flash.message = message(code: msg)
    }

    def createDissertation(Node xmlNode) {

        Dissertacao newDissertation = new Dissertacao()
        newDissertation.title = XMLService.getAttributeValueFromNode(xmlNode, "TITULO-DA-DISSERTACAO-TESE")

        newDissertation.publicationDate = new Date()

        String tryingToParse = XMLService.getAttributeValueFromNode(xmlNode, "ANO-DE-OBTENCAO-DO-TITULO")
        if (tryingToParse.isInteger())
            newDissertation.publicationDate.set(year: tryingToParse.toInteger())

        newDissertation.school = XMLService.getAttributeValueFromNode(xmlNode, "NOME-INSTITUICAO")
        newDissertation.file = 'no File'
        newDissertation.address = 'no Address'
        newDissertation.save(flush: false)

    }

    Closure saveDissertations = {
        Node xmlFile ->
            Node dadosGerais = (Node) xmlFile.children()[0]
            Node mestrado = (Node) ((Node) dadosGerais.children()[3]).children()[1]
            Node doutorado = (Node) ((Node) dadosGerais.children()[3]).children()[2]

            createDissertation(mestrado)
            createDissertation(doutorado)
    }
//#end
}
