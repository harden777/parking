function search() {
    $("#myModalSearch").modal('show');
    var mycars = new Array("京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑",
        "湘", "皖", "鲁", "新", "苏", "浙", "赣", "鄂", "桂", "甘", "晋", "蒙",
        "陕", "吉", "闽", "贵", "粤", "青", "藏", "川", "宁", "琼", "使", "领");
    for (var i = 0; i < mycars.length; i++) {
        $("#selectIdtwo").append(
            "<option value='" + mycars[i] + "'>" + mycars[i]
            + "</option>");
    }
}

$.validator
    .addMethod(
        "checkCarNumber",
        function (value, element) {
            var mobile = /^(([A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/;
            return this.optional(element) || mobile.test(value);
        }, "请正确填写车牌号码");

$(function () {
    $("#searchInfo")
        .validate(
            {
                rules: {
                    "car_numtwo": {
                        "required": true,
                        "checkCarNumber": true
                    }
                },
                messages: {
                    "car_numtwo": {
                        "required": "车牌号不能为空",
                        "checkCarNumber": "请输入正确的车牌号码"
                    }
                },
                submitHandler: function (form) {
                    $.ajax({
                        async: false,
                        url: "/order/searchcarnumber",
                        data: {
                            "carNumber": $("#car_numtwo").val(),
                            "selectId": $("#selectIdtwo").val()
                        },
                        type: "POST",
                        dataType:"json",
                        success: function (data) {
                            console.log(data);
                            var carOrder = data.carOrder;
                            if (carOrder == null) {
                                $("#orderInfo").html("<p style='color:red'>没有搜索到车辆信息</p>");
                                return;
                            }
                            if (carOrder.state == 1) {
                                $("#orderInfo").html("<p style='color:red'>该车已出库</p>");
                                return;
                            }
                            var carSpace = data.carSpace;
                            var la = "<p>车牌号："
                                + carOrder.province
                                + "·"
                                + carOrder.carNumber
                                + "<br>"
                                + "停车人姓名："
                                + carOrder.customerName
                                + "&emsp;&emsp;停车人电话："
                                + carOrder.customerPhone
                                + "<br>"
                                + "目前在："
                                + carSpace.carStation.c_name
                                + "车库&emsp;车位名称："
                                + carSpace.s_name
                                + "</p>";
                            $("#orderInfo").html(la);
                        },
                        fail: function (e) {
                            alert("请求失败");
                        },
                        error: function (e) {
                            alert("error");
                        }
                    })
                }
            })//validate

})

function searchcaspace() {
    var v = $("#carspaceS_name").val();
    if (v == "" || v == " " || v == null) {
        $("#error").html("<p style='color:red;margin-left:150px'>车位名不能为空</p>");
        return;
    } else {
        $("#error").html("<p id='error'></p>")
    }
    $.ajax({
        async: false,
        url: "/order/searchCaspace",
        data: {
            "s_name": $("#carspaceS_name").val()
        },
        type: "POST",
        success: function (data) {
            if (data.s_name != null) {
                var state, type;
                if (data.s_state == 1) {
                    state = "有车";
                } else {
                    state = "无车";
                }
                if (data.s_type == 1) {
                    type = "小车位";
                } else {
                    type = "大车位";
                }
                var la = "<p>所属车库：" + data.carStation.c_name
                    + "<br>" + "车位名称：" + data.s_name
                    + "&emsp;&emsp;车位类型：" + type + "<br>"
                    + "车位价格：" + data.carStation.c_price + "元/"
                    + data.carStation.c_pricetime + "小时<br>" + "目前状态："
                    + state + "</p>";
                $("#spaceInfo").html(la);
            } else {
                $("#spaceInfo").html("<p style='color:red'>没有查询到该车位</p>");
            }
        },
        fail: function (e) {
            alert("fail");
        },
        error: function (e) {
            alert("error");
        }
    })
}

