
<%@ page import="rgms.member.Orientation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'orientation.label', default: 'Orientation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-orientation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-orientation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="tipo" title="${message(code: 'orientation.tipo.label', default: 'Tipo')}" />
					
						<th><g:message code="orientation.orientando.label" default="Orientando" /></th>
					
						<th><g:message code="orientation.orientador.label" default="Orientador" /></th>
					
						<th><g:message code="orientation.periodico.label" default="Periodico" /></th>
					
						<g:sortableColumn property="descricao" title="${message(code: 'orientation.descricao.label', default: 'Descricao')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${orientationInstanceList}" status="i" var="orientationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${orientationInstance.id}">${fieldValue(bean: orientationInstance, field: "tipo")}</g:link></td>
					
						<td>${fieldValue(bean: orientationInstance, field: "orientando")}</td>
					
						<td>${fieldValue(bean: orientationInstance, field: "orientador")}</td>
					
						<td>${fieldValue(bean: orientationInstance, field: "periodico")}</td>
					
						<td>${fieldValue(bean: orientationInstance, field: "descricao")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${orientationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
