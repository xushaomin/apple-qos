/**
  *  write by ftt
  *  time 2013.08.19
  */

$(function(){
	// 左侧高度
	$(".con_left").css({"height": window.screen.availHeight - 116 + "px"});
	$(".slideFrame_btn").css({"top": (document.body.offsetHeight / 2) - 102 + "px"});
	
	// 左侧二级导航显示隐藏
	$(".sub_nav > li > a").toggle(function(){
		$(this).parent().addClass("cur");
		$(this).parent().find("ul_sub_nav_down_menu").hide(300);
	},function(){
		$(this).parent().removeClass("cur");
		$(this).parent().find("ul_sub_nav_down_menu").show(300);
	});
	$(".sub_nav > li > a").eq(0).click();
	
	// 左侧二级导航点击
	$(".ul_sub_nav_down_menu li a").click(function(){
		$(this).closest(".sub_nav").find("a").removeClass("focus");
		$(this).addClass("focus");
	});
	$(".ul_sub_nav_down_menu li a").eq(0).click();
	
	// 右侧搜索区显示隐藏
	$(".btn_box_tab").toggle(function(){
		$(window.parent.rightMainFrame.document).find(".con_search").slideUp(200);
	},function(){
		$(window.parent.rightMainFrame.document).find(".con_search").slideDown(200);
	});
	
	// input激活
	$('.auto_hint').bind("focus", function(){
          if($(this).val() == $(this).attr('realValue')) {
               $(this).val('');
               $(this).removeClass('auto_hint');
          }
     });
	 $('.auto_hint').bind("blur", function(){
          if($(this).val() == '' &&$(this).attr('realValue') != '') {
               $(this).val($(this).attr('realValue'));
               $(this).addClass('auto_hint');
          }
     });
     $('.auto_hint').each(function() {
          if($(this).attr('realValue') == '') {return;}
          if($(this).val() == '') {
               $(this).val($(this).attr('realValue'));
          }else {
               $(this).removeClass('auto_hint');
          }
     });
	
	// table折叠展开
	$(".unfold_ico_down").click(function(){
		var tr = $(this).closest("tr");
		if(tr.hasClass("alreadyHide")) {
			$(this).attr("class","unfold_ico_down");
			$("tr[data="+tr.attr("data")+"][class!='tr']").show();
			tr.removeClass("alreadyHide");
		} else {
			$(this).attr("class","unfold_ico_up");
			$("tr[data="+tr.attr("data")+"][class!='tr']").hide();
			tr.addClass("alreadyHide");
		}		
	});
	$(".unfold_ico_down").click();

	// table鼠标经过
	$(".table_list td").bind("hover",function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});
	
	// 删除标签 / 删除图片
	$(".c_tags span a, .c_picShow span a").bind("click", function(){
		$(this).parents("span").remove();
	});
	
	// 删除地区
	$(".pop_area_list dd").last().addClass("noBorder");
	$(".pop_area_list dd a").bind("click", function(){
		$(this).parents("dd").remove();
		$(".pop_area_list dd").last().addClass("noBorder");
	});
	
	// 文字颜色选择
	$(".colorText").bind("click", function(){
		$(this).addClass("colorTextFocus").siblings().removeClass("colorTextFocus");
	});
	
	// 商户详情-编辑图片
	$(".merchant_pic").hover(function(){
		$(".merchant_pic").find(".btn").show();
	},function(){
		$(".merchant_pic").find(".btn").hide();
	});
});

// 点击关闭左侧栏
function switchBar(obj){
	if(parent.document.getElementById("framesetContains").cols == "210,*"){
		parent.document.getElementById("framesetContains").cols="10,*";
		$(".con_left").css({"margin-left":"10px"});
	}else{
		parent.document.getElementById('framesetContains').cols = "210,*";
		$(".con_left").css({"margin-left":"0"});
	}
}