<%@ page import="rgms.publication.BookChapter" %>



<div class="fieldcontain ${hasErrors(bean: bookChapterInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="bookChapter.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${bookChapterInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookChapterInstance, field: 'publicationDate', 'error')} required">
	<label for="publicationDate">
		<g:message code="bookChapter.publicationDate.label" default="Publication Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="publicationDate" precision="day"  value="${bookChapterInstance?.publicationDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bookChapterInstance, field: 'file', 'error')} ">
	<label for="file">
		<g:message code="bookChapter.file.label" default="File" />
	</label>
	<g:field type="file" name="file" id="file" required="" value="${fieldValue(bean: bookChapterInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookChapterInstance, field: 'researchLine', 'error')} ">
	<label for="researchLine">
		<g:message code="bookChapter.researchLine.label" default="Research Line" />
		
	</label>
	<g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id" value="${bookChapterInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookChapterInstance, field: 'publisher', 'error')} required">
	<label for="publisher">
		<g:message code="bookChapter.publisher.label" default="Publisher" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="publisher" required="" value="${bookChapterInstance?.publisher}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookChapterInstance, field: 'chapter', 'error')} required">
	<label for="chapter">
		<g:message code="bookChapter.chapter.label" default="Chapter" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="chapter" type="number" min="1" value="${bookChapterInstance.chapter}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookChapterInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="bookChapter.members.label" default="Members" />
		
	</label>
	
</div>

