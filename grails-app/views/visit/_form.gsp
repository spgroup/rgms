<%@ page import="rgms.visit.Visit" %>

<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'initialDate', 'error')} required">
    <label for="dataInicio">
        <g:message code="visit.initialDate.label" default="Initial Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="initialDate" precision="day"  value="${visitInstance?.initialDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'finalDate', 'error')} required">
    <label for="dataFim">
        <g:message code="visit.finalDate.label" default="Final Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="finalDate" precision="day"  value="${visitInstance?.finalDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'visitor', 'error')} required">
    <label for="visitor">
        <g:message code="visit.visitor.label" default="Visitor" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField id="visitor" name="nameVisitor" from="${rgms.visit.Visitor.list()}" optionKey="id" required="" value="${fieldValue(bean: visitInstance, field: "visitor.name")}" class="many-to-one"/>
</div>

<!-- #if( $reserchgroupobrigatorio ) -->
<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'researchGroup', 'error')}">
    <label for="researchGroup">
        <g:message code="visit.researchGroup.label" default="Research Group" />
    </label>
    <g:select id="researchGroup" name="researchGroup.id" from="${rgms.member.ResearchGroup.list()}" optionKey="id" value="${visitInstance?.researchGroup?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>
<!-- #end  -->

<!-- #if( $descricaovisita ) -->
<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="visit.descricao.label" default="Description" />
    </label>
    <g:textArea name="description" cols="40" rows="5" maxlength="100000" value="${visitInstance?.description}"/>
</div>
<!-- #end -->
