package pages.record;

import geb.Page
import rgms.member.Record
import pages.GetPageTitle

public class RecordPage extends Page {
    static url = "record/list"

    static at = {
        // title ==~ /Registro Listagem/
        GetPageTitle gp = new GetPageTitle()
        def recordLabel = gp.msg("record.label")
        def listLabel = gp.msg("default.list.label", [recordLabel])
        title ==~ listLabel
    }

    static content = {
    }

    def selectNewRecord() {
        $('a.create').click()
    }

    def visualizeRecord(String status)
    {
        def record = Record.findByStatus_H(status)
		$('table').find('td',text: status).parent().find('a').click()
    }
}
