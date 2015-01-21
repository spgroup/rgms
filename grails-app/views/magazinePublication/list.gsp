
<%@ page import="rgms.publication.MagazinePublication" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magazinePublication.label', default: 'MagazinePublication')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-magazinePublication" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-magazinePublication" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'magazinePublication.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="number" title="${message(code: 'magazinePublication.number.label', default: 'Number')}" />
					
						<g:sortableColumn property="page" title="${message(code: 'magazinePublication.page.label', default: 'Page')}" />
					
						<g:sortableColumn property="place" title="${message(code: 'magazinePublication.place.label', default: 'Place')}" />
					
						<g:sortableColumn property="month" title="${message(code: 'magazinePublication.month.label', default: 'Month')}" />
					
						<g:sortableColumn property="year" title="${message(code: 'magazinePublication.year.label', default: 'Year')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${magazinePublicationInstanceList}" status="i" var="magazinePublicationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${magazinePublicationInstance.id}">${fieldValue(bean: magazinePublicationInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: magazinePublicationInstance, field: "number")}</td>
					
						<td>${fieldValue(bean: magazinePublicationInstance, field: "page")}</td>
					
						<td>${fieldValue(bean: magazinePublicationInstance, field: "place")}</td>
					
						<td>${fieldValue(bean: magazinePublicationInstance, field: "month")}</td>
					
						<td>${fieldValue(bean: magazinePublicationInstance, field: "year")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${magazinePublicationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
