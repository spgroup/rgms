package rgms.tool

class TwitterController {

	static count = 0;
    def index() {
		count++
		println "TwitterController="+ count
		println "TwitterController="+ params.name
		TwitterTool.addTwitterHistory(params.name, 1.toString())
		//Member.list() as JSON
		render("Successful call to fooAction"+ count)
	}
}
