package pages;

import geb.Page

public class RecordEditPage extends Page {
    static url = "record/edit"

    static at = {
        title ==~ /Editar Record/
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
