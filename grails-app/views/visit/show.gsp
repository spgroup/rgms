<%@ page import="rgms.visit.Visit" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'visit.label', default: 'Visit')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-visit" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-visit" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list visit">

        <g:if test="${visitInstance?.dataInicio}">
            <li class="fieldcontain">
                <span id="dataInicio-label" class="property-label"><g:message code="visit.dataInicio.label"
                                                                              default="Data Inicio"/></span>

                <span class="property-value" aria-labelledby="dataInicio-label"><g:formatDate format="dd-MM-yyyy"
                                                                                              date="${visitInstance?.dataInicio}"/></span>

            </li>
        </g:if>

        <g:if test="${visitInstance?.dataFim}">
            <li class="fieldcontain">
                <span id="dataFim-label" class="property-label"><g:message code="visit.dataFim.label"
                                                                           default="Data Fim"/></span>

                <span class="property-value" aria-labelledby="dataFim-label"><g:formatDate format="dd-MM-yyyy"
                                                                                           date="${visitInstance?.dataFim}"/></span>

            </li>
        </g:if>

        <g:if test="${visitInstance?.visitor}">
            <li class="fieldcontain">
                <span id="visitor-label" class="property-label"><g:message code="visit.visitor.label"
                                                                           default="Visitor"/></span>

                <span class="property-value" aria-labelledby="visitor-label" id="${visitInstance?.visitor?.id}">${visitInstance?.visitor?.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${visitInstance?.researchGroup}">
            <li class="fieldcontain">
                <span id="visitor-label" class="property-label"><g:message code="visit.visitor.label"
                                                                           default="Research Group"/></span>

                <span class="property-value" aria-labelledby="visitor-label">
                    <g:link controller="researchGroup" action="show" id="${visitInstance?.researchGroup?.id}">
                        ${visitInstance?.researchGroup?.encodeAsHTML()}
                    </g:link>
                </span>

            </li>
        </g:if>

    <!-- #if( $descricaovisita ) -->
        <g:if test="${visitInstance?.descricao}">
            <li class="fieldcontain">
                <span id="descricao-label" class="property-label"><g:message code="ferramenta.descricao.label"
                                                                             default="Descricao"/></span>

                <span class="property-value" aria-labelledby="visitor-label">
                    ${visitInstance?.descricao}

                </span>

            </li>
        </g:if>
    <!-- #end -->

    </ol>

    <!--#if( $Twitter ) -->
    <!-- Twitter start -->
    <a href="javascript:window.open('https://twitter.com/intent/tweet?button_hashtag=RGMS&text=Visit:%20${visitInstance?.visitor}', 'Tweteet It', 'width=450,height=250');"
       class="twitter-hashtag-button"
       data-related="rgms_ufpe"
       id="button_twitter"
       target="_blank">
        <img id="share_twitter2"
             src="http://www.tabpress.com/fbui_share/share_button.png">
    </a>
    <script>
        $('#button_twitter').live('click', function (e) {
            $.get("http://localhost:8080/rgms/notifyTwitter/visit/${visitInstance?.visitor}");
        });
    </script>
<!-- Twitter end -->
<!--#end -->

    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${visitInstance?.id}"/>
            <g:link class="edit" action="edit" id="${visitInstance?.id}"><g:message code="default.button.edit.label"
                                                                                    default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
