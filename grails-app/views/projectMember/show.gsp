
<%@ page import="rgms.researchProject.ProjectMember" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'projectMember.label', default: 'ProjectMember')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-projectMember" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-projectMember" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list projectMember">
			
				<g:if test="${projectMemberInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="projectMember.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${projectMemberInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${projectMemberInstance?.responsable}">
				<li class="fieldcontain">
					<span id="responsable-label" class="property-label"><g:message code="projectMember.responsable.label" default="Responsable" /></span>
					
						<span class="property-value" aria-labelledby="responsable-label"><g:formatBoolean boolean="${projectMemberInstance?.responsable}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${projectMemberInstance?.cnpqId}">
				<li class="fieldcontain">
					<span id="cnpqId-label" class="property-label"><g:message code="projectMember.cnpqId.label" default="Cnpq Id" /></span>
					
						<span class="property-value" aria-labelledby="cnpqId-label"><g:fieldValue bean="${projectMemberInstance}" field="cnpqId"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${projectMemberInstance?.id}" />
					<g:link class="edit" action="edit" id="${projectMemberInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
