package pages

import geb.Page

class ArticleShowPage extends Page {
    static url = "periodico/show/1"

	static at = {
		//title ==~ /Ver Peri�dico/
		GetPageTitle gp = new GetPageTitle()
		def currentPeriodico = gp.msg("default.periodico.label")
		def currentTitle = gp.msg("default.show.label", [currentPeriodico])
		
		title ==~ currentTitle
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
//#if( $Facebook )
	def clickOnFacebookIt (String login, pw, message){
		$("#share_facebook").click()
		//$("#password").text = login
		//$("#username_or_email").text = pw
		//$("input", type:"submit", class:"button selected submit", value:"Entrar e Tweetar").click()
		//<input type="submit" class="button selected submit" value="Entrar e Tweetar">
	}

    def clickOnShareOnFacebook (){
        $("form").find(id: 'share').click()
    }
//#end
}
