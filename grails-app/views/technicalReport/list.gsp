
<%@ page import="rgms.publication.TechnicalReport" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'technicalReport.label', default: 'TechnicalReport')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-technicalReport" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-technicalReport" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'technicalReport.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="publicationDate" title="${message(code: 'technicalReport.publicationDate.label', default: 'Publication Date')}" />
					
						<g:sortableColumn property="file" title="${message(code: 'technicalReport.file.label', default: 'File')}" />
					
						<th><g:message code="technicalReport.researchLine.label" default="Research Line" /></th>
					
						
					
						<g:sortableColumn property="institution" title="${message(code: 'technicalReport.institution.label', default: 'Institution')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${technicalReportInstanceList}" status="i" var="technicalReportInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${technicalReportInstance.id}">${fieldValue(bean: technicalReportInstance, field: "title")}</g:link></td>
					
						<td><g:formatDate date="${technicalReportInstance.publicationDate}" /></td>
					
						<td>${fieldValue(bean: technicalReportInstance, field: "file")}</td>
					
						<td>${fieldValue(bean: technicalReportInstance, field: "researchLine")}</td>
					
					
						<td>${fieldValue(bean: technicalReportInstance, field: "institution")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${technicalReportInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
