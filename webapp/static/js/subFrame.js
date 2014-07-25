$.namespace("Mo.SubFrame");
Mo.SubFrame = {
	init : function() {
		this.isIframe();
		this.setSize();
		this.initEvent();
		Mo.SubFrame.controller.init();
	},
	isIframe : function() {
		if (top.location === self.location) {
			// location.href= Mo.Base.getAppUrl();
			return false;
		} else {
			return true;
		}
	},
	initEvent : function() {
		var that = this;
		$(window).resize(function() {
			that.setSize();
		});
	},

	getWindowSize : function() {
		var w = ($(window).width());
		var h = ($(window).height());
		return {
			w : w,
			h : h
		};
	},

	setSize : function() {
		if (this.isEasyUI()) {
			return;
		}
		
		var win = this.getWindowSize();
		var h = $(".detail-btn").outerHeight();
		$(".detail-body").height(win.h - h);
	},

	isEasyUI : function() {
		return $(".easyui-layout")[0];
	},
	
	getPageSize : function() {
		// 计算datagrid的pageSize
		return 14;
	}
};

Mo.SubFrame.controller = {
	superFrame : window.parent && window.parent.Mo.Frame.controller,
	init : function() {
		this.initUrlFilter();
	},
	// 拦截所有的带框架Target的URL，控制跳转
	initUrlFilter : function() {
		this.mainUrlFilter();
		this.detailUrlFilter();
	},

	// 拦截main-frame的带框架Target的URL，控制跳转
	mainUrlFilter : function() {
		var that = this.superFrame;
		$(document).on("click","a[target='" + that.mainFrameTag + "']",function(){
			var url = $(this).attr("href");
			if (url) {
				that.setMainFrame(url);
			}
			return false;
		});
	},
	// 拦截detail-frame的带框架Target的URL，控制跳转
	detailUrlFilter : function() {
		var that = this;
		$(document).on("click","a[target='" + this.detailFrameTag + "']",function(event) {
			var url = $(this).attr("href");
			if (url) {
				that.setDetailFrame(url);
			}
			return false;
		});
	},
	setMainFrame : function(url) {
		this.superFrame.setMainFrame(url);
	},
	// paddingTop和paddingLeft 如果不设置采用默认配置
	setDetailFrame : function(url, paddingTop, paddingLeft) {
		this.superFrame.setDetailFrame(url, paddingTop, paddingLeft);
	},

	showDetail : function(paddingTop, paddingLeft) {
		this.superFrame.showDetail(paddingTop, paddingLeft);
	},

	hideDetail : function() {
		this.superFrame.hideDetail();
	},

	setDetailSize : function(paddingTop, paddingLeft) {
		this.superFrame.setDetailSize(paddingTop, paddingLeft);
	},

	getMainFrame : function() {
		return this.superFrame.getMainFrame();
	},

	getDetailFrame : function() {
		return this.superFrame.getMainFrame();
	},

	callMainFrameFn:function(fnName){
		 this.superFrame.callMainFrameFn(fnName);
	},

	callDetailFrameFn:function(fnName){
		 this.superFrame.callDetailFrameFn(fnName);
	}
};

$.namespace("Mo.gridDetail");
Mo.gridDetail = {
	baseUrl : "",
	gridPageSize : Mo.SubFrame.getPageSize(),
	// datagrid参数
	datagridId : "#data-grid",
	datagridOptions : null,
	// treegrid参数
	treegridId : "#data-grid",
	treegridOptions : null,
	// tree参数
	treeId : "#mytree",
	treeOptions : null,
	init : function(options) {
		this.initOptions(options);
		this.initPage();
		this.initHint(".input-text");
	},
	initOptions : function(options) {
		this.initDefaultDatagridOptions(options);
		this.initDefaultTreegridOptions(options);
		this.initDefaultTreeOptions(options);
		
		$.extend(true, this, options);
	},
	initDefaultDatagridOptions : function(options) {
		// 若存在datagridOptions则初始化默认的datagridOptions
		if(options && options.datagridOptions) {
			var defaultDatagridOptions = {
				datagridOptions : {
					idField : "id",
					toolbar : "#tb",
					rownumbers : true,
					striped : false,
					singleSelect : true,
					border : false,
					fit : true,
					fitColumns : true,
					showHeader : true,
					pagination : true,
					pageSize : this.gridPageSize,
					pageList : [ this.gridPageSize ],
					loadMsg : "正在加载，请稍候...",

					onBeforeLoad : function(params) {
						params.start = (params.page - 1) * params.rows;
						params.limit = params.rows;
					},

					onClickRow : function(index, row) {
						var idField = $(this).datagrid("options").idField;
						Mo.gridDetail.doOpen(row[idField]);
					}
				}
			};
			
			$.extend(true, this, defaultDatagridOptions);
		}
	},
	initDefaultTreegridOptions : function(options) {
		// 若存在treegridOptions则初始化默认的treegridOptions
		if(options && options.treegridOptions) {
			var defaultTreegridOptions = {
				treegridOptions : {
					idField : "id",
					toolbar : "#tb",
					rownumbers : true,
					striped : false,
					singleSelect : true,
					border : false,
					fit : true,
					fitColumns : true,
					showHeader : true,
					loadMsg : "正在加载，请稍候...",

					onClickRow : function(row) {
						var idField = $(this).treegrid("options").idField;
						Mo.gridDetail.doOpen(row[idField]);
					}
				}
			};
			
			$.extend(true, this, defaultTreegridOptions);
		}
	},
	initDefaultTreeOptions : function(options) {
		// 若存在treeOptions则初始化默认的treeOptions
		if(options && options.treeOptions) {
			var defaultTreeOptions = {
				treeOptions: {
					formatter: function(node) {
						var s = node.text;
						//if(node.children && node.children.length>0) {
						//	s += "&nbsp;<span style='color: blue'>[" + node.children.length + "]</span>";
						//}
						return s;
					}
				}
			};
			
			$.extend(true, this, defaultTreeOptions);
		}
	},
	initPage : function() {
		this.initTree();
		this.initDatagrid();
		this.initTreegrid();
	},
	initTree : function() {
		// 若存在tree的配置则初始化
		if(this.treeOptions) {
			$(this.treeId).tree(this.treeOptions);
		}
	},
	initDatagrid : function() {
		// 若存在datagrid的配置则初始化
		if(this.datagridOptions) {
			$(this.datagridId).datagrid(this.datagridOptions);
			if(this.datagridOptions.pagination) {
				this.initPagination($(this.datagridId).datagrid("getPager"));
			}
		}
	},
	initTreegrid : function() {
		// 若存在treegrid的配置则初始化
		if(this.treegridOptions) {
			$(this.treegridId).treegrid(this.treegridOptions);
			if(this.treegridOptions.pagination) {
				this.initPagination($(this.treegridId).treegrid("getPager"));
			}
		}
	},

	initPagination:function(pager){
		pager.pagination({
			layout: ["manual","prev", "next"],
			displayMsg:"",
			beforePageText:"",
			afterPageText:"/ {pages}"
		});
	},

	initHint : function(selector) {
		// 初始化提示信息及相关事件
		$(selector).each(function() {
			$(this).val($(this).attr("mo-hint"));
		}).blur(function() {
			$(this).removeClass("active");
			var val = $(this).val();
			var hint = $(this).attr("mo-hint");
			if (val == "") {
				$(this).val(hint);
			}
		}).focus(function() {
			$(this).addClass("active");
			var val = $(this).val();
			var hint = $(this).attr("mo-hint");
			if (val == hint) {
				$(this).val("");
			}
		}).keydown(function(event){
			if (event.keyCode == 13) {
				if($.isFunction(window.doSearch)) {
					doSearch();
				}
				return false;
			}
		});
	},
	
	getMoInputVal: function(selector) {
		// 获取有提示信息的对象值
		var hint = $(selector).attr("mo-hint");
		if(!hint) {
			return $(selector).val();
		}
		
		if($(selector).val()!=hint) {
			return $(selector).val();
		} else {
			return "";
		}
	},
	getUrl: function(url) {
		if(!this.baseUrl) {
			return url;
		}
		
		var lastChar = this.baseUrl.charAt(this.baseUrl.length-1);
		if(lastChar=="/") {
			return this.baseUrl.substring(0, this.baseUrl.length-1) + url;
		} else {
			return this.baseUrl + url;
		}
	},
	doOpen: function(id) {
		Mo.SubFrame.controller.setDetailFrame(this.getUrl("/" + id));
	},
	reinit: function(options) {
		this.datagridOptions = null;
		
		this.initOptions(options);
		this.initDatagrid();
		this.initHint(".input-text");
	},
	load: function() {
		// 关闭打开的表单
		Mo.SubFrame.controller.hideDetail();
		
		// 加载数据
		$(this.datagridId).datagrid("load");
	},
	reload: function() {
		// 关闭打开的表单
		Mo.SubFrame.controller.hideDetail();
		
		// 用于表单操作后的列表刷新，采用reload可使datagrid保持在当前分页
		$(this.datagridId).datagrid("reload");
	},
	loadTreegrid: function() {
		// 关闭打开的表单
		Mo.SubFrame.controller.hideDetail();
		
		// 加载数据
		$(this.treegridId).treegrid("load");
	},
	reloadTreegrid: function() {
		// 关闭打开的表单
		Mo.SubFrame.controller.hideDetail();
		
		// 用于表单操作后的列表刷新，采用reload可使datagrid保持在当前分页
		$(this.treegridId).treegrid("reload");
	}
};