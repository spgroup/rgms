
<%@ page import="rgms.member.ResearchGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
    <g:set var="entityName2" value="${ message(code: 'researchGroup.label', default: 'Research Group') }" />
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

                <g:sortableColumn property="username" title="${message(code: 'member.username.label', default: 'Username')}" />

                <g:sortableColumn property="email" title="${message(code: 'member.email.label', default: 'Email')}" />

                <th>Extra</th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${memberInstanceList}" status="i" var="memberInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td>${fieldValue(bean: memberInstance, field: "name")}</td>

                    <td>${fieldValue(bean: memberInstance, field: "username")}</td>

                    <td><a href="mailto:${fieldValue(bean: memberInstance, field: "email")}">${fieldValue(bean: memberInstance, field: "email")}</a></td>

                    <td><g:link action="generateBibTex" id="${memberInstance.id}">Generate All BibTex</g:link></td>

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

                <td><g:link action="generateBibTexGroup" id="${researchGroupInstance.id}">Generate All BibTex</g:link></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>



</body>
</html>