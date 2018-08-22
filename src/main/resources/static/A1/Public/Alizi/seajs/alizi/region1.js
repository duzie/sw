define(function(require, exports, module) {
    var $ = require("jquery");

    $('#province').change(function(){
        load('city',$(this).val());
        $('#area').val(0);
        $('#street').val(0);
    });
    $('#city').change(function(){
        load('area',$(this).val());
        $('#street').val(0);
    });
    $('#area').change(function(){
        load('street',$(this).val());
    });


    function load(elId,id){
        id = id.split('|')[0];
        $.get('region/' + id,function(data){
            var s ='<option value="0|">请选择</option>';
            $.each(data,function (i,t) {
                s+='<option value="'+ t.id +'|'+ t.areaName +'">'+ t.areaName +'</option>';
            })
            $('#'+elId).html(s);
        });
    }

});