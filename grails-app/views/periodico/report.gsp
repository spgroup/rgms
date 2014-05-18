<!-- #if($Article) -->
<%@ page import="rgms.publication.Periodico" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'periodico.label', default: 'Periodico')}"/>
    <title><g:message code="default.report.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-periodico" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-periodico" class="content scaffold-list" role="main">
    <h1><g:message code="default.report.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <br/>
    <g:each in="${periodicoInstanceList}" status="i" var="periodicoInstance">
        <table border='1'>
            <tr>
                <td colspan="2" align="justify"><b>${entityName} #${i + 1}</b></td>
            </tr>
            <tr>
                <td>${message(code: 'periodico.title.label', default: 'Title')}</td>
                <td>${fieldValue(bean: periodicoInstance, field: "title")}</td>
            </tr>
            <tr>
                <td>${message(code: 'periodico.publicationDate.label', default: 'Publication Date')}</td>
                <td><g:formatDate date="${periodicoInstance.publicationDate}"/></td>
            </tr>
            <tr>
                <td>${message(code: 'periodico.journal.label', default: 'Journal')}</td>
                <td>${fieldValue(bean: periodicoInstance, field: "journal")}</td>
            </tr>
            <tr>
                <td><g:message code="periodico.researchLine.label" default="Research Line"/></td>
                <td>${fieldValue(bean: periodicoInstance, field: "researchLine")}</td>
            </tr>
            <tr>
                <td><g:message code="periodico.members.label" default="Member"/></td>
                <td>${fieldValue(bean: periodicoInstance, field: "members")}</td>
            </tr>
            <tr>
                <td><g:message code="default.citations.label"/></td>
                <td>
                    <table>
                        <tr>
                            <td>Google Scholar</td>
                            <td>${fieldValue(bean: periodicoInstance, field: "citations")}</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <br/><br/>
    </g:each>
    <div class="pagination">
        <g:paginate total="${periodicoInstanceTotal}"/>
    </div>
</div>
</body>
</html>
<!-- #end -->
