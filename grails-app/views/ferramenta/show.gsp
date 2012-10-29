
<%@ page import="rgms.publication.Ferramenta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ferramenta.label', default: 'Ferramenta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-ferramenta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-ferramenta" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list ferramenta">
			
				<g:if test="${ferramentaInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="ferramenta.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${ferramentaInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.publicationDate}">
				<li class="fieldcontain">
					<span id="publicationDate-label" class="property-label"><g:message code="ferramenta.publicationDate.label" default="Publication Date" /></span>
					
						<span class="property-value" aria-labelledby="publicationDate-label"><g:formatDate date="${ferramentaInstance?.publicationDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.file}">
				<li class="fieldcontain">
					<span id="file-label" class="property-label"><g:message code="ferramenta.file.label" default="File" /></span>
					
						<span class="property-value" aria-labelledby="file-label"><g:fieldValue bean="${ferramentaInstance}" field="file"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.researchLine}">
				<li class="fieldcontain">
					<span id="researchLine-label" class="property-label"><g:message code="ferramenta.researchLine.label" default="Research Line" /></span>
					
						<span class="property-value" aria-labelledby="researchLine-label"><g:link controller="researchLine" action="show" id="${ferramentaInstance?.researchLine?.id}">${ferramentaInstance?.researchLine?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<!-- //#if($Bibtex) -->
				<li class="fieldcontain">
					<g:link controller="Publication" action="generateBib" params="[id : ferramentaInstance.id]">Bibtex</g:link>
				</li>
				<!-- //#end -->
			
				<g:if test="${ferramentaInstance?.website}">
				<li class="fieldcontain">
					<span id="website-label" class="property-label"><g:message code="ferramenta.website.label" default="Website" /></span>
					
						<span class="property-value" aria-labelledby="website-label"><g:fieldValue bean="${ferramentaInstance}" field="website"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="ferramenta.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${ferramentaInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="ferramenta.members.label" default="Members" /></span>
					
						<g:each in="${ferramentaInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="member" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${ferramentaInstance?.id}" />
					<g:link class="edit" action="edit" id="${ferramentaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
