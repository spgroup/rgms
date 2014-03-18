<%@ page import="rgms.member.Member" %>
<%@ page import="rgms.publication.Book" %>
<%@ page import="rgms.publication.PublicationController" %>


<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="book.title.label" default="Title"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="title" maxlength="200" required="" value="${bookInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'publicationDate', 'error')} required">
    <label for="publicationDate">
        <g:message code="book.publicationDate.label" default="Publication Date"/>
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="publicationDate" precision="day" value="${bookInstance?.publicationDate}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'file', 'error')} ">
    <label for="file">
        <g:message code="book.file.label" default="File"/>

    </label>
    <g:field type="file" name="file" id="file" required=""
             value="${fieldValue(bean: bookInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'researchLine', 'error')} ">
    <label for="researchLine">
        <g:message code="book.researchLine.label" default="Research Line"/>

    </label>
    <g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id"
              value="${bookInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'authors', 'error')} ">
    <label for="authors">
        <g:message code="book.authors.label" default="Authors"/>

    </label>
    <g:textField name="authors" value="${bookInstance?.authors}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'publisher', 'error')} required">
    <label for="publisher">
        <g:message code="book.publisher.label" default="Publisher"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="publisher" required="" value="${bookInstance?.publisher}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'volume', 'error')} required">
    <label for="volume">
        <g:message code="book.volume.label" default="Volume"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="volume" type="number" min="1" value="${bookInstance.volume}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'pages', 'error')} required">
    <label for="pages">
        <g:message code="book.pages.label" default="Pages"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="pages" required="" value="${bookInstance?.pages}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'members', 'error')} ">
    <label for="members">
        <g:message code="book.members.label" default="Members"/>

    </label>

    <!-- #if( $contextualInformation ) -->
    <g:select name="members" from="${PublicationController.membersOrderByUsually()}" size="10" multiple="yes"
              optionKey="id" value="${bookInstance?.members.id}"/>
    <!-- #else <g:select name="members" from="${Member.list()}" size="10" multiple="yes" optionKey="id"
                         value="${bookInstance?.members.id}"/> -->
    <!-- #end -->
</div>

