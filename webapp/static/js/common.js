$.namespace("Mo.Base");
Mo.Base = {
	getAppUrl : function() {
		return Mo.Config.appUrl;
	},
	getStaticUrl : function() {
		return Mo.Config.staticUrl;
	}
};

//请求安全锁
$.namespace("Mo.Base.throttle");
Mo.Base.throttle = {
	time:15*1000,
	data:{},
	reg:function(key,time){
		time = time || this.time;
		this.data[key] = {lock:false,time:time,thread:null};
		return this.data[key];
	},
	lock:function(key,time){
		var data = this.getData(key);
		if(!data){
			data = this.reg(key,time);
		}
		clearTimeout(data.thread);
		var that = this;
		this.getData(key).lock = true;

		if(time==-1){return;}

		this.getData(key).thread = setTimeout(function(){
			that.getData(key).lock = false;
		},data.time);
	},
	unLock:function(key){
		var data = this.getData(key);
		if(!data){
			//data = this.reg(key);
			printf("unlock error key: "+key);
			return;
		}
		clearTimeout(data.thread);
		this.getData(key).lock = false;
	},
	isLock:function(key){
		var data = this.getData(key);
		if(!data){
			return false;
		}
		return data.lock;
	},
	getData:function(key){
		return this.data[key];
	}	
};

var regexEnum = {
	account:"^[\u4e00-\u9fa5\\w\\.@]+$", // 中文、英文字母（大小写）、数字、“_”、“@”、“.”
	email:"^[\\w]+(\\.[\\w]+)*@[\\w]+(\\.[\\w]+)+$", // 英文字母（大小写）、数字、“_”、“@”、“.”
	name: "^[\u4e00-\u9fa5\\w]+$", // 中文、英文字母（大小写）、数字、“_“
	password:"^[\\w\\.]+$", // 英文字母(大小写)、数字、“_”、“.”
	mobile:"^[0-9]{11}$", // 数字
	extNum:"^[0-9（）()\-，,；;*+#. ]{1,30}$" // 分机号码只允许"数字、（、）、，、-、；、*、+、#、空格、."！
};

//统一账号字符验证
$.namespace("Mo.Base.account");
Mo.Base.account = {
	checkEmail : function(value){
		if (!Mo.Base.validation.isRegExp(regexEnum.email,value)) {
			alert("请输入正确的邮箱地址");
			return false;
		};
		
		if(!Mo.Base.validation.checkLength(value, 48)) {
			alert("邮箱地址长度不能大于48个字符");
			return false;
		}
		
		return true;
	},
	checkAccount : function(value){
		if (!Mo.Base.validation.isRegExp(regexEnum.account,value)) {
			alert('用户名只允许中文、英文字母、数字、@、.或下划线字符!');
			return false;
		};
		
		if(!Mo.Base.validation.checkLength(value, 48)) {
			alert("用户名长度不能大于48个字符");
			return false;
		}
		
		return true;
	},
	checkName : function(value){
		if (!Mo.Base.validation.isRegExp(regexEnum.name,value)) {
			alert('姓名只允许中文、英文字母、数字或下划线字符!');
			return false;
		};
		
		if(!Mo.Base.validation.checkLength(value, 48)) {
			alert("姓名长度不能大于48个字符");
			return false;
		}
		
		return true;
	},
	checkPassword : function(value){
		if (!Mo.Base.validation.isRegExp(regexEnum.password,value)) {
			alert('密码只允许英文字母、数字、.或下划线字符!');
			return false;
		};
		
		if(!Mo.Base.validation.checkLength(value, 32)) {
			alert("密码长度不能大于32个字符");
			return false;
		}
		
		return true;
	},
	checkMobile : function(value){
		if (!Mo.Base.validation.isRegExp(regexEnum.mobile,value)) {
			alert('手机号码只允许11位数字!');
			return false;
		};
		
		return true;
	},
	checkExtNum : function(value){
		if (!Mo.Base.validation.isRegExp(regexEnum.mobile,value)) {
			alert('分机号码只允许"数字、（、）、，、-、；、*、+、#、空格、."！');
			return false;
		};
		
		return true;
	}
};

//字符验证
$.namespace("Mo.Base.validation");
Mo.Base.validation = {
	//是否数字
	isNumber:function(value){
		return typeof(value) == "number" ? true : false;
	},
	//是否字符串
	isString:function(value){
		return typeof(value) == "string" ? true : false;
	},
	//是否布尔型
	isBoolean:function(value){
		return typeof(value) == "boolean" ? true : false;
	},
	//是否对象
	isObject:function(value){
		return typeof(value) == "object" ? true : false;
	},
	//是否为真
	isTrue:function(value){
		if(this.isString(value)){
			return true;
		}
		var tmp = new String(value).toLowerCase().trim();
		return (tmp == "true" || tmp == "1") ? true : false;
	},
	//不为空 null 以及 undefined
	isNotNull:function(value){
		if(typeof(value) == "undefined"){
			return false;
		}
		if(value == null){
			return false;
		}
		if(value == ""){
			return false;
		}
		return true;
	},
	//是否是自定义的正则表达式
	isRegExp:function(reg,value){
		if(this.isString(value)){
			reg = new RegExp(reg);
		}
		if(!reg.test(value)){
			return false;
		}
		return true;
	},
	//是否为任意数字
	isNumeric:function(value){
		var reg = /^(-|\+)?\d+(\.\d+)?$/;
		return this.isRegExp(reg,value);
	},
	//检查是否为正数
	isUnsignedNumeric:function(value){
		var reg = /^\d+(\.\d+)?$/;
		return this.isRegExp(reg,value);
	},
	//是否为整数
	isInteger:function(value){
		var reg = /^(-|\+)?\d+$/;
		return this.isRegExp(reg,value);
	},
	//是否为非负整数
	isUnsignedInteger:function(value){
		var reg = /^\d+$/;
		return this.isRegExp(reg,value);
	},
	//是否为正整数
	isPositiveInteger:function(value){
		var reg = /^[0-9]*[1-9][0-9]*$/;
		return this.isRegExp(reg,value);
	},
	//是否为正整数（第一位不能是0开头）
	isPositiveInteger_:function(value){
		var reg = /^[1-9]*[1-9][0-9]*$/;
		return this.isRegExp(reg,value);
	},
	//是否是仅数字字母下滑线
	isFiledEn:function(value){
		var reg = /^\w+$/;
		return this.isRegExp(reg,value);
	},
	//是否是仅数字字母_-（）
	isFiledEn2:function(val){
		var reg=/^[\w\-\(\)]+$/; 
		return reg.test(val);
	},
	//是否仅中文
	isFiledCN:function(value){
		var reg = /^([\u4e00-\u9fa5])+$/;
		return this.isRegExp(reg,value);
	},
	//是否是中文数字字母下滑线
	isFiledString:function(value){
		var reg = /^(\w|[\u4e00-\u9fa5]|-)+$/;
		return this.isRegExp(reg,value);
	},
	//是否是中文数字字母下滑线点号
	isCodeString:function(value){
		var reg = /^(\w|[\u4e00-\u9fa5]|-|\.)+$/;
		return this.isRegExp(reg,value);
	},
	//是否在指定的范围
	isRange:function(max,min,value){
		if(value<min || value>max){
			return false;
		}else{
			return true;
		}
	},
	//是否为正浮点数
	isUnsignedFloat:function(value){
	  var reg= /^\d+(\.\d*)?$/;
	  return this.isRegExp(reg,value);
	},
	isCharAndNumber: function(value){//中文、英文字母、数字、下划线
		var reg = /^[\u4e00-\u9fa5a-zA-Z0-9_]+$/;
		return reg.test(value);
	},//验证固定电话
	checkLength:function(value, len) {
		var str=value.replace(/[^\x00-\xff]/g,"**");
		if (str.length > len) {
			return false;
		}
		return true;
	}
};

function $Reg(Obj,reg){
	var value = $F(Obj).trim();
	return Validation.isRegExp(reg,value);
}

$(function() {
	// IE也能用textarea
	$("textarea[maxlength]").keyup(function() {
		dealTextAreaMaxLength($(this));
	});
	// 复制的字符处理问题
	$("textarea[maxlength]").blur(function() {
		dealTextAreaMaxLength($(this));
	});
});

function dealTextAreaMaxLength(textarea) {
	var max = parseInt(textarea.attr("maxlength"), 10); // 获取maxlength的值
	if (max > 0) {
		var value = textarea.val();
		if (value.length > max) { // textarea的文本长度大于maxlength
			area.val(value.substr(0, max)); // 截断textarea的文本重新赋值
		}
	}
}