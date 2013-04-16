<%@page import="rgms.member.Member"%>
<%@ page import="rgms.publication.Ferramenta" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>


<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="ferramenta.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${ferramentaInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'publicationDate', 'error')} required">
	<label for="publicationDate">
		<g:message code="ferramenta.publicationDate.label" default="Publication Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="publicationDate" precision="day"  value="${ferramentaInstance?.publicationDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'file', 'error')} ">
	<label for="file">
		<g:message code="ferramenta.file.label" default="File" />
		
	</label>
	<g:field type="file" name="file" id="file" required="" value="${fieldValue(bean: ferramentaInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'researchLine', 'error')} ">
	<label for="researchLine">
		<g:message code="ferramenta.researchLine.label" default="Research Line" />
		
	</label>
	<g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id" value="${ferramentaInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'website', 'error')} required">
	<label for="website">
		<g:message code="ferramenta.website.label" default="Website" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="website" required="" value="${ferramentaInstance?.website}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="ferramenta.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" required="" value="${ferramentaInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'members', 'error')} required">
	<label for="members">
		<g:message code="ferramenta.members.label" default="Members" />
		<span class="required-indicator">*</span>
	</label>
	
	<g:select name="members" from="${Member.list()}" size="10" multiple="yes" optionKey="id"
		value="${ferramentaInstance?.members ? ferramentaInstance.members : Member.findByUsername(SecurityUtils.subject?.principal).id}" />

</div>

