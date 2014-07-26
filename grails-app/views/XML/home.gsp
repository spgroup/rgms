<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="rgms.publication.XMLController" %>
<%@ page import="rgms.publication.Periodico" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName"><g:message code="xml.label" default="XMLImport"/></g:set>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <g:javascript library="jquery" />
</head>

<body>
<div class="body">
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        </ul>
    </div>
    <br/>
    <g:if test="${request.message}">
        <div id="resultMessage" class="message" role="status">${request.message}</div>
    </g:if>
    <g:form controller="XML" method="post" action="upload" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit"/>
    </g:form>

    <g:if test="${publications?.size()>0}">
        <form id="publicationsForm" method="post" action="save">
            <div id="list-journals" class="content scaffold-list" role="main">
            <table>
                <thead>
                    <tr>
                        <g:set var="checkButtonLabel"><g:message code="xml.checkAll.label" default="All"/></g:set>
                        <td><input type="button" value="${checkButtonLabel}" onclick="checkAll();"/></td>
                        <td><g:message code="xml.importedPublicationType.label"/></td>
                        <td><g:message code="xml.importedPublicationTitle.label"/></td>
                        <td><g:message code="xml.importedPublicationDate.label"/></td>
                        <td><g:message code="xml.importedPublicationAuthors.label"/></td>
                        <td><g:message code="xml.importStatus.label"/></td>
                    </tr>
                </thead>
            <tbody>
                <!-- #if($Article) -->
                <g:if test="${publications?.journals}">
                    <g:each in="${publications?.journals}" status="i" var="journalInstance">
                        <input name="journals${i}.id" type="hidden" value="${journalInstance.id}" />
                        <input name="journals${i}.title" type="hidden" value="${journalInstance.obj.title}" />
                        <input name="journals${i}.publicationDate" type="hidden" value="${journalInstance.obj.publicationDate?.toCalendar().get(Calendar.YEAR)}" />
                        <input name="journals${i}.journal" type="hidden" value="${journalInstance.obj.journal}" />
                        <input name="journals${i}.volume" type="hidden" value="${journalInstance.obj.volume}" />
                        <input name="journals${i}.number" type="hidden" value="${journalInstance.obj.number}" />
                        <input name="journals${i}.pages" type="hidden" value="${journalInstance.obj.pages}" />
                        <tr>
                            <td><input type="checkbox" value="journals${i}" checked/></td>
                            <td><g:message code="periodico.journal.label" default="Journal"/></td>
                            <td>${journalInstance?.obj?.title}</td>
                            <td>${journalInstance?.obj?.publicationDate?.toCalendar().get(Calendar.YEAR)}</td>
                            <td>
                                <g:each in="${journalInstance.obj.authors}" status="j" var="author">
                                    <input name="journals${i}.authors${j}" type="hidden" value="${author}" />
                                    ${author}<br/>
                                </g:each>
                            </td>
                            <td>${journalInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- tools -->
                <g:if test="${publications?.tools}">
                    <g:each in="${publications?.tools}" status="i" var="toolInstance">
                        <input name="tools${i}.id" type="hidden" value="${toolInstance.id}" />
                        <input name="tools${i}.title" type="hidden" value="${toolInstance.obj.title}" />
                        <input name="tools${i}.publicationDate" type="hidden" value="${toolInstance.obj.publicationDate?.toCalendar().get(Calendar.YEAR)}" />
                        <input name="tools${i}.description" type="hidden" value="${toolInstance.obj.description}" />
                        <tr>
                            <td><input type="checkbox" value="tools${i}" checked/></td>
                            <td><g:message code="default.ferramenta.label" default="Tool"/></td>
                            <td>${toolInstance?.obj?.title}</td>
                            <td>${toolInstance?.obj?.publicationDate?.toCalendar().get(Calendar.YEAR)}</td>
                            <td>
                                <g:each in="${toolInstance.obj?.authors}" status="j" var="author">
                                    <input name="tools${i}.authors${j}" type="hidden" value="${author}" />
                                    ${author}<br/>
                                </g:each>
                            </td>
                            <td>${toolInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #end -->
                <!-- books -->
                <g:if test="${publications?.books}">
                    <g:each in="${publications?.books}" status="i" var="bookInstance">
                        <input name="books${i}.id" type="hidden" value="${bookInstance.id}" />
                        <input name="books${i}.title" type="hidden" value="${bookInstance.obj.title}" />
                        <input name="books${i}.publicationDate" type="hidden" value="${bookInstance.obj.publicationDate?.toCalendar().get(Calendar.YEAR)}" />
                        <input name="books${i}.publisher" type="hidden" value="${bookInstance.obj.publisher}" />
                        <input name="books${i}.volume" type="hidden" value="${bookInstance.obj.volume}" />
                        <input name="books${i}.pages" type="hidden" value="${bookInstance.obj.pages}" />
                        <tr>
                            <td><input type="checkbox" value="books${i}" checked/></td>
                            <td><g:message code="default.book.label" default="Book"/></td>
                            <td>${bookInstance?.obj?.title}</td>
                            <td>${bookInstance?.obj?.publicationDate?.toCalendar().get(Calendar.YEAR)}</td>
                            <td>
                                <g:each in="${bookInstance.obj.authors}" status="j" var="author">
                                    <input name="books${i}.authors${j}" type="hidden" value="${author}" />
                                    ${author}<br/>
                                </g:each>
                            </td>
                            <td>${bookInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- bookChapters -->
                <g:if test="${publications?.bookChapters}">
                    <g:each in="${publications?.bookChapters}" status="i" var="bookChapterInstance">
                        <input name="bookChapters${i}.id" type="hidden" value="${bookChapterInstance.id}" />
                        <input name="bookChapters${i}.title" type="hidden" value="${bookChapterInstance.obj.title}" />
                        <input name="bookChapters${i}.publicationDate" type="hidden" value="${bookChapterInstance.obj.publicationDate?.toCalendar().get(Calendar.YEAR)}" />
                        <input name="bookChapters${i}.publisher" type="hidden" value="${bookChapterInstance.obj.publisher}" />
                        <tr>
                            <td><input type="checkbox" value="bookChapters${i}" checked/></td>
                            <td><g:message code="default.bookchapter.label" default="Book Chapter"/></td>
                            <td>${bookChapterInstance?.obj?.title}</td>
                            <td>${bookChapterInstance?.obj?.publicationDate?.toCalendar().get(Calendar.YEAR)}</td>
                            <td>
                                <g:each in="${bookChapterInstance.obj.authors}" status="j" var="author">
                                    <input name="bookChapters${i}.authors${j}" type="hidden" value="${author}" />
                                    ${author}<br/>
                                </g:each>
                            </td>
                            <td>${bookChapterInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- masterDissertation -->
                <g:if test="${publications?.masterDissertation}">
                    <input name="masterDissertation0.id" type="hidden" value="${publications?.masterDissertation.id}" />
                    <input name="masterDissertation0.title" type="hidden" value="${publications?.masterDissertation.obj.title}" />
                    <input name="masterDissertation0.publicationDate" type="hidden" value="${publications?.masterDissertation.obj.publicationDate?.toCalendar().get(Calendar.YEAR)}" />
                    <input name="masterDissertation0.authors" type="hidden" value="${publications?.masterDissertation.obj.authors?.iterator()[0]}" />
                    <input name="masterDissertation0.school" type="hidden" value="${publications?.masterDissertation.obj.school}" />
                    <input name="masterDissertation0.address" type="hidden" value="${publications?.masterDissertation.address}" />
                    <tr>
                        <td><input type="checkbox" value="masterDissertation0" checked/></td>
                        <td><g:message code="default.dissertacao.label" default="Dissertation"/></td>
                        <td>${publications?.masterDissertation?.obj?.title}</td>
                        <td>${publications?.masterDissertation?.obj?.publicationDate?.toCalendar().get(Calendar.YEAR)}</td>
                        <td>${publications?.masterDissertation?.obj?.authors?.iterator()[0]}</td>
                        <td>${publications?.masterDissertation?.status}</td>
                    </tr>
                </g:if>
                <!-- thesis -->
                <g:if test="${publications?.thesis}">
                    <input name="thesis0.id" type="hidden" value="${publications?.thesis.id}" />
                    <input name="thesis0.title" type="hidden" value="${publications?.thesis.obj.title}" />
                    <input name="thesis0.publicationDate" type="hidden" value="${publications?.thesis.obj.publicationDate?.toCalendar().get(Calendar.YEAR)}" />
                    <input name="thesis0.authors" type="hidden" value="${publications?.thesis.obj.authors?.iterator()[0]}" />
                    <input name="thesis0.school" type="hidden" value="${publications?.thesis.obj.school}" />
                    <input name="thesis0.address" type="hidden" value="${publications?.thesis.address}" />
                    <tr>
                        <td><input type="checkbox" value="thesis0" checked/></td>
                        <td><g:message code="default.tese.label" default="Thesis"/></td>
                        <td>${publications?.thesis?.obj?.title}</td>
                        <td>${publications?.thesis?.obj?.publicationDate?.toCalendar().get(Calendar.YEAR)}</td>
                        <td>${publications?.thesis?.obj?.authors?.iterator()[0]}</td>
                        <td>${publications?.thesis?.status}</td>
                    </tr>
                </g:if>
                <!-- conferences -->
                <g:if test="${publications?.conferences}">
                    <g:each in="${publications?.conferences}" status="i" var="conferenceInstance">
                        <input name="conferences${i}.id" type="hidden" value="${conferenceInstance.id}" />
                        <input name="conferences${i}.title" type="hidden" value="${conferenceInstance.obj.title}" />
                        <input name="conferences${i}.publicationDate" type="hidden" value="${conferenceInstance.obj.publicationDate?.toCalendar().get(Calendar.YEAR)}" />
                        <input name="conferences${i}.booktitle" type="hidden" value="${conferenceInstance.obj.booktitle}" />
                        <input name="conferences${i}.pages" type="hidden" value="${conferenceInstance.obj.pages}" />
                        <tr>
                            <td><input type="checkbox" value="conferences${i}" checked/></td>
                            <td><g:message code="default.conferencia.label" default="Conference"/></td>
                            <td>${conferenceInstance?.obj?.title}</td>
                            <td>${conferenceInstance?.obj?.publicationDate?.toCalendar().get(Calendar.YEAR)}</td>
                            <td>
                                <g:each in="${conferenceInstance.obj.authors}" status="j" var="author">
                                    <input name="conferences${i}.authors${j}" type="hidden" value="${author}" />
                                    ${author}<br/>
                                </g:each>
                            </td>
                            <td>${conferenceInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #if($researchLine) -->
                <g:if test="${publications?.researchLines}">
                    <g:each in="${publications?.researchLines}" status="i" var="researchLineInstance">
                        <input name="researchLines${i}.id" type="hidden" value="${researchLineInstance.id}" />
                        <input name="researchLines${i}.name" type="hidden" value="${researchLineInstance.obj.name}" />
                        <input name="researchLines${i}.description" type="hidden" value="${researchLineInstance.obj.description}" />
                        <tr>
                            <td><input type="checkbox" value="researchLines${i}" checked/></td>
                            <td><g:message code="technicalReport.researchLine.label" default="Research Line"/></td>
                            <td>${researchLineInstance?.obj?.name}</td>
                            <td></td>
                            <td></td>
                            <td>${researchLineInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #end -->
                <!-- #if($researchProject) -->
                <g:if test="${publications?.researchProjects}">
                    <g:each in="${publications?.researchProjects}" status="i" var="projectInstance">
                        <input name="researchProjects${i}.id" type="hidden" value="${projectInstance.id}" />
                        <input name="researchProjects${i}.projectName" type="hidden" value="${projectInstance.obj.projectName}" />
                        <input name="researchProjects${i}.description" type="hidden" value="${projectInstance.obj.description}" />
                        <input name="researchProjects${i}.status" type="hidden" value="${projectInstance.obj.status}" />
                        <input name="researchProjects${i}.responsible" type="hidden" value="${projectInstance.obj.responsible}" />
                        <input name="researchProjects${i}.startYear" type="hidden" value="${projectInstance.obj.startYear}" />
                        <input name="researchProjects${i}.endYear" type="hidden" value="${projectInstance.obj.endYear}" />
                        <!-- #if($funder) -->
                            <g:each in="${projectInstance.obj.funders}" status="j" var="funder">
                                <input name="researchProjects${i}.funders${j}.name" type="hidden" value="${funder?.name}" />
                                <input name="researchProjects${i}.funders${j}.code" type="hidden" value="${funder?.code}" />
                                <input name="researchProjects${i}.funders${j}.nature" type="hidden" value="${funder?.nature}" />
                            </g:each>
                        <!-- #end -->
                        <tr>
                            <td><input type="checkbox" value="researchProjects${i}" checked/></td>
                            <td><g:message code="researchProject.label" default="Research Project"/></td>
                            <td>${projectInstance?.obj?.projectName}</td>
                            <td>${projectInstance?.obj?.startYear}</td>
                            <td><!-- #if($funder) -->
                                <g:each in="${projectInstance.obj.members}" status="j" var="member">
                                    <input name="researchProjects${i}.members.${j}" type="hidden" value="${member}" />
                                    ${member}<br/>
                                </g:each><!-- #end -->
                            </td>
                            <td>${projectInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #end -->
                <!-- #if(Orientation) -->
                <g:if test="${publications?.orientations}">
                    <g:each in="${publications?.orientations}" status="i" var="orientationInstance">
                        <input name="orientations${i}.id" type="hidden" value="${orientationInstance.id}" />
                        <input name="orientations${i}.tipo" type="hidden" value="${orientationInstance.obj.tipo}" />
                        <input name="orientations${i}.orientando" type="hidden" value="${orientationInstance.obj.orientando}" />
                        <input name="orientations${i}.tituloTese" type="hidden" value="${orientationInstance.obj.tituloTese}" />
                        <input name="orientations${i}.anoPublicacao" type="hidden" value="${orientationInstance.obj.anoPublicacao}" />
                        <input name="orientations${i}.instituicao" type="hidden" value="${orientationInstance.obj.instituicao}" />
                        <input name="orientations${i}.curso" type="hidden" value="${orientationInstance.obj.curso}" />
                        <tr>
                            <td><input type="checkbox" value="orientations${i}" checked/></td>
                            <g:set var="type" value="${orientationInstance?.obj?.tipo}" />
                            <td><g:message code="xml.importOrientation.label" args="[type]" default="Orientation Type: "/></td>
                            <td>${orientationInstance?.obj?.tituloTese}</td>
                            <td>${orientationInstance?.obj?.anoPublicacao}</td>
                            <td>${orientationInstance?.obj?.orientando}</td>
                            <td>${orientationInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #end -->
            </tbody>
            </table>
            </div>

            <fieldset class="buttons">
                <g:set var="saveButtonLabel"><g:message code="xml.button.save.label" default="Save"/></g:set>
                <g:submitButton name="create" class="save" value="${saveButtonLabel}" />
            </fieldset>
        </form>
    </g:if>

    <script language="javascript" type="text/javascript">
        var checkedAll = true;

        $("#publicationsForm").submit(function(){
            $(":checkbox").each(function() {
                if (!$(this).is(':checked')) {
                    var name = $(this).val()+"\\.";
                    $("input[type=hidden][name^="+name+"]").val("");
                }
            });
        });

        function checkAll(){
           if(checkedAll){
               checkedAll = false;
               $(":checkbox").prop("checked", false);
           }
           else {
               checkedAll = true;
               $(":checkbox").prop("checked", true);
           }
        }
    </script>

</div>
</body>
</html>