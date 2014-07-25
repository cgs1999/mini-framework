$.namespace("Mo.Dialog");
Mo.Dialog = {
	// 选择角色对话框，返回多个值，以半角逗号分隔
	selectRoles : function(roleIdsId, roleNamesId){
		var objectIds = $('#' + roleIdsId);
		if(!objectIds) {
			alert("角色id接收对象" + roleIdsId + "不存在！");
			return;
		}
		var objectNames = $('#' + roleNamesId);
		if(!objectNames) {
			alert("角色名称接收对象" + roleNamesId + "不存在！");
			return;
		}
		
		$.dialog.data('roleIds', objectIds.val());
		$.dialog.open(Mo.Config.appUrl + "/system/role/selectFromAllRole", {
			id: "selectRoleDialog",
			lock: true,
			opacity: 0.50,	// 透明度
			title: "选择角色",
			padding: 0,
			width: 620,
			height: 440,
			close: function () {
				var roleIds = $.dialog.data('roleIds');
				if(roleIds!=objectIds.val()) {
					objectIds.val(roleIds);
					objectNames.val($.dialog.data('roleNames'));
				}
			},
			cancel:false, // 隐藏关闭按钮
			drag: false // 不允许拖拽
		}, false);
	},

	// 选择菜单对话框，返回多个值，以半角逗号分隔
	selectMenus : function(menuIdsId, menuNamesId) {
		var objectIds = $('#' + menuIdsId);
		if(!objectIds) {
			alert("菜单id接收对象" + menuIdsId + "不存在！");
			return;
		}
		var objectNames = $('#' + menuNamesId);
		if(!objectNames) {
			alert("菜单名称接收对象" + menuNamesId + "不存在！");
			return;
		}
		
		$.dialog.data('menuIds', objectIds.val());
		$.dialog.open(Mo.Config.appUrl + "/system/menu/selectFromAllMenu", {
			id: "selectMenuDialog",
			lock: true,
			opacity: 0.50,	// 透明度
			title: "选择菜单",
			padding: 0,
			width: 360,
			height: 440,
			close: function () {
				var menuIds = $.dialog.data('menuIds');
				if(menuIds!=objectIds.val()) {
					objectIds.val(menuIds);
					objectNames.val($.dialog.data('menuNames'));
				}
			},
			cancel:false, // 隐藏关闭按钮
			drag: false // 不允许拖拽
		}, false);
	},
	
	// 选择服务域对话框，返回一个服务域的moid
	selectServiceDomain : function(serviceDomainMoidId, serviceDomainNameId, callback) {
		var objectId = $('#' + serviceDomainMoidId);
		if(!objectId) {
			alert("服务域moid接收对象" + serviceDomainMoidId + "不存在！");
			return;
		}
		var objectName = $('#' + serviceDomainNameId);
		if(!objectName) {
			alert("服务域名称接收对象" + serviceDomainNameId + "不存在！");
			return;
		}
		
		$.dialog.data('serviceDomainMoid', objectId.val());
		$.dialog.open(Mo.Config.appUrl + "/domain/serviceDomain/selectFromMyService", {
			id : "selectServiceDomainDialog",
			lock : true,
			opacity : 0.50, // 透明度
			title : "选择上级服务域",
			padding : 0,
			width : 360,
			height : 440,
			close : function() {
				var parentId = $.dialog.data('serviceDomainMoid');
				if (parentId != objectId.val()) {
					objectId.val(parentId);
					objectName.val($.dialog.data('serviceDomainName'));
				}
				
				if(typeof callback == "function"){
					callback();
			    }
			},
			cancel : false, // 隐藏关闭按钮
			drag : false
		// 不允许拖拽
		}, false);
	},
	
	// 选择平台域对话框，返回一个平台域的moid
	selectPlatformDomain : function(platformDomainMoidId, platformDomainNameId) {
		var objectId = $('#' + platformDomainMoidId);
		if(!objectId) {
			alert("平台域moid接收对象" + platformDomainMoidId + "不存在！");
			return;
		}
		var objectName = $('#' + platformDomainNameId);
		if(!objectName) {
			alert("平台域名称接收对象" + platformDomainNameId + "不存在！");
			return;
		}
		
		$.dialog.data('platformDomainMoid', objectId.val());
		$.dialog.open(Mo.Config.appUrl + "/domain/platformDomain/selectFromMyPlatform", {
			id : "selectPlatformDomainDialog",
			lock : true,
			opacity : 0.50, // 透明度
			title : "选择上级平台域",
			padding : 0,
			width : 360,
			height : 440,
			close : function() {
				var parentId = $.dialog.data('platformDomainMoid');
				if (parentId != objectId.val()) {
					objectId.val(parentId);
					objectName.val($.dialog.data('platformDomainName'));
				}
			},
			cancel : false, // 隐藏关闭按钮
			drag : false
		// 不允许拖拽
		}, false);
	},
	
	// 选择用户域对话框，返回一个平台域的moid
	selectUserDomain : function(userDomainMoidId, userDomainNameId, groupName, serviceDomainMoid, serviceDomainName, callback) {
		var objectId = $('#' + userDomainMoidId);
		if(!objectId) {
			alert("用户域moid接收对象" + userDomainMoidId + "不存在！");
			return;
		}
		var objectName = $('#' + userDomainNameId);
		if(!objectName) {
			alert("用户域名称接收对象" + userDomainNameId + "不存在！");
			return;
		}
		
		var groupName = $('#' + groupName);
		
		$.dialog.data('userDomainMoid', objectId.val());
		$.dialog.data('serviceDomainMoid', serviceDomainMoid);
		$.dialog.data('serviceDomainName', serviceDomainName);
		$.dialog.open(Mo.Config.appUrl + "/domain/userDomain/selectFromMyUserDomain", {
			id : "selectUserDomainDialog",
			lock : true,
			opacity : 0.50, // 透明度
			title : "选择用户域",
			padding : 0,
			width : 360,
			height : 440,
			close : function() {
				var parentId = $.dialog.data('userDomainMoid');
				if (parentId != objectId.val()) {
					objectId.val(parentId);
					objectName.val($.dialog.data('userDomainName'));
				}
				
				if (groupName) {
					var gName = $.dialog.data('groupName');
					if (gName == null || gName == '') {
						gName = $.dialog.data('userDomainName');
					}
					groupName.val(gName);
				}
				
				if(typeof callback == "function"){
					callback();
			    }
			},
			cancel : false, // 隐藏关闭按钮
			drag : false
		// 不允许拖拽
		}, false);
	}
};