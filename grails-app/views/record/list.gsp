

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'record.label', default: 'Record')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-record" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-record" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="start" title="${message(code: 'record.start.label', default: 'Start')}" />
					
						<g:sortableColumn property="end" title="${message(code: 'record.end.label', default: 'End')}" />
					
						<g:sortableColumn property="status_H" title="${message(code: 'record.status_H.label', default: 'Status H')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${recordInstanceList}" status="i" var="recordInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${recordInstance.id}">${fieldValue(bean: recordInstance, field: "start")}</g:link></td>
					
						<td><g:formatDate date="${recordInstance.end}" /></td>
					
						<td>${fieldValue(bean: recordInstance, field: "status_H")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${recordInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
