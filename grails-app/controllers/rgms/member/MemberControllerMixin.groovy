package rgms.member

import rgms.member.DefaultValueManager;

class MemberControllerMixin {

   def loadDefaultValues(member){
        def defaultValues = DefaultValueManager.getInstance()
        member.setEmail("@"+defaultValues.getPropertyValue(DefaultValueManager.Domain))
	member.setUniversity(defaultValues.getPropertyValue(DefaultValueManager.Univeristy))
	member.setCountry(defaultValues.getPropertyValue(DefaultValueManager.Country))
	member.setCity(defaultValues.getPropertyValue(DefaultValueManager.City))
    }
}
