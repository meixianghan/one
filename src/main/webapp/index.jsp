<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <script src="js/jquery-2.1.0.js"></script>
    <script>
        var data={
            account:"1111",
            password:'2222'
        };
        $.ajax({
            type: 'POST',
            // url: "http://www.mrerror.com/ssm/user/loginJson",
            url: "http://localhost:8080/ssm/user/loginJson",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function(a){
                alert(a);
            },
            dataType: 'json'
        });

    </script>
</head>
<body>
<h2>Hello World!</h2>
</body>
</html>
