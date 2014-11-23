package rgms.publication


/**
 * Created by Gabriela on 10-Nov-14.
 */
class MagazinePublication{
    String name
    int number
    String page
    String place
    String month
    int year

    static belongsTo = Periodico

    static constraints = {name nullable: false, blank: false, size: 1..50
        number blank: true, size: 1..3
        page nullable: true
        place nullable: false
        month nullable: true
        year nullable: false
    }
}
