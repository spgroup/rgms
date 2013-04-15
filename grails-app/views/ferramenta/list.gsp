
<%@ page import="rgms.publication.Ferramenta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ferramenta.label', default: 'Ferramenta')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ferramenta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <g:render template="navigation"/>
        <div class="xml" role="xmlUpload">
            <ul>
                <g:form action="uploadXMLFerramenta" method="post" enctype="multipart/form-data">
                    <label for="file">Import XML:</label>
                    <input type="file" name="file" id="file"/>
                    <input class="save" type="submit" value="Upload"/>
                </g:form>
            </ul>
        </div>
		<div id="list-ferramenta" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'ferramenta.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="publicationDate" title="${message(code: 'ferramenta.publicationDate.label', default: 'Publication Date')}" />
					
						<g:sortableColumn property="file" title="${message(code: 'ferramenta.file.label', default: 'File')}" />
					
						<th><g:message code="ferramenta.researchLine.label" default="Research Line" /></th>
					
						
					
						<g:sortableColumn property="website" title="${message(code: 'ferramenta.website.label', default: 'Website')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ferramentaInstanceList}" status="i" var="ferramentaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ferramentaInstance.id}">${fieldValue(bean: ferramentaInstance, field: "title")}</g:link></td>
					
						<td><g:formatDate date="${ferramentaInstance.publicationDate}" /></td>
					
						<td>${fieldValue(bean: ferramentaInstance, field: "file")}</td>
					
						<td>${fieldValue(bean: ferramentaInstance, field: "researchLine")}</td>
					
						
					
						<td>${fieldValue(bean: ferramentaInstance, field: "website")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ferramentaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
