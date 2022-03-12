/**
 * Created by smit on 12/3/22.
 */
var discoveryVariable = {

    discoveryFunction : function () {

        $("#discovery-a").on("click", function () {

           $("body").html();
        });
    }
};


function discoveryIn()
{
    var userName = $("#userName").val();
    var password = $("#password").val();

    $.ajax({

        type: "POST",
        data: "userName="+userName+"&password="+password,
        url: "discoveryProcess",
        timeout: 500,
        success: function (obj)
        {
            alert(obj);
            // window.location.href = "../WEB-INF/index.jsp";

             alert("Success");
        },
        error: function () {
            alert("Error");
        }

    });
}