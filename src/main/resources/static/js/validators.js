$(function (){
    //自定义校验规则
    //验证车牌是否未出库
    $.validator
        .addMethod(
            //规则的名称
            "checkNumbers",

            function (value, element, params) {
                var flag = false;
                $.ajax({
                    async: false,//必须改成false同步。Ajax请求将整个浏览器锁死，只有ajax请求返回结果后，才执行ajax后面的语句。
                    url: '/order/checkCarNumber',
                    data: {
                        "province": $("#selectId").val(),
                        "carNumber": $("#car_number").val(),
                    },
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        flag = data;
                    }
                });
                //返回false代表该校验器不通过
                return flag;
            }
        );

    //车牌验证
    $.validator.addMethod("checkCarNumber", function (value, element) {
        var mobile = /^(([A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/;
        return this.optional(element) || mobile.test(value);
    }, "请正确填写车牌号码");

    //登录 验证用户名是否存在，存在即为false
    $.validator
        .addMethod(
            //规则的名称
            "checkUsername",
            //校验的函数
            function (value, element, params) {
                //定义一个标志
                var flag = false;
                //value:输入的内容
                //element:被校验的元素对象
                //params：规则对应的参数值
                //目的：对输入的username进行ajax校验
                $.ajax({
                    async: false,//必须改成false同步。Ajax请求将整个浏览器锁死，只有ajax请求返回结果后，才执行ajax后面的语句。
                    url: '/user/checkUsername',
                    data: {
                        'username': value
                    },
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        flag = data;
                        alert(flag);
                        console.log(flag);
                    }
                });
                //返回false代表该校验器不通过
                return flag;
            }
        );

    //注册验证
    //验证用户名是否存在，存在即为false
    $.validator
        .addMethod(
            //规则的名称
            "check2Username",
            //校验的函数
            function (value, element, params) {

                //定义一个标志
                var flag = false;

                //value:输入的内容
                //element:被校验的元素对象
                //params：规则对应的参数值
                //目的：对输入的username进行ajax校验
                $.ajax({
                    async: false,//必须改成false同步。Ajax请求将整个浏览器锁死，只有ajax请求返回结果后，才执行ajax后面的语句。
                    url: '/user/checkUsername',
                    data: {
                        'username': value

                    },
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        flag = !data;
                    }
                });
                //返回false代表该校验器不通过
                return flag;
            }
        );

    $.validator
        .addMethod(
            //规则的名称
            "checkCode2",
            //校验的函数
            function (value, element, params) {

                //定义一个标志
                var flag = false;

                $.ajax({
                    async: false,//必须改成false同步。Ajax请求将整个浏览器锁死，只有ajax请求返回结果后，才执行ajax后面的语句。
                    url: '/user/checkCode',
                    data: {
                        'code': value

                    },
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        flag = data;
                    }
                });

                //返回false代表该校验器不通过
                return flag;
            }
        );

    $.validator
        .addMethod(
            "isMobile",
            function (value, element) {
                var length = value.length;
                var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
                return this.optional(element)
                    || (length == 11 && mobile.test(value));
            }, "请正确填写手机号码");
//邮箱验证
    $.validator
        .addMethod(
            "isEmail",
            function (value, element) {
                var myreg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
                if(!myreg.test(value)){
                    return false;
                }
                return true;
            });
//验证输入框不能有中文
    $.validator.addMethod(
        "typename",
        function (value,element){
            {
                var reg =/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
                //alert(1);
                if (reg.test(value)){

                    return false;
                }
                return true;
            }
        }
    )

    //验证密码
    $.validator
        .addMethod(
            //规则的名称
            "checkPassword",
            //校验的函数
            function (value, element, params) {
                //定义一个标志
                var flag = false;

                $.ajax({
                    url: '/user/checkPassword',
                    data: {
                        "password": value
                    },
                    type: 'POST',
                    dataType: 'json',
                    async:false,
                    success: function (data) {
                        flag = data;
                    }
                });
                //返回false代表该校验器不通过
                return flag;
            }
        );

})