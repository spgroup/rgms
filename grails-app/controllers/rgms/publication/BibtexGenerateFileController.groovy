package rgms.publication

import rgms.member.Member
import rgms.member.Membership
import rgms.member.ResearchGroup
import rgms.news.News

import static java.lang.Math.*
import static java.lang.Math.min as min


/**
 * Created with IntelliJ IDEA.
 * User: Diogo Falcão
 * Date: 02/09/13
 * Time: 03:24
 * To change this template use File | Settings | File Templates.
 */
class BibtexGenerateFileController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
    }

    def home = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [researchGroupInstanceList: ResearchGroup.list(params), researchGroupInstanceTotal: ResearchGroup.count(),memberInstanceList: Member.list(params), memberInstanceTotal: Member.count() ]
    }
}
