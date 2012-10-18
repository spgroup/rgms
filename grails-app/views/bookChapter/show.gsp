
<%@ page import="rgms.publication.BookChapter" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bookChapter.label', default: 'BookChapter')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bookChapter" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bookChapter" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bookChapter">
			
				<g:if test="${bookChapterInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="bookChapter.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${bookChapterInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bookChapterInstance?.publicationDate}">
				<li class="fieldcontain">
					<span id="publicationDate-label" class="property-label"><g:message code="bookChapter.publicationDate.label" default="Publication Date" /></span>
					
						<span class="property-value" aria-labelledby="publicationDate-label"><g:formatDate date="${bookChapterInstance?.publicationDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${bookChapterInstance?.file}">
				<li class="fieldcontain">
					<span id="file-label" class="property-label"><g:message code="bookChapter.file.label" default="File" /></span>
					
						<span class="property-value" aria-labelledby="file-label"><g:fieldValue bean="${bookChapterInstance}" field="file"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bookChapterInstance?.researchLine}">
				<li class="fieldcontain">
					<span id="researchLine-label" class="property-label"><g:message code="bookChapter.researchLine.label" default="Research Line" /></span>
					
						<span class="property-value" aria-labelledby="researchLine-label"><g:link controller="researchLine" action="show" id="${bookChapterInstance?.researchLine?.id}">${bookChapterInstance?.researchLine?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<!-- //#if($Bibtex) -->
				<li class="fieldcontain">
					<g:link controller="Publication" action="generateBib" params="[id : bookChapterInstance.id]">Bibtex</g:link>
				</li>
				<!-- //#end -->
			
				<g:if test="${bookChapterInstance?.publisher}">
				<li class="fieldcontain">
					<span id="publisher-label" class="property-label"><g:message code="bookChapter.publisher.label" default="Publisher" /></span>
					
						<span class="property-value" aria-labelledby="publisher-label"><g:fieldValue bean="${bookChapterInstance}" field="publisher"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bookChapterInstance?.chapter}">
				<li class="fieldcontain">
					<span id="chapter-label" class="property-label"><g:message code="bookChapter.chapter.label" default="Chapter" /></span>
					
						<span class="property-value" aria-labelledby="chapter-label"><g:fieldValue bean="${bookChapterInstance}" field="chapter"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bookChapterInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="bookChapter.members.label" default="Members" /></span>
					
						<g:each in="${bookChapterInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="member" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bookChapterInstance?.id}" />
					<g:link class="edit" action="edit" id="${bookChapterInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
