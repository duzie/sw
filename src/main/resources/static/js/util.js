var baseUrl = location.origin + '/';
/**
 * 取URL参数值
 * 
 * @param name
 * @returns
 */
function getQueryString(name) {  
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    var r = window.location.search.substr(1).match(reg);
    var context = "";  
    if (r != null)  
         context = r[2];  
    reg = null;  
    r = null;  
    return context == null || context == "" || context == "undefined" ? "" : context;  
}

/**
 * 格式化金额
 * 
 * @param s
 * @param n
 * @returns {String}
 */
function fmoney(s, n){
	if(s){
		return parseFloat(s).toFixed(2);
	}else{
		return '0.00';
	}
} 


/**
 * 手机号码校验
 * 
 * @param tel
 * @returns
 */
function checkMobileno(chr) {
	var reg = /^1\d{10}$/;
	return reg.test(chr);
}

/*
template.helper("baseUrl", function(a){
    return baseUrl;
}); */
