<%@ page import="rgms.publication.Dissertacao" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dissertacao.label', default: 'Dissertacao')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dissertacao" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <g:render template="navigation"/>
<!--//#if($upXMLDissertacao)-->
        <div class="xml" role="xmlUpload">
            <ul>
                <g:form action="uploadXMLDissertacao" method="post" enctype="multipart/form-data">
                    <label for="file">Import XML:</label>
                    <input type="file" name="file" id="file"/>
                    <input class="save" type="submit" value="Upload"/>
                </g:form>
            </ul>
        </div>
<!-- //#end -->
		<div id="list-dissertacao" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'dissertacao.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="publicationDate" title="${message(code: 'dissertacao.publicationDate.label', default: 'Publication Date')}" />
					
						<g:sortableColumn property="file" title="${message(code: 'dissertacao.file.label', default: 'File')}" />
					
						<th><g:message code="dissertacao.researchLine.label" default="Research Line" /></th>
					
					
						
					
						<g:sortableColumn property="school" title="${message(code: 'dissertacao.school.label', default: 'School')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dissertacaoInstanceList}" status="i" var="dissertacaoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dissertacaoInstance.id}">${fieldValue(bean: dissertacaoInstance, field: "title")}</g:link></td>
					
						<td><g:formatDate date="${dissertacaoInstance.publicationDate}" /></td>
					
						<td>${fieldValue(bean: dissertacaoInstance, field: "file")}</td>
					
						<td>${fieldValue(bean: dissertacaoInstance, field: "researchLine")}</td>
					
						
					
						<td>${fieldValue(bean: dissertacaoInstance, field: "school")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dissertacaoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
