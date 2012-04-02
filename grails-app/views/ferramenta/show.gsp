
<%@ page import="rgms.Ferramenta" %>
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
			
				<g:if test="${ferramentaInstance?.author}">
				<li class="fieldcontain">
					<span id="author-label" class="property-label"><g:message code="ferramenta.author.label" default="Author" /></span>
					
						<span class="property-value" aria-labelledby="author-label"><g:fieldValue bean="${ferramentaInstance}" field="author"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.descricao}">
				<li class="fieldcontain">
					<span id="descricao-label" class="property-label"><g:message code="ferramenta.descricao.label" default="Descricao" /></span>
					
						<span class="property-value" aria-labelledby="descricao-label"><g:fieldValue bean="${ferramentaInstance}" field="descricao"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.link}">
				<li class="fieldcontain">
					<span id="link-label" class="property-label"><g:message code="ferramenta.link.label" default="Link" /></span>
					
						<span class="property-value" aria-labelledby="link-label"><a href = <g:fieldValue bean="${ferramentaInstance}" field="link"/>><g:fieldValue bean="${ferramentaInstance}" field="link"/></a></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.publicacaoAssociada}">
				<li class="fieldcontain">
					<span id="publicacaoAssociada-label" class="property-label"><g:message code="ferramenta.publicacaoAssociada.label" default="Publicacao Associada" /></span>
					
						<span class="property-value" aria-labelledby="publicacaoAssociada-label"><g:fieldValue bean="${ferramentaInstance}" field="publicacaoAssociada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="ferramenta.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${ferramentaInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ferramentaInstance?.year}">
				<li class="fieldcontain">
					<span id="year-label" class="property-label"><g:message code="ferramenta.year.label" default="Year" /></span>
					
						<span class="property-value" aria-labelledby="year-label"><g:fieldValue bean="${ferramentaInstance}" field="year"/></span>
					
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
