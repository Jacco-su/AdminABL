

var doub_reg=/^\d+(\.\d*)?$/;
$.extend($.fn.validatebox.defaults.rules, {
		doub_check: {  
	        validator: function(value){
	              if(doub_reg.test(value)==false){
	                       return false;
	              }
	              if(parseFloat(value)<0){
	                       return false;
	              }
	              return true;
	        },  
	        message: '应该为大于等于0的实数'
	    },
		bl_check: { 
	        validator: function(value){
	              if(doub_reg.test(value)==false){
	                       return false;
	              }
	              if(parseFloat(value)<=0){
	                       return false;
	              }
	              if(parseFloat(value)>1){
	                  return false;
	              }
	              return true;
	        },  
	        message: '返退比例应在0到1之间，如0.15'
	    }	    
});