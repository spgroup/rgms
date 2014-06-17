<!-- #if($Article) -->
<%@ page import="rgms.member.Member" %>
<%@ page import="rgms.publication.Periodico" %>
<%@ page import="rgms.publication.PublicationController" %>

<!doctype html>

<html>
<!-- #if( $contextualInformation ) -->
<head>
    <meta charset="utf-8"/>
    <script type="text/javascript" src="/rgms/js/jquery-ui-1.8.18.custom.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#journal").autocomplete({
                source: '<g:createLink controller='periodico' action='ajaxJournalFinder'/>'
            });
        });
    </script>
</head>
<!-- #end -->

<body>
<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="periodico.title.label" default="Title"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="title" required="" value="${periodicoInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'publicationDate', 'error')} required">
    <label for="publicationDate">
        <g:message code="periodico.publicationDate.label" default="Publication Date"/>
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="publicationDate" precision="day" value="${periodicoInstance?.publicationDate}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'file', 'error')} ">
    <label for="file">
        <g:message code="periodico.file.label" default="File"/>

    </label>
    <g:field type="file" name="file" id="file" required=""
             value="${fieldValue(bean: periodicoInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'researchLine', 'error')} ">
    <label for="researchLine">
        <g:message code="periodico.researchLine.label" default="Research Line"/>

    </label>
    <g:select id="researchLine" name="researchLine.id" from="${rgms.publication.ResearchLine.list()}" optionKey="id"
              value="${periodicoInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'authors', 'error')} ">
    <label for="authors">
        <g:message code="periodico.authors.label" default="Authors"/>

    </label>
    <g:textField name="authors" value="${periodicoInstance?.authors}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'journal', 'error')} required">
    <label for="journal">
        <g:message code="periodico.journal.label" default="Journal"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="journal" name="journal" required="" value="${periodicoInstance?.journal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'volume', 'error')} required">
    <label for="volume">
        <g:message code="periodico.volume.label" default="Volume"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="volume" type="number" min="1" value="${periodicoInstance.volume}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'number', 'error')} required">
    <label for="number">
        <g:message code="periodico.number.label" default="Number"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="number" type="number" min="1" value="${periodicoInstance.number}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'pages', 'error')} required">
    <label for="pages">
        <g:message code="periodico.pages.label" default="Pages"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="pages" required="" value="${periodicoInstance?.pages}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'members', 'error')} required">
    <label for="members">
        <g:message code="periodico.members.label" default="Members"/>
        <span class="required-indicator">*</span>
    </label>

    <!-- #if( $contextualInformation ) -->
    <g:select name="members" from="${PublicationController.membersOrderByUsually()}" size="10" multiple="yes"
              optionKey="id" value="${periodicoInstance?.members.id}"/>
    <!-- #else <g:select name="members" from="${Member.list()}" size="10" multiple="yes" optionKey="id"
                         value="${periodicoInstance?.members.id}"/> -->
    <!-- #end -->

</div>
</body>
</html>
<!-- #end -->

