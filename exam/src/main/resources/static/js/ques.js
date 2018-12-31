/**
 * 
 */
var Wuestions=new Array();
//$(function () {
	var data=$.get("DoPaper",function(data){
//		console.log(data)
		//问题集合
		alert(data);
		Wuestions=data
		alert(Wuestions);
		alert(questions);
	})
		
//	});