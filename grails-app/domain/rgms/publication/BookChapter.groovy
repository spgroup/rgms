package rgms.publication

import rgms.Publication

class BookChapter extends Publication {

    String publisher
	int chapter
	
    static constraints = {
		publisher nullable: false, blank: false
		chapter nullable: false, blank: false, min: 1
    }
}
