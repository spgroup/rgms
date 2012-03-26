<%@ page import="rgms.Periodico" %>



<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'author', 'error')} ">
	<label for="author">
		<g:message code="periodico.author.label" default="Author" />
		
	</label>
	<g:textField name="author" value="${periodicoInstance?.author}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'journal', 'error')} ">
	<label for="journal">
		<g:message code="periodico.journal.label" default="Journal" />
		
	</label>
	<g:textField name="journal" value="${periodicoInstance?.journal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'number', 'error')} required">
	<label for="number">
		<g:message code="periodico.number.label" default="Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="number" required="" value="${fieldValue(bean: periodicoInstance, field: 'number')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'pages', 'error')} required">
	<label for="pages">
		<g:message code="periodico.pages.label" default="Pages" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="pages" required="" value="${fieldValue(bean: periodicoInstance, field: 'pages')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="periodico.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${periodicoInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'volume', 'error')} required">
	<label for="volume">
		<g:message code="periodico.volume.label" default="Volume" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="volume" required="" value="${fieldValue(bean: periodicoInstance, field: 'volume')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="periodico.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" required="" value="${fieldValue(bean: periodicoInstance, field: 'year')}"/>
</div>

<div class="fieldcontain">
	<input id="pdffile" name="pdffile" type="file"/>
</div>

