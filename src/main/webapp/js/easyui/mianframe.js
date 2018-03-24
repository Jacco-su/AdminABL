var _menus = {
			zlgl : [ {
				"menuid" : "10",
				"icon" : "icon-sys",
				"menuname" : "文件管理",
				"menus" : [ {
					"menuid" : "11",
					"menuname" : "局文档",
					"icon" : "icon icon-jwd",
					"url" : "file/index?type=0"
				},{
					"menuid" : "12",
					"menuname" : "部门文档",
					"icon" : "icon icon-bmwd",
					"url" : "file/index?type=1"
				},{
					"menuid" : "13",
					"menuname" : "个人文档",
					"icon" : "icon icon-grwd",
					"url" : "file/index?type=2"
				}]
			},{
				"menuid" : "20",
				"icon" : "icon-sys",
				"menuname" : "文件审核",
				"menus" : [ {
					"menuid" : "21",
					"menuname" : "待办任务",
					"icon" : "icon icon-dbrw",
					"url" : "file/checkList"
				},{
					"menuid" : "22",
					"menuname" : "发起任务",
					"icon" : "icon icon-fqrw",
					"url" : "file/startList"
				}]
			},{
				"menuid" : "30",
				"icon" : "icon-sys",
				"menuname" : "内部审核",
				"menus" : [ {
					"menuid" : "31",
					"menuname" : "年度审核计划",
					"icon" : "icon icon-shjh",
					"url" : "file/checkList"
				},{
					"menuid" : "32",
					"menuname" : "审核通知",
					"icon" : "icon icon-shtz",
					"url" : "msg/index?state=0"
				},{
					"menuid" : "33",
					"menuname" : "审核组",
					"icon" : "icon icon-shz",
					"url" : "checkGroup/index"
				},{
					"menuid" : "34",
					"menuname" : "审核流程",
					"icon" : "icon icon-shlc",
					"url" : "file/startList"
				}]
			}],
			jxgl : [{
				"menuid" : "60",
				"icon" : "icon-sys",
				"menuname" : "绩效管理",
				"menus" : [{
					"menuid" : "41",
					"icon" : "icon-sys",
					"menuname" : "待开发",
					"url" : "view/commons/notBuild.jsp"
				}]
			}],
			gzlh : [{
				"menuid" : "70",
				"icon" : "icon-sys",
				"menuname" : "工作例会",
				"menus" : [{
					"menuid" : "41",
					"icon" : "icon-sys",
					"menuname" : "待开发",
					"url" : "view/commons/notBuild.jsp"
				}]
			}],
			message : [{
				"menuid" : "40",
				"icon" : "icon-sys",
				"menuname" : "用户信箱",
				"menus" : [{
					"menuid" : "41",
					"icon" : "icon icon-inbox",
					"menuname" : "收件箱",
					"url" : "msg/index?state=1"
				},{
					"menuid" : "42",
					"icon" : "icon icon-outbox",
					"menuname" : "发件箱",
					"url" : "msg/index?state=0"
				}]
			}],
			system : [{
				"menuid" : "50",
				"icon" : "icon-system",
				"menuname" : "系统管理",
				"menus" : [{
					"menuid" : "51",
					"icon" : "icon icon-users",
					"menuname" : "用户管理",
					"url" : "user/index"
				},{
					"menuid" : "52",
					"icon" : "icon icon-dept",
					"menuname" : "部门管理",
					"url" : "dept/index"
				},{
					"menuid" : "53",
					"icon" : "icon icon-role",
					"menuname" : "角色管理",
					"url" : "role/index"
				},{
					"menuid" : "54",
					"icon" : "icon icon-role",
					"menuname" : "模块管理",
					"url" : "module/index"
				},{
					"menuid" : "55",
					"icon" : "icon icon-operation",
					"menuname" : "操作管理",
					"url" : "operation/index"
				},{
					"menuid" : "56",
					"icon" : "icon icon-filetype",
					"menuname" : "文件类型管理",
					"url" : "fileType/index"
				},{
					"menuid" : "57",
					"icon" : "icon icon-flow",
					"menuname" : "流程管理",
					"url" : "flow/index"
				}]
			}]
		};