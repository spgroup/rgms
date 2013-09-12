<%--
  Created by IntelliJ IDEA.
  User: Flavia
  Date: 03/09/13
  Time: 09:20
  To change this template use File | Settings | File Templates.
--%>



<%@ page import="rgms.publication.ResearchLine" %>
<!doctype html>
<html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'researchLine.label', default: 'Research Line')}" />
    <g:set var="member" value="${message(code: 'member.label', default: '')}" />
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

               <g:sortableColumn property="name" title="${message(code: 'member.name.label', default: 'Name')}" />

            </tr>
            </thead>
            <tbody>
            <g:each in="${researchLineInstanceList}" status="i" var="researchLineInstanceList">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">


                    <td>${fieldValue(bean: researchLineInstanceList, field: "name")}</td>


                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
