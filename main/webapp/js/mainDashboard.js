/**
 * Created by smit on 4/4/22.
 */

var dashboardmain = {

    onload: function ()
    {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Dashboard</h4> <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"> <div id="upCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Up<hr><p style="font-size: 25px">0</p> </div> <div id="downCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Down<hr><p style="font-size: 25px">0</p> </div>  <div id="unknownCount" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Unknown<hr><p style="font-size: 25px">0</p> </div> </div></div></div></div> <br><br>  <div class="col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <h4 class="card-title">Last 1 Hour Top Five CPU Utilization Data </h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> IP </th> <th> CPU(%) </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>  ' +
                '<br><br>  <div class="col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <h4 class="card-title">Last 1 Hour Top Five Memory Utilization Data </h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> IP </th> <th> Memory(%) </th> </tr> </thead> <tbody id="memory-table-body"> <tr> </tr> </tbody> </table> </div> </div> </div> </div> ' +
                '<br><br>  <div class="col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <h4 class="card-title">Last 1 Hour Top Five Disk Utilization Data </h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> IP </th> <th> Disk(%) </th> </tr> </thead> <tbody id="disk-table-body"> <tr> </tr> </tbody> </table> </div> </div> </div> </div> ' +
                '<br><br>  <div class="col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <h4 class="card-title">Last 1 Hour Top Five RTT Data </h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> IP </th> <th> RTT(ms) </th> </tr> </thead> <tbody id="rtt-table-body"> <tr> </tr> </tbody> </table> </div> </div> </div> </div> ');

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

        let tblData = null;

        let memoryTableData = null;

        let diskTableData = null;

        let rttTableData = null;

        $("#upCount").find("p").text(data.hashMap["Up"]);

        $("#downCount").find("p").text(data.hashMap["Down"]);

        $("#unknownCount").find("p").text(data.hashMap["unknown"]);

        $.each(data.topFiveCpuHashMap, function (key, value)
        {
            tblData += "<tr><td>" + key + "</td>" +
                "<td>" + value + "</td></tr>";
        });

        $("#tablebody").html(tblData);

        $.each(data.topFiveMemoryHashMap, function (key, value) {

            memoryTableData +="<tr><td>" + key + "</td><td>" + value + "</td></tr>";
        });

        $("#memory-table-body").html(memoryTableData);

        $.each(data.topFiveDiskHashMap, function (key, value) {

            diskTableData+="<tr><td>" + key + "</td><td>" + value + "</td></tr>";
        });

        $("#disk-table-body").html(diskTableData);

        $.each(data.topFiveRTTHashMap, function (key, value) {

            rttTableData+="<tr><td>" + key + "</td><td>" + value + "</td></tr>";
        });

        $("#rtt-table-body").html(rttTableData);

        toastr.info('Dashboard Table Loaded');
    },
};