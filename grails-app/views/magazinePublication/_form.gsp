<%@ page import="rgms.publication.MagazinePublication" %>



<div class="fieldcontain ${hasErrors(bean: magazinePublicationInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="magazinePublication.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="50" required="" value="${magazinePublicationInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magazinePublicationInstance, field: 'number', 'error')} required">
	<label for="number">
		<g:message code="magazinePublication.number.label" default="Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="number" type="number" value="${magazinePublicationInstance.number}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: magazinePublicationInstance, field: 'page', 'error')} ">
	<label for="page">
		<g:message code="magazinePublication.page.label" default="Page" />
		
	</label>
	<g:textField name="page" value="${magazinePublicationInstance?.page}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magazinePublicationInstance, field: 'place', 'error')} ">
	<label for="place">
		<g:message code="magazinePublication.place.label" default="Place" />
		
	</label>
	<g:textField name="place" value="${magazinePublicationInstance?.place}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magazinePublicationInstance, field: 'month', 'error')} ">
	<label for="month">
		<g:message code="magazinePublication.month.label" default="Month" />
		
	</label>
	<g:textField name="month" value="${magazinePublicationInstance?.month}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: magazinePublicationInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="magazinePublication.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="year" type="number" value="${magazinePublicationInstance.year}" required=""/>
</div>

