

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
    <title><g:message code="default.searchByTitle.label" /></title>
</head>
<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
    <span class="menuButton">
        <a href="./" > Voltar </a>
    </span>
    <g:form controller="member" action="searchByUniversity">
        <div>
            <label for="name">Procurar por universidade</label>
            <input class="inputUniversity" type="text" name="name" />
        </div>

        <input type="submit" class="searchByUniversity" value="<g:message code="default.searchByUniversity.label"/>">
    </g:form>
</div>
<div class="body">
    <h1><g:message code="default.searchByUniversity.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <g:if test="${params.name}">

        <div class="list">
            <table>
                <thead>
                <tr>

                    <g:sortableColumn property="id" title="${message(code: 'member.id.label', default: 'Id')}" />

                    <g:sortableColumn property="name" title="${message(code: 'member.name.label', default: 'Name')}" />

                    <g:sortableColumn property="username" title="${message(code: 'member.username.label', default: 'Username')}" />

                    <g:sortableColumn property="enabled" title="${message(code: 'member.enabled.label', default: 'Enabled')}" />

                    <g:sortableColumn property="email" title="${message(code: 'member.email.label', default: 'Email')}" />

                    <th>${message(code: 'member.roles.label', default: 'Roles')}</th>

                    <th>${message(code: 'member.permissions.label', default: 'Permissions')}</th>

                </tr>
                </thead>
                <tbody>
                <g:each in="${userMemberInstanceList}" status="i" var="userMemberInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td><g:link action="show" id="${userMemberInstance.member.id}">${fieldValue(bean: userMemberInstance.member, field: "id")}</g:link></td>

                        <td>${fieldValue(bean: userMemberInstance.member, field: "name")}</td>

                        <!--                            <td>{fieldValue(bean: memberInstance, field: "lastName")}</td>-->

                        <td>${fieldValue(bean: userMemberInstance.user, field: "username")}</td>

                        <td>${fieldValue(bean: userMemberInstance?.user, field: "enabled")}</td>

                        <td><a href="mailto:${fieldValue(bean: userMemberInstance.member, field: "email")}">${fieldValue(bean: userMemberInstance.member, field: "email")}</a></td>

                        <td>${fieldValue(bean: userMemberInstance?.user, field: "roles")}</td>

                        <td>${fieldValue(bean: userMemberInstance?.user, field: "permissions")}</td>

                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
        <div class="paginateButtons">
            <g:paginate total="${memberInstanceTotal}" />
        </div>

    </g:if>
    <g:else>
        Digite um nome de membro no campo acima para efetuar a buscar.
    </g:else>
</div>
</body>
</html>
