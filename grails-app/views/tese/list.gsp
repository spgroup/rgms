
<%@ page import="rgms.Tese" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${ message(code: 'tese.label', default: 'Tese')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-tese" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${ createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<!-- #if($bibtex) -->
				<li><g:link action="pdfTese" controller="publicacao">BibTex</g:link></li>
				<!-- #end -->
			</ul>
		</div>
		<div id="list-tese" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${ flash.message}">
			<div class="message" role="status">${ flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="author" title="${ message(code: 'tese.author.label', default: 'Author')}" />
					
						<g:sortableColumn property="title" title="${ message(code: 'tese.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="school" title="${ message(code: 'tese.school.label', default: 'School')}" />
					
						<g:sortableColumn property="year" title="${ message(code: 'tese.year.label', default: 'Year')}" />
					
						<g:sortableColumn property="month" title="${ message(code: 'tese.month.label', default: 'Month')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ teseInstanceList}" status="i" var="teseInstance">
					<tr class="${ (i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ teseInstance.id}">${ fieldValue(bean: teseInstance, field: "author")}</g:link></td>
					
						<td>${ fieldValue(bean: teseInstance, field: "title")}</td>
					
						<td>${ fieldValue(bean: teseInstance, field: "school")}</td>
					
						<td>${ fieldValue(bean: teseInstance, field: "year")}</td>
					
						<td>${ fieldValue(bean: teseInstance, field: "month")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ teseInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
