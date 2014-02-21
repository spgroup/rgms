package rgms.publication

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * Created by JoaoGFarias on 2/18/14.
 */

@TestFor(TechnicalReport)
class TechnicalReportSpec  extends Specification{

    def "Equal Technical Reports"(){

        setup:
        mockDomain(TechnicalReport)

        when:
        def tr1 = new TechnicalReport(institution: int1,title: t1)
        def tr2 = new TechnicalReport(institution: int1,title: t1)

        then:
        tr1.equals(tr2)

        where:
        int1 = "UFPE"
        t1 = "Title"
    }

    def "Technical Reports differ by institution"(){

        setup:
        mockDomain(TechnicalReport)

        when:
        def tr1 = new TechnicalReport(institution: int1,title: t1)
        def tr2 = new TechnicalReport(institution: int2,title: t1)

        then:
        !tr1.equals(tr2) && tr1.institution != tr2.institution && tr1.title == tr2.title

        where:
        int1 = "UFPE"
        int2 = int1 + "c"  // Always different
        t1 = "Title"
    }

    def "Null Obj"(){

        setup:
        mockDomain(TechnicalReport)

        when:
        def tr1 = new TechnicalReport(institution: int1,title: t1)


        then:
        !tr1.equals(nullPointer)

        where:
        int1 = "UFPE"
        t1 = "Title"
        nullPointer = null
    }

    def "Non TechnicalReport Obj"(){

        setup:
        mockDomain(TechnicalReport)

        when:
        def tr1 = new TechnicalReport(institution: int1,title: t1)


        then:
        !tr1.equals(sampleObj)

        where:
        int1 = "UFPE"
        t1 = "Title"
        sampleObj = new Object()
    }

    def "Technical Report toString"(){

        setup:
        mockDomain(TechnicalReport)

        when:
        def tr1 = new TechnicalReport(institution: int1,title: t1)

        then:
        tr1.toString() ==  "Institution: " + int1 + " | Publication: " + t1

        where:
        int1 = "UFPE"
        t1 = "Title"
    }




}