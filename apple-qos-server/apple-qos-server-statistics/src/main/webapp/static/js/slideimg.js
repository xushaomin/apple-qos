/*
 * time 2011.02.23
 *
 */

(function($){
	//扩展这个方法到jquery;
	$.fn.extend({   
		//将可选择的变量传递给方法
		slideimg: function(options) {  
			
			//参数和默认值
			var defaults = {
			   tol_class:'.goleftbtn',       //获得点击左按钮的class
			   tor_class:'.gorightbtn',      //获得点击右按钮的class
			   slide_class:'.slide_tolr',    //获得滑动层的class
			   center_class:'.star',         //获得中间点的class
			   imgshow_num:'1',      //最多可见图片的张数；
			   cen_dot_bar:'ture',   //当cen_dot_bar为'ture'时中间显示，否则关闭；
			   centerbar_class:'.center_dot',              //中间点所在层的class
			   runtime:'3000',      //自动运行时间
			   gostart:'gostart',	//自动运行
			   autoClose:'ture'   //设置自动运行，默认为不运行,当需要运行时，刚设置为false;
			}; 
			
			options = $.extend(defaults, options); 
			var count = 1;
			
			//遍历匹配元素的集合
			return this.each(function() {
			var o =options;
            
            //获得最外层的ID; 
			var thisid = $(this).attr("id");
			//转化：去除中间点的class的'.'
			var cenclass =o.center_class.substr('1');
			//获得中间点击点处的class
            var centerBar = $($(this).find(o.centerbar_class),$(this));
			var gostart = $($(this).find(o.gostart),$(this));
			var slidebar = $($(this).find(o.slide_class),$(this));
			//最多滑动的次数（Math.ceil()方法向上取整）
			o.maxnum = Math.ceil($("#" + thisid + " " +  o.slide_class).children().size()/o.imgshow_num);
			domemaxnum = Math.ceil(o.maxnum/o.imgshow_num);
			//设置可见层的宽
			o.slide_wid = slidebar.parent().width();
			//设置滑动层的宽；
			slidebar.css("width",o.slide_wid*o.maxnum+"px");
			
			//添加中间处的点击点
			
			if(o.cen_dot_bar == 'ture'){
				for( var i=1;i<=o.maxnum;i++){
					$(centerBar).append(
						'<span name=" '+ (i-1) +' " class=" ' + cenclass+  '">&nbsp;</span>'
					);
				}
			};
			
			var goleftbtn = $($(this).find(o.tol_class),$(this));
			var gorightbtn = $($(this).find(o.tor_class),$(this));
			var clickCenter = $($(this).find(o.center_class),$(this));
            
			//点击向左按钮时；
			$(goleftbtn).click(function(){
				if(count > 1){
				  count--;
				  com_cen_src();
				}else{
				  count = o.maxnum;
				  com_cen_src();
				}
			});
			 
			//点击向右按钮时；
			$(gorightbtn).click(function(){
				clickRight();
			});
			
			function clickRight(){
			    if( count < o.maxnum){
				  count++;
				  com_cen_src();	  
				}else{
				  slidebar.animate({left:'0'},{queue:false});
				  count = 1;
				  com_cen_src();
				}
			}
            
			//点击中间点时
			$(clickCenter).live("click",function(){     
				   count = $(this).attr("name");
				   count++;
				   com_cen_src();
			});
			
			//调置中间点击处的透明度
			$("#" + thisid + " " + o.center_class).eq(count-1).addClass("focus").siblings().removeClass("focus");

			$(o.tol_class+" img" +","+o.tor_class+" img").css("opacity","0.8");
			$(o.tol_class+" img" +","+o.tor_class+" img").hover(function(){
				    $(this).css("opacity","1");
				},function(){
				    $(this).css("opacity","0.8");
			});
			
			//自动运行测试start			
			$("#" + thisid).hover(function(){
				 go_stop();
				},function(){
				 go_start();
			});
			
			go_start();
			
			function go_start(){
			   if(o.autoClose === 'false'){
			       gostart = setInterval(clickRight,o.runtime);
			   }
			};
			function go_stop(){
				clearInterval(gostart);
			};
			
			//自动运行测试end
			

			function com_cen_src(){
				slidebar.animate({left:'-' + o.slide_wid*(count-1)},{queue:false});
			    $("#" + thisid + " " + o.center_class).eq(count-1).addClass("focus").siblings().removeClass("focus");
			}
		
		    });  
		}  
	});  
})(jQuery);