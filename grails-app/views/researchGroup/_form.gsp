<%@ page import="rgms.member.ResearchGroup" %>

<g:javascript library="jquery"/>


<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="researchGroup.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" maxlength="50" required="" value="${researchGroupInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'name', 'error')} required">
    <label for="twitter">
        <g:message code="researchGroup.twitter.label" default="Twitter"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="twitter" maxlength="50" required="" value="${researchGroupInstance?.twitter}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'description', 'error')} required">
    <label for="description">
        <g:message code="researchGroup.description.label" default="Description"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textArea name="description" cols="40" rows="5" maxlength="1000" required=""
                value="${researchGroupInstance?.description}"/>
</div>

<!-- #if($researchGroupHierarchy) -->

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'childOf', 'error')} ">
    <label for="childOf">
        <g:message code="researchGroup.childOf.label" default="Child Of"/>

    </label>
    <g:select id="childOf" name="childOf.id" from="${rgms.member.ResearchGroup.list()}" optionKey="id"
              value="${researchGroupInstance?.childOf?.id}" noSelection="['null': '']" class="many-to-one"/>
</div>
<!-- #end -->
<div>

    <label>
        Research Groups
    </label>
    <ul class="checklist">
        <g:each in="${rgms.member.ResearchGroup.list()}" var="group">
            <li>
                <label for="${group.id}">
                    <g:checkBox name="${group.id}" value="${true}"
                                onclick="${remoteFunction(action: 'edit',
                                        update: [success: 'members_id'],
                                        params: '\'groups=\' + this.name')}"/>
                    ${group.name}
                </label>
            </li>
        </g:each>

    </ul>
</div>
<!--<label>${deb}</label>-->
<div id="members_id" class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'members', 'error')} ">
    <label for="members">
        <g:message code="researchGroup.members.label" default="Members"/>

    </label>
    <g:select name="members" from="${membersInstance}" multiple="multiple" optionKey="id" size="5"
              value="${membersInstance*.id}" class="many-to-many"/>
</div>
