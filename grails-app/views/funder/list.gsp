<!-- #if($funder) -->
<%@ page import="rgms.researchProject.Funder" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'funder.label', default: 'Funder')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-funder" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-funder" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="code" title="${message(code: 'funder.code.label', default: 'Code')}" />
					
						<g:sortableColumn property="nature" title="${message(code: 'funder.nature.label', default: 'Nature')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'funder.name.label', default: 'Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${funderInstanceList}" status="i" var="funderInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${funderInstance.id}">${fieldValue(bean: funderInstance, field: "code")}</g:link></td>
					
						<td>${fieldValue(bean: funderInstance, field: "nature")}</td>
					
						<td>${fieldValue(bean: funderInstance, field: "name")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${funderInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
<!-- #end -->