<%@ page import="rgms.publication.Periodico" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'technicalReport.label', default: 'Technical Report')}"/>
    <title><g:message code="default.report.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-technicalReport" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                      default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-technicalReport" class="content scaffold-list" role="main">
    <h1><g:message code="default.report.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <br/>
    <g:each in="${technicalReportInstanceList}" status="i" var="technicalReportInstance">
        <table border='1'>
            <tr>
                <td colspan="2" align="justify"><b>${entityName} #${i + 1}</b></td>
            </tr>
            <tr>
                <td>${message(code: 'technicalReport.title.label', default: 'Title')}</td>
                <td>${fieldValue(bean: technicalReportInstance, field: "title")}</td>
            </tr>
            <tr>
                <td>${message(code: 'technicalReport.publicationDate.label', default: 'Publication Date')}</td>
                <td><g:formatDate date="${technicalReportInstance.publicationDate}"/></td>
            </tr>
            <tr>
                <td>${message(code: 'technicalReport.institution.label', default: 'Institution')}</td>
                <td>${fieldValue(bean: technicalReportInstance, field: "institution")}</td>
            </tr>
            <tr>
                <td><g:message code="technicalReport.researchLine.label" default="Research Line"/></td>
                <td>${fieldValue(bean: technicalReportInstance, field: "researchLine")}</td>
            </tr>
            <tr>
                <td><g:message code="technicalReport.members.label" default="Member"/></td>
                <td>${fieldValue(bean: technicalReportInstance, field: "members")}</td>
            </tr>
            <tr>
                <td><g:message code="default.citations.label"/></td>
                <td>
                    <table>
                        <tr>
                            <td>Google Scholar</td>
                            <td>${fieldValue(bean: technicalReportInstance, field: "citations")}</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <br/><br/>
    </g:each>
    <div class="pagination">
        <g:paginate total="${technicalReportInstanceTotal}"/>
    </div>
</div>
</body>
</html>