import grails.test.mixin.TestFor
import rgms.publication.Periodico
import spock.lang.Specification

/**
 * Created by JoaoGFarias on 2/7/14.
 */
@TestFor(Periodico)
class PeriodicoSpec extends Specification{

    def "Periodicos differ by number"() {
        setup:
        mockDomain(Periodico)

        when:
        def periodico1 = new Periodico(journal:journal, volume:vol, number:num+1, pages:pag )
        def periodico2 = new Periodico(journal:journal, volume:vol, number:num, pages:pag )

        then:
        !periodico1.equals(periodico2) && periodico1.number != periodico2.number && periodico1.volume == periodico2.volume

        where:
        journal = "UFPE Journal"
        vol = 42
        num = 1
        pag = "121"
    }

    def "Periodico toString"() {
        setup:
        mockDomain(Periodico)

        when:
        def periodico = new Periodico(journal:journal, volume:vol, number:num, pages:pag )

        then:
        periodico.toString() == "Journal: " +  journal + " | Volume: " + vol + " | Number: " + num

        where:
        journal = "UFPE Journal"
        vol = 42
        num = 1
        pag = "121"
    }


}
