package pages.Report

/**
 * Created with IntelliJ IDEA.
 * User: eduardoangelinramos
 * Date: 03/09/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */

import geb.Page

class ReportHTMLPage extends Page {

    static url = "jasper/?_format=HTML&_name=export&_file=report&"

    static at = {
        //title ==~ /ReportHTML/
    }

    static content = {
    }

    def selectGenerateBibtex (memberId) {
        //$('a.create').click()
        $('a').find([title: memberId]).click()
    }

//    def visualizeRecord(String status)
//    {
//        def record = Record.findByStatus_H(status)
//        $('table').find('td',text: status).parent().find('a').click()
//    }
}
