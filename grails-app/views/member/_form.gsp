<%@ page import="rgms.member.Member" %>
<%@ page import="rgms.authentication.User" %>

<g:javascript library="jquery" />

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'name', 'error')} required">
  <label for="name">
    <g:message code="member.name.label" default="Name" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="name" maxlength="50" required="" value="${userMemberInstanceList.memberInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.userInstance, field: 'username', 'error')} required">
  <label for="username">
    <g:message code="member.username.label" default="Username" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="username" maxlength="20" required="" value="${userMemberInstanceList.userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'email', 'error')} required">
  <label for="email">
    <g:message code="member.email.label" default="Email" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="email" maxlength="50" required="" value="${userMemberInstanceList.memberInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.userInstance, field: 'roles', 'error')} required">
  <label for="roles">
    <g:message code="member.roles.label" default="Roles" />
  </label>
  <shiroui:roleSelect name="roles" size="10" value="${userMemberInstanceList.userInstance?.roles}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.userInstance, field: 'permissions', 'error')} required">
  <label for="permissions">
    <g:message code="member.permissions.label" default="Permissions" />
  </label>
  <shiroui:permissionSelect name="permissions" size="10" value="${userMemberInstanceList.userInstance?.permissions}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'university', 'error')} required">
  <label for="university">
    <g:message code="member.university.label" default="University" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="university" maxlength="50" required="" value="${userMemberInstanceList.memberInstance?.university}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'phone', 'error')} required">
  <label for="phone">
    <g:message code="member.phone.label" default="Phone" />
  </label>
  <g:textField name="phone" maxlength="50" value="${userMemberInstanceList.memberInstance?.phone}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'website', 'error')} required">
  <label for="website">
    <g:message code="member.website.label" default="Website" />
  </label>
  <g:textField name="website" maxlength="50" value="${userMemberInstanceList.memberInstance?.website}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'city', 'error')} required">
  <label for="city">
    <g:message code="member.city.label" default="City" />
  </label>
  <g:textField name="city" maxlength="50" required="" value="${userMemberInstanceList.memberInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'country', 'error')} required">
  <label for="country">
    <g:message code="member.country.label" default="Country" />
  </label>
  <g:textField name="country" maxlength="50" required="" value="${userMemberInstanceList.memberInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'active', 'error')} required">
  <label for="active">
    <g:message code="member.active.label" default="Active" />
  </label>
  <g:checkBox name="active" value="${userMemberInstanceList.memberInstance?.active}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'additionalInfo', 'error')} required">
  <label for="additionalInfo">
    <g:message code="member.additionalInfo.label" default="Additional Info" />
  </label>
  <g:textArea name="additionalInfo" value="${userMemberInstanceList.memberInstance?.additionalInfo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.memberInstance, field: 'status', 'error')} required">
  <label for="status">
    <g:message code="member.status.label" default="Status" />
  </label>
  <g:select name="status" from="${["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"]}" value="${userMemberInstanceList.memberInstance?.status}" noSelection="['':'-Choose status-']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userMemberInstanceList.userInstance, field: 'enabled', 'error')} required">
  <label for="enabled">
    <g:message code="member.enabled.label" default="Enabled" />
  </label>
  <g:checkBox name="enabled" value="${userMemberInstanceList.userInstance?.enabled}" />
</div>

