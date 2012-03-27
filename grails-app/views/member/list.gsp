

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'member.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="firstName" title="${message(code: 'member.firstName.label', default: 'First Name')}" />
                        
                            <g:sortableColumn property="lastName" title="${message(code: 'member.lastName.label', default: 'Last Name')}" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'member.username.label', default: 'Username')}" />
                            
                            <g:sortableColumn property="enabled" title="${message(code: 'member.enabled.label', default: 'Enabled')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'member.email.label', default: 'Email')}" />
                        
                            <th>${message(code: 'member.roles.label', default: 'Roles')}</th>
                        
                            <th>${message(code: 'member.permissions.label', default: 'Permissions')}</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${memberInstanceList}" status="i" var="memberInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${memberInstance.id}">${fieldValue(bean: memberInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: memberInstance, field: "firstName")}</td>
                        
                            <td>${fieldValue(bean: memberInstance, field: "lastName")}</td>
                        
                            <td>${fieldValue(bean: memberInstance, field: "username")}</td>
                            
                            <td>${fieldValue(bean: memberInstance, field: "enabled")}</td>
							
                            <td><a href="mailto:${fieldValue(bean: memberInstance, field: "email")}">${fieldValue(bean: memberInstance, field: "email")}</a></td>
                        
							<td>${fieldValue(bean: memberInstance, field: "roles")}</td>
							
							<td>${fieldValue(bean: memberInstance, field: "permissions")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${memberInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
