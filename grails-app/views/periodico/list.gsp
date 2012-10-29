
<%@ page import="rgms.publication.Periodico" %>
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
					
						<g:sortableColumn property="title" title="${message(code: 'periodico.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="publicationDate" title="${message(code: 'periodico.publicationDate.label', default: 'Publication Date')}" />
					
						<g:sortableColumn property="file" title="${message(code: 'periodico.file.label', default: 'File')}" />
					
						<th><g:message code="periodico.researchLine.label" default="Research Line" /></th>
					
						<g:sortableColumn property="bibTex" title="${message(code: 'periodico.bibTex.label', default: 'Bib Tex')}" />
					
						<g:sortableColumn property="journal" title="${message(code: 'periodico.journal.label', default: 'Journal')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${periodicoInstanceList}" status="i" var="periodicoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${periodicoInstance.id}">${fieldValue(bean: periodicoInstance, field: "title")}</g:link></td>
					
						<td><g:formatDate date="${periodicoInstance.publicationDate}" /></td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "file")}</td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "researchLine")}</td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "bibTex")}</td>
					
						<td>${fieldValue(bean: periodicoInstance, field: "journal")}</td>
					
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
