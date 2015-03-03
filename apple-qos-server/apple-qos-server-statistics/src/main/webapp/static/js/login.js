/**
  *  write by ftt
  *  time 2013.08.22
  */

$(window).load(function(){
	$('.login_warp').css({"height": $(document).height() + "px"});
	if( $(window).height() < 768 ){
		$('.control_bg').css({"height": "668px"});
	}else{
		$('.control_bg').css({"height": $(document).height() - 100 + "px"});
	}
	
	// input激活
	$('.in_text').focus(function(){
		$(this).addClass('focus');
		if($(this).val() ==this.defaultValue){  
			$(this).val('');           
		} 
	}).blur(function(){
		$(this).removeClass('focus');
		if ($(this).val() == '') {
			$(this).val(this.defaultValue);
		}
	});
});