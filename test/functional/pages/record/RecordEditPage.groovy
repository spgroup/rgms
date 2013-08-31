package pages.record;

import geb.Page
import pages.GetPageTitle

public class RecordEditPage extends Page {
    static url = "record/edit"

    static at = {
        // title ==~ /Editar Registro/
        GetPageTitle gp = new GetPageTitle()
        def recordLabel = gp.msg("record.label")
        def editLabel = gp.msg("default.edit.label", [recordLabel])
        title ==~ editLabel
    }
           //status_H
    static content = {
    }

    def changeRecordDetails(String status) {
        def actualStatus = $("form").status_H
        $("form").status_H = status
    }

    def statusIsEmpty()
    {
        def actualStatus = $("form").status_H
        actualStatus == ""
    }
}
