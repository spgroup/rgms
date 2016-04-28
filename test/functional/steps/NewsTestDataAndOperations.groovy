package steps

import rgms.member.ResearchGroup
import rgms.news.News
import rgms.news.NewsController

class NewsTestDataAndOperations {
/*
*
*     String description
    Date date
*/
    static news = [
            [description: "New software", date: (new Date("12 October 2012"))],
            [description: "SQL changes", date: (new Date("13 October 2012"))],
            [description: "Software changes", date: (new Date("11 October 2011"))],
    ]

    static public List<News> findAll() {
        return news.asList()
    }


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
        cont.update(news.id)
        cont.response.reset()
    }

    static public boolean checkValidDate(String date) {

        boolean retorno = true

        String diaStr = "" + date.charAt(0) + date.charAt(1)
        String mesStr = "" + date.charAt(3) + date.charAt(4)
        int dia = Integer.valueOf(diaStr)
        int mes = Integer.valueOf(mesStr)

        if( (dia > 28) && (mes == 2) ) {
            retorno = false
        }
        else if ( (dia > 30) && ( (mes == 4) || (mes == 6) || (mes == 7) || (mes == 11) ) ) {
            retorno = false
        }

        return retorno
    }
}
