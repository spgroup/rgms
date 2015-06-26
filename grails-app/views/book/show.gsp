<%@ page import="rgms.publication.Book" %>
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

    <g:set var="entityName" value="${message(code: 'book.label', default: 'Book')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-book" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-book" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list book">

        <g:if test="${bookInstance?.title}">
            <li class="fieldcontain">
                <span id="title-label" class="property-label"><g:message code="book.title.label"
                                                                         default="Title"/></span>

                <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${bookInstance}"
                                                                                         field="title"/></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.publicationDate}">
            <li class="fieldcontain">
                <span id="publicationDate-label" class="property-label"><g:message code="book.publicationDate.label"
                                                                                   default="Publication Date"/></span>

                <span class="property-value" aria-labelledby="publicationDate-label"><g:formatDate
                        date="${bookInstance?.publicationDate}"/></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.file}">
            <li class="fieldcontain">
                <span id="file-label" class="property-label"><g:message code="book.file.label" default="File"/></span>

                <span class="property-value" aria-labelledby="file-label"><g:fieldValue bean="${bookInstance}"
                                                                                        field="file"/></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.researchLine}">
            <li class="fieldcontain">
                <span id="researchLine-label" class="property-label"><g:message code="book.researchLine.label"
                                                                                default="Research Line"/></span>

                <span class="property-value" aria-labelledby="researchLine-label"><g:link controller="researchLine"
                                                                                          action="show"
                                                                                          id="${bookInstance?.researchLine?.id}">${bookInstance?.researchLine?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.authors}">
            <li class="fieldcontain">
                <span id="autores-label" class="property-label"><g:message code="book.autores.label"
                                                                           default="Autores"/></span>

                <span class="property-value" aria-labelledby="autores-label"><g:fieldValue bean="${bookInstance}"
                                                                                           field="authors"/></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.publisher}">
            <li class="fieldcontain">
                <span id="publisher-label" class="property-label"><g:message code="book.publisher.label"
                                                                             default="Publisher"/></span>

                <span class="property-value" aria-labelledby="publisher-label"><g:fieldValue bean="${bookInstance}"
                                                                                             field="publisher"/></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.volume}">
            <li class="fieldcontain">
                <span id="volume-label" class="property-label"><g:message code="book.volume.label"
                                                                          default="Volume"/></span>

                <span class="property-value" aria-labelledby="volume-label"><g:fieldValue bean="${bookInstance}"
                                                                                          field="volume"/></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.pages}">
            <li class="fieldcontain">
                <span id="pages-label" class="property-label"><g:message code="book.pages.label"
                                                                         default="Pages"/></span>

                <span class="property-value" aria-labelledby="pages-label"><g:fieldValue bean="${bookInstance}"
                                                                                         field="pages"/></span>

            </li>
        </g:if>

        <g:if test="${bookInstance?.members}">
            <li class="fieldcontain">
                <span id="members-label" class="property-label"><g:message code="book.members.label"
                                                                           default="Members"/></span>

                <g:each in="${bookInstance.members}" var="m">
                    <span class="property-value" aria-labelledby="members-label"><g:link controller="member"
                                                                                         action="show"
                                                                                         id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <!--#if( $Facebook ) -->
    <div id="fb-root"></div>
    <script>
        window.fbAsyncInit = function () {
            FB.init({
                //appId: 'precisa requisitar no Facebook',
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
         src="http://www.bateucuriosidade.com/imagem/compartilhar%20fb.png">
    <script type="text/javascript">
        $(document).ready(function () {
            var bookTitle = "${bookInstance?.title}"
            var bookId = "${bookInstance?.id}"
            var bookPublisher = "${bookInstance?.publisher}"
            var bookVolume = "${bookInstance?.volume}"
            var bookPages = "${bookInstance?.pages}"
            var link = document.URL
            var callback = "http://localhost:8080/rgms/notifyFacebook/book/" + bookId
            $('#share_facebook').live('click', function (e) {
                e.preventDefault();
                FB.ui(
                        {
                            method: 'feed',
                            name: 'Book in RGMS',
                            link: link,
                            caption: 'Title:' + bookTitle,
                            description: 'Publisher: ' + bookPublisher + ', Volume: ' + bookVolume +
                            ', Pages: ' + bookPages,
                            message: 'Personal Message.'
                        });
                jQuery.get("http://localhost:8080/rgms/notifyFacebook/book/" + bookId + "/" + bookTitle);
            });
        });
    </script>
    <!-- Facebook end -->
    <!--#end -->
    <!--#if( $Twitter ) -->
    <!-- Twitter start -->
    <a href="javascript:window.open('https://twitter.com/intent/tweet?button_hashtag=RGMS&text=Book:%20${bookInstance?.title}', 'Tweteet It', 'width=450,height=250');"
       class="twitter-hashtag-button"
       data-related="rgms_ufpe"
       id="button_twitter"
       target="_blank">
        <img id="share_twitter2"
             src="http://www.queroviajarmais.com/wp-content/uploads/2015/01/compartilhar-twitter.png">
    </a>
    <script>
        $('#button_twitter').live('click', function (e) {
            jQuery.get("http://localhost:8080/rgms/notifyTwitter/book/${bookInstance?.id}/${bookInstance?.title}");
        });
    </script>
<!-- Twitter end -->
<!--#end -->
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${bookInstance?.id}"/>
            <g:link class="edit" action="edit" id="${bookInstance?.id}"><g:message code="default.button.edit.label"
                                                                                   default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
