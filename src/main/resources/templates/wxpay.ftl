<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>微信支付</title>
    <script src="/js/jquery-2.1.4.js"></script>
    <script src="/js/jquery.qrcode.min.js"></script>
</head>
<body style="text-align: center;padding: 1rem;">
<div id="scan" style="display: none">
    <div id="output"></div>
    请扫码支付，<a href="">支付成功</>

</div>

</body>
</html>
<script>

    if('${wx.mwebUrl!}' != '')
        location.href = '${wx.mwebUrl!}';
    else if('${wx.codeUrl!}' != ''){
        $('#scan').show();
        $('#output').qrcode("${wx.codeUrl!}");
        setInterval(function(){
            $.get('/order/ispay/${o.id!}',function(d){
                if(d == 1)
                    location.href = '/order/${o.id!}';
            })
        },2000);
    }else{

    }

    function onBridgeReady(){
        WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":"${wx.appId!}",     //公众号名称，由商户传入
                    "timeStamp":"${wx.timeStamp!}",         //时间戳，自1970年以来的秒数
                    "nonceStr":"${wx.nonceStr!}", //随机串
                    "package":"${wx.packageValue!}",
                    "signType":"MD5",         //微信签名方式：
                    "paySign":"${wx.paySign!}" //微信签名
                },
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok" ){
                        location.href = '/order/${o.id};'
                    }
                });
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }

</script>