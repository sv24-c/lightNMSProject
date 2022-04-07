/**
 * Created by smit on 4/4/22.
 */
var dashboardmain = {

    onload: function ()
    {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Dashboard</h4> <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"> <div id="upCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Up<hr><p style="font-size: 25px"></p> </div> <div id="downCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Down<hr><p style="font-size: 25px"></p> </div>  <div id="unknownCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Unknown<hr><p style="font-size: 25px"></p> </div> </div></div></div>');

            let request = {

                url: "dashboardClick",

                data: "",

                callback: dashboardCallback.onload,
            };

            mainHelper.ajaxpost(request);

    },
};

var dashboardCallback = {

    onload: function (data) {

        $("#upCount").find("p").text(data.dashboardList[0]);
        $("#downCount").find("p").text(data.dashboardList[1]);
        $("#unknownCount").find("p").text(data.dashboardList[2]);

        toastr.info('Dashboard Table Loaded');
    },

};