function move(cs) {
	var addflag = true;
	var fl = cs.wxcs.selectedIndex;
	var item = new Option(cs.wxcs.options[fl].text, cs.wxcs.options[fl].value);
	// 判断右列表是否已经存在所选信息
	for ( var x = 0; x < cs.sxcs.options.length; x++) {
		if (cs.sxcs.options[x].value == cs.wxcs.options[fl].value) {// 如果右列表中的值等于左列表的值
			addflag = false;
		}
	}
	// 添加未存在在右边列表的信息
	if (addflag) {
		cs.sxcs.options[cs.sxcs.options.length] = item;
	}
}

function moveAll(cs) {
	var addflag;
	var fl = cs.wxcs.selectedIndex;
	for ( var i = 0; i < cs.wxcs.options.length; i++) {// 循环左框所有值对象
		addflag = true;
		if (cs.wxcs.options[i].selected) {// 判断值是否被选中
		// 判断右列表是否已经存在所选信息
			for ( var x = 0; x < cs.sxcs.options.length; x++) {
				if (cs.sxcs.options[x].value == cs.wxcs.options[i].value) {// 如果右列表中的值等于左列表的值
					addflag = false;
				}
			}
			// 添加未存在在右边列表的信息
			if (addflag) {
				cs.sxcs.options[cs.sxcs.options.length] = new Option(
						cs.wxcs.options[i].text, cs.wxcs.options[i].value);
			}
		}
	}
}
function delAll(cs) {
	var addflag;
	var fl = cs.sxcs.selectedIndex;
	for ( var i = 0; i < cs.sxcs.options.length; i++) {// 循环左框所有值对象
		addflag = true;
		if (cs.sxcs.options[i].selected) {
			cs.sxcs.remove(i);
			i--;
		}
	}
}

function dele(cs) {
	var sp = cs.sxcs.selectedIndex;// 当前选择的位置编号
	//var so = cs.sxcs.options[sp];// 获得sp位置编号的值对象
	cs.sxcs.remove(sp);// 移除此框中的值对象
}
function check(frm) {
	if (frm.sxcs.length <= 0) {
		alert("请选择联系人!");
		return false;
	}
	var phones = "";
	for (x = 0; x < frm.sxcs.length; x++) {
		frm.sxcs.options[x].selected = "true";
		phones += frm.sxcs.options[x].value + ";";
	}
	$("#phone").val(phones);
}
