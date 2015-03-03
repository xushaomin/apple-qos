/*
 *  time 2013.06.29
 */ 
 
// 图层居中方法
function setCenter(promptComBar){
	offX = ($(window).width()-$("."+ promptComBar).width())/2;
	offY = ($(window).height()-$("."+ promptComBar).height())/2 + $(window).scrollTop();
	$("."+ promptComBar).css({
		"left":offX + "px",
		"top":offY + "px"
	});
}

// 显示蒙板方法
function showMask(){
	maskCon = '<div class="promptMask"></div>';
	$(maskCon).appendTo($("body"));
	$(".promptMask").css({
		"background":"#000",
		"width":"100%",
		"position":"absolute",
		"z-index":"9999",
		"top":"0",
		"left":"0",
		"display":"block",
		"height":$(document).height(),
		"opacity":"0.2"
	});
}

// 不用显示蒙板时调用
function showBar(promptComBar){
	$("."+ promptComBar).show();
	setCenter(promptComBar);
}


// 同时显示蒙板时调用
function showAndMask(promptComBar){
	$("."+ promptComBar).show();
	setCenter(promptComBar);
	showMask();
}

// 没有蒙板时调用关闭
function closeBar(promptComBar){
	$("."+ promptComBar).hide();
}

// 有蒙板时调用关闭
function closeAndMask(promptComBar){
	$("."+ promptComBar).hide();
	$(".promptMask").remove();
}