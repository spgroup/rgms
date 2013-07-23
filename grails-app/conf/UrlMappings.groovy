class UrlMappings {

    static mappings = {
//#if( $Facebook )
        "/notifyFacebook/periodico/$id?/$name?"(controller: "Facebook", action: "index")
//#end
//#if( $Twitter )
        "/notifyTwitter/periodico/$id?/$name?"(controller: "Twitter", action: "index")
//#end
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/bibtexFileController/upload" {
            controller = "bibtexFile"
            action = "upload"
        }

        "/"(controller: "Auth", action: "index")
        "500"(view: '/error')
    }
}
