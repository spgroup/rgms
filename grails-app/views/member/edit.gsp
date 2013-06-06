


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${memberInstance}">
            <div class="errors">
                <g:renderErrors bean="${memberInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${memberInstance?.id}" />
                <g:hiddenField name="version" value="${memberInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="member.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${memberInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username"><g:message code="member.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" maxlength="20" value="${memberInstance?.username}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email"><g:message code="member.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${memberInstance?.email}" />
                                </td>
                            </tr>
<!-- #if($twitter) -->
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="twitterAccessToken"><g:message code="member.twitterAccessToken.label" default="Twitter Access Token" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'twitterAccessToken', 'errors')}">
                                    <g:textField name="twitterAccessToken" value="${memberInstance?.twitterAccessToken}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="twitterAccessSecret"><g:message code="member.twitterAccessSecret.label" default="Twitter Access Secret" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'twitterAccessSecret', 'errors')}">
                                    <g:textField name="twitterAccessSecret" value="${memberInstance?.twitterAccessSecret}" />
                                </td>
                            </tr>
<!-- #end -->
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="roles"><g:message code="member.roles.label" default="Roles" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'roles', 'errors')}">
                                    <shiroui:roleSelect name="roles" size="10" value="${memberInstance?.roles}"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="permissions"><g:message code="member.permissions.label" default="Permissions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'permissions', 'errors')}">
                                    <shiroui:permissionSelect name="permissions" size="10" value="${memberInstance?.permissions}"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="university"><g:message code="member.university.label" default="University" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'university', 'errors')}">
                                    <g:textField name="university" value="${memberInstance?.university}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="phone"><g:message code="member.phone.label" default="Phone" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'phone', 'errors')}">
                                    <g:textField name="phone" value="${memberInstance?.phone}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="website"><g:message code="member.website.label" default="Website" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'website', 'errors')}">
                                    <g:textField name="website" value="${memberInstance?.website}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="city"><g:message code="member.city.label" default="City" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'city', 'errors')}">
                                    <g:textField name="city" value="${memberInstance?.city}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="country"><g:message code="member.country.label" default="Country" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'country', 'errors')}">
                                    <g:textField name="country" value="${memberInstance?.country}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="active"><g:message code="member.active.label" default="Active" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'active', 'errors')}">
                                    <g:checkBox name="active" value="${memberInstance?.active}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="additionalInfo"><g:message code="member.additionalInfo.label" default="Additional Info" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'additionalInfo', 'errors')}">
                                    <g:textArea name="additionalInfo" value="${memberInstance?.additionalInfo}"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="status"><g:message code="member.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'status', 'errors')}">
                                    <g:select name="status" from="${["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"]}" value="${memberInstance?.status}" noSelection="['':'-Choose your status-']"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled"><g:message code="member.enabled.label" default="Enabled" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'enabled', 'errors')}">
                                    <g:checkBox name="enabled" value="${memberInstance?.enabled}" />
                                </td>
                            </tr>
                            
                            
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
<!--                  <span class="button"><g:actionSubmit title="Save changes" class="save" value="Save historic" action="update" onclick="return confirm('Are you sure?')" /></span>-->
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
