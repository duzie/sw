<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单查询</title>
    <style>

        table

        {

            border-collapse:collapse;

        }

        td

        {

           padding: .4rem;

            border:1px solid black

        }

    </style>


</head>
<body style="margin-top: 1rem;">
<table width="auto" cellpadding="0" cellspacing="0" border="1" align="center">

    <tr><td colspan="2" style="text-align: center">订单查询</td></tr>
    <#--<tr><td>序号</td><td>${o.id!}</td></tr>-->
    <tr><td>订单号</td><td>${o.orderNo!}</td></tr>
    <#--<tr><td>访客ID</td><td>${o.vistorId!}</td></tr>-->
    <tr><td>下单时间</td><td>${o.createDate!}</td></tr>
    <tr><td>金额</td><td>${o.amount!}元</td></tr>
    <#--<tr><td>SKU</td><td>${o.sku!}</td></tr>-->
    <tr><td>商品名</td><td>${o.goodsName!}</td></tr>
    <tr><td>数量</td><td>${o.quantity!}</td></tr>
    <tr><td>收货人</td><td>${o.name!}</td></tr>
    <tr><td>电话</td><td>${o.mobile!}</td></tr>
    <tr><td>地址</td><td>${o.province!}${o.city!}${o.area!}${o.street!}${o.address!}</td></tr>
    <tr><td>备注</td><td>${o.remark!}</td></tr>
    <tr><td>支付方式</td><td>
        <#if o.payment == '1'>
            货到付款
            <#elseif o.payment == '5'>
   微信
            <#elseif o.payment == '4'>
   支付宝
            </#if>

    </td></tr>
         <#if o.payment != '1'>
    <tr><td>支付时间</td><td>${o.payDate!}</td></tr>
    <tr><td>支付订单号</td><td>${o.payOrderNo!}</td></tr>
    <tr><td>支付金额</td><td>${o.payAmount!}元</td></tr>
    <tr><td>支付状态</td><td><#if o.state == 0>
                    待支付
                <#else>
                已支付
                </#if>
    </td></tr>
         </#if>
</table>
</body>
</html>