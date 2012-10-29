
<%@ page import="rgms.publication.Conferencia" %>
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
					
						<g:sortableColumn property="title" title="${message(code: 'conferencia.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="publicationDate" title="${message(code: 'conferencia.publicationDate.label', default: 'Publication Date')}" />
					
						<g:sortableColumn property="file" title="${message(code: 'conferencia.file.label', default: 'File')}" />
					
						<th><g:message code="conferencia.researchLine.label" default="Research Line" /></th>
					
						<g:sortableColumn property="bibTex" title="${message(code: 'conferencia.bibTex.label', default: 'Bib Tex')}" />
					
						<g:sortableColumn property="booktitle" title="${message(code: 'conferencia.booktitle.label', default: 'Booktitle')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${conferenciaInstanceList}" status="i" var="conferenciaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${conferenciaInstance.id}">${fieldValue(bean: conferenciaInstance, field: "title")}</g:link></td>
					
						<td><g:formatDate date="${conferenciaInstance.publicationDate}" /></td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "file")}</td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "researchLine")}</td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "bibTex")}</td>
					
						<td>${fieldValue(bean: conferenciaInstance, field: "booktitle")}</td>
					
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
