if (!window.WebSocket && window.MozWebSocket)
	window.WebSocket=window.MozWebSocket;
if (!window.WebSocket)
	alert("No Support ");
var ws;
 
$(function(){
	 $("#sendbutton").attr("disabled", false);
	 $("#sendbutton").click(sendMessage);
	//startWebSocket();
});
 
/**
 * 发送消息按钮
 * @returns
 */
function sendMessage()
{
	var msg=$("#message").val();
	send(msg);
}
function send(data)
{
	console.log("Send:"+data);
	ws.send(data);
}
function startWebSocket()
{	
	ws = new WebSocket("ws://" + location.host + "/zhaohang/websocket/"+$("#user").val());
    ws.onopen = function(){
    	console.log("success open");
        $("#sendbutton").attr("disabled", false);
    };
    //接收后端的消息并处理
	 ws.onmessage = function(event)
	 {
		 alert("RECEIVE:"+event.data);
		 $("#msg").val(event.data);
		 handleData(event.data); 
	 };
	  ws.onclose = function(event) { 
         console.log('Client notified socket has closed',event); 
      }; 
  
}
 
function handleData(data)
{
   $("#msg").val(data);
	
}