
<%@ page import="rgms.ResearchGroup" %>

<g:javascript library="jquery" />


<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'name', 'error')} required">
  <label for="name">
    <g:message code="researchGroup.name.label" default="Name" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="name" maxlength="50" required="" value="${researchGroupInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'description', 'error')} required">
  <label for="description">
    <g:message code="researchGroup.description.label" default="Description" />
    <span class="required-indicator">*</span>
  </label>
  <g:textArea name="description" cols="40" rows="5" maxlength="1000" required="" value="${researchGroupInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'childOf', 'error')} required">
  <label for="childOf">
    <g:message code="researchGroup.childOf.label" default="Child Of" />
    <span class="required-indicator">*</span>
  </label>
  <g:select id="childOf" name="childOf.id" from="${rgms.ResearchGroup.list()}" optionKey="id" required="" value="${researchGroupInstance?.childOf?.id}" class="many-to-one"/>
</div>

<div>

  <label>
    Research Groups		
  </label>
  <ul class="checklist">
    <g:each in="${rgms.ResearchGroup.list()}" var="group">
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
    <g:message code="researchGroup.members.label" default="Members" />

  </label>
  <g:select name="members" from="${membersInstance}" multiple="multiple" optionKey="id" size="5" value="${membersInstance*.id}" class="many-to-many"/>
</div>
