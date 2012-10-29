
<%@ page import="rgms.publication.Periodico" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'periodico.label', default: 'Periodico')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-periodico" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-periodico" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list periodico">
			
				<g:if test="${periodicoInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="periodico.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${periodicoInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.publicationDate}">
				<li class="fieldcontain">
					<span id="publicationDate-label" class="property-label"><g:message code="periodico.publicationDate.label" default="Publication Date" /></span>
					
						<span class="property-value" aria-labelledby="publicationDate-label"><g:formatDate date="${periodicoInstance?.publicationDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.file}">
				<li class="fieldcontain">
					<span id="file-label" class="property-label"><g:message code="periodico.file.label" default="File" /></span>
					
						<span class="property-value" aria-labelledby="file-label"><g:fieldValue bean="${periodicoInstance}" field="file"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.researchLine}">
				<li class="fieldcontain">
					<span id="researchLine-label" class="property-label"><g:message code="periodico.researchLine.label" default="Research Line" /></span>
					
						<span class="property-value" aria-labelledby="researchLine-label"><g:link controller="researchLine" action="show" id="${periodicoInstance?.researchLine?.id}">${periodicoInstance?.researchLine?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<!-- //#if($Bibtex) -->
				<li class="fieldcontain">
					<g:link controller="Publication" action="generateBib" params="[id : periodicoInstance.id]">Bibtex</g:link>
				</li>
				<!-- //#end -->
			
				<g:if test="${periodicoInstance?.journal}">
				<li class="fieldcontain">
					<span id="journal-label" class="property-label"><g:message code="periodico.journal.label" default="Journal" /></span>
					
						<span class="property-value" aria-labelledby="journal-label"><g:fieldValue bean="${periodicoInstance}" field="journal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.volume}">
				<li class="fieldcontain">
					<span id="volume-label" class="property-label"><g:message code="periodico.volume.label" default="Volume" /></span>
					
						<span class="property-value" aria-labelledby="volume-label"><g:fieldValue bean="${periodicoInstance}" field="volume"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.number}">
				<li class="fieldcontain">
					<span id="number-label" class="property-label"><g:message code="periodico.number.label" default="Number" /></span>
					
						<span class="property-value" aria-labelledby="number-label"><g:fieldValue bean="${periodicoInstance}" field="number"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.pages}">
				<li class="fieldcontain">
					<span id="pages-label" class="property-label"><g:message code="periodico.pages.label" default="Pages" /></span>
					
						<span class="property-value" aria-labelledby="pages-label"><g:fieldValue bean="${periodicoInstance}" field="pages"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="periodico.members.label" default="Members" /></span>
					
						<g:each in="${periodicoInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="member" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${periodicoInstance?.id}" />
					<g:link class="edit" action="edit" id="${periodicoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
