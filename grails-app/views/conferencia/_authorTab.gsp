<%@ page import="rgms.publication.ConferenciaController" %>

<table>
    <thead>
        <tr><th colspan="2"><g:message code="conferencia.authors.label" default="Authors"/></th></tr>
    </thead>
    <tbody>
        <g:each in="${((Iterable) session["authors"]).iterator()}" var="author" >
            <g:if test="${ConferenciaController.getLoggedMemberName().equals(author)}">
                <tr><td>${author}</td></tr>
            </g:if>
            <g:else>
                <tr>
                    <td>${author}</td>
                    <td>
                        <input id="${author}" class="removeAuthor" type="button" value="${message(code: 'conferencia.removeAuthor.label', default: 'remove author')}" />
                    </td>
                </tr>
            </g:else>
        </g:each>
    </tbody>
</table>

<jq:jquery>
    $(document).ready(function(){

        clearInput = function(){
            $("#addAuthor").val('');
            $("#addAuthor").focus();
        };

        $("#addAuthorButton").click(function(){
            var authorName = $("#addAuthor").val() ;
            clearInput();

            jQuery.ajax({type:'POST',data:{addAuthor: authorName},
                url:'/rgms/conferencia/addAuthor',
                success:function(data,textStatus){jQuery("#authors").html(data);},
                error:function(XMLHttpRequest,textStatus,errorThrown){}});
            return false
        });

        $(".removeAuthor").click(function(){
            clearInput();
            jQuery.ajax({type:'POST',data:{removeAuthor: $(this).attr("id")},
                url:'/rgms/conferencia/removeAuthor',
                success:function(data,textStatus){jQuery("#authors").html(data);},
                error:function(XMLHttpRequest,textStatus,errorThrown){}});
            return false
        });

        //setup before functions
        var typingTimer;                //timer identifier
        var doneTypingInterval = 1500;  //time in ms

        //on keyup, start the countdown
        $("#addAuthor").keyup(function(){
            clearTimeout(typingTimer);
            if ($("#addAuthor").val()) {
                typingTimer = setTimeout(function(){

                jQuery.ajax({type:'POST',data:{addAuthor: $("#addAuthor").val()},
                url:'/rgms/conferencia/findMemberByName',
                success:function(data,textStatus){
                    jQuery("#addAuthor").val(data);
                    jQuery("#addAuthor").select();
                    jQuery("#addAuthor").focus();
                    }, error:function(XMLHttpRequest,textStatus,errorThrown){}});
                }, doneTypingInterval);
            }

        });
    });
</jq:jquery>