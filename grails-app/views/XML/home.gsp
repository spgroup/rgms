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
        <g:set var="total" value="${0}"/>
        <g:form action="save">
            <div id="list-journals" class="content scaffold-list" role="main">
            <table>
                <thead>
                    <tr>
                        <td>Publication Type</td>
                        <td>Title</td>
                        <td>Publication Date</td>
                        <td>Authors</td>
                        <td>Import Status</td>
                    </tr>
                </thead>
            <tbody>
                <!-- #if($Article) -->
                <g:if test="${publications?.journals}">
                    <g:each in="${publications?.journals}" status="i" var="journalInstance">
                        <input name="journals${i}.title" type="hidden" value="${journalInstance.obj.title}" />
                        <input name="journals${i}.publicationDate" type="hidden" value="${journalInstance.obj.publicationDate}" />
                        <input name="journals${i}.authors" type="hidden" value="${journalInstance.obj.authors}" />
                        <input name="journals${i}.journal" type="hidden" value="${journalInstance.obj.journal}" />
                        <input name="journals${i}.volume" type="hidden" value="${journalInstance.obj.volume}" />
                        <input name="journals${i}.number" type="hidden" value="${journalInstance.obj.number}" />
                        <input name="journals${i}.pages" type="hidden" value="${journalInstance.obj.pages}" />
                        <tr>
                            <td>Journal</td>
                            <td>${journalInstance?.obj?.title}</td>
                            <td><g:formatDate date="${journalInstance?.obj?.publicationDate}"/></td>
                            <td>${journalInstance?.obj?.authors}</td>
                            <td>${journalInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- tools -->
                <g:if test="${publications?.tools}">
                    <g:each in="${publications?.tools}" status="i" var="toolInstance">
                        <input name="tools${i}.title" type="hidden" value="${toolInstance.obj.title}" />
                        <input name="tools${i}.publicationDate" type="hidden" value="${toolInstance.obj.publicationDate}" />
                        <input name="tools${i}.authors" type="hidden" value="${toolInstance.obj.authors}" />
                        <input name="tools${i}.description" type="hidden" value="${toolInstance.obj.description}" />
                        <tr>
                            <td>Tool</td>
                            <td>${toolInstance?.obj?.title}</td>
                            <td><g:formatDate date="${toolInstance?.obj?.publicationDate}"/></td>
                            <td>${toolInstance?.obj?.authors}</td>
                            <td>${toolInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #end -->
                <!-- books -->
                <g:if test="${publications?.books}">
                    <g:each in="${publications?.books}" status="i" var="bookInstance">
                        <input name="books${i}.title" type="hidden" value="${bookInstance.obj.title}" />
                        <input name="books${i}.publicationDate" type="hidden" value="${bookInstance.obj.publicationDate}" />
                        <input name="books${i}.authors" type="hidden" value="${bookInstance.obj.authors}" />
                        <input name="books${i}.publisher" type="hidden" value="${bookInstance.obj.publisher}" />
                        <input name="books${i}.volume" type="hidden" value="${bookInstance.obj.volume}" />
                        <input name="books${i}.pages" type="hidden" value="${bookInstance.obj.pages}" />
                        <tr>
                            <td>Book</td>
                            <td>${bookInstance?.obj?.title}</td>
                            <td><g:formatDate date="${bookInstance?.obj?.publicationDate}"/></td>
                            <td>${bookInstance?.obj?.authors}</td>
                            <td>${bookInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- bookChapters -->
                <g:if test="${publications?.bookChapters}">
                    <g:each in="${publications?.bookChapters}" status="i" var="bookChapterInstance">
                        <input name="bookChapters${i}.title" type="hidden" value="${bookChapterInstance.obj.title}" />
                        <input name="bookChapters${i}.publicationDate" type="hidden" value="${bookChapterInstance.obj.publicationDate}" />
                        <input name="bookChapters${i}.authors" type="hidden" value="${bookChapterInstance.obj.authors}" />
                        <input name="bookChapters${i}.publisher" type="hidden" value="${bookChapterInstance.obj.publisher}" />
                        <tr>
                            <td>Book Chapter</td>
                            <td>${bookChapterInstance?.obj?.title}</td>
                            <td><g:formatDate date="${bookChapterInstance?.obj?.publicationDate}"/></td>
                            <td>${bookChapterInstance?.obj?.authors}</td>
                            <td>${bookChapterInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- masterDissertation -->
                <g:if test="${publications?.masterDissertation}">
                    <input name="masterDissertation0.title" type="hidden" value="${publications?.masterDissertation.obj.title}" />
                    <input name="masterDissertation0.publicationDate" type="hidden" value="${publications?.masterDissertation.obj.publicationDate}" />
                    <input name="masterDissertation0.authors" type="hidden" value="${publications?.masterDissertation.obj.authors}" />
                    <input name="masterDissertation0.school" type="hidden" value="${publications?.masterDissertation.obj.school}" />
                    <input name="masterDissertation0.address" type="hidden" value="${publications?.masterDissertation.address}" />
                    <tr>
                        <td>Dissertation</td>
                        <td>${publications?.masterDissertation?.obj?.title}</td>
                        <td><g:formatDate date="${publications?.masterDissertation?.obj?.publicationDate}"/></td>
                        <td>${publications?.masterDissertation?.obj?.authors}</td>
                        <td>${publications?.masterDissertation?.status}</td>
                    </tr>
                </g:if>
                <!-- thesis -->
                <g:if test="${publications?.thesis}">
                    <input name="thesis0.title" type="hidden" value="${publications?.thesis.obj.title}" />
                    <input name="thesis0.publicationDate" type="hidden" value="${publications?.thesis.obj.publicationDate}" />
                    <input name="thesis0.authors" type="hidden" value="${publications?.thesis.obj.authors}" />
                    <input name="thesis0.school" type="hidden" value="${publications?.thesis.obj.school}" />
                    <input name="thesis0.address" type="hidden" value="${publications?.thesis.address}" />
                    <tr>
                        <td>Thesis</td>
                        <td>${publications?.thesis?.obj?.title}</td>
                        <td><g:formatDate date="${publications?.thesis?.obj?.publicationDate}"/></td>
                        <td>${publications?.thesis?.obj?.authors}</td>
                        <td>${publications?.thesis?.status}</td>
                    </tr>
                </g:if>
                <!-- conferences -->
                <g:if test="${publications?.conferences}">
                    <g:each in="${publications?.conferences}" status="i" var="conferenceInstance">
                        <input name="conferences${i}.title" type="hidden" value="${conferenceInstance.obj.title}" />
                        <input name="conferences${i}.publicationDate" type="hidden" value="${conferenceInstance.obj.publicationDate}" />
                        <input name="conferences${i}.authors" type="hidden" value="${conferenceInstance.obj.authors}" />
                        <input name="conferences${i}.booktitle" type="hidden" value="${conferenceInstance.obj.booktitle}" />
                        <input name="conferences${i}.pages" type="hidden" value="${conferenceInstance.obj.pages}" />
                        <tr>
                            <td>Conference</td>
                            <td>${conferenceInstance?.obj?.title}</td>
                            <td><g:formatDate date="${conferenceInstance?.obj?.publicationDate}"/></td>
                            <td>${conferenceInstance?.obj?.authors}</td>
                            <td>${conferenceInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #if($researchLine) -->
                <g:if test="${publications?.researchLines}">
                    <g:each in="${publications?.researchLines}" status="i" var="researchLineInstance">
                        <input name="researchLines${i}.name" type="hidden" value="${researchLineInstance.obj.name}" />
                        <input name="researchLines${i}.description" type="hidden" value="${researchLineInstance.obj.description}" />
                        <tr>
                            <td>Research Line</td>
                            <td>${researchLineInstance?.obj?.name}</td>
                            <td> </td>
                            <td>${researchLineInstance?.obj?.members}</td>
                            <td>${researchLineInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #end -->
                <!-- #if($researchProject) -->
                <g:if test="${publications?.researchProjects}">
                    <g:each in="${publications?.researchProjects}" status="i" var="projectInstance">
                        <input name="researchProjects${i}.projectName" type="hidden" value="${projectInstance.obj.projectName}" />
                        <input name="researchProjects${i}.description" type="hidden" value="${projectInstance.obj.description}" />
                        <input name="researchProjects${i}.status" type="hidden" value="${projectInstance.obj.status}" />
                        <input name="researchProjects${i}.responsible" type="hidden" value="${projectInstance.obj.responsible}" />
                        <input name="researchProjects${i}.startYear" type="hidden" value="${projectInstance.obj.startYear}" />
                        <input name="researchProjects${i}.endYear" type="hidden" value="${projectInstance.obj.endYear}" />
                        <input name="researchProjects${i}.members" type="hidden" value="${projectInstance.obj.members}" />
                        <!-- #if($funder) -->
                            <input name="researchProjects${i}.funders" type="hidden" value="${projectInstance.obj.funders}" />
                        <!-- #end -->
                        <tr>
                            <td>Research Project</td>
                            <td>${projectInstance?.obj?.projectName}</td>
                            <td>${projectInstance?.obj?.startYear} </td>
                            <td><!-- #if($funder) -->${projectInstance?.obj?.funders}<!-- #end --></td>
                            <td>${projectInstance?.status}</td>
                        </tr>
                    </g:each>
                </g:if>
                <!-- #end -->
                <!-- #if(Orientation) -->
                <g:if test="${publications?.orientations}">
                    <g:each in="${publications?.orientations}" status="i" var="orientationInstance">
                        <input name="orientations${i}.tipo" type="hidden" value="${orientationInstance.obj.tipo}" />
                        <input name="orientations${i}.orientando" type="hidden" value="${orientationInstance.obj.orientando}" />
                        <input name="orientations${i}.tituloTese" type="hidden" value="${orientationInstance.obj.tituloTese}" />
                        <input name="orientations${i}.anoPublicacao" type="hidden" value="${orientationInstance.obj.anoPublicacao}" />
                        <input name="orientations${i}.instituicao" type="hidden" value="${orientationInstance.obj.instituicao}" />
                        <input name="orientations${i}.curso" type="hidden" value="${orientationInstance.obj.curso}" />
                        <tr>
                            <td>Orientação de ${orientationInstance?.obj?.tipo}</td>
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
                <g:submitButton name="create" class="save" value="Save"/>
            </fieldset>
        </g:form>
    </g:if>
</div>
</body>
</html>
