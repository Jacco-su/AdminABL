var cbasePath="";
function ininUpload(basePath){
cbasePath=basePath;
$('#fileInput2').uploadify({
    'uploader': basePath+'/js/uploadify/uploadify.swf?n='+Math.random(),
	'script': basePath+'/panfile/panupload?jsessionid=${session.id}',
	'folder': 'uploads',
	'cancelImg': basePath+'/js/uploadify/cancel.png',
	'fileDataName': 'fileInput2',
    'queueID': 'fileQueue2',
    'removeCompleted' : true,
    'auto': true,
    'multi': true,
    'simUploadLimit' : 1,
    'buttonText': '选择附件',
    'fileExt' : '*.ppt;*.pptx;*.doc;*.txt;*.docx;*.wps;*.xls;*.xlsx;*.jpg;*.gif;*.png;*.zip;*.rar;*.avi;*.rm;*.rmvb;*.wmv;*.vob;*.mov;*.mp4;*.3gp;*.flash;*.mid;*.mp3;*.cda;*.midi;*.wav;*.wma;',
	'fileDesc' : '文档，图片，压缩文件，音乐，视频',
    'displayData': 'percentage',//有speed和percentage两种，一个显示速度，一个显示完成百分比
    'onComplete': function (event, queueID, fileObj, restext, data){
    	var jsondata=eval("("+restext+")");
    	var response=jsondata.id;
    	var files = $("#files").val();
    	if(files==''){
    		files +=response;
    	}else{ 
    		files += ";"+response;
    	}
    	files=files.replace(';;',';');
    	$("#files").val(files);
    	var lfiles = $("#lfiles").html();
    	lfiles += "<div id='"+response+"' class='outdiv'><a class='adelete' onclick=\"delFiles('"+response+"')\">删除</a><img src='"+basePath+"/images/icons/"+jsondata.suffix+".png' width=18 height=18/>"+jsondata.realName+"  "+jsondata.sizedesc+"</div>";
    	$("#lfiles").html(lfiles);
      },
    'onCancel': function(event,ID,fileObj,data) {
	}
});
}
function delFiles(id){

	$.post(cbasePath+"/panfile/deleteFile",{"id":id},function(data){
					var wid = $("#files").val();
					wid = wid.replace(id,'');
					wid = wid.replace(';;',';');
					$("#files").val(wid);
					 var ob = document.getElementById(id);
				     ob.parentNode.removeChild(ob);
	},"text");

}