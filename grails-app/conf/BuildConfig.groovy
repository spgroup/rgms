grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"
cloudbees.account = ''
cloudbees.api.key = ''
cloudbees.api.secret = ''

grails.project.dependency.resolution = {

    def gebVersion = "0.7.1"
    def seleniumVersion = "2.22.0"

    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()

        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        test("org.codehaus.geb:geb-junit4:$gebVersion")
        test("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion")
        test("org.seleniumhq.selenium:selenium-support:$seleniumVersion")
        compile('lib:itextpdf:5.4.0')
        compile('lib:itext-pdfa:5.4.0')
        compile('lib:itext-xtra:5.4.0')

        compile(group: 'org.apache.poi', name: 'poi', version: '3.7') {
            excludes 'xmlbeans'
        }
        compile(group: 'org.apache.poi', name: 'poi-ooxml', version: '3.7') {
            excludes 'xmlbeans'
        }
        // runtime 'mysql:mysql-connector-java:5.1.16'
    }

    plugins {
        compile ":twitter4j:0.3.2"
        compile ":remote-control:1.4"
        compile ":codenarc:0.20"
        compile ":gmetrics:0.3.1"

        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"
        runtime ':jasper:1.6.1'
        runtime ":rest:0.7"
        // Uncomment these (or fillLoginData new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"
        test ":geb:$gebVersion"
        test ":cucumber:0.8.0"
        test ":spock:0.7"
    }
}
