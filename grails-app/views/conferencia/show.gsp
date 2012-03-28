
<%@ page import="rgms.Conferencia"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'conferencia.label', default: 'Conferencia')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
	<a href="#show-conferencia" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
			<li><g:link class="list" action="list">
					<g:message code="default.list.label" args="[entityName]" />
				</g:link></li>
			<li><g:link class="create" action="create">
					<g:message code="default.new.label" args="[entityName]" />
				</g:link></li>
		</ul>
	</div>
	<div id="show-conferencia" class="content scaffold-show" role="main">
		<h1>
			<g:message code="default.show.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<ol class="property-list conferencia">

			<g:if test="${conferenciaInstance?.author}">
				<li class="fieldcontain"><span id="author-label"
					class="property-label"><g:message
							code="conferencia.author.label" default="Author" /></span> <span
					class="property-value" aria-labelledby="author-label"><g:fieldValue
							bean="${conferenciaInstance}" field="author" /></span></li>
			</g:if>

			<g:if test="${conferenciaInstance?.title}">
				<li class="fieldcontain"><span id="title-label"
					class="property-label"><g:message
							code="conferencia.title.label" default="Title" /></span> <span
					class="property-value" aria-labelledby="title-label"><g:fieldValue
							bean="${conferenciaInstance}" field="title" /></span></li>
			</g:if>

			<g:if test="${conferenciaInstance?.conference}">
				<li class="fieldcontain"><span id="conference-label"
					class="property-label"><g:message
							code="conferencia.conference.label" default="Conference" /></span> <span
					class="property-value" aria-labelledby="conference-label"><g:fieldValue
							bean="${conferenciaInstance}" field="conference" /></span></li>
			</g:if>

			<g:if test="${conferenciaInstance?.year}">
				<li class="fieldcontain"><span id="year-label"
					class="property-label"><g:message
							code="conferencia.year.label" default="Year" /></span> <span
					class="property-value" aria-labelledby="year-label"><g:fieldValue
							bean="${conferenciaInstance}" field="year" /></span></li>
			</g:if>

			<g:if test="${conferenciaInstance?.pageInitial}">
				<li class="fieldcontain"><span id="pageInitial-label"
					class="property-label"><g:message
							code="conferencia.pageInitial.label" default="PageInitial" /></span> <span
					class="property-value" aria-labelledby="pageInitial-label"><g:fieldValue
							bean="${conferenciaInstance}" field="pageInitial" /></span></li>
			</g:if>

			<g:if test="${conferenciaInstance?.pageFinal}">
				<li class="fieldcontain"><span id="pageFinal-label"
					class="property-label"><g:message
							code="conferencia.pageFinal.label" default="PageFinal" /></span> <span
					class="property-value" aria-labelledby="pageFinal-label"><g:fieldValue
							bean="${conferenciaInstance}" field="pageFinal" /></span></li>
			</g:if>

			<g:if test="${conferenciaInstance?.month}">
				<li class="fieldcontain"><span id="month-label"
					class="property-label"><g:message
							code="conferencia.month.label" default="Month" /></span> <span
					class="property-value" aria-labelledby="month-label"><g:fieldValue
							bean="${conferenciaInstance}" field="month" /></span></li>
			</g:if>

		</ol>
		<g:form>
			<fieldset class="buttons">
				<g:hiddenField name="id" value="${conferenciaInstance?.id}" />
				<g:link class="edit" action="edit" id="${conferenciaInstance?.id}">
					<g:message code="default.button.edit.label" default="Edit" />
				</g:link>
				<g:actionSubmit class="delete" action="delete"
					value="${message(code: 'default.button.delete.label', default: 'Delete')}"
					onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			</fieldset>
		</g:form>
	</div>
</body>
</html>
