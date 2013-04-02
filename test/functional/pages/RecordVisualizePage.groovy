package pages;

import geb.Page
import rgms.member.Record

public class RecordVisualizePage extends Page {
    static url = "record/show"

    static at = {
        title ==~ /Ver Record/
    }

    static content = {
    }

    def editRecord()
    {
        $('a.edit').click()
    }

    def checkRecordDetails(String status)
    {
        def record = Record.findAll("from Record as r where r.status_H =?", [status])
        assert record != null
        def status_html = $('span.property-value',text: status).text()
        assert (record.status_H.toString().contains(status_html))
    }

    def getRecordStartDate(String status)
    {
        $('ol').find("span[aria-labelledby='start-label']").text()
    }

    def getRecordEndDate(String status)
    {
        $('ol').find("span[aria-labelledby='end-label']").text()
    }
}
