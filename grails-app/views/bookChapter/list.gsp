
<%@ page import="rgms.publication.BookChapter" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bookChapter.label', default: 'BookChapter')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-bookChapter" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-bookChapter" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'bookChapter.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="publicationDate" title="${message(code: 'bookChapter.publicationDate.label', default: 'Publication Date')}" />
					
						<g:sortableColumn property="file" title="${message(code: 'bookChapter.file.label', default: 'File')}" />
					
						<th><g:message code="bookChapter.researchLine.label" default="Research Line" /></th>
					
						<g:sortableColumn property="bibTex" title="${message(code: 'bookChapter.bibTex.label', default: 'Bib Tex')}" />
					
						<g:sortableColumn property="publisher" title="${message(code: 'bookChapter.publisher.label', default: 'Publisher')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bookChapterInstanceList}" status="i" var="bookChapterInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${bookChapterInstance.id}">${fieldValue(bean: bookChapterInstance, field: "title")}</g:link></td>
					
						<td><g:formatDate date="${bookChapterInstance.publicationDate}" /></td>
					
						<td>${fieldValue(bean: bookChapterInstance, field: "file")}</td>
					
						<td>${fieldValue(bean: bookChapterInstance, field: "researchLine")}</td>
					
						<td>${fieldValue(bean: bookChapterInstance, field: "bibTex")}</td>
					
						<td>${fieldValue(bean: bookChapterInstance, field: "publisher")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${bookChapterInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
