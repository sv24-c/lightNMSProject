/**
 * Created by smit on 4/4/22.
 */

var dashboardmain = {

    onload: function ()
    {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Dashboard</h4> <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"> <div id="upCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Up<hr><p style="font-size: 25px">0</p> </div> <div id="downCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Down<hr><p style="font-size: 25px">0</p> </div>  <div id="unknownCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Unknown<hr><p style="font-size: 25px">0</p> </div> </div></div></div></div> <br><br>  <div class="col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <h4 class="card-title">Last 1 Hour Top Five CPU Utilization Data </h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> IP </th> <th> CPU(%) </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>');

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

        $("#upCount").find("p").text(data.hashMap["Up"]);

        $("#downCount").find("p").text(data.hashMap["Down"]);

        $("#unknownCount").find("p").text(data.hashMap["unknown"]);


        let tblData = null;

        $.each(data.topFiveCpuHashMap, function (key, value)
        {
            tblData += "<tr><td>" + key + "</td>" +
                "<td>" + value + "</td></tr>";
        });

        $("#tablebody").html(tblData);

        toastr.info('Dashboard Table Loaded');
    },
};