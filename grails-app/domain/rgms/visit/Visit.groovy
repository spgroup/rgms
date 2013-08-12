package rgms.visit

import rgms.member.ResearchGroup

class Visit {

    Date initialDate
    Date finalDate
    Visitor visitor

    //#if( $reserchgroupobrigatorio )
    ResearchGroup researchGroup;
    //#end

    //#if( $descricaovisita )
    String description
    //#end

    static constraints = {
        initialDate(nullable:false,blank:false)
        researchGroup(nullable:true,blank:true)
        description(nullable:true,blank:true)
        finalDate(validator: { val, obj ->
            (val?.compareTo(obj.initialDate) >= 0)
        })
    }
}
