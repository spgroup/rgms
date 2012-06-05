
<%@ page import="rgms.Tese" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${ message(code: 'tese.label', default: 'Tese')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-tese" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${ createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tese" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${ flash.message}">
			<div class="message" role="status">${ flash.message}</div>
			</g:if>
			<ol class="property-list tese">
			
				<g:if test="${ teseInstance?.author}">
				<li class="fieldcontain">
					<span id="author-label" class="property-label"><g:message code="tese.author.label" default="Author" /></span>
					
						<span class="property-value" aria-labelledby="author-label"><g:fieldValue bean="${ teseInstance}" field="author"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ teseInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="tese.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${ teseInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ teseInstance?.school}">
				<li class="fieldcontain">
					<span id="school-label" class="property-label"><g:message code="tese.school.label" default="School" /></span>
					
						<span class="property-value" aria-labelledby="school-label"><g:fieldValue bean="${ teseInstance}" field="school"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ teseInstance?.year}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="tese.year.label" default="Year" /></span>
					
						<span class="property-value" aria-labelledby="year-label"><g:fieldValue bean="${ teseInstance}" field="year"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ teseInstance?.month}">
				<li class="fieldcontain">
					<span id="month-label" class="property-label"><g:message code="tese.month.label" default="Month" /></span>
					
						<span class="property-value" aria-labelledby="month-label"><g:fieldValue bean="${ teseInstance}" field="month"/></span>
					
				</li>
				</g:if>
				#if($bibtex)
				<g:if test="${ teseInstance?.bibTex}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="tese.bibTex.label" default="BibTex" /></span>
					
						<span class="property-value" aria-labelledby="bibTex-label"><g:fieldValue bean="${ teseInstance}" field="bibTex"/></span>
					
				</li>
				</g:if>
				#end
				<li class="fieldcontain">
					<span id="month-label" class="property-label"><g:message code="tese.arquivo.label" default="Arquivo" /></span>
					<span class="property-value" aria-labelledby="arquivo-label">
					<a href="${ (resource(dir: 'uploads', file: teseInstance.arquivo)).replaceAll('static','')}">${ teseInstance?.arquivo}</a>
					</span>
				</li>
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${ teseInstance?.id}" />
					<g:link class="edit" action="edit" id="${ teseInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${ message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${ message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
