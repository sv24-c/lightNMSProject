/**
 * Created by smit on 12/3/22.
 */
var v = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery table</h4>   <button type="submit" class="btn btn-primary me-2" id="dis-add-btn" onclick="discoveryClickButton()">Add</button>     <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>';
var vtwo = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>';

var discoveryVariable = {

    discoveryOnClick : function ()
    {

        $("#discovery-onclick").on("click", function ()
        {

            $("#overview").html(v);

            $.ajax({
                type: "GET",
                url:"discoveryProcess",
                success: function (data)
                {

                    var tblData="";

                    $.each(data.discoveryBeanList, function() {

                        tblData += "<tr><td>" + this.name + "</td>" +
                            "<td>" + this.IP + "</td>" +
                            "<td>" + this.type + "</td>" +
                            "<td>"+
                            "<button onclick='addToMonitor(this);' data-id='"+this.id+"' class='btn btn-sm btn-info' >Provision</button>"+
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
                type: "GET",
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
                            "<button onclick='show(this);' data-id='"+this.id+"' class='btn btn-sm btn-info' >Show</button>"+
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
            type: "GET",
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

function addToMonitor(that) {

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