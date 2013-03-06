package pages;

import geb.Page

public class RecordCreatePage extends Page {
    static url = "record/create"

    static at = {
        title ==~ /Criar Record/
    }

    static content = {
    }

    def fillRecordDetails() {
        $("form").status_H = "MSc Student"
    }
}

