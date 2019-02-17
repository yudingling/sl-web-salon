layui.use(['layer'],function(){
    let layer = layui.layer;
    
    let evalJson = function(value) {
	    if (Object.prototype.toString.call(value) === "[object String]")
	        return eval('(' + value + ')');
	    else
	        return value;
	};
    
    let setCookie = function(name,value){ 
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
    };
    
	$('.content button').click(function(){
        let data = {
            phoneNumber: $('.uid').val(), 
            password: $('.pwd').val(), 
            keep: true
        };
        if(data.phoneNumber.length == 0 || data.password.length == 0){
            Notice.error("提醒", "请输入用户名密码");
            return;
        }
        
        let loading = layer.load();
        
		$.ajax({
            data: data,
            url: './auth/signin'
        }).done(function(ret){
            layer.close(loading);
            
			ret = evalJson(ret);
			
			if(ret.errorCode == 0){
                setCookie('k', ret.data.token);
				location.href = "./main/index";
				
			}else{
				Notice.error("请求错误", ret.errorMsg);
			}
			
		}).fail(function(error){
            layer.close(loading);
            
			var errMsg = "Status: " + error.status + "<br/>StatusText: " + error.statusText + "<br/>ResponseText: " + error.responseText;
			Notice.error("请求错误!", errMsg);
		});
    });
    
    $('.content .pwd').keydown(function(event){
        if(event.which == 13){
            $('.content button').click();
        }
    });
});