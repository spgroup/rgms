<!-- #if($researchLine) -->



<%@ page import="rgms.publication.Book" %>
<!doctype html>
<html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'book.label', default: 'Book')}" />
    <g:set var="Title" value="${message(code: 'Title.label', default: '')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>

</div><div class="body">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>

    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="name" title="${message(code: 'book.title.label', default: 'Title')}" />

            </tr>
            </thead>
            <tbody>
            <g:each in="${bookInstanceList}" status="i" var="bookInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">


                    <td>${fieldValue(bean: bookInstance, field: "Title")}</td>


                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
<!-- #end -->