package pages;

import geb.Page
import rgms.member.Record

public class RecordPage extends Page {
    static url = "record/list"

    static at = {
        title ==~ /Record Listagem/
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
