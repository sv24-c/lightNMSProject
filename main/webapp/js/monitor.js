/**
 * Created by smit on 4/4/22.
 */

let commanUp = 0;

let commanDown = 0;

let barChartTitle = "Last 24 Hour RTT Chart";

let barChartXAxisTitle = "RTT per TimeStamp";

let barChartYAxisTitle = "Total RTT";

let barChartYAxisMaximum = 10;

let barChartYAxisSufix = "ms";

let list = [];

var monitormain = {

    onload: function ()
    {
        $("#monitor-onclick").on("click", function () {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Monitor Grid</h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> <th> Availability </th> <th> Operation </th></tr> </thead> <tbody id="monitortablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>');

            let request = {

                url: "monitorShowDataProcess",

                data: "",

                callback: monitormain.callbackOnload,
            };

            mainHelper.ajaxpost(request);

        });
    },

    getChartsData: function (context)
    {
        let type = $(context).data("value");

        let id = $(context).data("id");

        let ip = $(context).data("ip");

        if (type === "SSH")
        {
            toastr.info('Data Loading...');

            monitormain.fetchSSHData(id, type, ip);
        }
        else
        {
            toastr.info('Data Loading...');

            monitormain.fetchPingData(id, type, ip);
        }
    },

    delete: function (context) {

        let id = $(context).data("id");

        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"><div class="card-body">Are You Sure, To Delete this Row? <button type="button" class="btn btn-success btn-block" id="delete-monitor-btn" data-id="'+context.dataset.id+'" onclick="monitormain.confirmdelete(this)">Delete</button>  <button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="monitormain.back()"> Back </button> </div></div></div>');

        toastr.warning('Data Would be Permanently deleted');
    },

    confirmdelete: function (context)
    {
        let id = $(context).data("id");

        let sendData = {
            id: id
        };

        let request = {

            url: "monitorDeleteProcess",

            data: sendData,

            callback: monitormain.callbackConfirmDelete(),
        };
        mainHelper.ajaxpost(request);

    },

    back: function ()
    {
        let request = {

            url: "monitorShowDataProcess",

            data: "",

            callback: monitormain.callbackOnload,
        };

        mainHelper.ajaxpost(request);
    },

    callbackOnload: function (data)
    {
        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Monitor Grid</h4> <div class="table-responsive pt-3"> <table class="table table-bordered" id="monitorTable"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> <th> Availability </th> <th> Operation </th></tr> </thead> </table> </div> </div> </div> </div>');

        table = $('#monitorTable').DataTable({lengthMenu: [5, 10, 20, 50, 100, 200, 500]});

        $.each(data.monitorBeanList, function () {
            table.row.add([this.name, this.ip, this.type, this.availability,"<button onclick='monitormain.getChartsData(this);' data-id='"+this.id+"' data-value='"+this.type+"' data-ip='"+this.ip+"' class='btn btn-sm btn-info' >Show</button><button onclick='monitormain.delete(this);' data-id='"+this.id+"' class='btn btn-sm btn-danger'>Delete</button>"]).draw();
        }); // todo table column and data.

        //monitorHelper.loaddata(data, table);
    },

    callbackConfirmDelete: function ()
    {
        toastr.info('Data Deleted Successfully');

        window.location.reload();
    },

    fetchSSHData: function (id, type, ip)
    {
        $("#overview").html('<button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="monitormain.back()"> Back </button> <br> <div class="col-md-6 col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div>IP: <b>' + ip + '</b> &nbsp;&nbsp;&nbsp;Type: <b>' + type + '</b> </div></div></div></div><br><br>   <div class="row"><div id="chartContainer" style="height: 300px; width: 100%;"> </div></div><br><br><br>   <div class="card card-rounded"> <div class="card-body"> <div id="pingchartContainerPartTwo" style="height: 300px; width: 100%;"></div> </div></div>  <br><br><br>   <div class="card card-rounded"> <div class="card-body">  <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"><div id="cpu" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px; padding: 10px">CPU(%)<hr><p style="font-size: 25px"></p></div>    <div id="disk" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Disk(%)<hr><p style="font-size: 25px"></p> </div><div id="memory" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Memory(%)<hr><p style="font-size: 25px"></p></div>  <div id="swapMemory" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Swap Memory(%)<hr><p style="font-size: 25px"></p></div>  </div>  </div></div>  ');

        let sendData = {
            id: id, Type: type, IP:ip
        };

        let request = {

            url: "monitorShowTableProcess",

            data: sendData,

            callback: monitormain.callbackFetchSSHData,
        };

        mainHelper.ajaxpost(request);
    },

    fetchPingData: function (id, type, ip)
    {
        $("#overview").html('<button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="monitormain.back()"> Back </button> <br><div class="col-md-6 col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div>IP: <b>' + ip + '</b> &nbsp;&nbsp;&nbsp;Type: <b>' + type + '</b> </div></div></div></div> <br><br> <div class="row"><div id="chartContainer" style="height: 300px; width: 100%;"></div></div><br><br><br>   <div class="card card-rounded"> <div class="card-body"><div id="pingchartContainerPartTwo" style="height: 300px; width: 100%;"></div></div></div>  <br><br><br>      <div class="card card-rounded"> <div class="card-body">  <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"><div id="packetLoss" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Packet Loss(%)<hr><p style="font-size: 25px"></p></div>    <div id="rtt" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">RTT(ms)<hr><p style="font-size: 25px"> </p> </div><div id="receivePacket" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Receive Packet<hr><p style="font-size: 25px"> </p> </div><div id="sendPacket" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Send Packet<hr><p style="font-size: 25px"> </p></div>    </div>  </div></div>    ');

        let sendData = {
            id: id, Type: type, IP:ip
        };

        let request = {

            url: "monitorShowTableProcess",

            data: sendData,

            callback: monitormain.callbackPingData,
        };
        mainHelper.ajaxpost(request);
    },


    callbackFetchSSHData: function (data) {

        barChartTitle = "Last 24 Hour CPU Chart";

        barChartXAxisTitle = "CPU per TimeStamp";

        barChartYAxisTitle = "Total CPU";

        barChartYAxisMaximum = 100;

        barChartYAxisSufix = "%";

        list = [];

        let cpuUsed = 0;

        let timeStamp = 0;

        $("#cpu").find("p").text(data.sshMatrixHashMap["CPU"]);

        $("#disk").find("p").text(data.sshMatrixHashMap["Disk"]);

        $("#memory").find("p").text(data.sshMatrixHashMap["Memory"]);

        $("#swapMemory").find("p").text(data.sshMatrixHashMap["SwapMemory"]);

        commanUp = data.pingStatusHashMap["SUM(Status='Up')"];

        commanDown = data.pingStatusHashMap["SUM(Status='Down')"];

        monitormain.callbackDoughnut(commanUp, commanDown);

        $.each(data.cpuMap, function (key, value) {

            timeStamp = data.cpuMap[key]["PollingTime"];

            cpuUsed = data.cpuMap[key]["CPU"];

            list.push({
                label: timeStamp,
                y: cpuUsed
            });

        });

        monitormain.callbackBarChart(barChartTitle, barChartXAxisTitle, barChartYAxisMaximum, barChartYAxisSufix, barChartYAxisTitle, list);
    },

    callbackPingData: function (data) {

        barChartTitle = "Last 24 Hour RTT Chart";

        barChartXAxisTitle = "RTT per TimeStamp";

        barChartYAxisTitle = "Total RTT";

        barChartYAxisMaximum = 10;

        barChartYAxisSufix = "ms";

        list = [];

        let rtt = 0;

        let rttTimeStamp = 0;

        $("#packetLoss").find("p").text(data.pingMatrixHashMap["PacketLoss"]);

        $("#sendPacket").find("p").text(data.pingMatrixHashMap["SendPacket"]);

        $("#receivePacket").find("p").text(data.pingMatrixHashMap["ReceivePacket"]);

        $("#rtt").find("p").text(data.pingMatrixHashMap["RTT"]);

        commanUp = data.pingStatusHashMap["SUM(Status='Up')"];

        commanDown = data.pingStatusHashMap["SUM(Status='Down')"];

        monitormain.callbackDoughnut(commanUp, commanDown);

        $.each(data.rttMap, function (key, value) {

            rttTimeStamp = data.rttMap[key]["PollingTime"];

            rtt = data.rttMap[key]["RTT"];

            list.push({
                label: rttTimeStamp,
                y: rtt
            });
        });

        monitormain.callbackBarChart(barChartTitle, barChartXAxisTitle, barChartYAxisMaximum, barChartYAxisSufix, barChartYAxisTitle, list);
    },

    callbackDoughnut : function (commanUp, commanDown) {

        var chart = new CanvasJS.Chart("chartContainer", {
            animationEnabled: true,
            title:{
                text: "Availability of Last 24 Hour",
                horizontalAlign: "left"
            },

            data: [{
                type: "doughnut",
                startAngle: 60,
                indexLabelFontSize: 17,
                indexLabel: "{label} - #percent%",
                toolTipContent: "<b>{label}:</b> #percent%",
                dataPoints: [
                    { y: commanUp, label: "Up" },
                    { y: commanDown, label: "Down" },
                ]
            }]
        });
        chart.render();
    },

    callbackBarChart: function (barChartTitle, barChartXAxisTitle, barChartYAxisMaximum, barChartYAxisSufix, barChartYAxisTitle, list) {

        var rttchart = new CanvasJS.Chart("pingchartContainerPartTwo", {

            animationEnabled: true,
            exportEnabled: true,
            theme: "light1",
            title:{
                text: barChartTitle
            },
            axisX: {
                title: barChartXAxisTitle,
                reversed:true
            },
            axisY: {
                title: barChartYAxisTitle,
                minimum: 0,
                maximum: barChartYAxisMaximum,
                suffix: barChartYAxisSufix,
                includeZero: true,
            },
            data: [{
                type: "column",
                indexLabel: "{y}",
                indexLabelFontColor: "#000000",
                indexLabelFontSize: 16,
                indexLabelPlacement: "outside",
                dataPoints: list,
            }]
        });

        rttchart.render();
    }
};
