package rgms.tool

class FacebookController {
	
	static count = 0;

    def index() {
		count++
		println "FacebookController="+ count
		println "FacebookController="+ params.name
		FacebookTool.sendGFacebook(params.name, 1.toString())
		//Member.list() as JSON
		render("Successful call to fooAction"+ count)
	}
}
