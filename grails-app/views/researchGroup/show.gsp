<%@ page import="rgms.member.ResearchGroup" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'researchGroup.label', default: 'ResearchGroup')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<style>
    .my_div{
        display: inline;
    }

</style>

<!--  #if($XML) -->
<div class="my_div"><g:jasperReport jasper="report" format="XML" name="export" style="display: block">
    <input type="hidden" name="researchGroup_id" value="${researchGroupInstance?.id}"/>
</g:jasperReport>
</div>
<!-- #end -->

<!-- #if($HTML) -->
<div class="my_div"><g:jasperReport jasper="report" format="HTML" name="export" style="display: block">
    <input type="hidden" name="researchGroup_id" value="${researchGroupInstance?.id}"/>
</g:jasperReport>
</div>
<!-- #end -->

<!-- #if($PDF) -->
<div class="my_div"><g:jasperReport jasper="report" format="PDF" name="export" style="display: block">
    <input type="hidden" name="researchGroup_id" value="${researchGroupInstance?.id}"/>
</g:jasperReport>
</div>
<!-- #end -->



<a href="#show-researchGroup" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                    default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-researchGroup" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list researchGroup">

        <g:if test="${researchGroupInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="researchGroup.name.label"
                                                                        default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${researchGroupInstance}"
                                                                                        field="name"/></span>

            </li>
        </g:if>

        <g:if test="${researchGroupInstance?.description}">
            <li class="fieldcontain">
                <span id="description-label" class="property-label"><g:message code="researchGroup.description.label"
                                                                               default="Description"/></span>

                <span class="property-value" aria-labelledby="description-label"><g:fieldValue
                        bean="${researchGroupInstance}" field="description"/></span>

            </li>
        </g:if>

        <g:if test="${researchGroupInstance?.twitter}">
            <li class="fieldcontain">
                <span id="twitter-label" class="property-label"><g:message code="researchGroup.twitter.label"
                                                                           default="Twitter"/></span>

                <span class="property-value" aria-labelledby="twitter-label"><g:fieldValue
                        bean="${researchGroupInstance}" field="twitter"/></span>

            </li>
        </g:if>

        <g:if test="${researchGroupInstance?.sigla}">
            <li class="fieldcontain">
                <span id="sigla-label" class="property-label"><g:message code="researchGroup.sigla.label"
                                                                         default="Sigla"/></span>

                <span class="property-value" aria-labelledby="sigla-label"><g:fieldValue bean="${researchGroupInstance}"
                                                                                         field="sigla"/></span>

            </li>
        </g:if>

        <g:if test="${researchGroupInstance?.childOf}">
            <li class="fieldcontain">
                <span id="childOf-label" class="property-label"><g:message code="researchGroup.childOf.label"
                                                                           default="Child Of"/></span>

                <span class="property-value" aria-labelledby="childOf-label"><g:link controller="researchGroup"
                                                                                     action="show"
                                                                                     id="${researchGroupInstance?.childOf?.id}">${researchGroupInstance?.childOf?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>

        <g:if test="${researchGroupInstance?.memberships}">
            <li class="fieldcontain">
                <span id="memberships-label" class="property-label"><g:message code="researchGroup.memberships.label"
                                                                               default="Memberships"/></span>

                <g:each in="${researchGroupInstance.memberships}" var="m">
                    <span class="property-value" aria-labelledby="memberships-label"><g:link controller="membership"
                                                                                             action="show"
                                                                                             id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

        <g:if test="${researchGroupInstance?.news}">
            <li class="fieldcontain">
                <span id="news-label" class="property-label"><g:message code="researchGroup.news.label"
                                                                        default="News"/></span>

                <g:each in="${researchGroupInstance.news}" var="n">
                    <span class="property-value" aria-labelledby="news-label"><g:link controller="news" action="show"
                                                                                      id="${n.id}">${n?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${researchGroupInstance?.id}"/>
            <g:link class="edit" action="edit" id="${researchGroupInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
