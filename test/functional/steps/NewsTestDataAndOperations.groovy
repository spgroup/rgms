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
        cont.update(news.id)
        cont.response.reset()
    }

    static public boolean checkValidDate(String date) {

        boolean retorno

        String diaStr = "" + date.charAt(0) + date.charAt(1)
        String mesStr = "" + date.charAt(3) + date.charAt(4)
        int dia = Integer.valueOf(diaStr)
        int mes = Integer.valueOf(mesStr)

        if( (dia > 28) && (mes == 2) ) {         // fevereiro
            retorno = false
        }
        else if ( (dia > 30) && (mes == 4) ) {   // abril
            retorno = false
        }
        else if ( (dia > 30) && (mes == 6) ) {   // junho
            retorno = false
        }
        else if ( (dia > 30) && (mes == 7) ) {   // setembro
            retorno = false
        }
        else retorno = !((dia > 30) && (mes == 11))

        return retorno
    }
}
