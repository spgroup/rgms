package steps

class CommonTestAndDataOperations {

	static public void createAndSave(cont){
	    cont.create()
	    cont.save()
	    cont.response.reset()
	}
	
	static public boolean isCompatible(object, mock){
		def compatible = false
        	if (mock == null && object == null) {
         		compatible = true
        	} else if (mock != null && object != null) {
            		compatible = true
            		mock.each { key, data ->
                		compatible = compatible && (object."$key" == data)
            		}
        	}
        	return compatible;
	}
	
}

