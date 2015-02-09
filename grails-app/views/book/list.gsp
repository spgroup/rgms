<%@ page import="rgms.publication.Book" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'book.label', default: 'Book')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-book" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<!-- #if($XMLImp) -->
<div class="xml" role="xmlUpload">
    <ul>
        <br>
        <g:form controller="XML" action="uploadXMLBook" method="post" enctype="multipart/form-data">
            <label for="file">&nbsp;&nbsp;&nbsp;&nbsp;Importar XML:</label>
            <input type="file" name="file" id="file"/>
            <input class="save" type="submit" value="Enviar"/>
        </g:form>
    </ul>
</div>
<!-- #end -->
<div>
    <form name="input" action="listSearchVolume" method="post">
        <input type="text" name="volume">
        <input type="submit" value="Buscar Volume">
    </form>
</div>
<div>
    <form name="input2" action="listSearchPublisher" method="post">
        <input type="text" name="Publisher">
        <input type="submit" value="Buscar Publisher">
    </form>
</div>
<div>
    <form name="input3" action="listSearchTitle" method="post">
        <input type="text" name="Title">
        <input type="submit" value="Buscar Title">
    </form>
</div>
<div id="list-book" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>




    <table>
        <thead>
        <tr>

            <g:sortableColumn property="title" title="${message(code: 'book.title.label', default: 'Title')}"/>

            <g:sortableColumn property="publicationDate"
                              title="${message(code: 'book.publicationDate.label', default: 'Publication Date')}"/>

            <g:sortableColumn property="file" title="${message(code: 'book.file.label', default: 'File')}"/>

            <th><g:message code="book.researchLine.label" default="Research Line"/></th>

            <g:sortableColumn property="autores" title="${message(code: 'book.authors.label', default: 'Autores')}"/>

            <g:sortableColumn property="publisher"
                              title="${message(code: 'book.publisher.label', default: 'Publisher')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${bookInstanceList}" status="i" var="bookInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${bookInstance.id}">${fieldValue(bean: bookInstance, field: "title")}</g:link></td>

                <td><g:formatDate date="${bookInstance.publicationDate}"/></td>

                <td>${fieldValue(bean: bookInstance, field: "file")}</td>

                <td>${fieldValue(bean: bookInstance, field: "researchLine")}</td>

                <td>${fieldValue(bean: bookInstance, field: "authors")}</td>

                <td>${fieldValue(bean: bookInstance, field: "publisher")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${bookInstanceTotal}"/>
    </div>
</div>
</body>
</html>