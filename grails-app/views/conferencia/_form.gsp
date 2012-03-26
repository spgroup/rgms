<%@ page import="rgms.Conferencia" %>



<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'author', 'error')} required">
	<label for="author">
		<g:message code="conferencia.author.label" default="Author" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="author" required="" value="${conferenciaInstance?.author}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="conferencia.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${conferenciaInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'conference', 'error')} required">
	<label for="conference">
		<g:message code="conferencia.conference.label" default="Conference" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="conference" required="" value="${conferenciaInstance?.conference}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="conferencia.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" required="" value="${fieldValue(bean: conferenciaInstance, field: 'year')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'pages', 'error')} required">
	<label for="pages">
		<g:message code="conferencia.pages.label" default="Pages" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" id="num" name="pages" required="" value="${fieldValue(bean: conferenciaInstance, field: 'pages')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'month', 'error')} required">
	<label for="month">
		<g:message code="conferencia.month.label" default="Month" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" id="num" name="month" required="" value="${fieldValue(bean: conferenciaInstance, field: 'month')}"/>
</div>

