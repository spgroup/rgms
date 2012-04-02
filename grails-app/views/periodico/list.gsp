
<%@ page import="rgms.Periodico" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'periodico.label', default: 'Periodico')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-periodico" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-periodico" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="author" title="${message(code: 'periodico.author.label', default: 'Author')}" />
					
						<g:sortableColumn property="journal" title="${message(code: 'periodico.journal.label', default: 'Journal')}" />
					
						<g:sortableColumn property="number" title="${message(code: 'periodico.number.label', default: 'Number')}" />
					
						<g:sortableColumn property="pageInitial" title="${message(code: 'periodico.pageInitial.label', default: 'PageInitial')}" />
						
						<g:sortableColumn property="pageFinal" title="${message(code: 'periodico.pageFinal.label', default: 'PageFinal')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'periodico.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="volume" title="${message(code: 'periodico.volume.label', default: 'Volume')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${periodicoInstanceList}" status="i" var="periodicoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${periodicoInstance.id}">${fieldValue(bean: periodicoInstance, field: "author")}</g:link></td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "journal")}</td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "number")}</td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "pageInitial")}</td>
						
						<td>${fieldValue(bean: periodicoInstance, field: "pageFinal")}</td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "volume")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${periodicoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
