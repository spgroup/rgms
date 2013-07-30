package pages

import geb.Page

class OrientationEditPage extends Page {
    static url = "orientation/edit/1"

    static at = {


        /* def currentOrientation = gp.msg("orientation.label")
        def currentTitle = gp.msg("default.edit.label", [currentOrientation])*/

        title ==~ /Editar Orientation/

    }

    static content = {
    }


	def edit(String novovalor){

        $("form").tituloTese = novovalor
        $("form").save().click()

    }
	
	def select(String s) {
		$("form").find("input", value: s).click()
	}
}
