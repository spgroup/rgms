
<%@ page import="rgms.Conferencia" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'conferencia.label', default: 'Conferencia')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-conferencia" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-conferencia" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="author" title="${message(code: 'conferencia.author.label', default: 'Author')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'conferencia.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="conference" title="${message(code: 'conferencia.conference.label', default: 'Conference')}" />
					
						<g:sortableColumn property="year" title="${message(code: 'conferencia.year.label', default: 'Year')}" />
					
						<g:sortableColumn property="pageInitial" title="${message(code: 'conferencia.pageinitial.label', default: 'PageInitial')}" />
						
						<g:sortableColumn property="PageFinal" title="${message(code: 'conferencia.PageFinal.label', default: 'PageFinal')}" />
					
						<g:sortableColumn property="month" title="${message(code: 'conferencia.month.label', default: 'Month')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${conferenciaInstanceList}" status="i" var="conferenciaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${conferenciaInstance.id}">${fieldValue(bean: conferenciaInstance, field: "author")}</g:link></td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "conference")}</td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "year")}</td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "pageInitial")}</td>
						
						<td>${fieldValue(bean: conferenciaInstance, field: "pageFinal")}</td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "month")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${conferenciaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
