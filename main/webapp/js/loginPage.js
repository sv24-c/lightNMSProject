/**
 * Created by smit on 10/3/22.
 */
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

        let username = $("#userName").val();

        let password = $("#password").val();

        let sendData = {

            userName: username,

            password: password,
        };

        let request = {

            url: "loginProcess",

            async: false,

            data: sendData,

            callback: logincallback.onLogincallback,
        };

        mainHelper.ajaxpost(request);
    },

    onvalidation : function () {

        let requesturl = $(location).attr('href');

        let splitUrl = requesturl.split("/");

        let lastUrlName = splitUrl[splitUrl.length - 1];

        if (lastUrlName !== "logoutProcess" || lastUrlName !== null)
        {
            $("#validationText").show();

            if(lastUrlName === "login.jsp")
            {
                $("#validationText").hide();
            }

            if(lastUrlName === "logoutProcess")
            {
                $("#validationText").hide();
            }
        }
    },

   /* onlogout : function () {

       $("#logout").on("click", function () {

           let request = {

               url : "logoutProcess",

               data: " ",

               callback: logincallback.onlogout()
           };

           mainHelper.ajaxpost(request);
       });

    }*/

};

var logincallback = {

    onLogincallback : function (data) {

        /*toastr.error(data.status);*/

        if (data.status === "Success")
        {
            window.location.href = "afterLoginFetchDashboardData";
        }
        else
        {
            toastr.error("Wrong Username or Password");
        }
    }

   /* onlogout : function () {

        toastr.success("LogOut Successfully");
    }*/
};
