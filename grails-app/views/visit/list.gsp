
<%@ page import="rgms.visit.Visit" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'visit.label', default: 'Visit')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-visit" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-visit" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dataInicio" title="${message(code: 'visit.dataInicio.label', default: 'Data Inicio')}" />
					
						<g:sortableColumn property="dataFim" title="${message(code: 'visit.dataFim.label', default: 'Data Fim')}" />
					
						<th><g:message code="visit.visitor.label" default="Visitor" /></th>
						
					    <th><g:message code="researchGroup.name.label" default="Reserarch Group" /></th>	
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${visitInstanceList}" status="i" var="visitInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>
						   <g:link action="show" id="${visitInstance.id}">
						     <g:formatDate format="dd-MM-yyyy">
						       ${fieldValue(bean: visitInstance, field: "dataInicio")}
						     </g:formatDate>
						   </g:link>
						</td>
					
						<td><g:formatDate format="dd-MM-yyyy"  date="${visitInstance.dataFim}" /></td>
					
						<td>${fieldValue(bean: visitInstance, field: "visitor")}</td>
					
						<td>${fieldValue(bean: visitInstance, field: "researchGroup")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${visitInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
