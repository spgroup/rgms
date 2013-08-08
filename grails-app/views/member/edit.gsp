<%@ page import="rgms.member.Member" %>
<!doctype html>



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
                            <!--#if($facebook) -->
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Atualizar Access Token: </label>
                                </td>
                                <td valign="top" >
                                    <div id="login_" style="text-align: left;">
                                        <div style="text-align: left;">
                                            <input id="login" style="text-align: left;" value="Login" type="button">
                                        </div>
                                        <div id="user-info" style="display: none;"></div>

                                        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>

                                        <div id="fb-root"></div>

                                        <script src="http://connect.facebook.net/en_US/all.js"></script>
                                        
                                        <script>
                                              // initialize the library with the API key
                                            FB.init({ appId: ${grailsApplication.config.appid},oauth: true});

                                              // fetch the status on load
                                            //FB.getLoginStatus(handleSessionResponse2);

                                            $('#login').bind('click', function() {
                                                FB.login(handleSessionResponse,{scope: 'email,publish_actions'});
                                            });

                                            $('#logout').bind('click', function() {
                                                FB.logout(handleSessionResponse);
                                            });

                                            $('#disconnect').bind('click', function() {
                                                FB.api({ method: 'Auth.revokeAuthorization' }, function(response) {
                                                    clearDisplay();
                                                });
                                            });

                                              // no user, clear display
                                            function clearDisplay() {
                                                $('#user-info').hide('fast');
                                            }

                                              // handle a session response from any of the auth related calls
                                            function handleSessionResponse(response) {
                                                // if we dont have a session, just hide the user info
                                                if (!(response.status=="connected")) {
                                                  clearDisplay();
                                                  return;
                                                }
                                                document.getElementById("access_token").value = response.authResponse.accessToken
                                                document.getElementById("facebook_id").value = response.authResponse.userID
                                            }
                                               
                                        </script>

                                    </div>
                                </td>
                            </tr>    
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="facebook_id"><g:message code="member.facebook_id.label" default="Facebook ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'facebook_id', 'errors')}">
                                    <g:textField name="facebook_id" value="${memberInstance?.facebook_id}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="access_token"><g:message code="member.access_token.label" default="Access Token" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: memberInstance, field: 'access_token', 'errors')}">
                                    <g:textField id="access_token" name="access_token" value="${memberInstance?.access_token}" />
                                </td>
                            </tr>
                            <!-- #end -->
                        
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
