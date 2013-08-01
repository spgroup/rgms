<!-- #literal() -->
<%@ page import="rgms.member.ResearchGroup" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'researchGroup.label', default: 'Research Group')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
-<!-- #if($XML) -->
-  <br>
-  <g:jasperReport jasper="report" format="XML" name="export">
    <input type="hidden" name="research_group_id" value="${researchGroupInstance?.id}" />
</g:jasperReport>
<!-- #end -->

-<!-- #if($HTML)  -->
-  <br>
-  <g:jasperReport jasper="report" format="HTML" name="export">
    <input type="hidden" name="research_group_id" value="${researchGroupInstance?.id}" />
</g:jasperReport>
<!--#end -->

 <!-- #if($PDF) -->
-<br>
-<g:jasperReport jasper="report" format="PDF" name="export">
    <input type="hidden" name="research_group_id" value="${researchGroupInstance?.id}"/>
</g:jasperReport>
<!--#end -->

<g:link class="edit" action="updateNewsFromTwitter" id="${researchGroupInstance?.id}">
    <g:message code="default.button.update.twitter.label" default="xxx"/>
</g:link>

<g:link class="edit" action="updateNewsFromTwitter" id="${researchGroupInstance?.id}">
    <g:message code="default.button.update.twitter.label" default="xxx"/>
</g:link>

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

        <g:if test="${researchGroupInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="researchGroup.twitter.label"
                                                                        default="Twitter"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${researchGroupInstance}"
                                                                                        field="twitter"/></span>

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
    <!--#end -->
    <!-- #if($researchGroupHierarchy) -->
        <g:if test="${researchGroupInstance?.childOf}">
            <li class="fieldcontain">
                <span id="childOf-label" class="property-label"><g:message code="researchGroup.childOf.label"
                                                                           default="Child Of"/></span>

                <span class="property-value" aria-labelledby="childOf-label"><g:link controller="researchGroup"
                                                                                     action="show"
                                                                                     id="${researchGroupInstance?.childOf?.id}">${researchGroupInstance?.childOf?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>


    <!-- #end -->
    <!-- #literal() -->
        <g:if test="${currentMemberships}">
            <li class="fieldcontain">
                <span id="members-label" class="property-label"><g:message code="researchGroup.members.label"
                                                                           default="Members"/></span>

                <g:each in="${currentMemberships}" var="m">
                    <span class="property-value" aria-labelledby="members-label"><g:link controller="member"
                                                                                         action="show"
                                                                                         id="${m.member.id}">${m.member?.encodeAsHTML()}</g:link></span>

                </g:each>

            </li>
        </g:if>

        <g:if test="${currentNews}">
            <li class="fieldcontain">
                <span id="members-label" class="property-label"><g:message code="researchGroup.news.label"
                                                                           default="News"/></span>

                <table>

                    <g:each in="${currentNews}" var="n" status="index">
                        <tr>
                            <td>${index + 1} -</td><td>${n.description?.encodeAsHTML()}</td>
                        </tr>
                    </g:each>
                </table>

            </li>
        </g:if>

    </ol>
    <ol class="property-list researchGroup">
        <li class="fieldcontain">
            <label>Inference:</label>
            <g:select name="members" from="${publicationsInstance}" multiple="multiple" optionKey="id" size="5"
                      value="${publicationsInstance*.id}" id="${researchGroupInstance?.id}"" class="many-to-many"/>
        </li>
    </ol>

    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${researchGroupInstance?.id}"/>
            <g:link class="edit" action="edit" name="botaoEditar" id="${researchGroupInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
<!-- #end -->
