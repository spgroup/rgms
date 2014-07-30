function clearInput()
{
    $("#addAuthor").val('') ;
}

function removeFromList(input){
    var value = input.id ;

    jQuery.ajax({type:'POST',data:{selectedAuthor: value},
        url:'/rgms/conferencia/removeAuthor',
        success:function(data,textStatus){jQuery('#authorList').html(data);},
        error:function(XMLHttpRequest,textStatus,errorThrown){}});
    return false
}


