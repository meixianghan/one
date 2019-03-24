<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>图片延迟加载</title>
    <script type="application/javascript" src="js/jquery/jquery-2.1.0.js"></script>
    <script type="application/javascript" src="js/jquery/jquery.scrollstop.min.js"></script>
    <script type="application/javascript" src="js/jquery/jquery.lazyload.min.js"></script>
    <script type="application/javascript">
        $(document).ready(function(){
            console.log($("img.lazy"))
            $("img.lazy").lazyload({
                effect:"fadeIn", //淡入效果
                event:"scrollstop",
                threshold:200,//距离屏幕 200 像素时提前加载
                failure_limit:20,//发现20 个不在可见区域的图片时停止搜索
                skip_invisible : false//加载隐藏图片
            });
        });
    </script>
</head>
<body>
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<p>----------------------------------------------------------------------------------------------</p>
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
<img class="lazy" alt="" width="640" height="480" src="http://image.mrerror.cn/77.jpg" data-original="http://image.mrerror.cn/a/b/yiwei.jpg" />
</body>
</html>
