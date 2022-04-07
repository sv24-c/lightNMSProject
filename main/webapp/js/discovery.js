/**
 * Created by smit on 3/4/22.
 */

var ip = null;

var discoverymain = {

    onload: function ()
    {
        $("#discovery-onclick").on("click", function () {

            let request = {

                url: "discoveryProcess",

                data: "",

                callback: discoverycallback.onload,
            };

            mainHelper.ajaxpost(request);
        });

    },

    add: function ()
    {

        $("#dis-add-btn").on("click", function () {

            $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" required> <option value="ping">Ping</option> <option value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="add-btn">Add</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>');

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

            let sdata = {
                Name: name,
                IP: ip,
                Type: type,
                Username: username,
                Password: password,
            };

            let request = {

                url: "discoveryInsertProcess",

                data: sdata,

                callback: discoverycallback.add,
            };

            mainHelper.ajaxpost(request);

        });

        });
    },

    provision: function (that)
    {
        toastr.info("Provision Starting");

        let id = $(that).data("id");

        let sdata = {
            id: id
        };

        let request = {

            url: "monitorProcess",

            data: sdata,

            callback: discoverycallback.provision,
        };
        mainHelper.ajaxpost(request);

        //toastr.info(data.status);
    },

    edit: function (that)
    {
        var id = $(that).data("id");

        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <div class="container" > <div class="col-lg-5"> <div class="row"> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>Name</label><br /> <input type="text" name="Name" id="name" class="form-control input-sm" placeholder="Name" required /> </div> </div> <div class="col-xs-6 col-sm-6 col-md-6"> <div class="form-group"> <label>IP</label><br /> <input type="text" name="IP" id="ip" class="form-control input-sm" placeholder="IP" required /> </div> </div> </div> <div class="form-group"> <label for="type">Select Device Type:</label> <select name="Type" id="type" disabled="disabled" required> <option id="ping" value="ping">Ping</option> <option id="ssh" value="ssh">SSH</option> </select>  </div> <div id="adc" style="display: none;"> <label for="type">UserName</label><br /> <input type="text" class="form-control input-sm" id="usernamessh" name="usernamessh" placeholder="UserName" required /><br /> <label for="type">Password</label><br /> <input type="password" class="form-control input-sm" id="passwordssh" name="passwordssh"placeholder="Password" required /><br /><br /> </div> <button type="button" class="btn btn-success btn-block" id="update-btn"  data-id="'+that.dataset.id+'" onclick="discoverymain.update(this)">Update</button> </div></div><div class="text-center" id="resp" style="margin-top: 14px;" ></div></div></div></div>');

        $("#name").val($(that).parent().prev().prev().prev().text());

        $("#ip").val($(that).parent().prev().prev().text());

        if($(that).parent().prev().text()==="SSH")
        {

            $("#ssh").attr("selected","selected");
            $("#ping").removeAttr("selected");
            $("#adc").show();

            //$("#usernamessh").val(data.username);

            let sdata = {
                id: id,
            };
            let request = {

                url: "discoveryGetUsernameProcess",

                data: sdata,

                callback: discoverycallback.getUsername,
            };

            mainHelper.ajaxpost(request);
        }

        else
        {
            $("#ssh").removeAttr("selected");
            $("#ping").attr("selected","selected");
            $("#adc").hide();
        }

    },

    update: function (that)
    {
            var name = $("#name").val();
            var ip = $("#ip").val();
            var uname = $("#usernamessh").val();
            var pass = $("#passwordssh").val();
            var id = $(that).data("id");

            let sdata = {
            id: id,
            Name: name,
            IP: ip,
            Username: uname,
            Password: pass,
            };

           let request = {

                url: "discoveryUpdateProcess",

                data: sdata,

                callback: discoverycallback.update,
            };
            mainHelper.ajaxpost(request);
},

    delete: function (that)
    {

        var id = $(that).data("id");

        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"><div class="card-body">Are You Sure, To Delete this Row? <button type="button" class="btn btn-success btn-block" id="delete-btn" data-id="'+that.dataset.id+'" onclick="discoverymain.deletediscovery(this)">Delete</button> </div></div></div>');

        toastr.warning('Data Would be Permanently deleted');
    },

    deletediscovery: function (that)
    {

        var id = $(that).data("id");

        let sdata = {
            id: id,
        };

        let request = {

            url: "discoveryDeleteProcess",

            data: sdata,

            callback: discoverycallback.deletefinal,
        };
        mainHelper.ajaxpost(request);

    }


};

var discoverycallback = {

    onload: function (data)
    {
        $("#overview").html('<div class="col-lg-12 grid-margin stretch-card"> <div class="card"> <div class="card-body"> <h4 class="card-title">Discovery Grid</h4>   <button type="submit" class="btn btn-primary me-2" id="dis-add-btn" onclick="discoverymain.add()">Add</button>     <div class="table-responsive pt-3"> <table class="table table-bordered"> <thead> <tr> <th> Name </th> <th> IP </th> <th> Type </th> </tr> </thead> <tbody id="tablebody"> <tr> </tr> </tbody> </table> </div> </div> </div> </div>');

        let tblData = "";

        $.each(data.discoveryBeanList, function() {

            tblData += "<tr><td>" + this.name + "</td>" +
                "<td>" + this.IP + "</td>" +
                "<td>" + this.type + "</td>" +
                "<td>"+
                "<button onclick='discoverymain.provision(this);' data-id='"+this.id+"' class='btn btn-sm btn-info' >Provision</button>"+
                "<button onclick='discoverymain.delete(this);' data-id='"+this.id+"' class='btn btn-sm btn-danger'>Delete</button>"+
                "<button onclick='discoverymain.edit(this);' data-id='"+this.id+"' ' data-type='" + this.type + "' class='btn btn-sm btn-secondary' id='dis-edit-btn'>Edit</button>"+
                "</td></tr>";
        });

        $("#tablebody").html(tblData);

    },

     add: function (data)
     {
            $("#name").val(null);

            $("#ip").val(null);

            $("#type").val(null);

            $("#usernamessh").val(null);

            $("#passwordssh").val(null);
         toastr.success('Data Added to Discovery Grid');
         toastr.info(data.status+" already in Discovery");

         window.history.go(0);



     },

    provision: function (data)
    {
        //toastr.success('Discovery Provision Done');

        toastr.info(data.status);

        discoverycallback.onload();

        //window.location.reload();

    },

    update: function ()
    {
        toastr.success('Data Updated Successfully');

        window.location.reload();
    },

    deletefinal: function ()
    {
        toastr.info('Data Deleted Successfully');

        window.location.reload();
    },

    getUsername: function (data) {

        $("#usernamessh").val(data.username);
    }
};