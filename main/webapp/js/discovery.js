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

                callback: discoverymain.callbackOnLoad,
            };

            createWebsocket.getWebsocket();

            mainHelper.ajaxpost(request);
        });

    },

    add: function ()
    {
        $("#overview").html('<form id="addDeviceForm"> <div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body">  <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="name" id="name" class="form-control input-sm" placeholder="Name" required />  </div> </div>    <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="type" id="type" required> <option value="Ping">Ping</option> <option value="SSH">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="username" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="password" placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> <button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="discoverymain.back()"> Back </button></div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div> </form>');

        $("#type").on('change', function ()
        {
            if ($(this).val() === "SSH")
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
            let param = $('#addDeviceForm').serializeArray().reduce(function(finalParam, currentValue) {
                finalParam[currentValue.name] = currentValue.value;
                return finalParam;
                }, {});

            let name = $("#name").val();

            let ip = $("#ip").val();

            let type = $("#type :selected ").text();

            let username = $("#usernamessh").val();

            let password = $("#passwordssh").val();

            if (name && ip && type)
            {
                if(type==="Ping")
                {
                    let request = {

                        url: "discoveryInsertProcess",

                        data: param,

                        callback: discoverymain.callbackAdd,
                    };

                    mainHelper.ajaxpost(request);
                }
                else
                {
                    if(username && password)
                    {
                        let request = {

                            url: "discoveryInsertProcess",

                            data: param,

                            callback: discoverymain.callbackAdd,
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

            callback: discoverymain.callbackProvision,

        };

             mainHelper.ajaxpost(request);
        },

    edit: function (that)
    {
        let id = $(that).data("id");

        let type = $(that).data("type");

        $("#overview").html('<form id="editDeviceForm"> <div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="type" id="type" <!--disabled="disabled"--> required> <option id="ping" value="Ping">Ping</option> <option id="ssh" value="SSH">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="username" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="password"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="update-btn"  data-id="'+that.dataset.id+'" onclick="discoverymain.update(this)">Update</button> <button type="button" class="btn btn-success btn-block" id="dis-back-btn" onclick="discoverymain.back()"> Back </button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>  </form>');

        let sendData = {
            id: id,
        };
        let request = {

            url: "discoveryGetUsernameProcess",

            data: sendData,

            callback: discoverymain.callbackFetchUsername,
        };

        mainHelper.ajaxpost(request);

        if($(that).parent().prev().text()==="SSH")
        {
            deviceType = "SSH";

            $("#ssh").attr("selected","selected");

            $("#ping").removeAttr("selected");

            $("#adc").show();
        }
        else
        {
            deviceType = "Ping";

            $("#ssh").removeAttr("selected");

            $("#ping").attr("selected","selected");

            $("#adc").hide();
        }

        $("#type").mousedown(function (event) {
            event.preventDefault();
        });
    },

    update: function (that)
    {
        let param = $('#editDeviceForm').serializeArray().reduce(function(finalParam, currentValue) {
            finalParam[currentValue.name] = currentValue.value;
            return finalParam;
        }, {});

            let name = $("#name").val();
            let ip = $("#ip").val();
            let type = $("#type").val();
            let uname = $("#usernamessh").val();
            let pass = $("#passwordssh").val();

            if(deviceType==="SSH")
            {
                if (name && ip && uname && pass)
                {
                    let id = $(that).data("id");

                    let request = {

                        url: "discoveryUpdateProcess",

                        data: param,

                        callback: discoverymain.callbackUpdate,
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

                    let request = {

                        url: "discoveryUpdateProcess",

                        data: param,

                        callback: discoverymain.callbackUpdate,
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

            callback: discoverymain.callbackConfirmDelete,
        };
        mainHelper.ajaxpost(request);
    },

    back: function ()
    {
        let request = {

            url: "discoveryProcess",

            data: "",

            callback: discoverymain.callbackOnLoad,
        };
        mainHelper.ajaxpost(request);
    },

    callbackOnLoad: function (data)
    {
        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery Grid</h4>   <button type="button" class="btn btn-primary me-2" id="dis-add-btn" onclick="discoverymain.add()">Add Device</button>     <div class="table-responsive pt-3"> <table class="table table-bordered" id="discoveryTable"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> <th> Operation </th> </tr> </thead> </table> </div> </div> </div> </div>');

        let table = $('#discoveryTable').DataTable({lengthMenu: [5, 10, 20, 50, 100, 200, 500]});

        $.each(data.discoveryBeanList, function () {
            table.row.add([this.name, this.IP, this.type, "<button onclick='discoverymain.provision(this);' data-id='"+this.id+"' class='btn btn-sm btn-info'>Provision</button><button onclick='discoverymain.delete(this);' data-id='"+this.id+"' class='btn btn-sm btn-danger'>Delete</button><button onclick='discoverymain.edit(this);' data-id='"+this.id+"'  data-type='" + this.type + "' class='btn btn-sm btn-secondary' id='dis-edit-btn'>Edit</button>"]).draw();
        });
    },

    callbackAdd: function (data)
    {
        $("#name").val(null);

        $("#ip").val(null);

        $("#type").val(null);

        $("#usernamessh").val(null);

        $("#passwordssh").val(null);

        toastr.info(data.status);

        discoverymain.back();
    },

    callbackProvision: function (data)
    {
        toastr.success(data.status);
    },

    callbackFetchUsername: function (data) {

        $("#name").val(data.name);

        $("#ip").val(data.IP);

        $("#usernamessh").val(data.username);
    },

    callbackUpdate: function ()
    {
        toastr.success('Data Updated Successfully');

        discoverymain.back();
    },

    callbackConfirmDelete: function ()
    {
        toastr.info('Data Deleted Successfully');

        discoverymain.back();
    },

};