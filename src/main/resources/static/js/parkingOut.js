function parkOut() {
    $("#myModalCarOut").modal('show');
    var mycars = new Array("京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑",
        "湘", "皖", "鲁", "新", "苏", "浙", "赣", "鄂", "桂", "甘", "晋", "蒙",
        "陕", "吉", "闽", "贵", "粤", "青", "藏", "川", "宁", "琼", "使", "领");
    for (var i = 0; i < mycars.length; i++) {
        $("#selectId").append(
            "<option value='" + mycars[i] + "'>" + mycars[i]
            + "</option>");
    }
}

//出库框关闭 拍照识别框打开
function clickCamera() {
    $("#myModalCarOut").modal("hide");
    $("#myModalOCR").modal("show");
}

//拍照识别
function goGetCarNumber() {
    var img = convertCanvasToImage();
    $.ajax({
        async: false,
        url: "/order/OCRNumberParkIn",
        data: {
            "Base64Image": img
        },
        type: "POST",
        dataType:'json',
        success: function (data) {
            if (data == null || data.number == null) {
                alert("请重新拍照识别！");
                return;
            }
            $("#myModalOCR").modal("hide");
            $("#myModalCarOut").modal("show");
            $("#car_num").val(data.number);
            var a = "option:contains(" + data.province + ")";
            $("#selectId").find(a).attr("selected", true);
        },
        fail: function (e) {
            alert("error");
        },
        error: function (e) {
            alert("error");
        }
    })
}

function flush() {
    window.location.reload();
}

$(function () {
    $("#costInfo")
        .validate(
            {
                rules: {
                    "car_num": {
                        "required": true,
                        "checkCarNumber": true
                    }
                },
                messages: {
                    "car_num": {
                        "required": "车牌号不能为空",
                        "checkCarNumber": "请输入正确的车牌号码"
                    }
                },
                submitHandler: function (form) {
                   layui.use('layer',function () {


                    $.ajax({
                            async: false,
                            url: "/order/findCarorder",
                            data: {
                                "province": $("#selectId").val(),
                                "carNumber": $("#car_num").val()
                            },
                            type: "POST",
                            dataType:"json",
                            success: function (data) {
                                if (data.carOrder != null) {
                                    var carOrder = data.carOrder;
                                    var carSpace = data.carSpace;
                                    var user = data.user;
                                    var cost;
                                    var type;
                                    if (carSpace.s_type == 1) {
                                        type = "小车位";
                                    } else {
                                        type = "大车位";
                                    }
                                    var price="";
                                    if(carOrder.is==1){
                                        price="<strong>校内车辆五折:</strong>"+(data.cost/5);
                                        cost=data.cost/5;
                                    }
                                    else{

                                        price=data.cost;
                                        cost=data.cost;
                                    }
                                    var la = "<div id='oid' style='display:none;'>"
                                        + carOrder.id
                                        + "</div>"
                                        + "<strong>车牌号：</strong><em>"
                                        + carOrder.province
                                        + "·"
                                        + carOrder.carNumber
                                        + "</em><br>"
                                        + "<strong>车库：</strong><em>"
                                        + carSpace.carStation.c_name
                                        + "</em>&emsp;&emsp;&emsp;<strong>车位：</strong><em>"
                                        + carSpace.s_name
                                        + "</em><br>"
                                        + "<strong>车位类型:</strong><em>"
                                        + type
                                        + "</em><br>"
                                        + "<strong>入库时间:</strong><em>"
                                        + data.startTime
                                        + "</em>&emsp;&emsp;&emsp;<strong>出库时间:</strong><em>"
                                        + data.endTime
                                        + "</em><br>"
                                        + "<strong>价格：</strong><em>"
                                        + carSpace.carStation.c_price
                                        + "元/"
                                        + carSpace.carStation.c_pricetime
                                        + "小时</em>&emsp;&emsp;&emsp;<strong>停车时间：</strong><em >"
                                        + data.time
                                        + "小时</em><br>"
                                        + "<strong>操作人:</strong><em>"
                                        + user.name
                                        + "</em>&emsp;&emsp;&emsp;<strong>工号：</strong><em>"
                                        + user.code
                                        + "</em><br>"
                                        + "<strong>总计:</strong><strong style='color:red' >￥"
                                        + price
                                        + "元</strong><br><br>"
                                        //用于结算参数
                                        +"<input type='hidden' id='s_id' value='"+carSpace.s_id+"' >"
                                        +"<input type='hidden' id='cost' value='"+cost+"' >"
                                        +"<input type='hidden' id='time' value='"+data.time+"' >"
                                        +"<input type='hidden' id='o_id' value='"+carOrder.id+"' >";


                                    $("#carCost").html(la);
                                    layer.msg("计算费用成功！");
                                } else {
                                    layer.msg("没有查找到该车牌号！");
                                }
                            },
                            fail: function (e) {
                                layer.msg("没有查找到该车牌号！");
                            },
                            error: function (e) {
                                layer.msg("error！");
                            }
                        }) //ajax
                   })
                }

            })

    $('#settlement').bind("click",function () {
        layui.use('layer',function () {
        var layer=layui.layer;
       if ($('#o_id').val()!=null){
           $.ajax({
               async: false,
               url: "/order/OutCar",
               data: {
                   "o_id": $('#o_id').val(),
                   "s_id": $('#s_id').val(),
                   "time": $('#time').val(),
                   "cost": $('#cost').val()
               },
               type: "POST",
               dataType: "json",
               success: function (data) {
                   if (data >= 1) {
                       layer.msg("结算成功！");
                       $("#carCost").html('');
                       $("#car_num").val('');
                       $("#myModalCarOut").modal('hide');
                       window.location.reload();
                   } else {
                       layer.msg("结算失败！");
                   }
               },
               fail: function (e) {
                   layer.msg("error");
               },
               error: function (e) {
                   layer.msg("error");
               }
           })
       }else {
           layer.msg("请输入车牌")
       }

        })
    })


})//$(function () {







