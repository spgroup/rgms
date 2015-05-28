<%@ page import="rgms.member.Membership" %>



<div class="fieldcontain ${hasErrors(bean: membershipInstance, field: 'dateLeft', 'error')} ">
	<label for="dateLeft">
		<g:message code="membership.dateLeft.label" default="Date Left" />
		
	</label>
	<g:datePicker name="dateLeft" precision="day"  value="${membershipInstance?.dateLeft}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: membershipInstance, field: 'dateJoined', 'error')} required">
	<label for="dateJoined">
		<g:message code="membership.dateJoined.label" default="Date Joined" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateJoined" precision="day"  value="${membershipInstance?.dateJoined}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: membershipInstance, field: 'member', 'error')} required">
	<label for="member">
		<g:message code="membership.member.label" default="Member" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="member" name="member.id" from="${rgms.member.Member.list()}" optionKey="id" required="" value="${membershipInstance?.member?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: membershipInstance, field: 'researchGroup', 'error')} required">
	<label for="researchGroup">
		<g:message code="membership.researchGroup.label" default="Research Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="researchGroup" name="researchGroup.id" from="${rgms.member.ResearchGroup.list()}" optionKey="id" required="" value="${membershipInstance?.researchGroup?.id}" class="many-to-one"/>
</div>

