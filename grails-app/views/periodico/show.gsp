
<%@ page import="rgms.Periodico" %>
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
			
				<g:if test="${periodicoInstance?.author}">
				<li class="fieldcontain">
					<span id="author-label" class="property-label"><g:message code="periodico.author.label" default="Author" /></span>
					
						<span class="property-value" aria-labelledby="author-label"><g:fieldValue bean="${periodicoInstance}" field="author"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.journal}">
				<li class="fieldcontain">
					<span id="journal-label" class="property-label"><g:message code="periodico.journal.label" default="Journal" /></span>
					
						<span class="property-value" aria-labelledby="journal-label"><g:fieldValue bean="${periodicoInstance}" field="journal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.number}">
				<li class="fieldcontain">
					<span id="number-label" class="property-label"><g:message code="periodico.number.label" default="Number" /></span>
					
						<span class="property-value" aria-labelledby="number-label"><g:fieldValue bean="${periodicoInstance}" field="number"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.pageInitial}">
				<li class="fieldcontain">
					<span id="pageInitial-label" class="property-label"><g:message code="periodico.pageInitial.label" default="PageInitial" /></span>
					
						<span class="property-value" aria-labelledby="pageInitial-label"><g:fieldValue bean="${periodicoInstance}" field="pageInitial"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${periodicoInstance?.pageFinal}">
				<li class="fieldcontain">
					<span id="pageFinal-label" class="property-label"><g:message code="periodico.pageFinal.label" default="PageFinal" /></span>
					
						<span class="property-value" aria-labelledby="pageFinal-label"><g:fieldValue bean="${periodicoInstance}" field="pageFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="periodico.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${periodicoInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.volume}">
				<li class="fieldcontain">
					<span id="volume-label" class="property-label"><g:message code="periodico.volume.label" default="Volume" /></span>
					
						<span class="property-value" aria-labelledby="volume-label"><g:fieldValue bean="${periodicoInstance}" field="volume"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${periodicoInstance?.year}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="periodico.year.label" default="Year" /></span>
					
						<span class="property-value" aria-labelledby="year-label"><g:fieldValue bean="${periodicoInstance}" field="year"/></span>
					
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
