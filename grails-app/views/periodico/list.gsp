<%@ page import="rgms.publication.Periodico" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'periodico.label', default: 'Periodico')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-periodico" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                default="Skip to content&hellip;"/></a>
<g:render template="navigation"/>
<!-- #if($XMLImp && $Journal) -->
<div class="fieldcontain">
    <ul>
        <g:form controller="XML" action="uploadXMLPeriodico" method="post" enctype="multipart/form-data">
            <label for="file">Import XML</label>
            <input type="file" name="file" id="file"/>
            <input class="save" type="submit" value="Upload"/>
        </g:form>
    </ul>
</div>
<!-- #end -->
<!-- #if($FilterArticlesByAuthor) -->
<div class="fieldcontain">
    <ul>
        <g:form action="filterByAuthor" method="post">
            <label for="filter">${message(code: 'default.author.label', default: 'Author')}</label>
            <input name="authorName" id="authorName" type="text">
            <input name="buttonFilterByAuthor" class="save" type="submit" value="${message(code: 'default.button.search.label', default: 'Search')}"/>
        </g:form>
    </ul>
</div>
<!-- #end -->
<div id="list-periodico" class="content scaffold-list" role="main">
  <g:form>
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
			<!-- #if($RemoveMultiplesArticles) -->
            <th></th>
            <!-- #end -->
            
            <g:sortableColumn property="title" title="${message(code: 'periodico.title.label', default: 'Title')}"/>

            <g:sortableColumn property="publicationDate"
                              title="${message(code: 'periodico.publicationDate.label', default: 'Publication Date')}"/>

            <g:sortableColumn property="file" title="${message(code: 'periodico.file.label', default: 'File')}"/>

            <th><g:message code="periodico.researchLine.label" default="Research Line"/></th>

            <g:sortableColumn property="autores"
                              title="${message(code: 'periodico.authors.label', default: 'Autores')}"/>

            <g:sortableColumn property="journal"
                              title="${message(code: 'periodico.journal.label', default: 'Journal')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${periodicoInstanceList}" status="i" var="periodicoInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

  				<!-- #if($RemoveMultiplesArticles) -->
				<td><g:checkBox name="check" value="${periodicoInstance.id}" checked="${false}"/></td>
                <!-- #end -->
                
                <td><g:link action="show"
                            id="${periodicoInstance.id}">${fieldValue(bean: periodicoInstance, field: "title")}</g:link></td>

                <td><g:formatDate date="${periodicoInstance.publicationDate}"/></td>

                <td>${fieldValue(bean: periodicoInstance, field: "file")}</td>

                <td>${fieldValue(bean: periodicoInstance, field: "researchLine")}</td>

                <td>${fieldValue(bean: periodicoInstance, field: "authors")}</td>

                <td>${fieldValue(bean: periodicoInstance, field: "journal")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${periodicoInstanceTotal}"/>
    </div>
    <!-- #if($RemoveMultiplesArticles) -->
    <g:if test="${periodicoInstanceTotal > 0}">
	    <fieldset class="buttons">
	        <g:hiddenField name="id" value="${periodicoInstance?.id}"/>
	        <g:actionSubmit id="removeMultiple" class="delete" action="deleteMultiples"
	                        value="${message(code: 'default.button.delete.multiples.label', default: 'Delete Selected')}"
	                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
	    </fieldset>
    </g:if>
    <!-- #end -->
  </g:form>
</div>
</body>
</html>
