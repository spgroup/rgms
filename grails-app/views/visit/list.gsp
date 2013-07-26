<%@ page import="rgms.visit.Visit" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'visit.label', default: 'Visit')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>

    <link rel="stylesheet" type="text/css"
          href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>

    <script type="text/javascript">
        $(function () {
            // Dialog
            $('.dialog').dialog({
                autoOpen: false,
                width: 600,
                buttons: {
                    "Ok": function () {
                        $(this).dialog("close");
                    }
                }
            });

            // Dialog Link
            $('.dialog_link').click(function () {
                $('.dialog').dialog('close');

                var id_link = $(this).attr('id');
                var index = id_link.split('_');

                $('#txt_' + index[1]).dialog('open');

                return false;
            });
        });
    </script>

    <style type="text/css">

    .dialog_link {
        padding: .4em 1em .4em 20px;
        text-decoration: none;
        position: relative;
    }

    .dialog_link span.ui-icon {
        margin: 0 5px 0 0;
        position: absolute;
        left: .2em;
        top: 50%;
        margin-top: -8px;
    }
    </style>
</head>

<body>
<a href="#list-visit" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-visit" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="dataInicio"
                              title="${message(code: 'visit.dataInicio.label', default: 'Data Inicio')}"/>

            <g:sortableColumn property="dataFim" title="${message(code: 'visit.dataFim.label', default: 'Data Fim')}"/>

            <th><g:message code="visit.visitor.label" default="Visitor"/></th>

            <!-- #if( $reserchgroupobrigatorio ) -->
            <th><g:message code="visit.researchGroup.label" default="Research Group"/></th>
            <!-- #end -->

            <!-- #if( $descricaovisita ) -->
            <th><g:message code="visit.descricao.label" default="Descrição"/></th>
            <!-- #end -->

        </tr>
        </thead>
        <tbody>
        <g:each in="${visitInstanceList}" status="i" var="visitInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td>
                    <g:link action="show" id="${visitInstance.id}">
                        <g:formatDate format="dd/MM/yyyy">
                            ${fieldValue(bean: visitInstance, field: "dataInicio")}
                        </g:formatDate>
                    </g:link>
                </td>

                <td><g:formatDate format="dd/MM/yyyy" date="${visitInstance.dataFim}"/></td>

                <td>${fieldValue(bean: visitInstance, field: "visitor")}</td>

                <!-- #if( $reserchgroupobrigatorio ) -->
                <td>${fieldValue(bean: visitInstance, field: "researchGroup")}</td>
                <!-- #end -->

                <!-- #if( $descricaovisita ) -->
                <td>
                    <a href="#" id="lk_1" class="dialog_link ">
                        <span class="ui-icon ui-icon-newwin"></span>
                        ${fieldValue(bean: visitInstance, field: "descricao", maxLengh: "10")}
                    </a>
                </td>

                <div title="Descrição" id="txt_1" class="dialog">
                    ${fieldValue(bean: visitInstance, field: "descricao")}
                </div>
                <!-- #end -->
            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${visitInstanceTotal}"/>
    </div>
</div>
</body>
</html>
