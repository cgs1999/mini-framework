$.namespace("Mo.Frame");
Mo.Frame = {
	default_detail_paddingTop : 56, // 默认详细表单顶部偏移量
	default_detail_paddingLeft : 244, // 默认详细表单左边偏移量
    detail_paddingTop: this.default_detail_paddingTop,
    detail_paddingLeft: this.default_detail_paddingLeft,
    main_min_width:1256,
    main_padding_left:128,
    main_min_height:400,
    main_padding_bottom:68,
    main_top_height:98,

    fixLineHeight:2,

    init:function(){
        this.setSize();
        this.initEvent();
    },

    initEvent:function(){
        var that = this;
        $(window).resize(function(){
            that.setSize();
        })
    },

    setSize:function(){
       var win = this.getWindowSize();
       if(win.w<this.main_min_width){
          win.w = this.main_min_width;
       }

      if(win.h<this.main_min_height){
          //win.h = this.main_min_height;
       }

       var wrapW = win.w-(this.main_padding_left*2);
       var wrapH = win.h;
       $(".wrap-all").width(wrapW);
       $(".wrap-all").height(win.h);
       var innerHeight = win.h-this.main_top_height-this.main_padding_bottom;
       $("#inner-main").height(innerHeight);
       var viewHeight = innerHeight-(this.fixLineHeight*2);

	   $("#main-content").height(viewHeight);
       $("#detail-content").css("top",this.detail_paddingTop+this.fixLineHeight);
       $("#detail-content").height(viewHeight-this.detail_paddingTop);
       var viewDetailWidth =  $("#main-content").width()-this.detail_paddingLeft;

       $("#detail-content").width(viewDetailWidth);
    },

    getWindowSize:function(){
        var w = ($(window).width());
        var h = ($(window).height());
        return {w:w,h:h};
    },

    getSideW:function(){
      return ($("#aside").outerWidth());
    }
}

Mo.Frame.controller = {
	mainFrameTag : "main-frame",
	detailFrameTag : "detail-frame",
	showHideTime : 200,
	init : function() {
		this.initEvent();
	},

	initEvent : function() {
		var that = this;
		$("#e-bn-fold").click(function() {
			that.hideDetail();
		});
	},

	initUrlFilter : function() {
		this.mainUrlFilter();
		this.detailUrlFilter();
	},
	mainUrlFilter : function() {
		var that = this;
		$(document).on("click","a[target='" + this.mainFrameTag + "']",function(){
			var url = $(this).attr("href");
			if (url) {
				that.setMainFrame(url);
			}
			return false;
		});
	},

	detailUrlFilter : function() {
		var that = this;
		$(document).on("click","a[target='" + this.detailFrameTag + "']",function(event) {
			var url = $(this).attr("href");
			if (url) {
				that.setDetailFrame(url, Mo.Frame.detail_paddingTop, Mo.Frame.detail_paddingLeft);
			}
			return false;
		});
	},

	fiterFrameURL : function(url) {
		if (url == "#") {
			return false;
		}
		return url;
	},

	setMainFrame : function(url) {
		url = this.fiterFrameURL(url);
		if (url) {
			$("#main-frame")[0].src = url;
		}
		this.hideDetail();
	},

	setDetailFrame : function(url, paddingTop, paddingLeft) {
		url = this.fiterFrameURL(url);
		if (url) {
			$("#detail-frame")[0].src = url;
			this.showDetail(paddingTop, paddingLeft);
		}
	},

	getMainFrame : function() {
		return	$("#main-frame")[0];
	},

	getDetailFrame : function() {
		return	$("#detail-frame")[0];
	},

	callMainFrameFn:function(fnName){
		var obj = this.getMainFrame().contentWindow;
		if(typeof obj[fnName] == "function"){
			obj[fnName]();
		}
	},

	callDetailFrameFn:function(fnName){
		var obj = this.getDetailFrame().contentWindow;
		if(typeof obj[fnName] == "function"){
			obj[fnName]();
		}
	},

	showDetail : function(paddingTop, paddingLeft) {

		paddingTop = paddingTop || Mo.Frame.detail_paddingTop;
		paddingLeft = paddingLeft || Mo.Frame.detail_paddingLeft;
		if (Mo.Frame.detail_paddingTop != paddingTop || Mo.Frame.detail_paddingLeft != paddingLeft) {
			this.setDetailSize(paddingTop, paddingLeft);
		}

		if ($("#detail-content").is(":visible")) {
			return;
		}

		$("#detail-content").stop(true, true).show().css("left",
				$("#inner-main").width()).animate({
			left : paddingLeft
		}, this.showHideTime);
	},

	setDetailSize : function(paddingTop, paddingLeft) {
		Mo.Frame.detail_paddingTop = paddingTop || Mo.Frame.default_detail_paddingTop;
		Mo.Frame.detail_paddingLeft = paddingLeft || Mo.Frame.default_detail_paddingLeft;
		Mo.Frame.setSize();
	},

	hideDetail : function() {
		//if($("#detail-content").is(":hidden")){
		// return;
		//}

		$("#detail-content").stop(true, true).animate({
			left : $("#inner-main").width()
		}, this.showHideTime, function() {
			$(this).hide();
			$("#detail-frame")[0].src = "about:blank";
		});
	}
};

// 框架导航
Mo.Frame.nav = {
	init: function() {
		this.initEvent();
	},
	initEvent: function() {
		$(document).bind("click",function(e){
			var target  = $(e.target);
			if(target.closest(".setting").length == 0 && target.closest(".setting-list").length == 0){
				$(".setting-list", "#header").hide();
			}
		});
		
		$(".setting", "#header").bind("click", function(){
			$(".setting-list", "#header").show();
		});
	}
};

$.namespace("Mo.Frame.aside");
Mo.Frame.aside = {
	init:function(){
		this.initEvent();
	},
	initEvent:function(){
		var that = this;
		$(".aside-menu").on("click",".menu h3",function(){
			var $menu = $(this).parent(".menu");
			if($menu.hasClass(".selected")){
				return;
			}
			$(".aside-menu .menu.selected").removeClass("selected");
			$menu.addClass("selected");
			
			var li = $("li:first",$menu);
			li.click();
			li.find("a").click();
		});
		
		$(".aside-menu").on("click",".menu li",function(){
			that.selectedLi($(this));
		});

	},

	selectedLi:function(li){
		if(li.hasClass(".selected")){
			return;
		}
		$(".aside-menu li.selected").removeClass("selected");
		li.addClass("selected");
	}
};

$.namespace("Mo.Home");
Mo.Home = {
	init:function(){
		//frame
		Mo.Frame.init();
    	Mo.Frame.controller.init();
    	Mo.Frame.controller.initUrlFilter();
    	Mo.Frame.nav.init();
     	Mo.Frame.aside.init();

		//page
		Mo.Home.asideMenu.init();
		Mo.Home.modifyUser.init();
		Mo.Home.modifyPassword.init();
		Mo.Home.exit.init();
		Mo.Home.about.init();
	}
};

//左侧菜单
Mo.Home.asideMenu={
	init: function(){
		this.initMenu();
		this.initEvent();
	},
	initMenu: function() {
		var data = Mo.Menu.allMenus;

		var html = "";
		var rootMenus = this.getRootMenus(data);
		var len = rootMenus.length;
		for(var i=0; i<len; i++) {
			var subMenus = this.getSubMenus(data, rootMenus[i].id);
			var subLen = subMenus.length;
			html += '<div class="menu">';
			html += '<h3><span>' + rootMenus[i].name + '</span><s>' + subLen + '</s></h3>';
			html += '<ul>';
			if(subLen>0) {
				for(var j=0; j<subLen; j++) {
					//var func = (subMenus[j].url ? "Mo.Frame.AsideMenu.loadMainFrame('" + subMenus[j].url + "')" : "void(0)");
					html += '<li><a href="' + Mo.Base.getAppUrl()+ subMenus[j].url + '" target="main-frame">' + subMenus[j].name + '</a></li>';
				}
			}
			html += '</ul>';
			html += '</div>';
		}
		
		$("#divAsideMenu").html(html);
	},
	initEvent: function(){
		
	},
	getRootMenus: function(data) {
		return this.getSubMenus(data, Mo.Menu.rootMenuId);
	},
	getSubMenus: function(data, parentId) {
		var menus = [];
		var len = data.length;
		if(len >0) {
			for(var i=0; i<len; i++) {
				if(data[i].parentId == parentId) {
					menus[menus.length] = data[i];
				}
			}
		}
		return menus;
	},
	loadMainFrame: function(url) {
		// 隐藏detail-frame
		Mo.Frame.controller.hideDetail();
		
		// 重新设置main-frame
		Mo.Frame.controller.setMainFrame(Mo.Config.appUrl + url);
	}
};



//修改用户信息
Mo.Home.modifyUser = {
	init: function(){
		this.initEvent();
	},
	initEvent: function(){
		var that = this;
		$("#modifyUser").bind("click", function(){
			$.dialog({
				padding: 0,
				id:"modifyUserWindow",
				lock: true,
				opacity: 0.50,	// 透明度
				content: document.getElementById('modifyUserWrapper'),
				close: function () {//关闭清空数据
					that.hideMsg();
					$("#account").val($("#oldAccount").val());
					$("#mobile").val($("#oldMobile").val());
					$("#email").val($("#oldEmail").val());
					$("#officeLocation").val($("#oldOfficeLocation").val());
				},
				cancel:false, // 隐藏关闭按钮
				drag: false // 不允许拖拽
			});
		});
		//绑定按钮
		$(".confirm", "#modifyUserWrapper").bind("click",function(){
			that.hideMsg();
			
			var data = that.checkData();
			if(false==data){
				return false;
			}
			$.ajax({
				type: 'post',
				url: Mo.Base.getAppUrl()+"/system/user/updateUser",
				data: data,
				dataType: 'json',
				success: function(msg){
					if(msg.success) {
						$("#oldAccount").val($("#account").val());
						$("#oldMobile").val($("#mobile").val());
						$("#oldEmail").val($("#email").val());
						$("#oldOfficeLocation").val($("#officeLocation").val());
					}
					that.showMsg(msg.description);
				},
				error: function(){
					that.showMsg("修改个人资料失败，请与系统管理员联系！");
				}
			});
		});
		//关闭
		$(".W_close", "#modifyUserWrapper").bind("click",function(){
			that.close();
		});
		
		//取消
		$(".cancel", "#modifyUserWrapper").bind("click",function(){
			that.close();
		});
	},
	close: function(){
		$.dialog({id: 'modifyUserWindow'}).close();
	},
	//验证
	checkData: function(){
		var data = {
			account:$("#account").val(),
			mobile:$("#mobile").val(),
			email:$("#email").val(),
			officeLocation:$("#officeLocation").val()
		};
		if('' == $.trim(data.account)){
			this.showMsg("请输入账号。");
			$("#account").focus();
			return false;
		}
		if('' == $.trim(data.mobile)){
			this.showMsg("请输入联系电话。");
			$("#mobile").focus();
			return false;
		}
		if('' == $.trim(data.email)){
			this.showMsg("请输入Email。");
			$("#email").focus();
			return false;
		}
		if('' == $.trim(data.officeLocation)){
			this.showMsg("请输入联系地址。");
			$("#officeLocation").focus();
			return false;
		}
		
		return data;
	},
	showMsg: function(msg){
		$(".msg", "#modifyUserWrapper").text(msg);
		$(".item-msg", "#modifyUserWrapper").show();
	},
	hideMsg: function(){
		$(".item-msg", "#modifyUserWrapper").hide();
		$(".msg", "#modifyUserWrapper").text('');
	}
};
//修改密码
Mo.Home.modifyPassword = {
	init: function(){
		this.initEvent();
	},
	initEvent: function(){
		var that = this;
		$("#modifyPassword").bind("click", function(){
			$.dialog({
				padding: 0,
				id:"modifyPasswordWindow",
				lock: true,
				opacity: 0.50,	// 透明度
				title: "修改密码",
				content: document.getElementById('modifyPasswordWrapper'),
				close: function () {//关闭清空数据
			    	$("#oldPassword").val("");
			    	$("#newPassword").val("");
			    	$("#confirmPassword").val("");
			    	that.hideMsg();
			    },
				cancel:false, // 隐藏关闭按钮
				drag: false // 不允许拖拽
			});
		});
		//绑定按钮
		$(".confirm", "#modifyPasswordWrapper").bind("click",function(){
			that.hideMsg();
			
			var data = that.checkData();
			if(data == false){
				return false;
			}
			
			$.ajax({
				type: 'post',
				url: Mo.Base.getAppUrl()+"/system/user/updatePassword",
				data: data,
				dataType: 'json',
				success: function(msg){
					that.showMsg(msg.description);
				},
				error: function(){
				}
			});
		});
		//取消
		$(".W_close", "#modifyPasswordWrapper").bind("click",function(){
			that.close();
		});
		//取消
		$(".cancel", "#modifyPasswordWrapper").bind("click",function(){
			that.close();
		});
	},
	close: function(){
		$.dialog({id: 'modifyPasswordWindow'}).close();
	},
	checkData: function(){
		var data = {
			moid: "kedacom",
			oldPassword: $("#oldPassword").val(),
			newPassword: $("#newPassword").val(),
			confirmPassword: $("#confirmPassword").val()
		};
		if('' == $.trim(data.oldPassword)){
			this.showMsg("当前密码不能为空，请输入当前密码。");
			$("#oldPassword").focus();
			return false;
		}
		if('' == $.trim(data.newPassword)){
			this.showMsg("新密码不能为空，请输入新密码。");
			$("#newPassword").focus();
			return false;
		}
		if(data.newPassword.length < 6 || data.newPassword.length > 16) {
			this.showMsg("新密码不符合要求！");
			$("#newPassword").focus();
			return false;
		}
		if($.trim(data.oldPassword) == $.trim(data.newPassword)){
			this.showMsg("新密码不能与旧密码一致");
			$("#newPassword").focus();
			return false;
		}
		if($.trim(data.newPassword) != $.trim(data.confirmPassword)){
			this.showMsg("两次输入密码不一致，请重新输入。");
			$("#confirmPassword").focus();
			return false;
		}
		
		return data;
	},
	showMsg: function(msg){
		$(".msg", "#modifyPasswordWrapper").text(msg);
		$(".item-msg", "#modifyPasswordWrapper").show();
	},
	hideMsg: function(){
		$(".item-msg", "#modifyPasswordWrapper").hide();
		$(".msg", "#modifyPasswordWrapper").text('');
	}
};

Mo.Home.exit={
	init: function(){
		this.initEvent();
	},
	initEvent: function(){
		var that =this;
		$(".logout", "#header").bind("click", function(){
			$.dialog({
				padding: 0,
				id: "logoutWindow",
				content: document.getElementById('logoutWrapper'),
				lock: true,
				opacity: 0.50,	// 透明度
				cancel:false, // 隐藏关闭按钮
				drag: false // 不允许拖拽
			});
		});
		//取消
		$(".W_close", "#logoutWrapper").bind("click",function(){
			that.close();
		});
		
		$(".cancel", "#logoutWrapper").bind("click",function(){
			that.close();
		});
		
		$(".confirm", "#logoutWrapper").bind("click",function(){
			location.href = Mo.Base.getAppUrl()+"/logout";
			return false;
		});
	},
	close: function(){
		$.dialog({id: 'logoutWindow'}).close();
	}
};

Mo.Home.about={
	init: function(){
		this.initEvent();
	},
	initEvent: function(){
		var that = this;
		$("#about").bind("click", function(){
			$.dialog({
				padding: 0,
				id: "aboutWindow",
				content: document.getElementById('aboutWrapper'),
				lock: true,
				opacity: 0.50,	// 透明度
				cancel:false, // 隐藏关闭按钮
				drag: false // 不允许拖拽
			});
		});
		
		//取消
		$(".W_close", "#aboutWrapper").bind("click",function(){
			that.close();
		});
	},
	close: function(){
		$.dialog({id: 'aboutWindow'}).close();
	}
};