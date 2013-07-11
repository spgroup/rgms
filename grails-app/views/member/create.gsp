<%@ page import="rgms.member.Member" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>

<a href="#create-member" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
    </ul>
    <!-- #if($XMLImp && $Member) -->
    <g:form url="[action:'uploadMemberXML']" method="post" enctype="multipart/form-data">
        <label for="file">Import XML:</label>
        <input type="file" name="file" id="file"/>
        <input class="save" type="submit" value="Upload"/>
    </g:form>
  </div>


</body>
</html>
