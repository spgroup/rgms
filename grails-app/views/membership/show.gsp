
<%@ page import="rgms.Membership" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'membership.label', default: 'Membership')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-membership" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-membership" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list membership">
			
				<g:if test="${membershipInstance?.dateJoined}">
				<li class="fieldcontain">
					<span id="dateJoined-label" class="property-label"><g:message code="membership.dateJoined.label" default="Date Joined" /></span>
					
						<span class="property-value" aria-labelledby="dateJoined-label"><g:formatDate date="${membershipInstance?.dateJoined}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${membershipInstance?.dateLeft}">
				<li class="fieldcontain">
					<span id="dateLeft-label" class="property-label"><g:message code="membership.dateLeft.label" default="Date Left" /></span>
					
						<span class="property-value" aria-labelledby="dateLeft-label"><g:formatDate date="${membershipInstance?.dateLeft}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${membershipInstance?.member}">
				<li class="fieldcontain">
					<span id="member-label" class="property-label"><g:message code="membership.member.label" default="Member" /></span>
					
						<span class="property-value" aria-labelledby="member-label"><g:link controller="member" action="show" id="${membershipInstance?.member?.id}">${membershipInstance?.member?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${membershipInstance?.researchGroup}">
				<li class="fieldcontain">
					<span id="researchGroup-label" class="property-label"><g:message code="membership.researchGroup.label" default="Research Group" /></span>
					
						<span class="property-value" aria-labelledby="researchGroup-label"><g:link controller="researchGroup" action="show" id="${membershipInstance?.researchGroup?.id}">${membershipInstance?.researchGroup?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${membershipInstance?.id}" />
					<g:link class="edit" action="edit" id="${membershipInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
