package steps

import rgms.member.ResearchGroup
import rgms.news.News
import rgms.news.NewsController

class NewsTestDataAndOperations {

    static public void createNews(String descriptionParam, Date dateParam, ResearchGroup groupParam) {
        def cont = new NewsController()
        cont.params << [description: descriptionParam, date: dateParam, researchGroup: groupParam]
        cont.create()
        cont.save()
        cont.response.reset()
    }

    static public void deleteNews(String description, Date date, ResearchGroup researchGroup) {
        def cont = new NewsController()
        def identificador = News.findByDescriptionAndDateAndResearchGroup(description, date, researchGroup).id
        cont.params << [id: identificador]
        cont.request.setContent(new byte[1000]) // Could also vary the request content.
        cont.delete()
        //cont.save()
        cont.response.reset()
    }

    static public boolean checkExistingNews(String description, String date, String group){
        Date dateAsDateObj = Date.parse("dd-MM-yyyy", date)
        def researchGroup = TestDataAndOperations.createAndGetResearchGroupByName(group)
        def news = News.findByDescriptionAndDateAndResearchGroup(description, dateAsDateObj, researchGroup)
        return news != null
    }

    static public boolean checkExistingNewsByDescription(String description) {
        def news = News.findByDescription(description)
        return news != null
    }

    static public void editNewsDescription(String description, String newDescription, Date date, ResearchGroup researchGroup) {
        def cont = new NewsController()
        def news = News.findByDescriptionAndDateAndResearchGroup(description, date, researchGroup)
        news.setDescription(newDescription)
        cont.params << news.properties
        cont.update()
        cont.response.reset()
    }
}
