<%@ page import="rgms.Publication" %>



<div class="fieldcontain ${hasErrors(bean: publicationInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="publication.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${publicationInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: publicationInstance, field: 'publicationDate', 'error')} required">
	<label for="publicationDate">
		<g:message code="publication.publicationDate.label" default="Publication Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="publicationDate" precision="day"  value="${publicationInstance?.publicationDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: publicationInstance, field: 'researchLine', 'error')} ">
	<label for="researchLine">
		<g:message code="publication.researchLine.label" default="Research Line" />
		
	</label>
	<g:select id="researchLine" name="researchLine.id" from="${rgms.ResearchLine.list()}" optionKey="id" value="${publicationInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: publicationInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="publication.members.label" default="Members" />
		
	</label>
	
</div>

