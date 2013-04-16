<%@page import="rgms.member.Member"%>
<%@ page import="rgms.publication.Dissertacao" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>


<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="dissertacao.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${dissertacaoInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'publicationDate', 'error')} required">
	<label for="publicationDate">
		<g:message code="dissertacao.publicationDate.label" default="Publication Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="publicationDate" precision="day"  value="${dissertacaoInstance?.publicationDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'file', 'error')} ">
	<label for="file">
		<g:message code="dissertacao.file.label" default="File" />
		
	</label>
	<g:field type="file" name="file" id="file" required="" value="${fieldValue(bean: dissertacaoInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'researchLine', 'error')} ">
	<label for="researchLine">
		<g:message code="dissertacao.researchLine.label" default="Research Line" />
		
	</label>
	<g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id" value="${dissertacaoInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'school', 'error')} required">
	<label for="school">
		<g:message code="dissertacao.school.label" default="School" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="school" required="" value="${dissertacaoInstance?.school ? dissertacaoInstance.school : Member.findByUsername(SecurityUtils.subject?.principal).university}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'address', 'error')} required">
	<label for="address">
		<g:message code="dissertacao.address.label" default="Address" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="address" required="" value="${dissertacaoInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'members', 'error')} required">
	<label for="members">
		<g:message code="dissertacao.members.label" default="Members" />
		<span class="required-indicator">*</span>
	</label>
	
	<g:select name="members" from="${Member.list()}" size="10" multiple="yes" optionKey="id"
		value="${dissertacaoInstance?.members ? dissertacaoInstance.members : Member.findByUsername(SecurityUtils.subject?.principal).id}" />

</div>

