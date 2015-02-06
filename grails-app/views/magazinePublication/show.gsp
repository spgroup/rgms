
<%@ page import="rgms.publication.MagazinePublication" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'magazinePublication.label', default: 'MagazinePublication')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-magazinePublication" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-magazinePublication" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list magazinePublication">
			
				<g:if test="${magazinePublicationInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="magazinePublication.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${magazinePublicationInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${magazinePublicationInstance?.number}">
				<li class="fieldcontain">
					<span id="number-label" class="property-label"><g:message code="magazinePublication.number.label" default="Number" /></span>
					
						<span class="property-value" aria-labelledby="number-label"><g:fieldValue bean="${magazinePublicationInstance}" field="number"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${magazinePublicationInstance?.page}">
				<li class="fieldcontain">
					<span id="page-label" class="property-label"><g:message code="magazinePublication.page.label" default="Page" /></span>
					
						<span class="property-value" aria-labelledby="page-label"><g:fieldValue bean="${magazinePublicationInstance}" field="page"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${magazinePublicationInstance?.place}">
				<li class="fieldcontain">
					<span id="place-label" class="property-label"><g:message code="magazinePublication.place.label" default="Place" /></span>
					
						<span class="property-value" aria-labelledby="place-label"><g:fieldValue bean="${magazinePublicationInstance}" field="place"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${magazinePublicationInstance?.month}">
				<li class="fieldcontain">
					<span id="month-label" class="property-label"><g:message code="magazinePublication.month.label" default="Month" /></span>
					
						<span class="property-value" aria-labelledby="month-label"><g:fieldValue bean="${magazinePublicationInstance}" field="month"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${magazinePublicationInstance?.year}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="magazinePublication.year.label" default="Year" /></span>
					
						<span class="property-value" aria-labelledby="year-label"><g:fieldValue bean="${magazinePublicationInstance}" field="year"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${magazinePublicationInstance?.id}" />
					<g:link class="edit" action="edit" id="${magazinePublicationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
