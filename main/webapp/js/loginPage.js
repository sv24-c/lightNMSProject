/**
 * Created by smit on 10/3/22.
 */
$(document).ready(function () {
    var formInputs = $('input[type="text"],input[type="password"]');
    formInputs.focus(function() {
        $(this).parent().children('p.formLabel').addClass('formTop');
        $('div#formWrapper').addClass('darken-bg');
        $('div.logo').addClass('logo-active');
    });
    formInputs.focusout(function() {
        if ($.trim($(this).val()).length == 0){
            $(this).parent().children('p.formLabel').removeClass('formTop');
        }
        $('div#formWrapper').removeClass('darken-bg');
        $('div.logo').removeClass('logo-active');
    });
    $('p.formLabel').click(function(){
        $(this).parent().children('.form-style').focus();
    });
});

var logInVar = {

    logInFunction : function () {


    }

};


/*

function logIn()
{
    var userName = $("#userName").val();
    var password = $("#password").val();

    $.ajax({

        type: "POST",
        data: "userName="+userName+"&password="+password,
        url: "loginProcess",
        success: function (obj)
        {
            alert(obj);
            // window.location.href = "../WEB-INF/index.jsp";

            // alert("Success");
        },
        error: function () {
          alert("Error");
        }

    });
}*/
