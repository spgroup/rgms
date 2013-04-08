package steps

import rgms.member.Member
import rgms.publication.Periodico
import rgms.publication.PeriodicoController
import rgms.publication.ResearchLine
import rgms.publication.ResearchLineController
import rgms.member.Record
import rgms.member.RecordController
import pages.*

class TestDataAndOperationsEquipe4 {
    static articles = [
            [journal: "Theoretical Computer Science", volume: 455, number: 1, pages: "2-30",
                    title: "A theory of software product line refinement",
                    publicationDate: (new Date("12 October 2012"))],
            [journal: "Science of Computer Programming", volume: 455, pages: "2-30",
                    title: "Algebraic reasoning for object-oriented programming",
                    publicationDate: (new Date("12 October 2012"))]
    ]

	static researchLines = [ 
		[name: "IA Avancada", description: ""],
		[name: "Teoria da informacao - Complexidade no espaco", description: "P=NP"],
		[name: "Novo Padrao Arquitetural MVCE", description: "Nova arquitetura que promete revolucionar a web"],
		[name: "Modelo Cascata Renovado", description: "Alteração do modelo original"]
	]

	static records = [
		[status_H: "MSc Student",start: (new Date()), end: null],
		[status_H: "Graduate Student",start: (new Date()), end: null]

	]

    static public def findArticleByTitle(String title) {
        articles.find { article ->
            article.title == title
        }
    }

	static public def findRecordByStatus(def status)
	{
		records.find{ record ->
			record.status_H == status			
		}
	}

	static public def findResearchLineByName(String name) {
		researchLines.find { researchLine ->
			researchLine.name == name
		}
	}

	static public boolean recordIsAssociated(def status)
	{
		def c = Member.createCriteria()
		def recordId = Record.findByStatus_H(status).id
		def members = c.listDistinct{
			historics{
				eq("id",recordId)
			}
		}
		members.size() > 0
	}

    static public boolean compatibleTo(article, title) {
        def testarticle = findArticleByTitle(title)
        def compatible = false
        if (testarticle == null && article == null) {
            compatible = true
        } else if (testarticle != null && article != null) {
            compatible = true
            testarticle.each { key, data ->
                compatible = compatible && (article."$key" == data)
            }
        }
        return compatible
    }

    static public void createArticle(String title, filename) {
        def cont = new PeriodicoController()
        def date = new Date()
        cont.params << TestDataAndOperationsEquipe4.findArticleByTitle(title) << [file: filename]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static void clearArticles() {
        Periodico.findAll()*.delete flush: true // Could also delete the created files.
    }

	static public void deleteResearchLine(def id){
		def res = new ResearchLineController()
		res.params.id = id
		res.request.setContent(new byte[1000]) // Could also vary the request content.
		res.delete()
		res.response.reset()
	}

	static public void updateResearchLine(String name,String description){
		def res = new ResearchLineController()
		def research_line = ResearchLine.findByName(name)		
		res.params.id = research_line.id
		res.params.name = research_line.name
		res.params.description = description
		res.request.setContent(new byte[1000]) // Could also vary the request content.
		res.update()
		res.response.reset()
	}

	static public void createResearchLine(String name) {
		def cont = new ResearchLineController()
		def research = TestDataAndOperationsEquipe4.findResearchLineByName(name)
		cont.params.name = research.name
		cont.params.description = research.description
		cont.request.setContent(new byte[1000]) // Could also vary the request content.
		cont.create()
		cont.save()
		cont.response.reset()
	}

	static public void deleteRecord(def id){
		def rec = new RecordController()
		rec.params.id = id
		rec.request.setContent(new byte[1000]) // Could also vary the request content.
		rec.delete()
		rec.response.reset()
	}
	static public void updateRecord(def status, String end){
		def record = Record.findByStatus_H(status)
		def rec = new RecordController()
		rec.params.id = record.id
		rec.params.start = record.start
		rec.params.status_H = record.status_H
		rec.params.end = Date.parse("dd/mm/yyyy",end)
		rec.request.setContent(new byte[1000]) // Could also vary the request content.
		rec.update()
		rec.response.reset()
	}

	static public def createRecord(def status)
	{
		def cont = new RecordController()
		def record = TestDataAndOperationsEquipe4.findRecordByStatus(status)
		cont.params.status_H = record.status_H
		cont.params.start = record.start
		cont.params.end = record.end
		cont.create()
		cont.save()
		cont.response.reset()

	}

	static public def insertsResearchLine(String name)
	{
		def inserted = ResearchLine.findByName(name)
		if(!inserted)
		{
			def research = TestDataAndOperationsEquipe4.findResearchLineByName(name)
			ResearchLine rl = new ResearchLine()
			rl.setName(research.name)
			rl.setDescription(research.description)
			rl.save()
		}		
	}

	static public def insertsRecord(String status)
	{
		def inserted = Record.findByStatus_H(status)
		if(!inserted)
		{
			def record = TestDataAndOperationsEquipe4.findRecordByStatus(status)
			Record r = new Record()
			r.setStatus_H(record.status_H)
			r.setStart(r.start)
			r.setEnd(r.end)
			r.save()
		}
	}
}