/**
 * Created by smit on 4/4/22.
 */
var monitormain = {

    onload: function ()
    {
        $("#monitor-onclick").on("click", function () {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Monitor Grid</h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> <th> Availability </th> <th> Operation </th></tr> </thead> <tbody id="monitortablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>');

            let request = {

                url: "monitorShowDataProcess",

                data: "",

                callback: monitorcallback.onload,
            };

            mainHelper.ajaxpost(request);

        });
    },

    getChartsData: function (that)
    {

        let type = $(that).data("value");

        let id = $(that).data("id");

        let ip = $(that).parent().prev().prev().prev().text();

        if (type === "SSH")
        {
            toastr.info('Data Loading...');

            monitorHelper.showsshdata(id, type, ip);

        }
        else
        {
            toastr.info('Data Loading...');

            monitorHelper.showpingdata(id, type, ip);
        }

    },

    delete: function (that) {

        var id = $(that).data("id");

        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"><div class="card-body">Are You Sure, To Delete this Row? <button type="button" class="btn btn-success btn-block" id="delete-monitor-btn" data-id="'+that.dataset.id+'" onclick="monitormain.confirmdelete(this)">Delete</button> </div></div></div>');

        toastr.warning('Data Would be Permanently deleted');
    },

    confirmdelete: function (that)
    {
        var id = $(that).data("id");

        let sdata = {
            id: id
        };

        let request = {

            url: "monitorDeleteProcess",

            data: sdata,

            callback: monitorcallback.confirmdelete,
        };
        mainHelper.ajaxpost(request);

    }
};

var monitorHelper = {

    showsshdata: function (id, type, ip)
    {

        let sdata = {
            id: id, Type: type, IP:ip
        };

        let request = {

            url: "monitorShowTableProcess",

            data: sdata,

            callback: monitorcallback.showsshdata,
        };

        mainHelper.ajaxpost(request);

    },

    showpingdata: function (id, type, ip)
    {
        let sdata = {
            id: id, Type: type, IP:ip
        };

        let request = {

            url: "monitorShowTableProcess",

            data: sdata,

            callback: monitorcallback.showpingdata,
        };
        mainHelper.ajaxpost(request);

    },
};

var monitorcallback = {

    onload: function (data)
    {
        var monitorTblData="";

        $.each(data.monitorBeanList, function() {

            monitorTblData += "<tr><td>" + this.name + "</td>" +
                "<td>" + this.ip + "</td>" +
                "<td>" + this.type + "</td>" +
                "<td>" + this.availability + "</td>" +
                "<td>"+
                "<button onclick='monitormain.getChartsData(this);' data-id='"+this.id+"' data-value='"+this.type+"' class='btn btn-sm btn-info' >Show</button>"+
                "<button onclick='monitormain.delete(this);' data-id='"+this.id+"' class='btn btn-sm btn-danger'>Delete</button>"+
                "</td></tr>";
        });

        $("#monitortablebody").html(monitorTblData);

        toastr.success('Monitor Grid Data Loaded');

    },

    confirmdelete: function ()
    {
        toastr.info('Data Deleted Successfully');

        window.location.reload();
    },

    showsshdata: function (data) {

        let type =data.type;

        let id = data.id;

        toastr.info('Data Loading...');

        $("#overview").html('<div class="col-md-6 col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div>IP: <b>' + ip + '</b> &nbsp;&nbsp;&nbsp;Type: <b>' + type + '</b></div></div></div></div><br><br>   <div class="row"><div id="chartContainer" style="height: 300px; width: 100%;"> </div></div><br><br><br>   <div class="card card-rounded"> <div class="card-body"> <div id="columnChartContainer" style="height: 300px; width: 100%;"></div> </div></div>  <br><br><br>   <div class="card card-rounded"> <div class="card-body">  <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"><div id="cpu" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px; padding: 10px">CPU(%)<hr><p style="font-size: 25px"></p></div>    <div id="disk" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Disk(%)<hr><p style="font-size: 25px"></p> </div><div id="memory" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Memory(MB)<hr><p style="font-size: 25px"></p></div>  <div id="swapMemory" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Swap Memory(MB)<hr><p style="font-size: 25px"></p></div>  </div>  </div></div>  ');

        let sshup = 0;
        let sshdown = 0;

        let columnChart;
        let cpuUsed = 0;
        let timeStamp = 0;
        let list = [];


        $("#cpu").find("p").text(data.sshMatrixList[0]);
        $("#disk").find("p").text(data.sshMatrixList[2]);
        $("#memory").find("p").text(data.sshMatrixList[1]);
        $("#swapMemory").find("p").text(data.sshMatrixList[3]);
        sshup = data.sshStatusList[0];
        sshdown = data.sshStatusList[1];

        var chart = new CanvasJS.Chart("chartContainer", {
            animationEnabled: true,
            title:{
                text: "Availability of Last 24 Hour",
                horizontalAlign: "left"
            },

            data: [{
                type: "doughnut",
                startAngle: 60,
                //innerRadius: 60,
                indexLabelFontSize: 17,
                indexLabel: "{label} - #percent%",
                toolTipContent: "<b>{label}:</b> {y} (#percent%)",
                dataPoints: [
                    { y: sshup, label: "Up" },
                    { y: sshdown, label: "Down" },

                ]
            }]
        });
        chart.render();

        $.each(data.cpuMap, function (key, value) {

            timeStamp = key;
            cpuUsed = value;

            list.push({
                label: timeStamp,
                y: cpuUsed
            });

        });

        columnChart = new CanvasJS.Chart("columnChartContainer", {
            animationEnabled: true,
            exportEnabled: true,
            theme: "light1", // "light1", "light2", "dark1", "dark2"
            title:{
                text: "Last 24 Hour CPU Usage Chart"
            },
            axisX: {
                title: "CPU Usage per TimeStamp",
                valueFormatString: '##.##'
            },
            axisY: {
                title: "Total CPU",
                minimum: 0,
                maximum: 100,
                suffix: "%",
                includeZero: true
            },
            data: [{
                type: "column", //change type to bar, line, area, pie, etc
                indexLabel: "{y}", //Shows y value on all Data Points
                indexLabelFontColor: "#5A5757",
                indexLabelFontSize: 16,
                indexLabelPlacement: "outside",

                dataPoints: list
            }]

        });

        columnChart.render();

    },

    showpingdata: function (data) {

        let type =data.type;

        let id = data.id;

        let ip = data.IP;

        $("#overview").html('<div class="col-md-6 col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div>IP: <b>' + ip + '</b> &nbsp;&nbsp;&nbsp;Type: <b>' + type + '</b></div></div></div></div> <br><br> <div class="row"><div id="pingchartContainer" style="height: 300px; width: 100%;"></div></div><br><br><br>   <div class="card card-rounded"> <div class="card-body"><div id="pingchartContainerPartTwo" style="height: 300px; width: 100%;"></div></div></div>  <br><br><br>      <div class="card card-rounded"> <div class="card-body">  <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"><div id="packetLoss" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Packet Loss(%)<hr><p style="font-size: 25px"></p></div>    <div id="rtt" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">RTT(ms)<hr><p style="font-size: 25px"> </p> </div><div id="receivePacket" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Receive Packet<hr><p style="font-size: 25px"> </p> </div><div id="sendPacket" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Send Packet<hr><p style="font-size: 25px"> </p></div>    </div>  </div></div>    ');

        let up = 0;
        let down = 0;
        let rtt = 0;
        let rttTimeStamp = 0;
        let rttList = [];


        $("#packetLoss").find("p").text(data.matrixList[2]);
        $("#sendPacket").find("p").text(data.matrixList[0]);
        $("#receivePacket").find("p").text(data.matrixList[1]);
        $("#rtt").find("p").text(data.matrixList[3]);
        up = data.pingStatusList[0];
        down = data.pingStatusList[1];

        var chart = new CanvasJS.Chart("pingchartContainer", {
            animationEnabled: true,
            title:{
                text: "Availability of last 24 Hour",
                horizontalAlign: "left"
            },
            data: [{
                type: "doughnut",
                startAngle: 60,
                //innerRadius: 60,
                indexLabelFontSize: 17,
                indexLabel: "{label} - #percent%",
                toolTipContent: "<b>{label}:</b> {y} (#percent%)",
                dataPoints: [
                    { y: up, label: "Up" },
                    { y: down, label: "Down" },
                ]
            }]
        });
        chart.render();

        $.each(data.rttMap, function (key, value) {

            rttTimeStamp = key;
            rtt = value;

            rttList.push({
                label: rttTimeStamp,
                y: rtt
            });

        });

        var rttchart = new CanvasJS.Chart("pingchartContainerPartTwo", {
            animationEnabled: true,
            exportEnabled: true,
            theme: "light1", // "light1", "light2", "dark1", "dark2"
            title:{
                text: "Last 24 Hour RTT Chart"
            },
            axisX: {
                title: "RTT per TimeStamp",
            },
            axisY: {
                title: "Total RTT",
                minimum: 0,
                maximum: 10,
                suffix: "ms",
                includeZero: true
            },
            data: [{
                type: "column", //change type to bar, line, area, pie, etc
                //indexLabel: "{y}", //Shows y value on all Data Points
                indexLabelFontColor: "#5A5757",
                indexLabelFontSize: 16,
                indexLabelPlacement: "outside",
                dataPoints: rttList
            }]
        });
        rttchart.render();

        }

};