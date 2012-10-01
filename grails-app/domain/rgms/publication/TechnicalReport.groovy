package rgms.publication

import rgms.Publication

class TechnicalReport extends Publication {

	String institution
	
    static constraints = {
		institution nullable: false, blank: false
    }
}
