<%@ page import="rgms.publication.Tese" %>



<div class="fieldcontain ${hasErrors(bean: teseInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="tese.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${teseInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teseInstance, field: 'publicationDate', 'error')} required">
	<label for="publicationDate">
		<g:message code="tese.publicationDate.label" default="Publication Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="publicationDate" precision="day"  value="${teseInstance?.publicationDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: teseInstance, field: 'file', 'error')} ">
	<label for="file">
		<g:message code="tese.file.label" default="File" />
		
	</label>
	<g:field type="file" name="file" id="file" required="" value="${fieldValue(bean: teseInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teseInstance, field: 'researchLine', 'error')} ">
	<label for="researchLine">
		<g:message code="tese.researchLine.label" default="Research Line" />
		
	</label>
	<g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id" value="${teseInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teseInstance, field: 'school', 'error')} required">
	<label for="school">
		<g:message code="tese.school.label" default="School" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="school" required="" value="${teseInstance?.school}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teseInstance, field: 'address', 'error')} required">
	<label for="address">
		<g:message code="tese.address.label" default="Address" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="address" required="" value="${teseInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teseInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="tese.members.label" default="Members" />
		
	</label>
	
</div>

