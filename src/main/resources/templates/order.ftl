<!DOCTYPE html>

<html>

<head>

    <title>订单查询</title>
    <meta charset="utf-8" />

    <meta content="yes" name="apple-mobile-web-app-capable"/>

    <meta content="yes" name="apple-touch-fullscreen"/>

    <meta content="telephone=no" name="format-detection"/>

    <meta content="black" name="apple-mobile-web-app-status-bar-style">

    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=no" name="viewport">

    <link rel="shortcut icon" href="/Public/Assets/img/alizi.ico" />

    <link href="/Public/Alizi/alizi-order.css?v=Alizi-V2.4.6" rel="stylesheet">

    <!--[if lt IE 9]><link href="/Public/Alizi/alizi-ie.css?v=Alizi-V2.4.6" rel="stylesheet"><![endif]-->



    <style>

        .alizi-detail-wrap{max-width:;}
        .result ul label {
            width: 8em;
        }
        .result ul span {
            padding-left: 8em;
        }

    </style>

</head>

<body>



<div class="result">
    <h1><img src="/Public/Alizi/success.png"> 订单提交成功</h1>
    <div class="innner order_div success">
        <div class="order" style="min-height: calc(100vh - 244px);">
            <ul>
                <li><label>订单编号：</label><span>${o.sysOrderNo!}</span></li>
                <#--<li><label>CFS订单编号：</label><span>${o.orderNo!}</span></li>-->
                <li><label>商品名称：</label><span>${o.goodsName!}</span></li>
                <li><label>订购数量：</label>
                <span>
                ${o.quantity!}					</span>
            </li>					<li>
                <label>订单价格：</label>
                <span>
							<b>${o.amount!}元</b>						</span>
            </li>					<li>
                <label>收货人姓名：</label>
                <span>
                ${o.name!}				</span>
            </li>					<li>
                <label>手机号码：</label>
                <span>
                ${o.mobile!}						</span>
            </li>					<li>
                <label>选择地区：</label>
                <span>
							${o.province!} ${o.city!} ${o.area!} ${o.street!}					</span>
            </li>					<li>
                <label>详细地址：</label>
                <span>
                ${o.address!}						</span>
            </li>					<li>
                <label>留言备注：</label>
                <span>${o.remark!}
													</span>
            </li>					<li>
                <label>支付方式：</label>
                <span>
							    <#if o.payment == '1'>
            货到付款
                                <#elseif o.payment == '5'>
   微信
                                <#elseif o.payment == '4'>
   支付宝
                                </#if>					</span>
            </li>
                <#if o.payment != '1'>
                <li><label>支付时间：</label><span>${o.payDate!}</span></li>
                <li><label>支付订单号：</label><span>${o.payOrderNo!}</span></li>
                <li><label>支付金额：</label><span>${o.payAmount!}元</span></li>
                </#if>
            </ul>
        </div>

        <div class="foot">
            <a href="/" class="foot_btn">返回上页</a>
            <p><br>
                Copyright © 2018 All Rights Reserved

        </div>
    </div>
</div>



</body>

</html>