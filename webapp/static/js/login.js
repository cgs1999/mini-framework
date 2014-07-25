$.namespace("Mo.Login");
Mo.Login = {
	init: function(){
		this.initEvent();
	}, 
	initEvent: function(){
		$(".arrow-down", ".item-setting-language").bind("click",function(){
			$(".item-language-list").show();
		});

		$(document).bind("click",function(e){
			var target  = $(e.target);
			if(target.closest(".arrow-down").length == 0 && target.closest(".item-language-list").length == 0){
				$(".item-language-list").hide();
				$(".setting-language").removeClass("select");
			}
		});

		$(".setting-language", ".ul-language-list").bind("click", function(){
			var locale = $(this).attr("vId");
			$(".current-language").text($(this).attr("value"));
			$.ajax({
				type: 'post',
				url: Mo.Base.getAppUrl()+"/changeLocale",
				data: {locale:locale},
				dataType: 'json',
				success: function(msg){
					if(!msg.success) {
						alert(msg.description);
					}
					
					location.href=Mo.Base.getAppUrl()+"/login";
				},
				error: function(){
					location.href=Mo.Base.getAppUrl()+"/login";
				}
			});
		}).mouseover(function(){
			if (!$(this).hasClass("select")) {
				$(".setting-language").removeClass("select");
				$(this).addClass("select");
			}
		});;

		$(".forgot-password").bind("click", function(){
			Mo.Login.showForgotPassword();
		});

		$(".W_close", "#forgotPassword-wrapper").bind("click", function(){
			Mo.Login.showLogin();
		});
		
		$("#account").keydown(function(event){
			if (event.keyCode == 13) {
				$("#loginBtn").click();
				return false;
			}
			
			var regDom = $(".reg-label", ".item-account");
			regDom.hide();
		}).blur(function(){
			var regDom = $(".reg-label", ".item-account");
			var account = $("#account").val();
			if ($.trim(account) == '') {
				regDom.show();
				$("#account").val('');
			}
		});

		$("#password").keydown(function(event){
			if (event.keyCode == 13) {
				$("#loginBtn").click();
				return false;
			}
			
			var regDom = $(".reg-label", ".item-password");
			regDom.hide();
		}).blur(function(){
			var regDom = $(".reg-label", ".item-password");
			var password = $("#password").val();
			if (password == '') {
				regDom.show();
				$("#password").val('');
			}
		});

		$("#verifyCode").keydown(function(event){
			if (event.keyCode == 13) {
				$("#loginBtn").click();
				return false;
			}
			
			var regDom = $(".reg-label", ".item-verifyCode");
			regDom.hide();
		}).blur(function(){
			var regDom = $(".reg-label", ".item-verifyCode");
			var verifyCode = $("#verifyCode").val();
			if ($.trim(verifyCode) == '') {
				regDom.show();
				$("#verifyCode").val('');
			}
		});

		$("#loginBtn").bind("click", function(){
			if (Mo.Base.throttle.isLock("/login")) {
				return false;
			}
			
			Mo.Base.throttle.lock("/login"); // 正在登录中...
			
			Mo.Login.hideLoginMsg();
			var data = Mo.Login.checkData();
			if (!data || data == null) {
				Mo.Base.throttle.unLock("/login");
				return false;
			}

			$.ajax({
				type: 'post',
				url: Mo.Base.getAppUrl() + "/login",
				data: data,
				dataType: 'json',
				success: function(msg){
					if (msg.success) {
						location.href=Mo.Base.getAppUrl()+"/home";
					} else {
						Mo.Login.showLoginMsg(msg.description);
						$("#verifyImage").click();
						Mo.Login.showVerifyCode();
					}
					
					Mo.Base.throttle.unLock("/login");
				},
				error: function(){
					$("#verifyImage").click();
					Mo.Login.showVerifyCode();
					Mo.Login.showLoginMsg($lang.login_login_msg_ajax_error);
					Mo.Base.throttle.unLock("/login");
				}
			});
		});

		$("#verifyImage").bind("click", function(){
			document.getElementById("verifyImage").src=Mo.Base.getAppUrl() + "/adminVerifyImage?random="+new Date().getTime();
		});

		$("#reg-email").keydown(function(){
			var regDom = $(".reg-label", ".item-reg-email");
			regDom.hide();
		}).blur(function(){
			var regDom = $(".reg-label", ".item-reg-email");
			var regEmail = $("#reg-email").val();
			if ($.trim(regEmail) == '') {
				regDom.show();
				$("#reg-email").val('');
			}
		});

		$("#forgotPasswordBtn").bind("click", function(){
			var regEmail = $("#reg-email").val();
			if ($.trim(regEmail) == '') {
				Mo.Login.showForgotPasswordMsg($lang.login_forgot_msg_regEmail_empty);
				return false;
			}
		});
	},
	checkData: function(){
		var data = {
			account: $("#account").val(),
			password: $("#password").val(),
			verifyCode: $("#verifyCode").val()
		}

		if ($.trim(data.account) == '') {
			Mo.Login.showLoginMsg($lang.login_login_msg_account_empty);
			$("#account").focus();
			return false;
		}

		if ($.trim(data.password) == '') {
			Mo.Login.showLoginMsg($lang.login_login_msg_password_empty);
			$("#password").focus();
			return false;
		}

		if ($(".item-verifyCode").hasClass("hidden")) {
			data.enableVerifyCode = "0";
		} else {
			data.enableVerifyCode = "1";
			if($.trim(data.verifyCode) == ''){
				Mo.Login.showLoginMsg($lang.login_login_msg_verifyCode_empty);
				$("#verifyCode").focus();
				return false;
			}
		}

		return data;
	},
	showLogin: function() {
		$("#login-wrapper").show();
		$("#account").focus();
		$("#forgotPassword-wrapper").hide();
		$("#reg-email").val("");
		$(".reg-label", ".item-reg-email").show();
		Mo.Login.hideForgotPasswordMsg();
	},
	showForgotPassword: function() {
		$("#login-wrapper").hide();
		$("#forgotPassword-wrapper").show();
		$("#reg-email").focus();
		$("#account").val("");
		$(".reg-label", ".item-account").show();
		$("#password").val("");
		$(".reg-label", ".item-password").show();
		$("#verifyCode").val("");
		$(".reg-label", ".item-verifyCode").show();
		Mo.Login.hideLoginMsg();
	},
	showLoginMsg: function(msg) {
		$(".msg", "#login-wrapper").text(msg);
		$(".msg-div", "#login-wrapper").show();
	},
	hideLoginMsg: function() {
		$(".msg-div", "#login-wrapper").hide();
		$(".msg", "#login-wrapper").text('');
	},
	showForgotPasswordMsg: function(msg) {
		$(".msg", "#forgotPassword-wrapper").text(msg);
		$(".msg-div", "#forgotPassword-wrapper").show();
	},
	hideForgotPasswordMsg: function() {
		$(".msg-div", "#forgotPassword-wrapper").hide();
		$(".msg", "#forgotPassword-wrapper").text('');
	},
	showVerifyCode: function() {
		if ($(".item-verifyCode").hasClass("hidden")) {
			$(".item-verifyCode").removeClass("hidden");
		}
	},
	hideVerifyCode: function(){
		if (!$(".item-verifyCode").hasClass("hidden")) {
			$(".item-verifyCode").addClass("hidden");
			$("#verifyCode").val('');
			var regDom = $(".reg-label", ".item-verifyCode");
			if (regDom.hasClass("hidden")) {
				regDom.removeClass("hidden");
			}
		}
	}
}