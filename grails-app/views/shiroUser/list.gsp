

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'shiroUser.label', default: 'ShiroUser')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'shiroUser.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="firstName" title="${message(code: 'shiroUser.firstName.label', default: 'First Name')}" />
                        
                            <g:sortableColumn property="lastName" title="${message(code: 'shiroUser.lastName.label', default: 'Last Name')}" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'shiroUser.username.label', default: 'Username')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'shiroUser.email.label', default: 'Email')}" />
                        
                            <th>${message(code: 'shiroUser.roles.label', default: 'Roles')}</th>
                        
                            <th>${message(code: 'shiroUser.permissions.label', default: 'Permissions')}</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${shiroUserInstanceList}" status="i" var="shiroUserInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${shiroUserInstance.id}">${fieldValue(bean: shiroUserInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: shiroUserInstance, field: "firstName")}</td>
                        
                            <td>${fieldValue(bean: shiroUserInstance, field: "lastName")}</td>
                        
                            <td>${fieldValue(bean: shiroUserInstance, field: "username")}</td>
							
                            <td><a href="mailto:${fieldValue(bean: shiroUserInstance, field: "email")}">${fieldValue(bean: shiroUserInstance, field: "email")}</a></td>
                        
							<td>${fieldValue(bean: shiroUserInstance, field: "roles")}</td>
							
							<td>${fieldValue(bean: shiroUserInstance, field: "permissions")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${shiroUserInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
