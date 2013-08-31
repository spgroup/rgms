package pages.record;

import geb.Page
import pages.GetPageTitle

public class RecordCreatePage extends Page {
    static url = "record/create"

    static at = {
        // title ==~ /Criar Registro/
        GetPageTitle gp = new GetPageTitle()
        def recordLabel = gp.msg("record.label")
        def createLabel = gp.msg("default.create.label", [recordLabel])
        title ==~ createLabel
    }

    static content = {
    }

    def fillRecordDetails() {
        $("form").status_H = "MSc Student"
		$("form").start_day = "1"
		$("form").start_month = "1"
		$("form").start_year = "2012"
		assert $("form").status_H == "MSc Student"
		assert $("form").start_day == "1" && $("form").start_month == "1" && $("form").start_year == "2012"
    }
}

