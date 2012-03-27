


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${memberInstance}">
            <div class="errors">
                <g:renderErrors bean="${memberInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="firstName"><g:message code="member.firstName.label" default="First Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'firstName', 'errors')}">
                                    <g:textField name="firstName" value="${memberInstance?.firstName}" />
                                </td>
                            </tr>
                            
<!--                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="middleName"><g:message code="member.middleName.label" default="Middle Name" /></label>
                                </td>
                                <td valign="top" class="value {hasErrors(bean: memberInstance, field: 'middleName', 'errors')}">
                                    <g:textField name="middleName" value="{memberInstance?.middleName}" />
                                </td>
                            </tr>-->
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastName"><g:message code="member.lastName.label" default="Last Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'lastName', 'errors')}">
                                    <g:textField name="lastName" value="${memberInstance?.lastName}" />
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
                        
<!--                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="university"><g:message code="member.university.label" default="University" /></label>
                                </td>
                                <td valign="top" class="value {hasErrors(bean: memberInstance, field: 'university', 'errors')}">
                                    <g:textField name="university" value="{memberInstance?.university}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="phone"><g:message code="member.phone.label" default="Phone" /></label>
                                </td>
                                <td valign="top" class="value {hasErrors(bean: memberInstance, field: 'phone', 'errors')}">
                                    <g:textField name="phone" value="{memberInstance?.phone}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="website"><g:message code="member.website.label" default="Website" /></label>
                                </td>
                                <td valign="top" class="value {hasErrors(bean: memberInstance, field: 'website', 'errors')}">
                                    <g:textField name="website" value="{memberInstance?.website}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="city"><g:message code="member.city.label" default="City" /></label>
                                </td>
                                <td valign="top" class="value {hasErrors(bean: memberInstance, field: 'city', 'errors')}">
                                    <g:textField name="city" value="{memberInstance?.city}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="country"><g:message code="member.country.label" default="Country" /></label>
                                </td>
                                <td valign="top" class="value {hasErrors(bean: memberInstance, field: 'country', 'errors')}">
                                    <g:textField name="country" value="{memberInstance?.country}" />
                                </td>
                            </tr>-->
                            
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
                                    <g:select name="status" from="${["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"]}" value="${memberInstance?.status}" noSelection="['':'-Choose status-']"/>
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
