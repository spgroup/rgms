
<%@ page import="rgms.Dissertacao" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dissertacao.label', default: 'Dissertacao')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dissertacao" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dissertacao" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="author" title="${message(code: 'dissertacao.author.label', default: 'Author')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'dissertacao.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="school" title="${message(code: 'dissertacao.school.label', default: 'School')}" />
					
						<g:sortableColumn property="year" title="${message(code: 'dissertacao.year.label', default: 'Year')}" />
					
						<g:sortableColumn property="month" title="${message(code: 'dissertacao.month.label', default: 'Month')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dissertacaoInstanceList}" status="i" var="dissertacaoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dissertacaoInstance.id}">${fieldValue(bean: dissertacaoInstance, field: "author")}</g:link></td>
					
						<td>${fieldValue(bean: dissertacaoInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: dissertacaoInstance, field: "school")}</td>
					
						<td>${fieldValue(bean: dissertacaoInstance, field: "year")}</td>
					
						<td>${fieldValue(bean: dissertacaoInstance, field: "month")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dissertacaoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
