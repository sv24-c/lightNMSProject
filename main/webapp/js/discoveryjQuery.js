/**
 * Created by smit on 12/3/22.
 */

var v = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery table</h4>   <button type="submit" class="btn btn-primary me-2" id="dis-add-btn" onclick="discoveryClickButton()">Add</button>     <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>';
var vtwo = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>';
var ip = null;

var discoveryVariable = {

    discoveryOnClick : function ()
    {

        $("#discovery-onclick").on("click", function ()
        {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery table</h4>   <button type="submit" class="btn btn-primary me-2" id="dis-add-btn" onclick="discoveryClickButton()">Add</button>     <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>');

            $.ajax({
                type: "POST",
                url:"discoveryProcess",
                success: function (data)
                {

                    var tblData="";

                    $.each(data.discoveryBeanList, function() {

                        tblData += "<tr><td>" + this.name + "</td>" +
                            "<td>" + this.IP + "</td>" +
                            "<td>" + this.type + "</td>" +
                            "<td>"+
                            "<button onclick='provision(this);' data-id='"+this.id+"' class='btn btn-sm btn-info' >Provision</button>"+
                            "<button onclick='deleteUser(this);' data-id='"+this.id+"' class='btn btn-sm btn-danger'>Delete</button>"+
                            "<button onclick='editUser(this);' data-id='"+this.id+"' class='btn btn-sm btn-secondary' id='dis-edit-btn'>Edit</button>"+
                            "</td></tr>";
                    });

                    //$("#resp").html(tblData);
                    $("#tablebody").html(tblData);



                },
                error: function ()
                {
                    alert("Something went Wrong");
                }
            });

        });

    },

    showMonitor : function ()
    {

        $("#monitor-onclick").on("click", function ()
        {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery table</h4> <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> <th> Availability </th> <th> Operation </th></tr> </thead> <tbody id="monitortablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>');

            $.ajax({
                type: "POST",
                url:"monitorShowDataProcess",
                success: function (data)
                {

                    var monitorTblData="";

                    $.each(data.monitorBeanList, function() {

                        monitorTblData += "<tr><td>" + this.name + "</td>" +
                            "<td>" + this.ip + "</td>" +
                            "<td>" + this.type + "</td>" +
                            "<td>" + this.availability + "</td>" +
                            "<td>"+
                            "<button onclick='showMonitor(this);' data-id='"+this.id+"' data-value='"+this.type+"' class='btn btn-sm btn-info' >Show</button>"+
                            "<button onclick='deleteMonitorUser(this);' data-id='"+this.id+"' class='btn btn-sm btn-danger'>Delete</button>"+
                            "</td></tr>";
                    });

                    $("#monitortablebody").html(monitorTblData);

                },
                error: function ()
                {
                    alert("Something went Wrong");
                }
            });
        });
    }

};

function discoveryClickButton()
{

    $("#dis-add-btn").on("click", function () {

        $("#overview").html(vtwo);

        $("#type").on('change', function () {
            if ($(this).val() === "ssh") {
                $("#adc").show();
            }
            else
            {
                $("#adc").hide();
            }
        });

        $("#add-btn").on("click", function ()
        {
            var Name = $("#name").val();
            var IP = $("#ip").val();
            var Type = $("#type :selected ").text();
            var username = $("#usernamessh").val();
            var password = $("#passwordssh").val();

            $.ajax({

                type: "POST",
                data: "Name="+Name+"&IP="+IP+"&Type="+Type+"&Username="+username+"&Password="+password,
                url: "discoveryInsertProcess",
                success: function ()
                {

                    alert("Add Success");

                    $("#name").val(null);

                    $("#ip").val(null);

                    $("#type").val(null);

                    $("#usernamessh").val(null);

                    $("#passwordssh").val(null);

                    window.history.go(0);
                },

                error: function ()
                {
                    alert("Error");
                }

            });
        });
    });

}

function editUser(that) {

    var id = $(that).data("id");

    $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" disabled="disabled" required> <option id="ping" value="ping">Ping</option> <option id="ssh" value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="update-btn"  data-id="'+that.dataset.id+'" onclick="updateDis(this)">Update</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>');

    $("#name").val($(that).parent().prev().prev().prev().text());

    $("#ip").val($(that).parent().prev().prev().text());

    if($(that).parent().prev().text()==="SSH")
    {

        $("#ssh").attr("selected","selected");
        $("#ping").removeAttr("selected");
        $("#adc").show();

        $.ajax({
            type: "POST",
            data: "id="+id,
            url:"discoveryGetUsernameProcess",
            success: function (data)
            {
                //$("#usernamessh").val(data.username);
                $("#usernamessh").val(data.username);
            },
            error: function ()
            {
                alert("Something went Wrong");
            }
        });

    }

    else
    {
        $("#ssh").removeAttr("selected");
        $("#ping").attr("selected","selected");
        $("#adc").hide();
    }

}

function updateDis(that) {

    $("#update-btn").on("click", function (){

        var name =$("#name").val();
        var ip = $("#ip").val();
        var uname = $("#usernamessh").val();
        var pass = $("#passwordssh").val();
        var id = $(that).data("id");

        $.ajax({

            type: "POST",
            data: "Name="+name+"&IP="+ip+"&Username="+uname+"&Password="+pass+"&id="+id,
            url: "discoveryUpdateProcess",
            success: function ()
            {
                alert("Update Success");

                window.location.reload();
            },

            error: function ()

            {
                alert("Error");
            }

        });
    });

}

function deleteUser(that) {

    var id = $(that).data("id");

    $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"><div class="card-body">Are You Sure, To Delete this Row? <button type="button" class="btn btn-success btn-block" id="delete-btn" data-id="'+that.dataset.id+'" onclick="deleteDis(this)">Delete</button> </div></div></div>');

}

function deleteDis(that)
{
    $("#delete-btn").on("click", function () {

        var id = $(that).data("id");

        $.ajax({

            type: "POST",
            data: "id="+id,
            url: "discoveryDeleteProcess",
            success: function ()
            {
                $(that).parent().parent().remove();

                alert("Delete Success");

                window.location.reload();
            },

            error: function ()
            {
                alert("Error");
            }

        });
    });
}

function provision(that) {

    var id = $(that).data("id");

    $.ajax({

        data: "id="+id,
        type: "POST",
        url: "monitorProcess",

        success: function ()
        {
            alert("Send for monitor ping Success");

            window.location.reload();
        },

        error: function ()
        {
            alert("Error in sending to monitor");
        }

    });

}

function deleteMonitorUser(that) {

    var id = $(that).data("id");

    $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"><div class="card-body">Are You Sure, To Delete this Row? <button type="button" class="btn btn-success btn-block" id="delete-monitor-btn" data-id="'+that.dataset.id+'" onclick="deleteMonitor(this)">Delete</button> </div></div></div>');

}

function deleteMonitor(that)
{
    $("#delete-monitor-btn").on("click", function () {

        var id = $(that).data("id");

        $.ajax({

            type: "POST",
            data: "id="+id,
            url: "monitorDeleteProcess",
            success: function ()
            {
                $(that).parent().parent().remove();

                alert("Delete Success");

                window.location.reload();
            },

            error: function ()
            {
                alert("Error");
            }

        });
    });
}

function showMonitor(that)
{
    //$("#overview").html('<div class="col-lg-4 d-flex flex-column"> <div class="row flex-grow"> <div class="col-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> </div> </div> </div> </div> </div>');
    //$("#overview").html('<div class="row flex-grow"> <div class="col-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div class="row"> <div class="col-lg-12"> <div class="d-flex justify-content-between align-items-center mb-3"> <h4 class="card-title card-title-dash">Type By Amount</h4> </div> <canvas class="my-auto" id="doughnutChart" height="200"></canvas> <div id="doughnut-chart-legend" class="mt-5 text-center"    ></div> </div> </div> </div> </div> </div> </div> ');
    //$("#overview").html('<div class="row flex-grow"> <div class="col-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div class="row"> <div class="col-lg-12"><div class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand"><div class=""></div></div><div class="chartjs-size-monitor-shrink"><div class=""></div></div></div> <div class="d-flex justify-content-between align-items-center mb-3"> <h4 class="card-title card-title-dash">Type By Amount</h4> </div> <canvas class="my-auto chartjs-render-monitor" id="doughnutChart" height="613" style="display: block; width: 200px; height: 300px;" width="300"></canvas> <div id="doughnut-chart-legend" class="mt-5 text-center"><div class="chartjs-legend"><ul class="justify-content-center"><li><span style="background-color:#1F3BB3"></span>Total</li><li><span style="background-color:#FDD0C7"></span>Net</li><li><span style="background-color:#52CDFF"></span>Gross</li><li><span style="background-color:#81DADA"></span>AVG</li></ul></div></div> </div> </div> </div> </div> </div> </div> ');


   /* if ($("#doughnutChart").length) {
        var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
        var doughnutChart = new Chart(doughnutChartCanvas, {
            type: 'doughnut',
            data: doughnutPieData,
            options: doughnutPieOptions
        });
    }*/

   /* if ($("#doughnutChart").length) {
        var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
        var doughnutPieData = {
            datasets: [{
                data: [40, 20, 30, 10],
                backgroundColor: [
                    "#1F3BB3",
                    "#FDD0C7",
                    "#52CDFF",
                    "#81DADA"
                ],
                borderColor: [
                    "#1F3BB3",
                    "#FDD0C7",
                    "#52CDFF",
                    "#81DADA"
                ],
            }],

            // These labels appear in the legend and in the tooltips when hovering different arcs
            labels: [
                'Total',
                'Net',
                'Gross',
                'AVG',
            ]
        };
        var doughnutPieOptions = {
            cutoutPercentage: 50,
            animationEasing: "easeOutBounce",
            animateRotate: true,
            animateScale: false,
            responsive: true,
            maintainAspectRatio: true,
            showScale: true,
            legend: false,
            legendCallback: function (chart) {
                var text = [];
                text.push('<div class="chartjs-legend"><ul class="justify-content-center">');
                for (var i = 0; i < chart.data.datasets[0].data.length; i++) {
                    text.push('<li><span style="background-color:' + chart.data.datasets[0].backgroundColor[i] + '">');
                    text.push('</span>');
                    if (chart.data.labels[i]) {
                        text.push(chart.data.labels[i]);
                    }
                    text.push('</li>');
                }
                text.push('</div></ul>');
                return text.join("");
            },

            layout: {
                padding: {
                    left: 0,
                    right: 0,
                    top: 0,
                    bottom: 0
                }
            },
            tooltips: {
                callbacks: {
                    title: function(tooltipItem, data) {
                        return data['labels'][tooltipItem[0]['index']];
                    },
                    label: function(tooltipItem, data) {
                        return data['datasets'][0]['data'][tooltipItem['index']];
                    }
                },

                backgroundColor: '#fff',
                titleFontSize: 14,
                titleFontColor: '#0B0F32',
                bodyFontColor: '#737F8B',
                bodyFontSize: 11,
                displayColors: false
            }
        };
        var doughnutChart = new Chart(doughnutChartCanvas, {
            type: 'doughnut',
            data: doughnutPieData,
            options: doughnutPieOptions
        });
        document.getElementById('doughnut-chart-legend').innerHTML = doughnutChart.generateLegend();
    }*/

    var type = $(that).data("value");
    var id = $(that).data("id");
    ip = $(that).parent().prev().prev().prev().text();

    if(type === "Ping")
   {
       $("#overview").html('<div class="col-md-6 col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div>IP: <b>'+ip +'</b> &nbsp;&nbsp;&nbsp;Type: <b>'+type+'</b></div></div></div></div> <br><br> <div class="row"><div id="chartContainer" style="height: 300px; width: 100%;"></div></div><br><br><br>   <div class="card card-rounded"> <div class="card-body"><div id="chartContainerPartTwo" style="height: 300px; width: 100%;"></div></div></div>  <br><br><br>      <div class="card card-rounded"> <div class="card-body">  <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"><div id="packetLoss" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Packet Loss(%)<hr><p style="font-size: 25px"></p></div>    <div id="rtt" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">RTT(ms)<hr><p style="font-size: 25px"> </p> </div><div id="ReceivePacket" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Receive Packet<hr><p style="font-size: 25px"> </p> </div><div id="SendPacket" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Send Packet<hr><p style="font-size: 25px"> </p></div>    </div>  </div></div>    ');



       var up = 0;
       var down = 0;

       var chart = new CanvasJS.Chart("chartContainerPartTwo", {
           animationEnabled: true,
           exportEnabled: true,
           theme: "light1", // "light1", "light2", "dark1", "dark2"
           title:{
               text: "Simple Column Chart with Index Labels"
           },
           axisY: {
               includeZero: true
           },
           data: [{
               type: "column", //change type to bar, line, area, pie, etc
               //indexLabel: "{y}", //Shows y value on all Data Points
               indexLabelFontColor: "#5A5757",
               indexLabelFontSize: 16,
               indexLabelPlacement: "outside",
               dataPoints: [
                   { x: 10, y: 71 },
                   { x: 20, y: 55 },
                   { x: 30, y: 50 },
                   { x: 40, y: 65 },
                   { x: 50, y: 92, indexLabel: "\u2605 Highest" },
                   { x: 60, y: 68 },
                   { x: 70, y: 38 },
                   { x: 80, y: 71 },
                   { x: 90, y: 54 },
                   { x: 100, y: 60 },
                   { x: 110, y: 36 },
                   { x: 120, y: 49 },
                   { x: 130, y: 21, indexLabel: "\u2691 Lowest" }
               ]
           }]
       });
       chart.render();

       $.ajax({
           type: "POST",
           data: "id="+id+"&type="+type,
           url:"monitorShowTableProcess",
           success: function (data)
           {
               $("#packetLoss p").text(data.matrixList[2]);
               $("#sendPacket p").text(data.matrixList[0]);
               $("#receivePacket p").text(data.matrixList[1]);
               $("#rtt p").text(data.matrixList[3]);
               up = data.pingStatusList[0];
               down = data.pingStatusList[1];

               var chart = new CanvasJS.Chart("chartContainer", {
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

           },
           error: function ()
           {
               alert("Something went Wrong");
           }
       });

   }

   else if(type === "SSH")
   {
       $("#overview").html('<div class="col-md-6 col-lg-12 grid-margin stretch-card"> <div class="card card-rounded"> <div class="card-body"> <div>IP: <b>'+ip +'</b> &nbsp;&nbsp;&nbsp;Type: <b>'+type+'</b></div></div></div></div><br><br>   <div class="row"><div id="chartContainer" style="height: 300px; width: 100%;"> </div></div><br><br><br>   <div class="card card-rounded"> <div class="card-body"> <div id="chartContainerPartTwo" style="height: 300px; width: 100%;"></div> </div></div>  <br><br><br>   <div class="card card-rounded"> <div class="card-body">  <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-auto-rows: 130px"><div id="cpu" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px; padding: 10px">CPU(%)<hr><p style="font-size: 25px"></p></div>    <div id="disk" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Disk(%)<hr><p style="font-size: 25px"></p> </div><div id="memory" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Memory(MB)<hr><p style="font-size: 25px"></p></div>  <div id="memory" style="height: 130px; width: 150px; color: white; background-color: #008000; font-size: 18px;  padding: 10px">Swap Memory(MB)<hr><p style="font-size: 25px"></p></div>  </div>  </div></div>  ');


       var up = 0;
       var down = 0;

       var chart = new CanvasJS.Chart("chartContainerPartTwo", {
           animationEnabled: true,
           exportEnabled: true,
           theme: "light1", // "light1", "light2", "dark1", "dark2"
           title:{
               text: "Simple Column Chart with Index Labels"
           },
           axisY: {
               includeZero: true
           },
           data: [{
               type: "column", //change type to bar, line, area, pie, etc
               //indexLabel: "{y}", //Shows y value on all Data Points
               indexLabelFontColor: "#5A5757",
               indexLabelFontSize: 16,
               indexLabelPlacement: "outside",
               dataPoints: [
                   { x: 10, y: 71 },
                   { x: 20, y: 55 },
                   { x: 30, y: 50 },
                   { x: 40, y: 65 },
                   { x: 50, y: 92, indexLabel: "\u2605 Highest" },
                   { x: 60, y: 68 },
                   { x: 70, y: 38 },
                   { x: 80, y: 71 },
                   { x: 90, y: 54 },
                   { x: 100, y: 60 },
                   { x: 110, y: 36 },
                   { x: 120, y: 49 },
                   { x: 130, y: 21, indexLabel: "\u2691 Lowest" }
               ]
           }]
       });
       chart.render();

       $.ajax({
           type: "POST",
           data: "id="+id+"&type="+type,
           url:"monitorShowTableProcess",
           success: function (data)
           {
               $("#cpu p").text(data.sshMatrixList[0]);
               $("#disk p").text(data.sshMatrixList[2]);
               $("#memory p").text(data.sshMatrixList[1]);
               up = data.sshStatusList[0];
               down = data.sshStatusList[1];

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
                           { y: up, label: "Up" },
                           { y: down, label: "Down" },

                       ]
                   }]
               });
               chart.render();

           },
           error: function ()
           {
               alert("Something went Wrong");
           }
       });




   }

}