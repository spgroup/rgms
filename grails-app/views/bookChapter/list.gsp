<%@ page import="rgms.publication.BookChapter" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'default.bookchapter.label', default: 'BookChapter')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-bookChapter" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>
<!-- #if($XMLImp && $BookChapter) -->
<div class="xml" role="xmlUpload">
    <ul>
        <br>
        <g:form controller="XML" action="uploadXMLBookChapter" method="post" enctype="multipart/form-data">
            <label for="file">&nbsp;&nbsp;&nbsp;&nbsp;Importar XML:</label>
            <input type="file" name="file" id="file"/>
            <input class="save" type="submit" value="Enviar"/>
        </g:form>
    </ul>
</div>
<!-- #end -->

<!--#if($FilterArticlesByAuthor)-->
<div class="filterByAuthor">
    <ul>
        <g:form controller="filterByAuthor" action="filterByAuthor" method="post">
            <label for="authorName">${message(code: 'default.author.label', default: 'Author')}</label>
            <input name="authorName" id="authorName" type="text">
            <input name="buttonFilterByAuthor" class="save" type="submit" value="${message(code: 'default.button.search.label', default: 'Search')}"/>
        </g:form>
    </ul>
</div>
<!--#end-->

<div id="list-bookChapter" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="title" title="${message(code: 'bookChapter.title.label', default: 'Title')}"/>

            <g:sortableColumn property="publicationDate"
                              title="${message(code: 'bookChapter.publicationDate.label', default: 'Publication Date')}"/>

            <g:sortableColumn property="file" title="${message(code: 'bookChapter.file.label', default: 'File')}"/>

            <th><g:message code="bookChapter.researchLine.label" default="Research Line"/></th>

            <g:sortableColumn property="autores"
                              title="${message(code: 'bookChapter.authors.label', default: 'Autores')}"/>

            <g:sortableColumn property="publisher"
                              title="${message(code: 'bookChapter.publisher.label', default: 'Publisher')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${bookChapterInstanceList}" status="i" var="bookChapterInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${bookChapterInstance.id}">${fieldValue(bean: bookChapterInstance, field: "title")}</g:link></td>

                <td><g:formatDate date="${bookChapterInstance.publicationDate}"/></td>

                <td>${fieldValue(bean: bookChapterInstance, field: "file")}</td>

                <td>${fieldValue(bean: bookChapterInstance, field: "researchLine")}</td>



                <td>${fieldValue(bean: bookChapterInstance, field: "authors")}</td>

                <td>${fieldValue(bean: bookChapterInstance, field: "publisher")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${bookChapterInstanceTotal}"/>
    </div>
</div>
</body>
</html>
