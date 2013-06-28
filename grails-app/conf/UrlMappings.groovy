class UrlMappings {

	static mappings = {
		"/notifyFacebook/periodico/$id"(controller: "Facebook", action: "index")
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/"(controller: "Auth", action: "index")
		"500"(view:'/error')
	}
}
