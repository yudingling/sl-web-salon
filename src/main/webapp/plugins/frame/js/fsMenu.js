/**
 * @Description: 菜单配置
 * @Copyright: 2017 wueasy.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.8.4
 * @License：MIT
 */
layui.define(['element',"fsConfig","fsCommon"], function(exports){
    let getCookie = function getCookie(name) {
      var v = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
      return v ? v[2] : null;
    };
    
    let roleId = getCookie('r');
    
    let menuData = [];
    if(roleId == 'ADMIN'){
        menuData = [
            {"menuId":"31","menuName":"品牌管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"311","menuName":"品牌列表","menuIcon":"&#xe68e;","menuHref":"../views/brandMgr/index.html","parentMenuId":"31"},
            {"menuId":"312","menuName":"分店列表","menuIcon":"&#xe68e;","menuHref":"../views/shopMgr/index.html","parentMenuId":"31"}
        ];
        
    }else if(roleId == 'SHOP'){
        menuData = [
            {"menuId":"36","menuName":"预览","menuIcon":"","menuHref":"../views/preview/index.html","parentMenuId":3},
            {"menuId":"32","menuName":"店家管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"321","menuName":"基本信息","menuIcon":"fa-info-circle","menuHref":"../views/shopDetail/index.html","parentMenuId":"32"},
            {"menuId":"322","menuName":"用户管理","menuIcon":"fa-user-o","menuHref":"../views/shopWorker/index.html","parentMenuId":"32"},
            {"menuId":"324","menuName":"假期管理","menuIcon":"fa-paper-plane-o","menuHref":"../views/shopHoliday/index.html","parentMenuId":"32"},
            {"menuId":"325","menuName":"活动管理","menuIcon":"fa-hashtag","menuHref":"../views/shopEvent/index.html","parentMenuId":"32"},
            {"menuId":"34","menuName":"理发师管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"341","menuName":"理发师列表","menuIcon":"fa-user-circle","menuHref":"../views/barber/index.html","parentMenuId":"34"},
            {"menuId":"342","menuName":"项目设定","menuIcon":"fa-envira","menuHref":"../views/barberProject/index.html","parentMenuId":"34"},
            {"menuId":"343","menuName":"时段屏蔽","menuIcon":"fa-clock-o","menuHref":"../views/barberShield/index.html","parentMenuId":"34"},
            {"menuId":"344","menuName":"作品管理","menuIcon":"fa-list-ul","menuHref":"../views/barberWork/index.html","parentMenuId":"34"},
            {"menuId":"35","menuName":"订单管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"351","menuName":"历史订单","menuIcon":"fa-list-ul","menuHref":"../views/order/index.html","parentMenuId":"35"}
        ];
        
    }else if(roleId == 'SHOP_WORKER'){
        menuData = [
            {"menuId":"36","menuName":"预览","menuIcon":"","menuHref":"../views/preview/index.html","parentMenuId":3},
            {"menuId":"32","menuName":"店家管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"321","menuName":"基本信息","menuIcon":"fa-info-circle","menuHref":"../views/shopDetail/index.html","parentMenuId":"32"},
            {"menuId":"324","menuName":"假期管理","menuIcon":"fa-paper-plane-o","menuHref":"../views/shopHoliday/index.html","parentMenuId":"32"},
            {"menuId":"325","menuName":"活动管理","menuIcon":"fa-hashtag","menuHref":"../views/shopEvent/index.html","parentMenuId":"32"},
            {"menuId":"34","menuName":"理发师管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"341","menuName":"理发师列表","menuIcon":"&#xe68e;","menuHref":"../views/barber/index.html","parentMenuId":"34"},
            {"menuId":"342","menuName":"项目设定","menuIcon":"fa-envira","menuHref":"../views/barberProject/index.html","parentMenuId":"34"},
            {"menuId":"343","menuName":"时段屏蔽","menuIcon":"fa-clock-o","menuHref":"../views/barberShield/index.html","parentMenuId":"34"},
            {"menuId":"344","menuName":"作品管理","menuIcon":"fa-list-ul","menuHref":"../views/barberWork/index.html","parentMenuId":"34"},
            {"menuId":"35","menuName":"订单管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"351","menuName":"历史订单","menuIcon":"fa-list-ul","menuHref":"../views/order/index.html","parentMenuId":"35"}
        ];
        
    }else if(roleId == 'BRAND' || roleId == 'BRAND_WORKER'){
        menuData = [
            {"menuId":"33","menuName":"产品/项目管理","menuIcon":"","menuHref":"","parentMenuId":3},
            {"menuId":"331","menuName":"产品类型","menuIcon":"fa-product-hunt","menuHref":"../views/productType/index.html","parentMenuId":"33"},
            {"menuId":"332","menuName":"产品列表","menuIcon":"fa-list-ul","menuHref":"../views/product/index.html","parentMenuId":"33"},
            {"menuId":"333","menuName":"项目管理","menuIcon":"fa-list-ul","menuHref":"../views/project/index.html","parentMenuId":"33"}
        ];
    }
    
	var menuConfig = {
			dataType : "local" , //获取数据方式，local本地获取，server 服务端获取
			loadUrl : "", //加载数据地址
			method : "post",//请求类型，默认post
			rootMenuId : "0", //根目录菜单id
			defaultSelectTopMenuId : "3", //默认选中头部菜单id
			defaultSelectLeftMenuId : "36", //默认选中左边菜单id
			menuIdField : "menuId", //菜单id
			menuNameField : "menuName", //菜单名称
			menuIconField : "menuIcon" , //菜单图标，图标必须用css
			menuHrefField : "menuHref" , //菜单链接
			parentMenuIdField : "parentMenuId" ,//父菜单id
			data : [
                {"menuId":"3","menuName":"我的控制台","menuIcon":"","menuHref":"","parentMenuId":0},
				/*{"menuId":"1","menuName":"控制台","menuIcon":"fa-cog","menuHref":"","parentMenuId":0},
				{"menuId":"2","menuName":"测试","menuIcon":"","menuHref":"","parentMenuId":0},*/
				{"menuId":"11","menuName":"案例","menuIcon":"fa-table","menuHref":"","parentMenuId":1},
				{"menuId":"12","menuName":"其他页面","menuIcon":"","menuHref":"","parentMenuId":"1"},
                
				{"menuId":"111","menuName":"首页","menuIcon":"&#xe68e;","menuHref":"../views/home/index.html","parentMenuId":"11"},
				{"menuId":"datagrid","menuName":"数据表格","menuIcon":"fa-list","menuHref":"../views/datagrid/index.html","parentMenuId":"11"},
				{"menuId":"datagrid2","menuName":"数据表格2","menuIcon":"fa-list","menuHref":"../views/datagrid2/index.html","parentMenuId":"11"},
				{"menuId":"treeDatagrid","menuName":"树+表格","menuIcon":"fa-list","menuHref":"../views/treeDatagrid/index.html","parentMenuId":"11"},
				{"menuId":"multiDatagrid","menuName":"多数据表格","menuIcon":"fa-list","menuHref":"../views/multiDatagrid/index.html","parentMenuId":"11"},
				{"menuId":"tabDatagrid","menuName":"tab数据表格","menuIcon":"fa-list","menuHref":"../views/tabDatagrid/index.html","parentMenuId":"11"},
				{"menuId":"complexDatagrid","menuName":"复杂数据表格","menuIcon":"fa-list","menuHref":"../views/complexDatagrid/index.html","parentMenuId":"11"},
				{"menuId":"linkageDatagrid","menuName":"联动数据表格","menuIcon":"fa-list","menuHref":"../views/linkageDatagrid/index.html","parentMenuId":"11"},
				{"menuId":"linkageDatagrid2","menuName":"联动数据表格(复杂)","menuIcon":"fa-list","menuHref":"../views/linkageDatagrid2/index.html","parentMenuId":"11"},
				{"menuId":"staticDatagrid","menuName":"表格数据提交","menuIcon":"fa-list","menuHref":"../views/staticDatagrid/index.html","parentMenuId":"11"},
				{"menuId":"121","menuName":"404","menuIcon":"<i class=\"layui-icon\">&#xe61c;</i>","menuHref":"../404.html","parentMenuId":"12"},
				{"menuId":"21","menuName":"基本元素","menuIcon":"","menuHref":"","parentMenuId":"2"},
				{"menuId":"22","menuName":"徽章","menuIcon":"","menuHref":"http://www.layui.com/demo/badge.html","parentMenuId":"2"},
				{"menuId":"23","menuName":"数据表格","menuIcon":"","menuHref":"http://www.layui.com/demo/table.html","parentMenuId":"2"},
				{"menuId":"211","menuName":"按钮","menuIcon":"","menuHref":"http://www.layui.com/demo/button.html","parentMenuId":"21"},
				{"menuId":"212","menuName":"表单","menuIcon":"","menuHref":"http://www.layui.com/demo/form.html","parentMenuId":"21"},
				{"menuId":"213","menuName":"选项卡","menuIcon":"","menuHref":"http://www.layui.com/demo/tab.html","parentMenuId":"21"}
		 ] //本地数据
	};
    
    menuConfig.data = menuConfig.data.concat(menuData);

	var element = layui.element,
	fsCommon = layui.fsCommon,
	fsConfig = layui.fsConfig,
	statusName = $.result(fsConfig,"global.result.statusName","errorNo"),
  msgName = $.result(fsConfig,"global.result.msgName","errorInfo"),
	successNo = $.result(fsConfig,"global.result.successNo","0"),
  dataName = $.result(fsConfig,"global.result.dataName","results.data"),
	FsMenu = function (){

	};


	FsMenu.prototype.render = function(){

		this.loadData();

		this.showMenu();
	};

	/**
	 * 加载数据
	 */
	FsMenu.prototype.loadData = function(){

		if(menuConfig.dataType == "server"){//服务端拉取数据

			var url = menuConfig.loadUrl;
			if($.isEmpty(url)){
				fsCommon.errorMsg("未配置请求地址！");
				return;
			}

			fsCommon.invoke(url,{},function(data){
  			if(data[statusName] == successNo)
  			{
  				menuConfig.data = $.result(data,dataName);
  			}
  			else
  			{
  				//提示错误消息
  				fsCommon.errorMsg(data[msgName]);
  			}
  		},false,menuConfig.method);

		}

	}


	/**
	 * 获取图标
	 */
	FsMenu.prototype.getIcon = function(menuIcon){

		if(!$.isEmpty(menuIcon)){

			if(menuIcon.indexOf("<i") == 0){
				return menuIcon;
			}else if (menuIcon.indexOf("&#") == 0){
				return '<i class="layui-icon">'+menuIcon+'</i>';
			}else if (menuIcon.indexOf("fa-") == 0){
				return '<i class="fa '+menuIcon+'"></i>';
			}else {
				return '<i class="'+menuIcon+'"></i>';
			}
		}
		return "";
	};

	/**
	 * 清空菜单
	 */
	FsMenu.prototype.cleanMenu = function(){
		$("#fsTopMenu").html("");
		$("#fsLeftMenu").html("");
	}
	/**
	 * 显示菜单
	 */
	FsMenu.prototype.showMenu = function(){
		var thisMenu = this;
		var data = menuConfig.data;
		if(!$.isEmpty(data)){
			var _index = 0;
			//显示顶部一级菜单
			var fsTopMenuElem = $("#fsTopMenu");
			var fsLeftMenu = $("#fsLeftMenu");
			$.each(data,function(i,v){
				if(menuConfig.rootMenuId == v[menuConfig.parentMenuIdField]){

					var topStr = '<li class="layui-nav-item';
					if($.isEmpty(menuConfig.defaultSelectTopMenuId) && _index === 0){//为空默认选中第一个
						topStr += ' layui-this';
					}else if(!$.isEmpty(menuConfig.defaultSelectTopMenuId) && menuConfig.defaultSelectTopMenuId == v[menuConfig.menuIdField]){//默认选中处理
						topStr += ' layui-this';
					}
					_index ++ ;
					topStr += '" dataPid="'+v[menuConfig.menuIdField]+'"><a href="javascript:;">'+thisMenu.getIcon(v[menuConfig.menuIconField])+' <cite>'+v[menuConfig.menuNameField]+'</cite></a></li>';
					fsTopMenuElem.append(topStr);

					//显示二级菜单，循环判断是否有子栏目
					$.each(data,function(i2,v2){
						if(v[menuConfig.menuIdField] == v2[menuConfig.parentMenuIdField]){

							var menuRow = '<li class="layui-nav-item';
							if(!$.isEmpty(menuConfig.defaultSelectLeftMenuId) && menuConfig.defaultSelectLeftMenuId == v2[menuConfig.menuIdField]){//默认选中处理
								menuRow += ' layui-this';
							}
							//显示三级菜单，循环判断是否有子栏目
							var menuRow3 = "";
							$.each(data,function(i3,v3){
								if(v2[menuConfig.menuIdField] == v3[menuConfig.parentMenuIdField]){
									if($.isEmpty(menuRow3)){
										menuRow3 = '<dl class="layui-nav-child">';
									}
									menuRow3 += '<dd';
									if(!$.isEmpty(menuConfig.defaultSelectLeftMenuId) && menuConfig.defaultSelectLeftMenuId == v3[menuConfig.menuIdField]){//默认选中处理
										menuRow3 += ' class="layui-this"';
										menuRow += ' layui-nav-itemed';//默认展开二级菜单
									}

									menuRow3 += ' lay-id="'+v3[menuConfig.menuIdField]+'"><a href="javascript:;" menuId="'+v3[menuConfig.menuIdField]+'" dataUrl="'+v3[menuConfig.menuHrefField]+'">'+thisMenu.getIcon(v3[menuConfig.menuIconField])+' <cite>'+v3[menuConfig.menuNameField]+'</cite></a></dd>';

								}

							});

							menuRow += '" lay-id="'+v2[menuConfig.menuIdField]+'" dataPid="'+v2[menuConfig.parentMenuIdField]+'" style="display: none;"><a href="javascript:;" menuId="'+v2[menuConfig.menuIdField]+'" dataUrl="'+v2[menuConfig.menuHrefField]+'">'+thisMenu.getIcon(v2[menuConfig.menuIconField])+' <cite>'+v2[menuConfig.menuNameField]+'</cite></a>';


							if(!$.isEmpty(menuRow3)){
								menuRow3 += '</dl>';

								menuRow += menuRow3;
							}

							menuRow += '</li>';

							fsLeftMenu.append(menuRow);
						}

					});

				}
			});
		}
		element.render("nav");
	};

	var fsMenu = new FsMenu();
	exports("fsMenu",fsMenu);
});
