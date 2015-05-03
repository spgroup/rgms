
<%@ page import="rgms.member.ResearchGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
    <g:set var="entityName2" value="${ message(code: 'researchGroup.label', default: 'Research Group') }" />
    <g:set var="entityName3" value="${message(code: 'periodico.label', default: 'Periodico')}"/>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
</div>
<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="name" title="${message(code: 'member.name.label', default: 'Name')}" />

                <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" />

                <g:sortableColumn property="email" title="${message(code: 'member.email.label', default: 'Email')}" />

                <th>Extra</th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${userMemberInstance}" status="i" var="userMember">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td>${fieldValue(bean: userMember.member, field: "name")}</td>

                    <td>${fieldValue(bean: userMember.user, field: "username")}</td>

                    <td><a href="mailto:${fieldValue(bean: userMember.member, field: "email")}">${fieldValue(bean: userMember.member, field: "email")}</a></td>

                    <td><g:link action="generateBibTex" id="${userMember.member.id}">Generate All BibTex</g:link></td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

</div>


<div id="list-researchGroup" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName2]" /></h1>
    <g:if test="${ flash.message }">
        <div class="message" role="status">${ flash.message } </div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${ message(code: 'researchGroup.name.label', default: 'Name') }" />

            <g:sortableColumn property="description" title="${ message(code: 'researchGroup.description.label', default: 'Description') }" />

            <th>Extra</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${ researchGroupInstanceList }" status="i" var="researchGroupInstance">
            <tr class="${ (i % 2) == 0 ? 'even' : 'odd' }">

                <td>${fieldValue(bean: researchGroupInstance, field: "name")}</td>

                <td>${ fieldValue(bean: researchGroupInstance, field: "description") } </td>

                <td><g:link action="generateBibTexGroup" id="${researchGroupInstance.id}">Generate All BibTex from Members</g:link></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>

<div id="list-periodico" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName3]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="title" title="${message(code: 'periodico.title.label', default: 'Title')}"/>

            <g:sortableColumn property="publicationDate"
                              title="${message(code: 'periodico.publicationDate.label', default: 'Publication Date')}"/>

            <g:sortableColumn property="file" title="${message(code: 'periodico.file.label', default: 'File')}"/>

            <th><g:message code="periodico.researchLine.label" default="Research Line"/></th>

            <g:sortableColumn property="autores"
                              title="${message(code: 'periodico.authors.label', default: 'Autores')}"/>

            <g:sortableColumn property="journal"
                              title="${message(code: 'periodico.journal.label', default: 'Journal')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${periodicoInstanceList}" status="i" var="periodicoInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${periodicoInstance.id}">${fieldValue(bean: periodicoInstance, field: "title")}</g:link></td>

                <td><g:formatDate date="${periodicoInstance.publicationDate}"/></td>

                <td>${fieldValue(bean: periodicoInstance, field: "file")}</td>

                <td>${fieldValue(bean: periodicoInstance, field: "researchLine")}</td>

                <td>${fieldValue(bean: periodicoInstance, field: "authors")}</td>

                <td>${fieldValue(bean: periodicoInstance, field: "journal")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>

</body>
</html>