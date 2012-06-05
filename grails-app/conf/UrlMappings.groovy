class UrlMappings {

	static mappings = {
		#literal()
		"/$controller/$action?/$id?"{
		#end
			constraints {
				// apply constraints here
			}
		}
		#if($inter)
		#literal()
		"/$lang/$controller/$action?/$id?"{
		#end
			constraints {
				// apply constraints here
			}
		}
		#end

		"/"(controller:"default")
		"500"(view:'/error')
	}
}
