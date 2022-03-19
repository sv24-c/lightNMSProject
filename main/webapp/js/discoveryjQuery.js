/**
 * Created by smit on 12/3/22.
 */
var v = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery table</h4>   <button type="submit" class="btn btn-primary me-2" id="dis-add-btn" onclick="discoveryClickButton()">Add</button>     <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>';
var vtwo = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>';
var disedit = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="update-btn" onclick="updateDis()">Update</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>';
var disdelete = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"><div class="card-body">Are You Sure, To Delete this Row? <button type="button" class="btn btn-success btn-block" id="delete-btn" onclick="deleteDis()">Delete</button> </div></div></div>';

var discoveryVariable = {


    //var: vtwo = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>',


discoveryFunction : function () {

        $("#discovery-btn").on("click", function (data) {


            var name = $("#name").val(data);
            var ip = $("#ip").val(data);
            var type = $("#type").text();

            $.ajax({
                type: "GET",
                url:"discoveryProcess",
                success: function (data) {

                    $("#resp").show();

                    $(".dashboard").html('<br><br><div class="discovery-main-div"> NMS Discovery Page <button id="add-monitor-btn"">Add Monitor</button></div> <br><br><table class="table table-striped" border="1px solid"> <thead> <th scope="col">Name</th> <th scope="col">IP</th> <th scope="col">Type</th></thead> <tbody> </tbody> </table>');

                    var tblData="";

                    //$.each(data, function() {

                    tblData = "<tr><td>" + name + "</td>" +
                        "<td>" + ip + "</td>" +
                        "<td>" + type + "</td>" +
                        "<td>"+
                        "<button onclick='fetchOldRecord(this);' class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Add</button>"+
                        "<button onclick='deleteUser(this);' class='btn btn-sm btn-danger'>Delete</button>"+
                        "<button onclick='deleteUser(this);' class='btn btn-sm btn-secondary'>Edit</button>"+
                        "</td></tr>";
                    //});

                    //$("#resp").html(tblData);
                    $(" tbody").append(tblData);

                },
                error: function (data) {

                    alert("Something went Wrong");
                }
            });
        });
    },

    /*showSSHFields  : function () {

        $("#type").on('change', function () {
            if ($(this).val() === "ssh") {
                $("#adc").show();
            }
            else
            {
                $("#adc").hide();
            }
        });

    },*/

    discoveryIn  : function () {

            /*var name = $("#name").val();

            var ip = $("#ip").val();

            var type = $("#type :selected").text();*/

        var name = $("#name").val();
        var ip = $("#ip").val();
        var type = $("#type :selected ").text();

            $.ajax({

                type: "POST",
                url: "discoveryInsertProcess",
                data: "name="+name+"&ip="+ip+"&type="+type,
                timeout: 500,

                success: function (data)
                {
                    $("#resp").show();

                    $(".dashboard").html('<br><br><div class="discovery-main-div"> NMS Discovery Page <button id="add-monitor-btn"">Add Monitor</button></div> <br><br><table class="table table-striped" border="1px solid"> <thead> <th scope="col">Name</th> <th scope="col">IP</th> <th scope="col">Type</th></thead> <tbody> </tbody> </table>');

                    $("#add-monitor-btn").on("click", function () {

                        /*
                         $(".discovery-main-div").html('<div id="modalpopup"> <div id="closebtn"> <span>Close Button</span> </div> <p id="rowid" style="visibility: hidden"></p> <label>Name<input type="text" class="addname"></label><br><br> <label>IP<input type="text" class="addip"></label><br><br> <label>Type<input type="text" class="addtype"></label><br><br> <button type="button" class="updtbtn">Add</button> </div>');
                         */
                        $(".container").show();

                        $("#add-btn").on("click", function (data) {

                            var name = $("#name").val();

                            var ip = $("#ip").val();

                            var type = $("#type :selected").text();


                            var tableData = "<tr><td>" + name + "</td>" +
                                "<td>" + ip + "</td>" +
                                "<td>" + type + "</td>" +
                                "<td>"+
                                "<button onclick='fetchOldRecord(this);' class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Add</button>"+
                                "<button onclick='deleteUser(this);' class='btn btn-sm btn-danger'>Delete</button>"+
                                "<button onclick='deleteUser(this);' class='btn btn-sm btn-secondary'>Edit</button>"+
                                "</td></tr>" ;

                            $("tbody").append(tableData);

                            $(".container").hide();

                            $("#name").val(null);

                            $("#ip").val(null);

                            $("#type").val(null);

                            process.exit();


                        });

                    });


                    //  alert(data);

                    alert("Success this");
                },

                error: function ()
                {
                    alert("Error");
                }

            });
        },



    discoveryOnClick : function ()
    {

        $("#discovery-onclick").on("click", function ()
        {

            $("#overview").html(v);
             //v = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery table</h4>   <button type="submit" class="btn btn-primary me-2" id="discovery-add-btn">Add</button>     <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>';


             //vtwo = '<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>';

            var three = ' ';
            $.ajax({
                type: "GET",
                url:"discoveryProcess",
                success: function (data)
                {
                    //$("#overview").html(vtwo);

                    var tblData="";

                    $.each(data.discoveryBeanList, function() {


                        tblData += "<tr><td>" + this.name + "</td>" +
                            "<td>" + this.IP + "</td>" +
                            "<td>" + this.type + "</td>" +
                            "<td>"+
                            "<button onclick='fetchOldRecord(this);' class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Add</button>"+
                            "<button onclick='deleteUser(this);' class='btn btn-sm btn-danger'>Delete</button>"+
                            "<button onclick='editUser(this);' class='btn btn-sm btn-secondary' id='dis-edit-btn'>Edit</button>"+
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
        

            /*$("#discovery-add-btn").on("click", function ()
            {

                                $("#overview").html(vtwo);

                $("#type").on('change', function ()
                {
                    if ($(this).val() === "ssh")
                    {
                        $("#adc").show();
                    }
                    else
                    {
                        $("#adc").hide();
                    }
                });



                $("#add-btn").on("click", function ()
                {

                    $("#overview").html(vtwo);

                    var Name = $("#name").val();
                    var IP = $("#ip").val();
                    var Type = $("#type :selected ").text();
                    var username = $("#usernamessh").val();
                    var password = $("#passwordssh").val();


                    $.ajax({

                        type: "POST",
                        data: "Name="+Name+"&IP="+IP+"&Type="+Type+"&Username="+username+"&Password="+password,
                        url: "discoveryInsertProcess",
                        success: function (data)
                        {

                            alert("Success");

                            //window.location.reload();
                        },

                        error: function ()
                        {
                            alert("Error");
                        }

                    });

                    $("#name").val(null);

                    $("#ip").val(null);

                    $("#type").val(null);

                    $("#usernamessh").val(null);

                    $("#passwordssh").val(null);

                    //$("#overview").html(v);

                    //process.exit();

                });

            });*/

    },

    /*discoveryAddClickButton : function () {

        /!*$("#dis-add-btn").on("click", function () {

            alert(123456);

        });*!/
        /!*var Name = $("#name").val();
        var IP = $("#ip").val();
        var Type = $("#type :selected ").text();
        var username = $("#usernamessh").val();
        var password = $("#passwordssh").val();

        $.ajax({


            type: "POST",
            data: "Name="+Name+"&IP="+IP+"&Type="+Type+"&Username="+username+"&Password="+password,
            url: "discoveryInsertProcess",
            success: function (data)
            {

                //$("#resp").show();

                $("#discovery-add-btn").on("click", function ()
                {
                    //$("#addbtnfields").show();


                    $("#overview").html('<div class="col-lg-12 grid-margin stretch-card" id="addbtnfields" > <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>');


                    $("#add-btn").on("click", function ()
                    {

                        //$("#overview").html(v);
                        $("#name").val(null);

                        $("#ip").val(null);

                        $("#type").val(null);

                        $("#usernamessh").val(null);

                        $("#passwordssh").val(null);

                        $("#overview").html("#discovery-onclick");



                    });

                });

                alert("Success");

                //window.location.reload();
            },

            error: function ()
            {
                alert("Error");
            }

        });*!/
    },*/


};
function discoveryClickButton() {
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
            var id = "";
 alert(123);
            $.ajax({

                type: "POST",
                data: "Name="+Name+"&IP="+IP+"&Type="+Type+"&Username="+username+"&Password="+password+"&id="+id,
                url: "discoveryInsertProcess",
                success: function (data)
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

            //$("#overview").html(v);

            //$("#overview").html("#discovery-onclick");

        });
    });
    /*var Name = $("#name").val();
     var IP = $("#ip").val();
     var Type = $("#type :selected ").text();
     var username = $("#usernamessh").val();
     var password = $("#passwordssh").val();

     $.ajax({


     type: "POST",
     data: "Name="+Name+"&IP="+IP+"&Type="+Type+"&Username="+username+"&Password="+password,
     url: "discoveryInsertProcess",
     success: function (data)
     {

     //$("#resp").show();

     $("#discovery-add-btn").on("click", function ()
     {
     //$("#addbtnfields").show();


     $("#overview").html('<div class="col-lg-12 grid-margin stretch-card" id="addbtnfields" > <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>');


     $("#add-btn").on("click", function ()
     {

     //$("#overview").html(v);
     $("#name").val(null);

     $("#ip").val(null);

     $("#type").val(null);

     $("#usernamessh").val(null);

     $("#passwordssh").val(null);

     $("#overview").html("#discovery-onclick");



     });

     });

     alert("Success");

     //window.location.reload();
     },

     error: function ()
     {
     alert("Error");
     }

     });*/
}

function editUser(that) {

    $("#overview").html(disedit);

    $("#type").on('change', function () {
        if ($(this).val() === "ssh") {
            $("#adc").show();
        }
        else
        {
            $("#adc").hide();
        }
    });

    $("#name").val($(that).parent().prev().prev().prev().text());
    $("#ip").val($(that).parent().prev().prev().text());
    $("#type:selected").val();
    $("#usernamessh").val();
    $("#passwordssh").val();
    
}

function updateDis() {

    $.ajax({

        type: "POST",
        data: "Name="+$("#name").val()+"&IP="+$("#ip").val(),
        url: "discoveryUpdateProcess",
        success: function (data)
        {
            alert("Update Success");

            window.location.reload();
        },

        error: function ()
        {
            alert("Error");
        }

    });
}

function deleteUser() {
    $("#overview").html(disdelete);

    $("#delete-btn").on("click", function () {

        $.ajax({

            type: "POST",
            data: "Name="+$("#name").val(),
            url: "discoveryUpdateProcess",
            success: function (data)
            {
                alert("Delete Success");

                window.location.reload();
            },

            error: function ()
            {
                alert("Error");
            }

        });
    })
}
function showDiscovery(data)
{

    var name = $("#name").text(data);
    var ip = $("#ip").val(data);
    var type = $("#type").text();

    $.ajax({
       type: "GET",
       url:"discoveryProcess",
        success: function (data) {

           $("#resp").show();

            $(".dashboard").html('<br><br><div class="discovery-main-div"> NMS Discovery Page <button id="add-monitor-btn"">Add Monitor</button></div> <br><br><table class="table table-striped" border="1px solid"> <thead> <th scope="col">Name</th> <th scope="col">IP</th> <th scope="col">Type</th></thead> <tbody> </tbody> </table>');

           var tblData="";

            //$.each(data, function() {

                tblData = "<tr><td>" + name + "</td>" +
                    "<td>" + ip + "</td>" +
                    "<td>" + type + "</td>" +
                    "<td>"+
                    "<button onclick='fetchOldRecord(this);' class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Add</button>"+
                    "<button onclick='deleteUser(this);' class='btn btn-sm btn-danger'>Delete</button>"+
                    "<button onclick='deleteUser(this);' class='btn btn-sm btn-secondary'>Edit</button>"+
                    "</td></tr>";
            //});

            //$("#resp").html(tblData);
            $(" tbody").append(tblData);

        },
        error: function (data) {

           alert("Something went Wrong");
        }
    });
}

function discoveryIn()
{

    var name = $("#name").val();

    var ip = $("#ip").val();

    var type = $("#type :selected").text();

    $.ajax({

        type: "GET",
        url: "discoveryProcess",
        timeout: 500,
        success: function (data) {
            $("#resp").show();

            $(".dashboard").html('<br><br><div class="discovery-main-div"> NMS Discovery Page <button id="add-monitor-btn"">Add Monitor</button></div> <br><br><table class="table table-striped" border="1px solid"> <thead> <th scope="col">Name</th> <th scope="col">IP</th> <th scope="col">Type</th></thead> <tbody> </tbody> </table>');

            $("#add-monitor-btn").on("click", function () {

                /*
                 $(".discovery-main-div").html('<div id="modalpopup"> <div id="closebtn"> <span>Close Button</span> </div> <p id="rowid" style="visibility: hidden"></p> <label>Name<input type="text" class="addname"></label><br><br> <label>IP<input type="text" class="addip"></label><br><br> <label>Type<input type="text" class="addtype"></label><br><br> <button type="button" class="updtbtn">Add</button> </div>');
                 */
                $(".container").show();

                $("#add-btn").on("click", function (data) {

                    var name = $("#name").val();

                    var ip = $("#ip").val();

                    var type = $("#type :selected").text();


                    var tableData = "<tr><td>" + name + "</td>" +
                        "<td>" + ip + "</td>" +
                        "<td>" + type + "</td>" +
                        "<td>" +
                        "<button onclick='fetchOldRecord(this);' class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Add</button>" +
                        "<button onclick='deleteUser(this);' class='btn btn-sm btn-danger'>Delete</button>" +
                        "<button onclick='deleteUser(this);' class='btn btn-sm btn-secondary'>Edit</button>" +
                        "</td></tr>";

                    $("tbody").append(tableData);

                    $(".container").hide();

                    $("#name").val(null);

                    $("#ip").val(null);

                    $("#type").val(null);

                    process.exit();


                });

            });


            //  alert(data);

             alert("Success");
        },

        error: function ()
        {
            alert("Error");
        }

    });
}


