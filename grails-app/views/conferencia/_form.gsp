<%@page import="rgms.member.Member"%>
<%@ page import="rgms.publication.Conferencia" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>


<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="conferencia.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${conferenciaInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'publicationDate', 'error')} required">
	<label for="publicationDate">
		<g:message code="conferencia.publicationDate.label" default="Publication Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="publicationDate" precision="day"  value="${conferenciaInstance?.publicationDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'file', 'error')} ">
	<label for="file">
		<g:message code="conferencia.file.label" default="File" />
		
	</label>
	<g:field type="file" name="file" id="file" required="" value="${fieldValue(bean: conferenciaInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'researchLine', 'error')} ">
	<label for="researchLine">
		<g:message code="conferencia.researchLine.label" default="Research Line" />
		
	</label>
	<g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id" value="${conferenciaInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'booktitle', 'error')} required">
	<label for="booktitle">
		<g:message code="conferencia.booktitle.label" default="Booktitle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="booktitle" required="" value="${conferenciaInstance?.booktitle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'pages', 'error')} required">
	<label for="pages">
		<g:message code="conferencia.pages.label" default="Pages" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="pages" required="" value="${conferenciaInstance?.pages}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'members', 'error')} required">
	<label for="members">
		<g:message code="conferencia.members.label" default="Members" />
		<span class="required-indicator">*</span>
	</label>
	
	<g:select name="members" from="${Member.list()}" size="10" multiple="true" optionKey="id"
          value="${conferenciaInstance?.members ? conferenciaInstance.members : Member.findByUsername(SecurityUtils.subject?.principal).id}" />

</div>

