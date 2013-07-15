package pages

import geb.Page

class VisitShowPage extends Page {
	static url = "visit/show/1"

	static at = {
	}

	static content = {
	}

	def select(String e,v) {
		if(v == 'delete'){
			assert withConfirm(true) { $("form").find(e, class: v).click() }
		} else {
			$("form").find(e, class: v).click()
		}
	}


//#if( $Twitter )
	def clickOnTwitteIt (String login, pw){
		$("#button_twitter").click()
		//$("#password").text = login
		//$("#username_or_email").text = pw
		//$("input", type:"submit", class:"button selected submit", value:"Entrar e Tweetar").click()
		//<input type="submit" class="button selected submit" value="Entrar e Tweetar">
	}
//#end
}
