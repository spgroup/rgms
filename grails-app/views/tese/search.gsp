<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'tese.label', default: 'Tese')}" />
    <title><g:message code="default.search.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>

    <g:form controller="tese" action="search">
        <div>
            <label for="title">Buscar tese:</label>
            <input type="text" name="title" />
        </div>

        <input type="submit" class="search" value="<g:message code="default.search.label"/>">
    </g:form>
</div>
<div class="body">
    <h1><g:message code="default.search.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <g:if test="${params.title}">

        <div class="list">
            <table>
                <thead>
                <tr>

                    <g:sortableColumn property="title" title="${message(code: 'tese.title.label', default: 'Title')}"/>

                    <g:sortableColumn property="publicationDate"
                                      title="${message(code: 'tese.publicationDate.label', default: 'Publication Date')}"/>

                    <g:sortableColumn property="file" title="${message(code: 'tese.file.label', default: 'File')}"/>

                    <th><g:message code="tese.researchLine.label" default="Research Line"/></th>

                    <g:sortableColumn property="school" title="${message(code: 'tese.school.label', default: 'School')}"/>

                </tr>
                </thead>
                <tbody>
                <g:each in="${teseInstanceList}" status="i" var="teseInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td><g:link action="show" id="${teseInstance.tese.title}">${fieldValue(bean: teseInstance.tese, field: "title")}</g:link></td>

                        <td>${fieldValue(bean: teseInstance.tese, field: "publicationDate")}</td>

                        <td>${fieldValue(bean: teseInstance.tese, field: "file")}</td>

                        <td>${fieldValue(bean: teseInstance?.tese, field: "researchLine")}</td>

                        <td>${fieldValue(bean: teseInstance?.tese, field: "school")}</td>

                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
        <div class="paginateButtons">
            <g:paginate total="${teseInstanceTotal}" />
        </div>

    </g:if>
    <g:else>
        Digite um titulo de tese no campo acima para efetuar a buscar.
    </g:else>
</div>
</body>
</html>
