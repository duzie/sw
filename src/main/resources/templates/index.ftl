<!-- 联系开发 1623301@qq.com -->
<!DOCTYPE html>
<html>
<head>
  <title>${wp.title!}</title>
  <meta charset="utf-8"/>
  <meta content="yes" name="apple-mobile-web-app-capable"/>
  <meta content="yes" name="apple-touch-fullscreen"/>
  <meta content="telephone=no" name="format-detection"/>
  <meta content="black" name="apple-mobile-web-app-status-bar-style">
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=no"
        name="viewport">
  <meta name="description" content="${wp.description!}">
  <meta name="keywords" content="${wp.keywords!}">
  <link href="/A1/Public/Alizi/alizi-order.css?v=V2.8.2-2" rel="stylesheet">
  <link href="/A1/Home/Tpl/Alizi/skin/assets/alizi.css?v=V2.8.2-2" rel="stylesheet">
  <!--[if lt IE 9]>
  <link href="/A1/Public/Alizi/alizi-ie.css?v=V2.8.2-2" rel="stylesheet"><![endif]-->
  <script type="text/javascript" src="/A1/Public/Alizi/seajs/seajs/sea.js"></script>
  <script type="text/javascript">
    var aliziHost = "", aliziRoot = "/A1/", aliziVersion = "V2.8.3", lang = "zh-cn";
    seajs.config({
      base: '/A1/Public/Alizi/seajs/',
      alias: {'jquery': 'jquery/jquery', 'alizi': 'alizi/alizi', 'lang': 'alizi/lang-' + lang},
      map: [['.css', '.css?v=' + aliziVersion], ['.js', '.js?v=' + aliziVersion]]
    });
  </script>

  <style>
    .alizi-region {
      width: 24.3%;
    }
    .alizi-payment .alizi-payment-2.active, .alizi-payment .alizi-payment-2:hover{
      background-color: #24a8ec;
      border-color: #24a8ec;
    }
    .alizi-payment .alizi-payment-3.active, .alizi-payment .alizi-payment-3:hover{
      background-color: #44b549;
      border-color: #44b549;
    }
    .buy {
      width: 100%;
      height: auto;
      background: #44361C;
      padding: 5px 0 15px;
      text-shadow: none;
      text-align: center;
      overflow: hidden;
      -webkit-animation: bounceInkeep .3s .2s linear both;
      -moz-animation: bounceInkeep .3s .2s linear both;
      -o-animation: bounceInkeep .3s .2s linear both;
      font-size: 16px;
    }
    .row1, .row2 {
      width: 100%;
      height: auto;
      margin: 0 auto;
      padding: 0;
      clear: both;
    }
    .row1 strong {
      display: block;
      width: 35%;
      float: left;
      font-size: 180%;
      color: #ff0;
      text-shadow: 1px 1px 0px #000;
      line-height: 60px;
    }

    .row2 {
      border-top: 1px dotted #524524;
      border-bottom: 1px dotted #524524;
      padding: 10px 0;
      line-height: 30px;
      margin-bottom: 20px;
      overflow: hidden;
    }
    .row2 strong {
      display: block;
      width: 35%;
      line-height: 30px;
      height: 30px;
      float: left;
      font-size: 16px;
      color: #9A7C48;
    }

    @media (min-width: 660px)
      .alizi-main {
        float: left;
        width: 65%;
      }
  </style>
</head>
<body>


<!--[if lt IE 9]>
<style>.header h1, .footer {
  color: #666;
}</style>
<![endif]-->

<div class="wrapper alizi-detail-wrap">


  <div class="alizi-page alizi-detail-content" >
  ${wp.content!}
    <div class="buy">
      <div class="row1">
        <strong>￥${timer['timer_price']}</strong>
        <ol>
          <li><p>扫描价</p>￥${timer['timer_price1']}</li>
          <li><p>折扣</p>${timer['timer_dis']}折</li>
          <li><p>节省</p>￥${timer['timer_dis_price']}</li>
        </ol>
      </div>
      <div class="row2">
        <strong>${timer['timer_buy_count']}已购买</strong>
        <div class="djs">
          <div class="timer">
            <div id="alizi-timer" class="alizi-timer">
              <span class="aliziDay"><b>0</b>天</span><span class="aliziDay"><b>02</b>时</span><span class="aliziDay"><b>18</b>分</span><span class="aliziDay"><b>40</b>秒</span>

            </div>
            <script type="text/javascript">
              seajs.use(['alizi'],function(alizi) {
                $('.alizi-detail-content img:eq(0)').after($('.buy'))
                alizi.timer('#alizi-timer', ${timer['timer_seconds']});
              })				</script>
          </div>
        </div>
      </div>
      <a class="btn-buy" href="#aliziOrder">立即购买</a>
    </div>



    </span></p><p style="border: 0px;"><span style="text-align: center;"></span></p>
    <div class="alizi-editor"
         style="background: #EEE9E6;color: #6C594D;font-size: 18px;line-height: 30px;margin: 0;width: 100%;clear: both;text-align: left;font-weight: normal;">
      <div style="color:#6C594D">&nbsp;▼&nbsp;在线订购</div>
    </div>
    <p style="border: 0px;">
      <link href='/A1/Public/Alizi/theme/thin/alizi.css?v=V2.8.2-2' rel='stylesheet'>
      <style>.alizi-border, .alizi-side.alizi-full-row {
        border-color: #FF6600;
      }

      .alizi-detail-wrap {
        max-width: 750px;
      }

      body, .alizi-order-wrap {
        background-color: #856D35;
      }

      .alizi-detail-content h2 {
        border-top-color: #856D35;
      }

      .alizi-border, .alizi-side.alizi-full-row {
        border-color: #666666;
      }

      .alizi-detail-header dt {
        color: #333333;
      }

      .alizi-order {
        color: #333333;
        background-color: #FFFFFF;
      }

      .alizi-title {
        background-color: #666666;
      }

      .alizi-btn, .alizi-btn:hover, .alizi-btn:active, .alizi-badge, .alizi-params.active, .alizi-group-box input:checked + label:after {
        background-color: #EE3300;
        border-color: #EE3300;
      }

      .alizi-foot-nav {
        background-color: #EE3300;
      }

      .alizi-group.alizi-params.alizi-checkbox.active:hover {
        background-color: #EE3300 !important;
        border-color: #EE3300 !important;
        color: #fff !important;
      }</style>
    <div class='alizi-order alizi-border alizi-lang-zh-cn clearfix' id='aliziOrder'>
      <div class='alizi-main alizi-border'>
        <div class='alizi-title alizi-title-order alizi-border ellipsis'><i class='icon-cart'></i>
        </div>
        <div class='alizi-form-content alizi-border'>
          <form action='/order' method='post' id='aliziForm'>
            <input type='hidden' name='vistorId' value='${serialNO}'>
            <input type='hidden' name='user_pid' value=''>
            <input type='hidden' name='sn' value=''>
            <input type='hidden' name='item_id' value=''>
            <input type='hidden' name='goodsName' id="goodsName" value=''>
            <input type='hidden' name='item_price'id='item_price' value=''>
            <input type='hidden' name='url' value=''>
            <input type='hidden' name='redirect' value=''>
            <input type='hidden' name='referer' value=''>
            <input type='hidden' name='order_page' value=''>
            <input type='hidden' name='channel_id' value=''>
            <input type='hidden' name='qrcode_pay' value=''>
            <input type='hidden' name='math' value='*1'>
            <input type='hidden' name='page' value=''>
            <input type='hidden' name='buy_num' value=''>
            <input type='hidden' name='buy_num_decrease' value=''>
            <div class='alizi-rows clearfix rows-id-params rows-id-params-radio'><label class='rows-head'>商品名称<span
              class='alizi-request '>*</span></label>
              <div class='rows-params'>

                                <#list goodsList as g>
                                  <label alizi-value='${(g.price?c)!}'
                                         alizi-rvalue='${(g.referPrice?c)!}'
                                         alizi-dvalue='${(g.discount?c)!}'
                                         alizi-target='#item_price' alizi-fx='alizi.quantity' alizi-fx-params='0' class=' ellipsis alizi-params ${(g_index == 0)?string('active','')} '>
                                    <input type='radio' name='sku' value='${g.sku}' ${(g_index == 0)?string('checked','')} >
                                    ${g.goodsName}</label>
                                </#list>
              </div>
            </div>
            <div class='alizi-box alizi-box-params'>
              <div class='alizi-rows clearfix rows-id-price'><label class='rows-head'>商品价格<span
                class='alizi-request alizi-request-none'>*</span></label>
                <div class='rows-params'>
                  ￥<strong class='alizi-total-rprice'></strong></div>
              </div>

              <div class='alizi-rows clearfix rows-id-price'><label class='rows-head'>优惠价格<span
                class='alizi-request alizi-request-none'>*</span></label>
                <div class='rows-params'>
                  ￥<strong class='alizi-total-dprice'></strong></div>
              </div>

              <div class='alizi-rows clearfix rows-id-price'><label class='rows-head'>支付价格<span
                class='alizi-request alizi-request-none'>*</span></label>
                <div class='rows-params'><span class='alizi-shipping' style='display:none;'><strong
                  class='alizi-order-price'>0.00</strong>+<strong
                  class='alizi-shipping-price'>0.00</strong>(运费)=</span>￥<strong
                  class='alizi-total-price'></strong></div>
              </div>

              <div>
                <div></div>
              </div>
              <div class='alizi-rows clearfix rows-id-quantity'><label class='rows-head'>订购数量<span
                class='alizi-request '>*</span></label>
                <div class='rows-params'>
                  <div class='alizi-quantity-group'><a class='quantity-dec' href='javascript:;'
                                                       onclick='alizi.quantity(-1)'>-</a><input
                    type='tel'
                    class='alizi-quantity'
                    size='4'
                    value='1'
                    name='quantity'
                    onkeyup='alizi.quantity(0)'><a
                    class='quantity-inc' href='javascript:;' onclick='alizi.quantity(1)'>+</a>
                  </div>
                </div>
              </div>
              <div class='alizi-rows clearfix rows-id-name'><label class='rows-head'>您的姓名<span
                class='alizi-request '>*</span></label>
                <div class='rows-params'><input type='text' name='name' placeholder=''
                                                autocomplete='off'
                                                alizi-request='1' class='alizi-input-text' value=''>
                </div>
              </div>
              <div class='alizi-rows clearfix rows-id-mobile'><label class='rows-head'>手机号码<span
                class='alizi-request '>*</span></label>
                <div class='rows-params'><input type='tel' name='mobile' placeholder=''
                                                autocomplete='off'
                                                class='alizi-input-text' alizi-request='1'
                                                value=''></div>
              </div>
              <div class='alizi-rows clearfix rows-id-region'><label class='rows-head'>选择地区<span
                class='alizi-request '>*</span></label>
                <div class='rows-params'>
                  <select name='province' id='province' class='alizi-region alizi-region-province' alizi-request='1'>
                    <option value="0|-">请选择</option>
                                       <#list province as p>
                                           <option value="${p.id?c}|${p.areaName}">${p.areaName}</option>
                                       </#list>
                  </select>
                  <select name='city' id='city' class='alizi-region alizi-region-city' alizi-request='1'><option value="0|-">请选择</option></select>
                  <select name='area' id='area' class='alizi-region alizi-region-area' alizi-request='1'><option value="0|-">请选择</option></select>
                  <select name='street' id='street' class='alizi-region alizi-region-street' alizi-request='1'><option value="0|-">请选择</option></select>
                  <script type='text/javascript'>seajs.use(['alizi/region1'], function (region1) {
                  });</script>
                </div>
              </div>
              <div class='alizi-rows clearfix rows-id-address'><label class='rows-head'>详细地址<span
                class='alizi-request '>*</span></label>
                <div class='rows-params'><input type='text' name='address' placeholder=''
                                                autocomplete='off'
                                                alizi-request='1' class='alizi-input-text'
                                                value=''>
                </div>
              </div>
              <div class='alizi-rows clearfix rows-id-remark'><label class='rows-head'>留言备注<span
                class='alizi-request alizi-request-none'>*</span></label>
                <div class='rows-params'><textarea name='remark' placeholder='' class='alizi-input-text'
                                                   alizi-request='' rows='2'></textarea></div>
              </div>
              <div class='alizi-rows clearfix rows-id-payment'><label class='rows-head'>支付方式<span
                class='alizi-request '>*</span></label>
                <div class='rows-params'>
                  <div class='alizi-payment clearfix'>
                    <label alizi-value='4' alizi-target=':payment'
                           alizi-fx='alizi.payment'
                           alizi-fx-params='4'
                           class='ellipsis alizi-params alizi-payment-2 active'>
                      <input type='radio' name='payment' value='4' checked>支付宝
                    </label>
                    <label alizi-value='5' alizi-target=':payment'
                           alizi-fx='alizi.payment'
                           alizi-fx-params='5'
                           class='ellipsis alizi-params alizi-payment-3 '>
                      <input type='radio' name='payment' value='5' >微信
                    </label>
                    <label
                      alizi-value='1'
                      alizi-target=':payment'
                      alizi-fx='alizi.payment'
                      alizi-fx-params='1'
                      class='ellipsis alizi-params alizi-payment-1 '>
                      <input  type='radio' name='payment' value='1'>货到付款
                    </label>
                  </div>
                  <div id='alizi-payment-info' class='alizi-alert clearfix'>
                    <div class='payment-info'><b>在线支付可享95折优惠</b><br/>
                    </div>
                  </div>
                </div>
              </div>
              <input type='hidden' name='item_index' value='0'>
              <div class='alizi-rows alizi-id-btn clearfix'><input type='submit' id='alizi-submit'
                                                                   class='alizi-btn alizi-submit'
                                                                   value='确认提交'/></div>
          </form>
        </div>
      </div>

    </div>
    <div class='alizi-side alizi-border '>
      <div class='alizi-title alizi-border ellipsis'>
        <i class='icon-shipping'></i>最新订购
      </div>
      <div class='alizi-delivery'>
        <div class='alizi-scroll '>
          <ul>
<#list newBuys as g>
  <li  ${(g_index % 2 == 1 )?string('class="even"','')}>
    <p><span class='alizi-badge'>${g.loc}</span>${g.name}</p>
    <p><span class='alizi-date'>${g.time}</span>${g.descr}</p>
  </li>

</#list>

          </ul>
        </div>
      </div>
    </div>
    <script type='text/javascript'>
      seajs.use(['jquery','alizi','alizi/scroll'], function(jQuery,alizi,scroll) {
        alizi.resize('2');
        jQuery(window).resize(function(){ alizi.resize('2');});
        var scrollHeight = jQuery('.alizi-scroll li').innerHeight();
        jQuery('.alizi-scroll').aliziScroll({speed:40,rowHeight:scrollHeight});});
    </script>

  </div>


</div>
<script type='text/javascript'>seajs.config({
  vars: {
    'payment': {
      "4": {
        "name": "\u652f\u4ed8\u5b9d",
        "info": "<b>在线支付可享95折优惠</b>",
        "type": {"1": "\u652f\u4ed8\u5b9d", "2": "QQ\u94b1\u5305", "3": "\u5fae\u4fe1\u652f\u4ed8"},
        "math": "*1"
      },
      "1": {
        "name": "\u8d27\u5230\u4ed8\u6b3e",
        "info": "\u8d27\u5230\u4ed8\u6b3e",
        "math": "+0"
      },
      "5": {"name": "\u626b\u7801\u652f\u4ed8", "info": "<b>在线支付可享95折优惠</b>", "math": "+0"}
    }, 'shipping': {"id": 0}
  }
});</script>
<img src="img/icp.jpg" style="margin-bottom: 3rem;">
</div>
</div>
</div>


<div class='alizi-remark'>

</div>
<div class="alizi-foot-nav alizi-detail-wrap">
  <a class="alizi-up operation" o="点击TOP" href="#" >TOP</a>
  <ul>
    <li class="foot-nav-1" ><a href="#aliziOrder"><strong class="icon edit operation" o="立即下单" >立即下单</strong></a></li>
    <li class="foot-nav-2" ><a href="sms:18682364329"><strong class="icon query showno operation" o="信息咨询" no="18682364329">信息咨询</strong></a></li>
    <li class="foot-nav-3" ><a href="tel:4008877095"><strong class="icon weixin showno operation" o="电话咨询" no="4008877095">电话咨询</strong></a></li>
  </ul>
</div>


<script type="text/javascript">
  seajs.use(['alizi', 'jquery/form', 'lang'], function (alizi) {
    window.alizi = alizi;
    alizi.quantity(0);
    var btnSubmit = $('.alizi-submit');
    $('#aliziForm').ajaxForm({
      cache: true,
      timeout: 50000,
      dataType: 'json',
      error: function () {
        layer.closeAll();
        alert(lang.ajaxError);
        btnSubmit.attr('disabled', false).val(lang.submit);
      },
      beforeSubmit: function () {
        if (checkForm('#aliziForm') == false) return false;
        layer.closeAll();
        layer.load();
        btnSubmit.attr('disabled', true).val(lang.loading);
        utq('track', 'Purchase', '124580');
        utq('track', 'FormSubmit', '128581');
      },
      success: function (data) {
        layer.closeAll();
        if (data.success) {
          if(data.aliform)
            $('body').html(data.aliform);
          else if(data.wxpay){
            if(isWeiXin()){
              location.href = 'wx/' + data.id;
            }else{
              $('#wxform').attr('action','order/' + data.id);
              $('#wxform').submit();
            }
          }else if (data.id)
            location.href = 'order/' + data.id;
        } else {
          btnSubmit.attr('disabled', false).val(lang.submit);
          layer.msg('下单失败');
        }
      }
    });
    $('.operation').click(function(){
      $.post('operation',{operation:$(this).attr('o'),vistorId:'${serialNO}'})
    });
    window.scrollBottom = false;
    $(window).scroll(function() {
      if (!window.scrollBottom && $(document).scrollTop() >= $(document).height() - $(window).height()) {
        window.scrollBottom = true;
        $.post('scrollBottom',{vistorId:'${serialNO}'})
      }
    });

    setInterval(function(){
      $.get('close/${serialNO}');
    },30000)

    function detectmob() {
      if( navigator.userAgent.match(/Android/i)
        || navigator.userAgent.match(/webOS/i)
        || navigator.userAgent.match(/iPhone/i)
        || navigator.userAgent.match(/iPad/i)
        || navigator.userAgent.match(/iPod/i)
        || navigator.userAgent.match(/BlackBerry/i)
        || navigator.userAgent.match(/Windows Phone/i)
      ){
        return true;
      }
      else {
        return false;
      }
    }
    if(!detectmob()){
      $('.showno').click(function(){
        var t = $(this);
        layer.alert(t.attr('o') + ":" + t.attr('no') );
      });
    }

    function isWeiXin(){
      var ua = window.navigator.userAgent.toLowerCase();
      if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
      }else{
        return false;
      }
    }
    $('input[name="sku"]:eq(2)').click();
  });



</script>

</div>

<form id="wxform" method="post">

</form>
</body>
</html>
<script>

  (function(w,d,t,s,q,m,n){if(w.utq)return;q=w.utq=function(){q.process?q.process(arguments):q.queue.push(arguments);};q.queue=[];m=d.getElementsByTagName(t)[0];n=d.createElement(t);n.src=s;n.async=true;m.parentNode.insertBefore(n,m);})(window,document,'script','https://image.uc.cn/s/uae/g/0s/ad/utracking.js');utq('set', 'convertMode', true);utq('set', 'trackurl', 'huichuan.sm.cn/lp');
</script>
