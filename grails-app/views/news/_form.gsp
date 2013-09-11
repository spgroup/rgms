<%@ page import="rgms.news.News" %>



<div class="fieldcontain ${hasErrors(bean: newsInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="news.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" required="" value="${newsInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: newsInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="news.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="minute"  value="${newsInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: newsInstance, field: 'researchGroup', 'error')} required">
	<label for="researchGroup">
		<g:message code="news.researchGroup.label" default="Research Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="researchGroup" name="researchGroup.id" from="${rgms.member.ResearchGroup.list()}" optionKey="id" required="" value="${newsInstance?.researchGroup?.id}" class="many-to-one"/>
</div>

