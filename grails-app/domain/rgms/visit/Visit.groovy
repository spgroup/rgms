package rgms.visit

import rgms.member.ResearchGroup

class Visit {

    Date    dataInicio
    Date    dataFim
    Visitor visitor

    //#if( $reserchgroupobrigatorio )
    ResearchGroup researchGroup;
    //#end

    //#if( $descricaovisita )
    String descricao
    //#end

    static constraints = {
        dataInicio(nullable:false,blank:false)
        researchGroup(nullable:true,blank:true)
        descricao(nullable:true,blank:true)
        dataFim(validator: { val, obj ->
            (val?.compareTo(obj.dataInicio) >= 0)
        })
    }
}
