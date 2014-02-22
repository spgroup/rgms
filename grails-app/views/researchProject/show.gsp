
<%@ page import="rgms.researchProject.ResearchProject" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'researchProject.label', default: 'ResearchProject')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-researchProject" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-researchProject" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list researchProject">
			
				<g:if test="${researchProjectInstance?.projectName}">
				<li class="fieldcontain">
					<span id="projectName-label" class="property-label"><g:message code="researchProject.projectName.label" default="Project Name" /></span>
					
						<span class="property-value" aria-labelledby="projectName-label"><g:fieldValue bean="${researchProjectInstance}" field="projectName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${researchProjectInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="researchProject.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${researchProjectInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${researchProjectInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="researchProject.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${researchProjectInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${researchProjectInstance?.startYear}">
				<li class="fieldcontain">
					<span id="startYear-label" class="property-label"><g:message code="researchProject.startYear.label" default="Start Year" /></span>
					
						<span class="property-value" aria-labelledby="startYear-label"><g:fieldValue bean="${researchProjectInstance}" field="startYear"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${researchProjectInstance?.endYear}">
				<li class="fieldcontain">
					<span id="endYear-label" class="property-label"><g:message code="researchProject.endYear.label" default="End Year" /></span>
					
						<span class="property-value" aria-labelledby="endYear-label"><g:fieldValue bean="${researchProjectInstance}" field="endYear"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${researchProjectInstance?.funders}">
				<li class="fieldcontain">
					<span id="funders-label" class="property-label"><g:message code="researchProject.funders.label" default="Funders" /></span>
					
						<g:each in="${researchProjectInstance.funders}" var="f">
						<span class="property-value" aria-labelledby="funders-label"><g:link controller="funder" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${researchProjectInstance?.projectMembers}">
				<li class="fieldcontain">
					<span id="projectMembers-label" class="property-label"><g:message code="researchProject.projectMembers.label" default="Project Members" /></span>
					
						<g:each in="${researchProjectInstance.projectMembers}" var="p">
						<span class="property-value" aria-labelledby="projectMembers-label"><g:link controller="projectMember" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${researchProjectInstance?.publications}">
				<li class="fieldcontain">
					<span id="publications-label" class="property-label"><g:message code="researchProject.publications.label" default="Publications" /></span>
					
						<g:each in="${researchProjectInstance.publications}" var="p">
						<span class="property-value" aria-labelledby="publications-label"><g:link controller="publication" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${researchProjectInstance?.id}" />
					<g:link class="edit" action="edit" id="${researchProjectInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
