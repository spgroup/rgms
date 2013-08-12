<%@ page import="rgms.publication.Periodico" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">

    <!--#if( $Facebook ) -->
    <script type="text/javascript"
            src="https://apis.google.com/js/plusone.js"></script>
    <script
            src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"
            type="text/javascript"></script>
    <!-- Facebook end -->
    <!--#end -->

    <g:set var="entityName"
           value="${message(code: 'periodico.label', default: 'Periodico')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-periodico" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                default="Skip to content&hellip;"/></a>
<g:render template="navigation"/>
<div id="show-periodico" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list periodico">

        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message
                    code="default.home.label"/></a></li>
            <li><g:link class="list" action="list">
                <g:message code="default.list.label" args="[entityName]"/>
            </g:link></li>
            <li><g:link class="create" action="create">
                <g:message code="default.new.label" args="[entityName]"/>
            </g:link></li>
        </ul>
</div>

<div id="show-periodico" class="content scaffold-show" role="main">
    <h1>
        <g:message code="default.show.label" args="[entityName]"/>
    </h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">
            ${flash.message}
        </div>
    </g:if>
    <ol class="property-list periodico">
        <g:if test="${periodicoInstance?.title}">
            <li class="fieldcontain"><span id="title-label"
                                           class="property-label"><g:message
                        code="periodico.title.label" default="Title"/></span> <span
                    class="property-value" aria-labelledby="title-label"><g:fieldValue
                        bean="${periodicoInstance}" field="title"/></span></li>
        </g:if>
        <g:if test="${periodicoInstance?.publicationDate}">
            <li class="fieldcontain"><span id="publicationDate-label"
                                           class="property-label"><g:message
                        code="periodico.publicationDate.label" default="Publication Date"/></span>
                <span class="property-value"
                      aria-labelledby="publicationDate-label"><g:formatDate
                        date="${periodicoInstance?.publicationDate}"/></span></li>
        </g:if>
        <g:if test="${periodicoInstance?.file}">
            <li class="fieldcontain"><span id="file-label"
                                           class="property-label"><g:message
                        code="periodico.file.label" default="File"/></span> <span
                    class="property-value" aria-labelledby="file-label"><g:fieldValue
                        bean="${periodicoInstance}" field="file"/></span></li>
        </g:if>
        <g:if test="${periodicoInstance?.researchLine}">
            <li class="fieldcontain"><span id="researchLine-label"
                                           class="property-label"><g:message
                        code="periodico.researchLine.label" default="Research Line"/></span> <span
                    class="property-value" aria-labelledby="researchLine-label"><g:link
                        controller="researchLine" action="show"
                        id="${periodicoInstance?.researchLine?.id}">
                    ${periodicoInstance?.researchLine?.encodeAsHTML()}
                </g:link></span></li>
        </g:if>
    <!-- //#if($Bibtex) -->
        <li class="fieldcontain"><g:link controller="Publication"
                                         action="generateBib" params="[id: periodicoInstance.id]">Bibtex</g:link>
        </li>
    <!-- //#end -->

        <g:if test="${periodicoInstance?.journal}">
            <li class="fieldcontain"><span id="journal-label"
                                           class="property-label"><g:message
                        code="periodico.journal.label" default="Journal"/></span> <span
                    class="property-value" aria-labelledby="journal-label"><g:fieldValue
                        bean="${periodicoInstance}" field="journal"/></span></li>
        </g:if>
        <g:if test="${periodicoInstance?.volume}">
            <li class="fieldcontain"><span id="volume-label"
                                           class="property-label"><g:message
                        code="periodico.volume.label" default="Volume"/></span> <span
                    class="property-value" aria-labelledby="volume-label"><g:fieldValue
                        bean="${periodicoInstance}" field="volume"/></span></li>
        </g:if>
        <g:if test="${periodicoInstance?.number}">
            <li class="fieldcontain"><span id="number-label"
                                           class="property-label"><g:message
                        code="periodico.number.label" default="Number"/></span> <span
                    class="property-value" aria-labelledby="number-label"><g:fieldValue
                        bean="${periodicoInstance}" field="number"/></span></li>
        </g:if>
        <g:if test="${periodicoInstance?.pages}">
            <li class="fieldcontain"><span id="pages-label"
                                           class="property-label"><g:message
                        code="periodico.pages.label" default="Pages"/></span> <span
                    class="property-value" aria-labelledby="pages-label"><g:fieldValue
                        bean="${periodicoInstance}" field="pages"/></span></li>
        </g:if>

        <span class="property-value" aria-labelledby="researchLine-label"><g:link controller="researchLine"
                                                                                  action="show"
                                                                                  id="${periodicoInstance?.researchLine?.id}">${periodicoInstance?.researchLine?.encodeAsHTML()}</g:link></span>
        <g:if test="${periodicoInstance?.members}">
            <li class="fieldcontain"><span id="members-label"
                                           class="property-label"><g:message
                        code="periodico.members.label" default="Members"/></span> <g:each
                    in="${periodicoInstance.members}" var="m">
                <span class="property-value" aria-labelledby="members-label"><g:link
                        controller="member" action="show" id="${m.id}">
                    ${m?.encodeAsHTML()}
                </g:link></span>
            </g:each></li>
        </g:if>

    </ol>
    <!--#if( $Facebook ) -->
    <div id="fb-root"></div>
    <script>
        window.fbAsyncInit = function () {
            FB.init({
                appId: '1374311522787070',
                status: true, // check login status
                cookie: true, // enable cookies to allow the server to access the session
                xfbml: true  // parse XFBML
            });
        };

        (function () {
            var e = document.createElement('script');
            e.src = document.location.protocol + '//connect.facebook.net/en_US/all.js';
            e.async = true;
            document.getElementById('fb-root').appendChild(e);
        }());
    </script>
    <img id="share_facebook"
         src="http://www.tabpress.com/fbui_share/share_button.png">
    <script type="text/javascript">
        $(document).ready(function () {
            var periodicoTitle = "${periodicoInstance?.title}"
            var periodicoId = "${periodicoInstance?.id}"
            var periodicoJournal = "${periodicoInstance?.journal}"
            var periodicoVolume = "${periodicoInstance?.volume}"
            var periodicoNumber = "${periodicoInstance?.number}"
            var periodicoPages = "${periodicoInstance?.pages}"
            var link = document.URL
            var callback = "http://localhost:8080/rgms/notifyFacebook/periodico/" + periodicoId
            $('#share_facebook').live('click', function (e) {
                e.preventDefault();
                FB.ui(
                        {
                            method: 'feed',
                            name: 'Periodico in RGMS',
                            link: link,
                            caption: 'Title:' + periodicoTitle,
                            description: 'Journal: ' + periodicoJournal + ', Volume: ' + periodicoVolume +
                                    ', Number: ' + periodicoNumber + ', Pages: ' + periodicoPages,
                            message: 'Personal Message.'
                        });
                $.get("http://localhost:8080/rgms/notifyFacebook/periodico/" + periodicoId + "/" + periodicoTitle);
            });
        });
    </script>
    <!-- Facebook end -->
    <!--#end -->
    <!--#if( $Twitter ) -->
    <!-- Twitter start -->
    <a href="javascript:window.open('https://twitter.com/intent/tweet?button_hashtag=RGMS&text=Article:%20${periodicoInstance?.title}', 'Tweteet It', 'width=450,height=250');"
       class="twitter-hashtag-button"
       data-related="rgms_ufpe"
       id="button_twitter"
       target="_blank">
        <img id="share_twitter2"
             src="http://www.tabpress.com/fbui_share/share_button.png">
    </a>
    <script>
        $('#button_twitter').live('click', function (e) {
            $.get("http://localhost:8080/rgms/notifyTwitter/periodico/${periodicoInstance?.id}/${periodicoInstance?.title}");
        });
    </script>
<!-- Twitter end -->
<!--#end -->
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${periodicoInstance?.id}"/>
            <g:link class="edit" action="edit" id="${periodicoInstance?.id}">
                <g:message code="default.button.edit.label" default="Edit"/>
            </g:link>
            <g:actionSubmit action="share" id="share" name="share" update="[success:'success',failure:'error']"
                          on404="alert('not found');" value="Share on Facebook"></g:actionSubmit>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
