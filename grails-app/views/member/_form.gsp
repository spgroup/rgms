<%@ page import="rgms.member.Member" %>

<g:javascript library="jquery" />

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'name', 'error')} required">
  <label for="name">
    <g:message code="member.name.label" default="Name" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="name" maxlength="50" required="" value="${memberInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'username', 'error')} required">
  <label for="username">
    <g:message code="member.username.label" default="Username" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="username" maxlength="20" required="" value="${memberInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'email', 'error')} required">
  <label for="email">
    <g:message code="member.email.label" default="Email" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="email" maxlength="50" required="" value="${memberInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'roles', 'error')} required">
  <label for="roles">
    <g:message code="member.roles.label" default="Roles" />
  </label>
  <shiroui:roleSelect name="roles" size="10" value="${memberInstance?.roles}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'permissions', 'error')} required">
  <label for="permissions">
    <g:message code="member.permissions.label" default="Permissions" />
  </label>
  <shiroui:permissionSelect name="permissions" size="10" value="${memberInstance?.permissions}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'university', 'error')} required">
  <label for="university">
    <g:message code="member.university.label" default="University" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="university" maxlength="50" required="" value="${memberInstance?.university}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'phone', 'error')} required">
  <label for="phone">
    <g:message code="member.phone.label" default="Phone" />
  </label>
  <g:textField name="phone" maxlength="50" value="${memberInstance?.phone}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'website', 'error')} required">
  <label for="website">
    <g:message code="member.website.label" default="Website" />
  </label>
  <g:textField name="website" maxlength="50"  value="${memberInstance?.website}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'city', 'error')} required">
  <label for="city">
    <g:message code="member.city.label" default="City" />
  </label>
  <g:textField name="city" maxlength="50" value="${memberInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'country', 'error')} required">
  <label for="country">
    <g:message code="member.country.label" default="Country" />
  </label>
  <g:textField name="country" maxlength="50" value="${memberInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'active', 'error')} required">
  <label for="active">
    <g:message code="member.active.label" default="Active" />
  </label>
  <g:checkBox name="active" value="${memberInstance?.active}" />
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'additionalInfo', 'error')} required">
  <label for="additionalInfo">
    <g:message code="member.additionalInfo.label" default="Additional Info" />
  </label>
  <g:textArea name="additionalInfo" value="${memberInstance?.additionalInfo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'status', 'error')} required">
  <label for="status">
    <g:message code="member.status.label" default="Status" />
  </label>
  <g:select name="status" from="${["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"]}" value="${memberInstance?.status}" noSelection="['':'-Choose status-']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'enabled', 'error')} required">
  <label for="enabled">
    <g:message code="member.enabled.label" default="Enabled" />
  </label>
  <g:checkBox name="enabled" value="${memberInstance?.enabled}" />
</div>

