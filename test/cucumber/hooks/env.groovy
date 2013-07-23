import geb.Browser
import geb.binding.BindingUpdater

import static cucumber.api.groovy.Hooks.After
import static cucumber.api.groovy.Hooks.Before
import static grails.plugin.cucumber.Hooks.hooks

hooks {
    integration("@i9n")
}

Before() {
    bindingUpdater = new BindingUpdater(binding, new Browser())
    bindingUpdater.initialize()
}

After() {
    bindingUpdater.remove()
    def rootPath = new File(".").getCanonicalPath() + File.separator
    def uploadsFolder = new File(rootPath + "web-app" + File.separator + "uploads")
    uploadsFolder.listFiles().each { innerFile ->
        if (innerFile.getName() != "necessary.txt")
            innerFile.deleteOnExit()
    }
}