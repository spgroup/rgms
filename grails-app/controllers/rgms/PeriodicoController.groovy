package rgms

class PeriodicoController {

	def scaffold = true
	def index(){
		redirect (action: home)
	}
	def home(){
		render "<b><center>Olah Mundo!!!</center></b>"
	}
}
