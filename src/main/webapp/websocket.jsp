<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>websocket test</title>
    <base href="<%=basePath%>">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <script type="application/javascript" src="js/jquery-2.1.0.js"></script>
    <script type="application/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            if ("WebSocket" in window||'MozWebSocket' in window) {
                // 打开一个 web socket
                var ws = new WebSocket("ws://localhost:8080/ssm/websocket/socketServer");
                ws.onopen = function () {// Web Socket 已连接上
                    //发送信息
                    $("button").click(function(){
                        if(ws.readyState === 1){
                            var content = $("#inputMsg").val();
                            ws.send(content);
                            $("#receive").append('<li class="list-group-item list-group-item-success"><span class="badge badge-color">1</span>'+content+'</li>');
                        }else{
                            $("#error").text("已断开连接");
                        }
                    });

                    //获取在线人数
                    $.getJSON("http://localhost:8080/ssm/websocket/online",function(data){
                        for(var i =0;i<data.peoples.length;i++)
                            $("#peoples").append(' <li class="list-group-item">'+data.peoples[i]+'</li>');
                    });
                };

                ws.onmessage = function (evt) {//收到服务器数据
                    if(evt.data.indexOf("已上线")!=-1){
                        $("#peoples").append(' <li class="list-group-item">'+data.peoples[i].substring(0,evt.data.indexOf("已上线"))+'</li>');
                    }else{
                        var color ="list-group-item-info";
                        if(evt.data.indexOf("one")!=-1){
                            color ="list-group-item-warning";
                        }
                        $("#receive").append('<li class="list-group-item '+color+'"><span class="badge badge-color">1</span>'+evt.data+'</li>');
                    }
                };

                ws.onclose = function () {//websocket close
                    alert("连接已关闭...");
                };
            } else {
                alert("您的浏览器不支持 WebSocket!");
            }


        });
    </script>
</head>
<body>

    <div class="row">
        <div class="col-md-6">
            <textarea rows="10" cols="100" id="inputMsg" name="inputMsg"></textarea>
            <button class="btn btn-default">发送</button>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">在线人数</div>
                <ul class="list-group" id="peoples">
                </ul>
            </div>
        </div>
    </div>

    <div id="error" class="alert alert-danger" role="alert">
    </div>
    <div class="panel panel-primary">
        <div class="panel-heading">收到消息</div>
        <div class="panel-body">
            <ul id="receive" class="list-group">
            </ul>
        </div>
    </div>

</body>
</html>