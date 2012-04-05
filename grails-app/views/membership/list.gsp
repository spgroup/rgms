
<%@ page import="rgms.Membership" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'membership.label', default: 'Membership')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-membership" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-membership" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dateJoined" title="${message(code: 'membership.dateJoined.label', default: 'Date Joined')}" />
					
						<g:sortableColumn property="dateLeft" title="${message(code: 'membership.dateLeft.label', default: 'Date Left')}" />
					
						<th><g:message code="membership.member.label" default="Member" /></th>
					
						<th><g:message code="membership.researchGroup.label" default="Research Group" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${membershipInstanceList}" status="i" var="membershipInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${membershipInstance.id}">${fieldValue(bean: membershipInstance, field: "dateJoined")}</g:link></td>
					
						<td><g:formatDate date="${membershipInstance.dateLeft}" /></td>
					
						<td>${fieldValue(bean: membershipInstance, field: "member")}</td>
					
						<td>${fieldValue(bean: membershipInstance, field: "researchGroup")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${membershipInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
