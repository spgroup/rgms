package rgms.news

import rgms.member.ResearchGroup

class News {

    String description
    Date date

    static belongsTo = [researchGroup: ResearchGroup]

    static constraints = {
        description(blank: false)
        date(blank: false)
        researchGroup nullable: false
    }

    static List getCurrentNews(researchGroup) {
        def list
        list = News.findAllByResearchGroup(researchGroup)
        return list
    }

    static List getCurrentNewsOrderByMostRecentDate(researchGroup){
        return getCurrentNews(researchGroup).sort{a , b -> b.date <=> a.date}
    }

}
