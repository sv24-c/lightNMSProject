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

    $.ajax({

        type: "POST",
        url: "discoveryProcess",
        timeout: 500,
        success: function (data)
        {
            $(".dashboard").html('<div class="discovery-main-div"> <h2>Hello World!</h2> NMS Discovery Page <button id="add-monitor-btn"">Add Monitor</button></div> <br><br><table border="1px solid"> <thead> <th>Name</th> <th>IP</th> <th>Type</th></thead> <tbody> </tbody> </table>');

            $("#add-monitor-btn").on("click", function () {

/*
                $(".discovery-main-div").html('<div id="modalpopup"> <div id="closebtn"> <span>Close Button</span> </div> <p id="rowid" style="visibility: hidden"></p> <label>Name<input type="text" class="addname"></label><br><br> <label>IP<input type="text" class="addip"></label><br><br> <label>Type<input type="text" class="addtype"></label><br><br> <button type="button" class="updtbtn">Add</button> </div>');
*/
                $(".discovery-main-div").html('<div class="modal-dialog modal-lg">...</div>');
            });

           /* var data = "<tr>" +
                "<td>"+Name+"</td>" +
                "<td>"+IP+"</td>" +
                "<td>"+Type+"</td>" +
                "<td><button type='button' class='addbtn'>Add</button></td>" +
                "<td><button type='button' class='editbtn'>Edit</button></td>" +
                "<td><button type='button' class='deletebtn'>Delete</button></td>" +
                "</tr>";


            $("tbody").append(data);*/

            //  alert(data);

             alert("Success");
        },

        error: function ()
        {
            alert("Error");
        }

    });
}
