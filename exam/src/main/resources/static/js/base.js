	
(function() {

    $(function() {
		//初始化菜单
		$("#home").addClass("on");
			$("#m1").removeClass("hide");
           $("#m1").addClass("show");
		startmenu=function(){
			$(".bs-docs-sidebar li").each(function(){
			if($(this).hasClass("active")){
			$(this).removeClass("active");
			
			}
		
		});
				
		$(".bs-docs-sidebar li:first-child").each(function(){
		$(this).addClass("active");
		
		});
				
		
		};
		
	
			var sidebar=$(".bs-docs-sidebar li").each(function(){
		
		$(this).click(function(){
			$(".bs-docs-sidebar li").each(function(){
			if($(this).hasClass("active")){
			$(this).removeClass("active");
			
			}
			
			});
		$(this).addClass("active");
		});
		
		
		});
		var menu=$(".header_menu_nav a").each(function(){

			$(this).click(function(){
				startmenu();
				$(".bs-docs-sidebar").each(function(){
					if(!$(this).hasClass("hide")){
				$(this).addClass("hide");
				}

				});
		$(".header_menu_nav a").each(function(){
		
		if($(this).hasClass("on")){
			$(this).removeClass("on");
			
			}
		});
		var atr=$(this).attr("attr");
		v=$("#"+atr);
		$("#"+atr).removeClass("hide")
		$("#"+atr).addClass("show");
		
		$(this).addClass("on");
		});
		});

       

         
    
    });
   
})()