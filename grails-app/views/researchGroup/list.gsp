
<%@ page import="rgms.ResearchGroup" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'researchGroup.label', default: 'Research Group')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <a href="#list-researchGroup" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
  </div>
  <div id="list-researchGroup" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
      <thead>
        <tr>

      <g:sortableColumn property="name" title="${message(code: 'researchGroup.name.label', default: 'Name')}" />

      <g:sortableColumn property="description" title="${message(code: 'researchGroup.description.label', default: 'Description')}" />

      <th><g:message code="researchGroup.childOf.label" default="Child Of" /></th>

      </tr>
      </thead>
      <tbody>
      <g:each in="${researchGroupInstanceList}" status="i" var="researchGroupInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

          <td><g:link action="show" id="${researchGroupInstance.id}">${fieldValue(bean: researchGroupInstance, field: "name")}</g:link></td>

        <td>${fieldValue(bean: researchGroupInstance, field: "description")}</td>

        <td>${fieldValue(bean: researchGroupInstance, field: "childOf")}</td>					
        </tr>
      </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${researchGroupInstanceTotal}" />
    </div>
  </div>
</body>
</html>
