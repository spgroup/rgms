<%@ page import="rgms.member.ResearchGroup" %>



<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="researchGroup.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" maxlength="10" required="" value="${researchGroupInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'description', 'error')} required">
    <label for="description">
        <g:message code="researchGroup.description.label" default="Description"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textArea name="description" cols="40" rows="5" maxlength="1000" required=""
                value="${researchGroupInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'twitter', 'error')} ">
    <label for="twitter">
        <g:message code="researchGroup.twitter.label" default="Twitter"/>

    </label>
    <g:textField name="twitter" value="${researchGroupInstance?.twitter}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'sigla', 'error')} required">
    <label for="sigla">
        <g:message code="researchGroup.sigla.label" default="Sigla"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="sigla" maxlength="10" required="" value="${researchGroupInstance?.sigla}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'childOf', 'error')} ">
    <label for="childOf">
        <g:message code="researchGroup.childOf.label" default="Child Of"/>

    </label>
    <g:select id="childOf" name="childOf.id" from="${rgms.member.ResearchGroup.list()}" optionKey="id"
              value="${researchGroupInstance?.childOf?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'memberships', 'error')} ">
    <label for="memberships">
        <g:message code="researchGroup.memberships.label" default="Memberships"/>

    </label>

    <ul class="one-to-many">
        <g:each in="${researchGroupInstance?.memberships ?}" var="m">
            <li><g:link controller="membership" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="membership" action="create"
                    params="['researchGroup.id': researchGroupInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'membership.label', default: 'Membership')])}</g:link>
        </li>
    </ul>

</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'news', 'error')} ">
    <label for="news">
        <g:message code="researchGroup.news.label" default="News"/>

    </label>

    <ul class="one-to-many">
        <g:each in="${researchGroupInstance?.news ?}" var="n">
            <li><g:link controller="news" action="show" id="${n.id}">${n?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="news" action="create"
                    params="['researchGroup.id': researchGroupInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'news.label', default: 'News')])}</g:link>
        </li>
    </ul>

</div>

