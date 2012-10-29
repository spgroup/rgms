
<%@ page import="rgms.publication.Conferencia" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'conferencia.label', default: 'Conferencia')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-conferencia" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-conferencia" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list conferencia">
			
				<g:if test="${conferenciaInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="conferencia.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${conferenciaInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conferenciaInstance?.publicationDate}">
				<li class="fieldcontain">
					<span id="publicationDate-label" class="property-label"><g:message code="conferencia.publicationDate.label" default="Publication Date" /></span>
					
						<span class="property-value" aria-labelledby="publicationDate-label"><g:formatDate date="${conferenciaInstance?.publicationDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${conferenciaInstance?.file}">
				<li class="fieldcontain">
					<span id="file-label" class="property-label"><g:message code="conferencia.file.label" default="File" /></span>
					
						<span class="property-value" aria-labelledby="file-label"><g:fieldValue bean="${conferenciaInstance}" field="file"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conferenciaInstance?.researchLine}">
				<li class="fieldcontain">
					<span id="researchLine-label" class="property-label"><g:message code="conferencia.researchLine.label" default="Research Line" /></span>
					
						<span class="property-value" aria-labelledby="researchLine-label"><g:link controller="researchLine" action="show" id="${conferenciaInstance?.researchLine?.id}">${conferenciaInstance?.researchLine?.encodeAsHTML()}</g:link></span>
					
				</li>
					
				</g:if>
				
				<!-- //#if($Bibtex) -->
				<li class="fieldcontain">
					<g:link controller="Publication" action="generateBib" params="[id : conferenciaInstance.id]">Bibtex</g:link>
				</li>
				<!-- //#end -->
				
				<g:if test="${conferenciaInstance?.booktitle}">
				<li class="fieldcontain">
					<span id="booktitle-label" class="property-label"><g:message code="conferencia.booktitle.label" default="Booktitle" /></span>
					
						<span class="property-value" aria-labelledby="booktitle-label"><g:fieldValue bean="${conferenciaInstance}" field="booktitle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conferenciaInstance?.pages}">
				<li class="fieldcontain">
					<span id="pages-label" class="property-label"><g:message code="conferencia.pages.label" default="Pages" /></span>
					
						<span class="property-value" aria-labelledby="pages-label"><g:fieldValue bean="${conferenciaInstance}" field="pages"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${conferenciaInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="conferencia.members.label" default="Members" /></span>
					
						<g:each in="${conferenciaInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="member" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${conferenciaInstance?.id}" />
					<g:link class="edit" action="edit" id="${conferenciaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
