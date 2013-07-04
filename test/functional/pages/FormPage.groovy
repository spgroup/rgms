package pages

import geb.Page

class FormPage extends Page {

    def selectedMembers() {
        $("form").members
    }

}