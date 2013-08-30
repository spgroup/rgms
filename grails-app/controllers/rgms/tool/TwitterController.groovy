package rgms.tool

import rgms.messagesTwitter.MessagesTwitter

class TwitterController {

	static count = 0;
    def index() {
		count++
		println MessagesTwitter.controller + count
		println MessagesTwitter.controller2 + params.name
		TwitterTool.addTwitterHistory(params.name, 1.toString())
		//Member.list() as JSON
		render(MessagesTwitter.sucess + count)
	}
}
