import grails.test.mixin.TestFor
import rgms.member.Orientation
import spock.lang.Specification

/**
 * Created by JoaoGFarias on 2/7/14.
 */

@TestFor(Orientation)
class OrientationSpec extends Specification{

    def "Orientations differ by tituloTese"(){

        setup:
        mockDomain(Orientation)

        when:
        def orientation1 = new Orientation(tituloTese: tituloTese1,tipo: tipo,instituicao:instituicao)
        def orientation2 =  new Orientation(tituloTese: tituloTese2,tipo: tipo,instituicao:instituicao)

        then:
        !orientation1.equals(orientation2) && orientation1.tituloTese != orientation2.tituloTese && orientation1.instituicao == orientation2.instituicao

        where:
        tituloTese1 = "UFPE"
        tituloTese2 = tituloTese1 + "c" // Always different
        tipo = "Mestrado"
        instituicao = "UFPE"
    }

    def "Orientation toString"(){

        setup:
        mockDomain(Orientation)

        when:
        def orientation = new Orientation(tituloTese: tituloTese,tipo: tipo,instituicao:instituicao,orientando: orientando)

        then:
        orientation.toString() == "Titulo: " + tituloTese + " Orientando: " + orientando

        where:
        tituloTese = "UFPE"
        tipo = "Mestrado"
        instituicao = "UFPE"
        orientando = "José Silva"
    }
}
