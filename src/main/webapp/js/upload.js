var cbasePath="";
function ininUpload(basePath){
cbasePath=basePath;
$('#fileInput2').uploadify({
    'uploader': basePath+'/js/uploadify/uploadify.swf?n='+Math.random(),
	'script': basePath+'/file/upload?jsessionid=${session.id}',
	'folder': 'uploads',
	'cancelImg': basePath+'/js/uploadify/cancel.png',
	'fileDataName': 'fileInput2',
    'queueID': 'fileQueue2',
    'removeCompleted' : true,
    'auto': true,
    'multi': true,
    'simUploadLimit' : 1,
    'buttonText': '选择附件',
    'fileExt' : '*.ppt;*.pptx;*.doc;*.txt;*.docx;*.wps;*.xls;*.xlsx;*.jpg;*.gif;*.png;*.zip;*.rar;',
	'fileDesc' : '文件 (.DOC .DOCX .TEXT .WPS .XLS .XLSX .PPT)',
    'displayData': 'percentage',//有speed和percentage两种，一个显示速度，一个显示完成百分比
    'onComplete': function (event, queueID, fileObj, restext, data){
    	var jsondata=eval("("+restext+")");
    	var response=jsondata.fileid;
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

	$.post(cbasePath+"/file/deleteFile",{"fileid":id},function(data){
					var wid = $("#files").val();
					wid = wid.replace(id,'');
					wid = wid.replace(';;',';');
					$("#files").val(wid);
					 var ob = document.getElementById(id);   
				     ob.parentNode.removeChild(ob);
	},"text");

}