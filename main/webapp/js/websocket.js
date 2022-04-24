/**
 * Created by smit on 11/4/22.
 */
let createWebsocket =
    {
        getWebsocket: function () {

            let requesturl = $(location).attr('href');

            let splitUrl = requesturl.split("/");

            let lastUrlName = splitUrl[splitUrl.length - 2];

            let websocket = new WebSocket("wss://" + lastUrlName + "/endpoint");

            websocket.onopen = function (message) {webSocketOnOpen(message)};

            websocket.onmessage = function (message) {webSocketOnMessage(message)};

            websocket.onclose = function (message) {webSocketOnClose(message);};

            websocket.onerror = function (message) {webSocketOnError(message)};

            function webSocketOnOpen(message)
            {
                //toastr.info("Websocket OnOpen "+message.data);
            }

            function webSocketOnMessage(message)
            {
                toastr.info(message.data);
            }

            function webSocketOnClose()
            {
                createWebsocket.getWebsocket();
            }

            function webSocketOnError(message)
            {
                //toastr.info("WebSocket have Error: "+message.data);
            }
        }
    };