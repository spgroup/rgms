<%@ page import="rgms.Dissertacao" %>



<div class="fieldcontain ${hasErrors(bean: dissertacaoInstance, field: 'author', 'error')} required">
	<label for="author">
		<g:message code="dissertacao.author.label" default="Author" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="author" required="" value="${dissertacaoInstance?.author}"/>
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

