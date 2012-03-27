package rgms

class Record {

    Date start
    Date end
    String status_H
    
    static belongTo = [user: Member]
        
    static constraints = {
        start(nullable: true, blank:false)
        end(nullable: true)
        status_H(blank:false)
    }
}
