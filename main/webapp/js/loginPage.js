/**
 * Created by smit on 10/3/22.
 */

let username = null;
let password = null;

var login = {

    onload : function () {

        let formInputs = $('input[type="text"],input[type="password"]');

        formInputs.focus(function() {
            $(this).parent().children('p.formLabel').addClass('formTop');
            $('div#formWrapper').addClass('darken   -bg');
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

        $("#show-password").change(function () {
            $(this).prop("checked") ? $("#password").prop("type", "text"): $("#password").prop("type", "password");
        });

    },

    onLogin : function () {

        username = $("#userName").val();

        password = $("#password").val();

        let param = $('#loginForm').serializeArray().reduce(function(finalParam, currentValue) {
            finalParam[currentValue.name] = currentValue.value;
            return finalParam;
        }, {});

        let request = {

            url: "loginProcess",

            data: param,

            callback: logincallback.onLogincallback,
        };

        mainHelper.ajaxpost(request);
    },
};

var logincallback = {

    onLogincallback : function (data) {

        if (data.status === "Success")
        {
            window.location.href = "afterLoginFetchDashboardData";
        }
        else
        {
            if (data.userName !== "" && data.password !== "")
            {
                toastr.error("Wrong Username or Password");
            }
        }
    }
};
