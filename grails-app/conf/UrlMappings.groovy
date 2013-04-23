class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/bibtexFileController/upload" {
			controller = "bibtexFile"
			action = "upload"
		}
		
        "/"(controller: "Auth", action: "index")
		"500"(view:'/error')
	}
}
