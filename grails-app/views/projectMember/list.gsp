
<%@ page import="rgms.researchProject.ProjectMember" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'projectMember.label', default: 'ProjectMember')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-projectMember" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-projectMember" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'projectMember.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="responsable" title="${message(code: 'projectMember.responsable.label', default: 'Responsable')}" />
					
						<g:sortableColumn property="cnpqId" title="${message(code: 'projectMember.cnpqId.label', default: 'Cnpq Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${projectMemberInstanceList}" status="i" var="projectMemberInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${projectMemberInstance.id}">${fieldValue(bean: projectMemberInstance, field: "name")}</g:link></td>
					
						<td><g:formatBoolean boolean="${projectMemberInstance.responsable}" /></td>
					
						<td>${fieldValue(bean: projectMemberInstance, field: "cnpqId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${projectMemberInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
