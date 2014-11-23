package steps

import rgms.publication.MagazinePublicationController


/**
 * Created by Gabriela on 12-Nov-14.
 */
class MagazinePublicationTestDataAndOperations {

    static magPublication = [
            [name: "Theoretical Computer Science", number: 40, page: "2-3",
             place: "Rio de Janeiro", month: "Junho", year: 2014],
            [name: "Science of Computer Programming", number: 3, page: "6-9",
            place: "Recife", month: "Janeiro", year: 2012],
             [name: "Algebraic reasoning for object-oriented programming",
            number: 20, place: "Paraíba", year: 2013],
            [name: "Science of Computer Programming", number: 15,  pages: "5-7",
            year: 2012],
            [name: "Cloud Computing", number: 18, place:"Amazônia", year: 2012]
    ]

    /*String name
    int number
    String page nullable
    String place
    String month nullable
    int year
    */

    static public def findMagazineByNotNullParams(String nome, int numero, String place, int year){
        magPublication.find{ magPublication ->
            magPublication.name == nome
            magPublication.number == numero
            magPublication.place == place
            magPublication.year == year
        }
    }

    static public void createMagPublication(String name, int number, String page, String place, String month, String year) {
        createMagPub(name, number, page, place, month, year)
    }

    static public void createMagPub(String name, int number, String page, String place, String month, String year){
        def control = new MagazinePublicationController();
        control.params << findMagazineByNotNullParams(name, number, page, place, month, year)
        control.request.setContent(new byte[1000]) // Could also vary the request content.
        control.create()
        control.save()
        control.response.reset()
        }

    static public void editMagPub(String name, int number, String page, String place, String month, String year){
        def control = new MagazinePublicationController();
        control.params << MagazinePublicationTestDataAndOperations.findMagazineByNumber(name, number)
        control.request.setContent(new byte[1000]) // Could also vary the request content.
        control.create()
        control.save()
        control.response.reset()
    }

    def Login
}
