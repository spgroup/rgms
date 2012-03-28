<%@ page import="rgms.Member" %>



<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="member.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${memberInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="member.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${memberInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'affiliation', 'error')} required">
	<label for="affiliation">
		<g:message code="member.affiliation.label" default="Affiliation" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="affiliation" from="${memberInstance.constraints.affiliation.inList}" required="" value="${memberInstance?.affiliation}" valueMessagePrefix="member.affiliation"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'university', 'error')} required">
	<label for="university">
		<g:message code="member.university.label" default="University" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="university" required="" value="${memberInstance?.university}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'phone', 'error')} ">
	<label for="phone">
		<g:message code="member.phone.label" default="Phone" />
		
	</label>
	<g:textField name="phone" value="${memberInstance?.phone}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'website', 'error')} ">
	<label for="website">
		<g:message code="member.website.label" default="Website" />
		
	</label>
	<g:field type="url" name="website" value="${memberInstance?.website}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'city', 'error')} ">
	<label for="city">
		<g:message code="member.city.label" default="City" />
		
	</label>
	<g:textField name="city" value="${memberInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="member.country.label" default="Country" />
		
	</label>
	<g:textField name="country" value="${memberInstance?.country}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'active', 'error')} ">
	<label for="active">
		<g:message code="member.active.label" default="Active" />
		
	</label>
	<g:checkBox name="active" value="${memberInstance?.active}" />
</div>

