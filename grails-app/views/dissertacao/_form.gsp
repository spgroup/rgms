<%@ page import="rgms.Dissertacao" %>


<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="dissertacao.members.label" default="Author Member" />
		
	</label>
	<g:select name="members" from="${rgms.Member.list()}" optionKey="id" value="${dissertacaoInstance?.member?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="dissertacao.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${dissertacaoInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'school', 'error')} required">
	<label for="school">
		<g:message code="dissertacao.school.label" default="School" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="school" required="" value="${dissertacaoInstance?.school}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="dissertacao.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" required="" value="${fieldValue(bean: dissertacaoInstance, field: 'year')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'month', 'error')} required">
	<label for="month">
		<g:message code="dissertacao.month.label" default="Month" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="month" required="" value="${fieldValue(bean: dissertacaoInstance, field: 'month')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'arquivo', 'error')} required">
	<label for="arquivo">
		<g:message code="dissertacao.arquivo.label" default="Arquivo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="file" name="arquivo" id="arquivo" required="" value="${fieldValue(bean: dissertacaoInstance, field: 'arquivo')}"/>
</div>