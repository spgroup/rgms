<%@ page import="rgms.Tese" %>



<div class="fieldcontain ${ hasErrors(bean: teseInstance, field: 'author', 'error')} required">
	<label for="author">
		<g:message code="tese.author.label" default="Author" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="author" required="" value="${ teseInstance?.author}"/>
</div>

<div class="fieldcontain ${ hasErrors(bean: teseInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="tese.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${ teseInstance?.title}"/>
</div>

<div class="fieldcontain ${ hasErrors(bean: teseInstance, field: 'school', 'error')} required">
	<label for="school">
		<g:message code="tese.school.label" default="School" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="school" required="" value="${ teseInstance?.school}"/>
</div>

<div class="fieldcontain ${ hasErrors(bean: teseInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="tese.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" required="" value="${ fieldValue(bean: teseInstance, field: 'year')}"/>
</div>

<div class="fieldcontain ${ hasErrors(bean: teseInstance, field: 'month', 'error')} required">
	<label for="month">
		<g:message code="tese.month.label" default="Month" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="month" required="" value="${ fieldValue(bean: teseInstance, field: 'month')}"/>
</div>

<div class="fieldcontain ${ hasErrors(bean: teseInstance, field: 'arquivo', 'error')} required">
	<label for="arquivo">
		<g:message code="tese.arquivo.label" default="Arquivo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="file" name="arquivo" id="arquivo" required="" value="${ fieldValue(bean: teseInstance, field: 'arquivo')}"/>
</div>