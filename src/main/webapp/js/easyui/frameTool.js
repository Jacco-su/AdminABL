$(function() {
	tabClose();
	tabCloseEven();

	$('#css3menu a').click(function() {
		$('#css3menu a').removeClass('active');
		$(this).addClass('active');
		
		var d = _menus[$(this).attr('name')];
		Clearnav();
		addNav(d);
		InitLeftMenu();
	});

	// 导航菜单绑定初始化
	$("#wnav").accordion( {
		animate : false
	});

	var firstMenuName = $('#css3menu a:first').attr('name');
	addNav(_menus[firstMenuName]);
	InitLeftMenu();
});

function Clearnav() {
	var pp = $('#wnav').accordion('panels');

	for(var i=0;i<pp.length-1;){
		n=pp[i];
	if(n){
	var t = n.panel('options').title;
	$('#wnav').accordion('remove', t);
	}
	}

	pp = $('#wnav').accordion('getSelected');
	if (pp) {
		var title = pp.panel('options').title;
		$('#wnav').accordion('remove', title);
	}
}

function addNav(data) {

	$.each(data, function(i, sm) {
		var menulist = "";
		menulist += '<ul>';
		$.each(sm.menus, function(j, o) {
			menulist += '<li><div><a ref="' + o.menuid + '" href="javascript:void(0)" rel="'
					+ syspath+"/"+o.url + '" ><span class="icon ' + o.icon
					+ '" >&nbsp;</span><span class="nav">' + o.menuname
					+ '</span></a></div></li> ';
		});
		menulist += '</ul>';

		$('#wnav').accordion('add', {
			title : sm.menuname,
			content : menulist,
			iconCls : 'icon ' + sm.icon
		});

	});

	var pp = $('#wnav').accordion('panels');
	var t = pp[0].panel('options').title;
	$('#wnav').accordion('select', t);

}

//初始化左侧
function InitLeftMenu() {
	$("#wnav").accordion({animate:false});


	$('.easyui-accordion li a').click(function(){
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var icon = $(this).children('span').attr("class");
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
		
		addTab(tabTitle,url,icon);

	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//选中第一个
	var panels = $('#wnav').accordion('panels');
	var t = panels[0].panel('options').title;
    $('#wnav').accordion('select', t);
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function addTab(subtitle,url,icon){
	if(subtitle.length>=10){
		subtitle=subtitle.substring(0,10)+"……";
	}
	autoClose(subtitle);
    
	if(!$('#tabs').tabs('exists',subtitle)){
		var fid=currentTime();
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame('about:blank',fid),
			closable:true,
			icon:icon
		});
		$("#"+fid).attr("src",url);
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function currentTime()
{
    var objD=new Date();
	var str;
	var dd = objD.getDate();
	var hh = objD.getHours();
	var mm = objD.getMinutes();
	var ss = objD.getSeconds();
	str =dd + "" + hh + "" + mm +  ""+ss;
	return (str);
}

function autoClose(currtab_title){
	$('#tabs').tabs('close',currtab_title);
}
function createFrame(url,fid)
{
	var s = '<iframe scrolling="auto" frameborder="0" id="'+fid+'" src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	});
	
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		var text= $(currTab.panel('options').content).text();
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		});
	});
	
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		if(currtab_title==''||currtab_title=='个人主页'){
			alert("主页不能关闭!");
			return false;
		}
		$('#tabs').tabs('close',currtab_title);
	})
	

	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t=='个人主页')return true;
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			return false;
		}
		prevall.each(function(i,n){
			if(prevall.length-1==i)return false;
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}
