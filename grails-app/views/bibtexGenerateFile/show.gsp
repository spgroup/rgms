
<%@ page import="rgms.member.ResearchGroup" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
    <g:set var="entityName2" value="${ message(code: 'researchGroup.label', default: 'Research Group') }" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
</div>
<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="content scaffold-create">
        <textarea class="bibtexContent" id="bibtexContent" rows="10" cols="1000" readonly>
            ${ bibtexContent }
        </textarea>
    <br/>
        <div>
            <g:form action="saveBibtexInFile">
                <g:hiddenField name="bibtexContent" value="${bibtexContent}"/>
                <fieldset class="buttons">
                <g:submitButton name="saveBibtexInFile" class="save"
                            value="${message(code: 'bibtex.generate.saveinfile', default: 'Save in file')}"/>
                <g:link action="exportToResearchGate">Export to Research Gate</g:link>
                </fieldset>
            </g:form>
        </div>
    </div>


</div>

</body>
</html>