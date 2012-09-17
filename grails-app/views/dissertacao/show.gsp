
<%@ page import="rgms.Dissertacao" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${ message(code: 'dissertacao.label', default: 'Dissertacao')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-dissertacao" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${ createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-dissertacao" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${ flash.message}">
			<div class="message" role="status">${ flash.message}</div>
			</g:if>
			<ol class="property-list dissertacao">
			
				<g:if test="${ dissertacaoInstance?.author}">
				<li class="fieldcontain">
					<span id="author-label" class="property-label"><g:message code="dissertacao.author.label" default="Author" /></span>
					
						<span class="property-value" aria-labelledby="author-label"><g:fieldValue bean="${ dissertacaoInstance}" field="author"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ dissertacaoInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="dissertacao.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${ dissertacaoInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ dissertacaoInstance?.school}">
				<li class="fieldcontain">
					<span id="school-label" class="property-label"><g:message code="dissertacao.school.label" default="School" /></span>
					
						<span class="property-value" aria-labelledby="school-label"><g:fieldValue bean="${ dissertacaoInstance}" field="school"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ dissertacaoInstance?.year}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="dissertacao.year.label" default="Year" /></span>
					
						<span class="property-value" aria-labelledby="year-label"><g:fieldValue bean="${ dissertacaoInstance}" field="year"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ dissertacaoInstance?.month}">
				<li class="fieldcontain">
					<span id="month-label" class="property-label"><g:message code="dissertacao.month.label" default="Month" /></span>
					
						<span class="property-value" aria-labelledby="month-label"><g:fieldValue bean="${ dissertacaoInstance}" field="month"/></span>
					
				</li>
				</g:if>
				<!--#if($bibtex) -->
				<g:if test="${ dissertacaoInstance?.bibTex}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="dissertacao.bibTex.label" default="BibTex" /></span>
					
						<span class="property-value" aria-labelledby="bibTex-label"><g:fieldValue bean="${ dissertacaoInstance}" field="bibTex"/></span>
					
				</li>
				</g:if>
				<!--#end -->
				<li class="fieldcontain">
					<span id="month-label" class="property-label"><g:message code="dissertacao.arquivo.label" default="Arquivo" /></span>
					<span class="property-value" aria-labelledby="arquivo-label">
					<a href="${ (resource(dir: 'uploads', file: dissertacaoInstance.arquivo)).replaceAll('static','')}">${ dissertacaoInstance?.arquivo}</a>
					</span>
				</li>
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${ dissertacaoInstance?.id}" />
					<g:link class="edit" action="edit" id="${ dissertacaoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${ message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${ message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
