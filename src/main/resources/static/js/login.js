// 将数字及大小写字母存进Arr中
var arr = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
for (var i = 65; i < 122; i++) {
    if (i > 90 && i < 97) {
        continue;
    }
    //fromCharCode是将数字转换为asc对应的字符
    arr.push(String.fromCharCode(i));
}
var a = 1;
// 创建canvas区域
var canvasStr, value;

function createCanvas() {
    canvasStr = '';
    value = '';
    // 在arr中随机取出6位作为展示在canvas区域的字符串
    for (var i = 0; i < 4; i++) {
        var a = arr[Math.floor(Math.random() * arr.length)]
        canvasStr += a + ' ';
        value += a;
    }
    var myCanvas = document.getElementById('myCanvas');
    var ctx = myCanvas.getContext('2d');
    var x = myCanvas.width / 2;
    var oImg = new Image();

    oImg.src = '../images/bg.jpg';
    oImg.onload = function () {
        // 在canvas内重复图片元素作为背景
        var pattern = ctx.createPattern(oImg, 'repeat');
        ctx.fillStyle = pattern;
        ctx.fillRect(0, 0, myCanvas.width, myCanvas.height);
        // 填充canvas文字内容
        ctx.textAlign = 'center';
        ctx.fillStyle = '#ccc';
        ctx.font = '16px Roboto Slab';
        // 设置文字得倾斜
        ctx.setTransform(1, -0.12, 0.2, 1, 0, 12);
        ctx.fillText(canvasStr, x, 15);
    }
}

createCanvas();
bindevent();

function bindevent() {
    var refresh = document.getElementsByClassName('refresh')[0];
    refresh.addEventListener('click', function () {
        createCanvas();
    })
}


$(function () {

    //忘记密码提示
    $("#forgetPassword").click(function () {
        $("#message2").attr("class","alert alert-warning");

        $("#message2").append("<p align='center'>请尝试联系高级管理员，让相关管理员为您更改密码！<p>");

       $(".forget").hide();
        setTimeout(function () {
            window.location.reload();
        }, 3000);
    });
})

//登录表单校验
$(function () {

    $.validator.addMethod(
        //规则的名称
        "checkCode",
        //校验的函数
        function (avalue, element, params) {
            inputValue = $("#inputcode").val();
            if (value.toLowerCase() != inputValue.toLowerCase()) {
                return false;
            }
            return true;
        })

    $("#myform").validate({
        rules: {
          /*  "username": {//绑定的是表单name属性,非id
                "required": true,
                "rangelength": [3, 12],
                "checkUsername":true,
                "typename":true
               },*/
            "password": {//绑定的是表单name属性,非id
                "required": true,
                "rangelength": [3, 12]
            },
            "inputcode": {
                "required": true,
                "checkCode": true
            }
        },
        messages: {
        /*    "username": {
                "required": "用户名不能为空",
                "rangelength": "用户名长度为3-12位",
                "checkUsername":"用户名不存在!",
                "typename":"请输入字符，不含中文"
            },*/
            "password": {
                "required": "密码不能为空",
                "rangelength": "密码长度3-12位"
            },
            "inputcode": {
                "required": "验证码不能为空",
                "checkCode": "验证码输入错误"
            }
        },
        //验证通过执行函数
        submitHandler:function (){

            $.ajax({
                type: 'post',
                url: '/user/login',
                data: {
                    'username': $('#username').val(),
                    'password': $('#password').val()
                },
                dataType: 'json',
                async:true,
                success: function (result) {
                    var p="<p align='center'>"+result.msg+"</p>";
                    if (result.status=="success") {
                        //登录成功
                        //弹出登录成功信息
                        $("#message2").attr("class","alert alert-success");
                        $("#message2").append(p);
                        setTimeout(function () {
                            window.location.href = "/admin/index";
                        }, 500);

                    } else {
                        //登录失败
                        //弹出登录失败原因信息
                        $("#message2").attr("class","alert alert-danger");
                        $("#message2").append(p);
                        setTimeout(function () {
                            window.location.href = "/user/toLogin";
                        }, 500);


                    }
                }

            });
        }
    })

/*   此方法表单验证有bug

 $("#loginuser").click(function (){

        $.ajax({
            type: 'post',
            url: '/user/login',
            data: {
                'username': $('#username').val(),
                'password': $('#password').val()
            },
            dataType: 'json',
            async:true,
            success: function (result) {
                var p="<p align='center'>"+result.msg+"</p>";
                if (result.status=="success") {
                    //登录成功
                    //弹出登录成功信息
                    $("#message2").attr("class","alert alert-success");

                    $("#message2").append(p);
                    setTimeout(function () {
                        window.location.href = "/user/index";
                    }, 2000);

                } else {
                    //登录失败
                    //弹出登录失败原因信息
                    $("#message2").attr("class","alert alert-danger");
                    $("#message2").append(p);
                    setTimeout(function () {
                        window.location.href = "/user/toLogin";
                    }, 2000);

                    //登录失败 layer.msg(data.msg);
                }
            }

        });
        return false;
    })*/
})

