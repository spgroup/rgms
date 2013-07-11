<%@ page import="rgms.visit.Visit" %>



<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'dataInicio', 'error')} required">
	<label for="dataInicio">
		<g:message code="visit.dataInicio.label" default="Data Inicio" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dataInicio" precision="day"  value="${visitInstance?.dataInicio}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'dataFim', 'error')} required">
	<label for="dataFim">
		<g:message code="visit.dataFim.label" default="Data Fim" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dataFim" precision="day"  value="${visitInstance?.dataFim}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'visitor', 'error')} required">
	<label for="visitor">
		<g:message code="visit.visitor.label" default="Visitor" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField id="visitor" name="name" from="${rgms.visit.Visitor.list()}" optionKey="id" required="" value="${visitInstance?.visitor}" class="many-to-one"/>
</div>

<!-- #if( $reserchgroupobrigatorio ) -->
<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'researchGroup', 'error')} required">
	<label for="researchGroup">
		<g:message code="visit.visitor.label" default="Research Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="researchGroup" name="nameGroup" from="${rgms.member.ResearchGroup.list()}" valGroue="${visitInstance?.researchGroup?.id}" class="many-to-one"/>
</div>
<!-- #end  -->


<!-- #if($descricaovisita) -->
<div class="fieldcontain ${hasErrors(bean: technicalReportInstance, field: 'descricao', 'error')} ">
	<label for="descricao">
		<g:message code="ferramenta.descricao.label" default="Descrição" />
	</label>
	<g:textArea name="descricao" cols="40" rows="5" maxlength="100000" value="${visitInstance?.descricao}"/>
</div>
<!-- #end -->
