/**
 * Created by smit on 3/4/22.
 */
var deviceType;

var discoverymain = {

    onload: function ()
    {
        $("#discovery-onclick").on("click", function () {

            let request = {

                url: "discoveryProcess",

                data: "",

                callback: discoverycallback.onload,
            };

            createWebsocket.getWebsocket();

            mainHelper.ajaxpost(request);
        });

    },

    add: function ()
    {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div id="nameRequired" style="display: none;"> Name field is Required! <br><br></div> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="name" id="name" class="form-control input-sm" placeholder="Name" required />  </div> </div>    <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> <button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="discoverymain.back()"> Back </button></div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>');

            $("#type").on('change', function ()
            {
                if ($(this).val() === "ssh") {
                    $("#adc").show();
                }
                else {
                    $("#adc").hide();
                }
            });

        $("#add-btn").on("click", function () {

            let name = $("#name").val();

            let ip = $("#ip").val();

            let type = $("#type :selected ").text();

            let username = $("#usernamessh").val();

            let password = $("#passwordssh").val();

            if (name && ip && type)
            {
                if(type==="Ping")
                {
                    let sendData = {
                        name: name,
                        IP: ip,
                        type: type,
                        username: username,
                        password: password,
                    };

                    let request = {

                        url: "discoveryInsertProcess",

                        data: sendData,

                        callback: discoverycallback.add,
                    };

                    mainHelper.ajaxpost(request);
                }
                else
                {
                    if(username && password)
                    {
                        let sendData = {
                            name: name,
                            IP: ip,
                            type: type,
                            username: username,
                            password: password,
                        };

                        let request = {

                            url: "discoveryInsertProcess",

                            data: sendData,

                            callback: discoverycallback.add,
                        };

                        mainHelper.ajaxpost(request);
                    }
                    else
                    {
                        toastr.warning("Field Required to Fill");
                    }
                }
            }
            else
            {
                toastr.warning("Field Required to Fill");
            }

        });
    },

    provision: function (that)
    {

        let id = $(that).data("id");

        let sendData = {
            id: id
        };

        let request = {

            url: "monitorProcess",

            data: sendData,

            callback: discoverycallback.provision,
        };
        mainHelper.ajaxpost(request);

        },

    edit: function (that)
    {
        let id = $(that).data("id");

        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="type" id="type" disabled="disabled" required> <option id="ping" value="ping">Ping</option> <option id="ssh" value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="update-btn"  data-id="'+that.dataset.id+'" onclick="discoverymain.update(this)">Update</button> <button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="discoverymain.back()"> Back </button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>');

        $("#name").val($(that).parent().prev().prev().prev().text());

        $("#ip").val($(that).parent().prev().prev().text());

        if($(that).parent().prev().text()==="SSH")
        {
            deviceType = "SSH";

            $("#ssh").attr("selected","selected");
            $("#ping").removeAttr("selected");
            $("#adc").show();

            let sendData = {
                id: id,
            };
            let request = {

                url: "discoveryGetUsernameProcess",

                data: sendData,

                callback: discoverycallback.getUsername,
            };

            mainHelper.ajaxpost(request);
        }

        else
        {
            deviceType = "Ping";

            $("#ssh").removeAttr("selected");
            $("#ping").attr("selected","selected");
            $("#adc").hide();
        }

    },

    update: function (that)
    {
            let name = $("#name").val();
            let ip = $("#ip").val();
            let uname = $("#usernamessh").val();
            let pass = $("#passwordssh").val();

            if(deviceType==="SSH")
            {
                if (name && ip && uname && pass)
                {
                    let id = $(that).data("id");

                    let sendData = {
                        id: id,
                        name: name,
                        IP: ip,
                        username: uname,
                        password: pass,
                    };

                    let request = {

                        url: "discoveryUpdateProcess",

                        data: sendData,

                        callback: discoverycallback.update,
                    };
                    mainHelper.ajaxpost(request);
                }
                else
                {
                    toastr.warning("Field Required to Fill");
                }
            }
            else
            {
                if (name && ip && type)
                {
                    let id = $(that).data("id");

                    let sendData = {
                        id: id,
                        name: name
                        ,
                        IP: ip,
                        username: uname,
                        password: pass,
                    };

                    let request = {

                        url: "discoveryUpdateProcess",

                        data: sendData,

                        callback: discoverycallback.update,
                    };
                    mainHelper.ajaxpost(request);
                }
                else
                {
                    toastr.warning("Field Required to Fill");
                }
            }
},

    delete: function (that)
    {
        let id = $(that).data("id");

        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"><div class="card-body">Are You Sure, To Delete this Row? <button type="button" class="btn btn-success btn-block" id="delete-btn" data-id="'+that.dataset.id+'" onclick="discoverymain.deletediscovery(this)">Delete</button> <button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="discoverymain.back()"> Back </button> </div></div></div>');

        toastr.warning('Data Would be Permanently deleted');
    },

    deletediscovery: function (that)
    {

        let id = $(that).data("id");

        let sendData = {
            id: id,
        };

        let request = {

            url: "discoveryDeleteProcess",

            data: sendData,

            callback: discoverycallback.confirmdelete,
        };
        mainHelper.ajaxpost(request);

    },

    back: function ()
    {
        let request = {

            url: "discoveryProcess",

            data: "",

            callback: discoverycallback.onload,
        };

        mainHelper.ajaxpost(request);

    },

};

var discoveryhelper = {

    adddata: function (data , table) {
        $.each(data.discoveryBeanList, function () {
            table.row.add([this.name, this.IP, this.type, "<button onclick='discoverymain.provision(this);' data-id='"+this.id+"' class='btn btn-sm btn-info'>Provision</button><button onclick='discoverymain.delete(this);' data-id='"+this.id+"' class='btn btn-sm btn-danger'>Delete</button><button onclick='discoverymain.edit(this);' data-id='"+this.id+"'  data-type='" + this.type + "' class='btn btn-sm btn-secondary' id='dis-edit-btn'>Edit</button>"]).draw();
        });
    },

};

var discoverycallback = {

    onload: function (data)
    {

        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery Grid</h4>   <button type="button" class="btn btn-primary me-2" id="dis-add-btn" onclick="discoverymain.add()">Add Device</button>     <div class="table-responsive pt-3"> <table class="table table-bordered" id="discoveryTable"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> <th> Operation </th> </tr> </thead> </table> </div> </div> </div> </div>');

        table = $('#discoveryTable').DataTable({lengthMenu: [5, 10, 20, 50, 100, 200, 500]});

        discoveryhelper.adddata(data, table);
    },

     add: function (data)
     {
            $("#name").val(null);

            $("#ip").val(null);

            $("#type").val(null);

            $("#usernamessh").val(null);

            $("#passwordssh").val(null);

         toastr.info(data.status);

         discoverymain.back();

     },

    provision: function (data)
    {
        toastr.success(data.status);

    },

    update: function ()
    {
        toastr.success('Data Updated Successfully');

        discoverymain.back();

    },

    confirmdelete: function ()
    {
        toastr.info('Data Deleted Successfully');

        discoverymain.back();

    },

    getUsername: function (data) {

        $("#usernamessh").val(data.username);
    }
};