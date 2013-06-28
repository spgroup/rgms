package rgms.tool

import rgms.publication.Periodico

class FacebookController {

    def index() {
		println "test1"
		FacebookTool.sendGFacebook(params.id, "")
		//render Periodico.get(params.id) as JSON
	}
}
