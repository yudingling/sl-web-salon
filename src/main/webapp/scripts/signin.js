layui.use([],function(){
    let evalJson = function(value) {
	    if (Object.prototype.toString.call(value) === "[object String]")
	        return eval('(' + value + ')');
	    else
	        return value;
	};
    
	$('.content button').click(function(){
		$.ajax({
            type: 'POST',
            data: {'uid': $('.uid').val(), 'pwd': $('.pwd').val()},
            url: '/signin'
        }).done(function(ret){
			ret = evalJson(ret);
			
			if(ret.errorCode == 0){
				location.href = "./index";
				
			}else{
				Notice.error("请求错误", ret.errorMsg);
			}
			
		}).fail(function(error){
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