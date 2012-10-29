
<%@ page import="rgms.publication.Dissertacao" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dissertacao.label', default: 'Dissertacao')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-dissertacao" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-dissertacao" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dissertacao">
			
				<g:if test="${dissertacaoInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="dissertacao.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${dissertacaoInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dissertacaoInstance?.publicationDate}">
				<li class="fieldcontain">
					<span id="publicationDate-label" class="property-label"><g:message code="dissertacao.publicationDate.label" default="Publication Date" /></span>
					
						<span class="property-value" aria-labelledby="publicationDate-label"><g:formatDate date="${dissertacaoInstance?.publicationDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${dissertacaoInstance?.file}">
				<li class="fieldcontain">
					<span id="file-label" class="property-label"><g:message code="dissertacao.file.label" default="File" /></span>
					
						<span class="property-value" aria-labelledby="file-label"><g:fieldValue bean="${dissertacaoInstance}" field="file"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dissertacaoInstance?.researchLine}">
				<li class="fieldcontain">
					<span id="researchLine-label" class="property-label"><g:message code="dissertacao.researchLine.label" default="Research Line" /></span>
					
						<span class="property-value" aria-labelledby="researchLine-label"><g:link controller="researchLine" action="show" id="${dissertacaoInstance?.researchLine?.id}">${dissertacaoInstance?.researchLine?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<!-- //#if($Bibtex) -->
				<li class="fieldcontain">
					<g:link controller="Publication" action="generateBib" params="[id : dissertacaoInstance.id]">Bibtex</g:link>
				</li>
				<!-- //#end -->
			
				<g:if test="${dissertacaoInstance?.school}">
				<li class="fieldcontain">
					<span id="school-label" class="property-label"><g:message code="dissertacao.school.label" default="School" /></span>
					
						<span class="property-value" aria-labelledby="school-label"><g:fieldValue bean="${dissertacaoInstance}" field="school"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dissertacaoInstance?.address}">
				<li class="fieldcontain">
					<span id="address-label" class="property-label"><g:message code="dissertacao.address.label" default="Address" /></span>
					
						<span class="property-value" aria-labelledby="address-label"><g:fieldValue bean="${dissertacaoInstance}" field="address"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dissertacaoInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="dissertacao.members.label" default="Members" /></span>
					
						<g:each in="${dissertacaoInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="member" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${dissertacaoInstance?.id}" />
					<g:link class="edit" action="edit" id="${dissertacaoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
