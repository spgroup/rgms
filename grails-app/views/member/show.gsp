<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<!--  #if($XML) -->
<br><g:jasperReport jasper="report" format="XML" name="export"></br>
    <input type="hidden" name="member_id" value="${userMemberInstanceList.memberInstance?.id}"/>
</g:jasperReport>
<!-- #end -->

<!-- #if($HTML) -->
<br><g:jasperReport jasper="report" format="HTML" name="export"></br>
    <input type="hidden" name="member_id" value="${userMemberInstanceList.memberInstance?.id}"/>
</g:jasperReport>
<!-- #end -->

<!-- #if($PDF) -->
<br><g:jasperReport jasper="report" format="PDF" name="export"></br>
    <input type="hidden" name="member_id" value="${userMemberInstanceList.memberInstance?.id}"/>
</g:jasperReport>
<!-- #end -->


<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.name.label" default="Name"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "name")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.username.label" default="Username"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList?.userInstance, field: "username")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.email.label" default="Email"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "email")}</td>

            </tr>


            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.permissions.label" default="Permissions"/></td>

                <td valign="top" class="value">
                    <g:each in="${userMemberInstanceList?.userInstance.permissions}" var="permission">
                        <ul><li>${permission?.encodeAsHTML()}</li></ul>
                    </g:each>
                </td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.roles.label" default="Roles"/></td>

                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${userMemberInstanceList?.userInstance.roles}" var="r">
                            <li><g:link controller="shiroRole" action="show"
                                        id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                        </g:each>
                    </ul>
                </td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.additionalInfo.label"
                                                         default="Additional Info"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "additionalInfo")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.university.label" default="University"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "university")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.phone.label" default="Phone"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "phone")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.additionalInfo.label"
                                                         default="Additional Info"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "additionalInfo")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.website.label" default="Website"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "website")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.city.label" default="City"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "city")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.country.label" default="Country"/></td>

                <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "country")}</td>


        </tr>
        <!-- #if($facebook) -->
        <tr class="prop">
          <td valign="top" class="name"><g:message code="member.facebook_id.label" default="Facebook ID" /></td>

        <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "facebook_id")}</td>

        </tr>

        <tr class="prop">
          <td valign="top" class="name"><g:message code="member.access_token.label" default="Access Token" /></td>

        <td valign="top" class="value">${fieldValue(bean: userMemberInstanceList.memberInstance, field: "access_token")}</td>

        </tr>
        <!-- #end -->
              
<tr class="prop">
                <td valign="top" class="name"><g:message code="member.active.label" default="Active"/></td>
  <td valign="top" style="text-align: left;" class="value">
                    <g:if test="${userMemberInstanceList.memberInstance?.active}">
                        <li><g:formatBoolean boolean="${userMemberInstanceList.memberInstance?.active}"/></li>
                    </g:if>
                </td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="member.enabled.label" default="Enabled"/></td>

                <td valign="top" style="text-align: left;" class="value">
                    <g:if test="${userMemberInstanceList?.userInstance?.enabled}">
                        <li><g:formatBoolean boolean="${userMemberInstanceList?.userInstance?.enabled}"/></li>
                    </g:if>
                </td>

            </tr>

            </tbody>
        </table>
    </div>
    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${userMemberInstanceList.memberInstance?.id}"/>
            <span class="button"><g:actionSubmit class="edit" action="edit"
                                                 value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
