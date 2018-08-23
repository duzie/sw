/*
 * 标题：阿狸子订单系统
 * 作者：justo2008（旺旺号）
 * 官方网址：www.alizi.net
 * 淘宝店铺：https://hiweb.taobao.com/
 * 警示信息：您可以复制使用本站静态文件（html/css/js/images），但请保留原创作者（旺旺：justo2008）信息，谢谢。
 */
define(function(require, exports, module) {
    require('../layer/skin/layer.css');
    var $ = require("jquery"),layer = require("layer/layer"),aliziScroll = require("alizi/scroll");

    checkForm = function(){
        var handle = $(arguments[0]);
        var name = handle.find('input[name=name]');
        var mobile = handle.find('input[name=mobile]');
        var region = handle.find('.region');
        var address = handle.find('input[name=address]');
        var qq = handle.find('input[name=qq]');
        var mail = handle.find('input[name=mail]');
        var verify = handle.find('input[name=verify]');
        var rule = /^1[3-8][0-9]\d{8}$/,ruleTw=/^(9|09)\d{8}$/,ruleJp=/^([789]|0[789])\d{9}$/;
        var nameReg=/^[\u0391-\uFFE5]+$/;

        try{
            layer = typeof(top.layer) == "undefined"?layer:top.layer
        }catch (ex){
            layer = layer;
        }


        if(name.length!=0 && $.trim(name.val()).length<2){layer.msg(lang.emptyName);return false;}
        //if(!nameReg.test(name.val())){ layer.msg('请填写有效的姓名');return false;}
        //if(mobile.length!=0 && (rule.test(mobile.val()) == false && ruleTw.test(mobile.val()) == false) && ruleJp.test(mobile.val()) == false){layer.msg(lang.invalidMobile);return false;}
        if(region.length!=0 && !region.eq(0).val()){layer.msg(lang.SelectRegion);return false;}
        //if(address.length!=0 && !address.val()){layer.msg(lang.emptyAddress);return false;}
        //if(mail.length!=0 && !mail.val()){layer.msg(lang.emptyEmail);return false;}
        if(verify.length!=0 && verify.val().length!= 4){layer.msg(lang.invalidVerify);return false;}
    };

    exports.quantity = function(){
        try{  seajs.data.vars.payment}catch(err){ return;}
        //if(!seajs.data.vars.payment) return;

        if($('.rows-id-params').hasClass('rows-id-params-checkbox')){
            var allPrice = 0;
            $(".rows-id-params-checkbox label.active").each(function(){
                allPrice += parseFloat($(this).attr('alizi-value'));
            })
            $("input[name=item_price]").val(allPrice);
        }

        $("input[name=item_price]").val($('.alizi-params.active').attr('alizi-value'));


        var paymentData = seajs.data.vars.payment;//require("payment"),
        amount = parseInt(arguments[0]),
            price = parseFloat($("input[name=item_price]").val()),
            payment = $("input[name=payment]:checked").val(),
            quantiryInput = $("input[name=quantity]"),
            qrcodepay = $("input[name=qrcode_pay]").val(),
            num   = parseInt(quantiryInput.val()),
            math = $("input[name=math]").val(),
            buy_num = $("input[name=buy_num]").val().split(',');
        buy_num_decrease = $("input[name=buy_num_decrease]").val().split(',');

        num = (isNaN(num) || num<0)?1:num;
        var totalNum = (amount+num)<1?1:(amount+num);
        totalNum = payment==5 && qrcodepay=='1'?1:totalNum;
        quantiryInput.val(totalNum);

        var data = paymentData[payment];
        var count = math.substr(0,1),fee=parseFloat(math.substr(1));
        var totalPrice = count=='+'?(price*totalNum+fee):(price*totalNum*fee);//订单总价
        var shippingCost = shipping(totalNum,totalPrice);//运费
        var subTotal=totalPrice+shippingCost;

        var decrease = 0;
        var len = buy_num.length;
        if(len>0 && totalNum>=buy_num[0]){
            var n=0;
            for(var i = 0; i < len; i++){
                var j = len-1;
                if(parseInt(buy_num[j])<=totalNum){
                    n = j;break;
                }else if(parseInt(buy_num[i])>totalNum){
                    n = i-1;break;
                }
            }
            decrease = buy_num_decrease[n];
        }
        subTotal -= decrease;

        var language = $('input[name=lang]').val();
        var aliziShipping =  $(".alizi-shipping");
        aliziShipping.find('.alizi-shipping-price').html(shippingCost);
        aliziShipping.find('.alizi-order-price').html(totalPrice);
        subTotal = language=='zh-cn'?subTotal.toFixed(2):(subTotal.toFixed(2)*100)/100;

        $(".alizi-total-price").html(subTotal);
        $(".alizi-total-number").html(totalNum);
    }

    exports.timer = function(elem,intDiff){

        window.setInterval(function(){
            var day=0,hour=0,minute=0,second=0,times=intDiff;
            if(intDiff > 0){
                day = Math.floor(intDiff / (60 * 60 * 24));
                hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
            }

            hour = hour<10?'0'+hour:hour;
            minute = minute<10?'0'+minute:minute;
            second = second<10?'0'+second:second;

            if(day==0 && hour=='00' && minute=='00' && second=='00'){
                //$('.alizi-submit').attr('disabled','disabled').val(text);
                $(elem).html(lang.actionEnd);
            }else{
                if(day==0){
                    $(elem).find('.aliziDay').remove();
                }else{
                    $(elem).find('.aliziDay strong').html(day);
                }
                $(elem).find('.aliziHour strong').html(hour);
                $(elem).find('.aliziMinute strong').html(minute);
                $(elem).find('.aliziSecond strong').html(second);
            }
            intDiff--;
        }, 1000);
    }
    exports.getCode = function(){
        var second=60,
            verify = $("input[name=verify]").val(),
            item_id = $("input[name=item_id]").val(),
            page = $("input[name=page]").val(),
            mobile = $("input[name=mobile]").val(),
            btn = $('#alizi-code');

        btn.attr('disabled','disabled');
        var interval = setInterval(function(){
            if(second<=0){
                clearInterval(interval);
                btn.attr('disabled',false).find('i').text('');
            }else{
                var text= "("+second+")";
                btn.find('i').text(text);
                second--;
            }
        }, 1000);
        $.ajax({
            url:aliziRoot+'index.php?m=Order&a=getCode',
            type:'post',
            data:{item_id:item_id,mobile:mobile,verify:verify,page:page},
            dataType:'json',
            success:function(data){
                if(data.status=='0'){
                    layer.msg(data.info);
                    clearInterval(interval);
                    btn.attr('disabled',false).find('i').text('');
                }
            }
        });
    }
    exports.payment = function(){
        var paymentData = seajs.data.vars.payment;//require("payment"),
        var info = paymentData[arguments[0]]['info'],payment=$('#alizi-payment-info');
        $("input[name=math]").val(paymentData[arguments[0]]['math']);
        if(info){ payment.show().find('.payment-info').html(info);}else{payment.hide();}
        this.quantity(0);
    }
    exports.resize = function(){
        var show = arguments[0];
        var width = window.document.body.offsetWidth,className='alizi-full-row',main=$('.alizi-main'),side=$('.alizi-side'),scroll=$('.alizi-scroll');
        if(show=='2' && width>600){
            main.removeClass(className);side.removeClass(className);scroll.removeClass(className);
            $(".alizi-scroll").height(main.height()-100);
        }else{
            main.addClass(className);side.addClass(className);scroll.addClass(className);
        }
    }
    exports.scroll = function(){
        var id = arguments[0],time=arguments[1]||2500,ul=$("#"+id+" ul");
        setInterval(function(){
            var last=ul.children('li:last'),height=last.height();
            ul.prepend(last.css({height:0}).animate({height:height},'slow'));
        },time);
    }
    function shipping(quantity,totalPrice){
        var shipping = seajs.data.vars.shipping;//require("shipping");
        if(shipping.id==0) return 0;
        if(shipping['is_free_num']==1 && quantity>=shipping['free_num']) return 0;
        if(shipping['is_free_cost']==1 && totalPrice>=shipping['free_cost']) return 0;
        if(quantity <= shipping['less_num']){
            return parseFloat(shipping['less_num_cost']);
        }else{
            var step = Math.ceil((quantity-parseInt(shipping['less_num']))/parseInt(shipping['step_num']));
            return parseFloat(shipping['less_num_cost'])+step*parseFloat(shipping['step_num_cost']);
        }
    }

    $('#goodsName').val($('input[name="sku"]:checked').parent().text().trim());

    $('.alizi-params').bind('click',function(){
        var _this = $(this),className='active';
        var target = _this.attr('alizi-target'),value = _this.attr('alizi-value'),fx = _this.attr('alizi-fx'),params = _this.attr('alizi-fx-params');

        if(_this.hasClass('alizi-checkbox')){
            var isCheck = _this.find('input:checked').length;
            if(isCheck==1){
                _this.addClass(className);
            }else{
                _this.removeClass(className);
            }
        }else{
            _this.addClass(className).siblings().removeClass(className);
        }
        $('#goodsName').val($('input[name="sku"]:checked').parent().text().trim());
        if(target){
            if(target.indexOf(':')===0){
                _this.find('input').get(0).checked=true;
            }else{
                $(target).val(value);
            }
            if(fx)eval(fx+'("'+params+'")');
        }
    })
    $('.rows-id-params-radio .alizi-radio').bind('click',function(){
        var i = $(this).index()+1;
        $('input[name=item_index]').val(i);


        $('.extends').addClass('extends-hidden');
        $('.extends').find('input').attr('disabled','disabled');
        $('.extends').find('select').attr('disabled','disabled');

        $('.extends-'+i).removeClass('extends-hidden');
        $('.extends-'+i).find('input').attr('disabled',false);
        $('.extends-'+i).find('select').attr('disabled',false);
    })

    $('.extends-level-1 .alizi-radio').bind('click',function(){
        var i = $(this).index()+1;

        $('.subextends').addClass('subextends-hidden');
        $('.subextends').find('input').attr('disabled','disabled');
        $('.subextends').find('select').attr('disabled','disabled');

        $('.subextends-'+i).removeClass('subextends-hidden');
        $('.subextends-'+i).find('input').attr('disabled',false);
        $('.subextends-'+i).find('select').attr('disabled',false);

        console.log(i);
    })
    $('.alizi-params-change').change(function(){
        var _this = $(this),fx = _this.attr('alizi-fx'),params = _this.attr('alizi-fx-params'),price = _this.find("option:selected").attr('value-price');
        $("input[name=item_price]").val(price);
        if(fx)eval(fx+'("'+params+'")');
    })


    var scrollHeight = $('.alizi-scroll li').innerHeight();
    $('.alizi-scroll').aliziScroll({speed:80,rowHeight:scrollHeight});

});