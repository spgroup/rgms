class UrlMappings {

	static mappings = {
		"/notifyFacebook/periodico/$id?/$name?"(controller: "Facebook", action: "index")
		"/notifyTwitter/periodico/$id?/$name?"(controller: "Twitter", action: "index")
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/"(controller: "Auth", action: "index")
		"500"(view:'/error')
	}
}
