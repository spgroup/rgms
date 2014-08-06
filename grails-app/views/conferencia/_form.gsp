<%@ page import="javax.swing.text.Document; rgms.member.Member" %>
<%@ page import="rgms.publication.Conferencia" %>
<%@ page import="rgms.publication.ResearchLine" %>
<%@ page import="rgms.publication.PublicationController" %>
<%@ page import="rgms.publication.ConferenciaController" %>


<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="conferencia.title.label" default="Title"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="title" required="" value="${conferenciaInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'publicationDate', 'error')} required">
    <label for="publicationDate">
        <g:message code="conferencia.publicationDate.label" default="Publication Date"/>
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="publicationDate" precision="day" value="${conferenciaInstance?.publicationDate}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'file', 'error')} ">
    <label for="file">
        <g:message code="conferencia.file.label" default="File"/>

    </label>
    <g:field type="file" name="file" id="file" required="" accept="application/pdf"
             value="${fieldValue(bean: conferenciaInstance, field: 'file')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'researchLine', 'error')} ">
    <label for="researchLine">
        <g:message code="conferencia.researchLine.label" default="Research Line"/>

    </label>
    <g:select id="researchLine" name="researchLine.id" from="${ResearchLine.list()}" optionKey="id"
              value="${conferenciaInstance?.researchLine?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'booktitle', 'error')} required">
    <label for="booktitle">
        <g:message code="conferencia.booktitle.label" default="Booktitle"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="booktitle" required="" value="${conferenciaInstance?.booktitle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'pages', 'error')} required">
    <label for="pages">
        <g:message code="conferencia.pages.label" default="Pages"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="pages" required="" value="${conferenciaInstance?.pages}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'pages', 'error')}">

    <g:if test="${conferenciaInstance.authors}">
        ${session.putAt("authors",conferenciaInstance.authors)}
    </g:if>
    <g:else>
        ${session.putAt("authors",ConferenciaController.defaultAuthors)}
    </g:else>

    <div id="authors">
        <g:render template="authorTab" />
    </div>

</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'pages', 'error')}">
    <label for="addAuthor">
        <g:message code="conferencia.authorName.label" default="Author Name"/>
    </label>

    <g:textField name="addAuthor" size="20"/>
    <input type="button" id="addAuthorButton" value="${message(code: 'conferencia.addAuthor.label', default: 'Add Author')}"/>
</div>