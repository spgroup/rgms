
<%@ page import="rgms.publication.ResearchLine" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'researchLine.label', default: 'Research Line')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-researchLine" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-researchLine" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list researchLine">
			
				<g:if test="${researchLineInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="researchLine.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${researchLineInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${researchLineInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="researchLine.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${researchLineInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${researchLineInstance?.publications}">
				<li class="fieldcontain">
					<span id="publications-label" class="property-label"><g:message code="researchLine.publications.label" default="Publications" /></span>
					
						<g:each in="${researchLineInstance.publications}" var="p">
						<span class="property-value" aria-labelledby="publications-label"><g:link controller="publication" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${researchLineInstance?.id}" />
					<g:link class="edit" action="edit" id="${researchLineInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
