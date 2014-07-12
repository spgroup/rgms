/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pages.Conferencia

import geb.Page
import pages.GetPageTitle

/**
 *
 * @author Micael
 */
class ConferenciaEditPage extends Page{

    static at = {
        GetPageTitle gp = new GetPageTitle()
        def currentPeriodico = gp.msg("default.conferencia.label")
        def currentTitle = gp.msg("default.edit.label", [currentPeriodico])

        title ==~ currentTitle
    }

    def edit(String newtitle) {
        $("form").title = newtitle
        assert $("form").name == new_name
    }

    def select(String s) {
        $("form").find("input", value: s).click()
    }
}

