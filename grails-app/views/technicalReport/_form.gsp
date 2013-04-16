<%@page import="rgms.member.Member"%>
<%@ page import="rgms.publication.TechnicalReport" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>


<div class="fieldcontain ${hasErrors(bean: technicalReportInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="technicalReport.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${technicalReportInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: technicalReportInstance, field: 'publicationDate', 'error')} required">
	<label for="publicationDate">
		<g:message code="technicalReport.publicationDate.label" default="Publication Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="publicationDate" precision="day"  value="${technicalReportInstance?.publicationDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: technicalReportInstance, field: 'file', 'error')} ">
	<label for="file">
		<g:message code="technicalReport.file.label" default="File" />
		
	</label>
	<g:textArea name="file" cols="40" rows="5" maxlength="100000" value="${technicalReportInstance?.file}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: technicalReportInstance, field: 'researchLine', 'error')} ">
	<label for="researchLine">
		<g:message code="technicalReport.researchLine.label" default="Research Line" />
		
	</label>
	<g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id" value="${technicalReportInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: technicalReportInstance, field: 'institution', 'error')} required">
	<label for="institution">
		<g:message code="technicalReport.institution.label" default="Institution" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="institution" required="" value="${technicalReportInstance?.institution}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: technicalReportInstance, field: 'members', 'error')} required">
	<label for="members">
		<g:message code="technicalReport.members.label" default="Members" />
		<span class="required-indicator">*</span>
	</label>
	
	<g:select name="members" from="${Member.list()}" size="10" multiple="yes" optionKey="id"
		value="${technicalReportInstance?.members ? technicalReportInstance.members : Member.findByUsername(SecurityUtils.subject?.principal).id}" />

</div>

