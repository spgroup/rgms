package rgms.tool

import rgms.messagesTwitter.MessagesTwitter

class TwitterController {

	static count = 0;
    def index() {
		count++
		println message (code: 'default.controller.message') + count
		println message (code: 'default.controller2.message') + params.name
		TwitterTool.addTwitterHistory(params.name, 1.toString())
		//Member.list() as JSON
		render(message(code:'default.sucess.message') + count)
	}
}
