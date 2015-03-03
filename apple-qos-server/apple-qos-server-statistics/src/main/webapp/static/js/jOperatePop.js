var jOperatePop = function() {};
jOperatePop.prototype = {
    initParam: {
	    isCheckbox: false,
		isModel: false,
		isSong: false,
		resultBox1: '#j-result1',
		resultBox2: '#j-result2',
		resultBox3: '#j-result3',
		fatherPop: '.j-special-pop',
		childPop: '.pop_information',
		handlerBtn: '.j-set',
		fatherPopId: 'aa'
	},
	init: function() {
		var curOpera = this;
		var resultBox1 = curOpera['initParam']['resultBox1'];
		var resultBox2 = curOpera['initParam']['resultBox2'];
		var resultBox3 = curOpera['initParam']['resultBox3'];
		var fatherPop = curOpera['initParam']['fatherPop'];
		var handlerBtn = curOpera['initParam']['handlerBtn'];
		var fatherPopId = curOpera['initParam']['fatherPopId'];
		
		//设置
		showAndMask(fatherPop.substr(1));
		$(fatherPop).attr('id', fatherPopId); 
		
		//初始所有表单
		if($.trim($(resultBox1).html()) == '' ) {
		    $('#' + fatherPopId + ' .j-hadchecked input[type="checkbox"]').prop('checked', false);
			$('#' + fatherPopId + ' .j-hadchecked input[type="text"]').val($('#' + fatherPopId + ' .j-hadchecked input[type="text"]').attr('title'))
			                                                          .addClass('auto_hint');
		}
		if($.trim($(resultBox2).html()) == '' ) {
		    $('#' + fatherPopId + ' .j-modle-box').html('');
		}
		if($.trim($(resultBox3).html()) == '' ) {
		    $('#' + fatherPopId + ' .j-song-box').html('');
		}
		curOpera.checkboxCheck();
		curOpera.addModel();
		curOpera.uploadSong();
		curOpera.submitSet();
		
		
	},
	
	//设置
	handlerClick: function(handler, callback) {
	    var curOpera = this;
		$(handler).unbind('click').bind('click', function() {
			curOpera.init();
			
			if(callback) callback();
	    });
	},
	
	callback: function() {},
	
	//checkbox
	checkboxCheck: function() {
	    var curOpera = this;
		var fatherPop = '#'+$(curOpera['initParam']['fatherPop']).attr('id');
		$(fatherPop + ' .j-hadchecked').undelegate('input[type="checkbox"]', 'click').delegate('input[type="checkbox"]', 'click', function() {
		    if($(fatherPop + ' .j-hadchecked input[type="checkbox"]:checked').length > 2) {
			     alert('最多选择二个选项！');
				 return false;
			}
		});   
	},
	
	//添加模板
	addModel: function() {
		var curOpera = this;
		var fatherPop = '#'+$(curOpera['initParam']['fatherPop']).attr('id');
		var childPop = curOpera['initParam']['childPop'];
		$(fatherPop + ' .j-add-modle').unbind('click').bind('click', function() {
			showAndMask(childPop.substr(1));
			var modelName = $(childPop + ' .j-modle-name');
			var modelCont = $(childPop + ' .j-modle-cont');
			modelName.val('');
			modelCont.val('');
			//提交, '.j-modle-cont'为模板内容，'.j-modle-name'为模板名称
			$(childPop + ' .j-submit-btn').unbind('click').bind('click', function() {
				if($.trim(modelName.val()) != '' && $.trim(modelCont.val()) != '') {
					closeBar(childPop.substr(1));
					$(fatherPop + ' .j-modle-box').append('<span title="'+ modelCont.val() +'">'
											 + '<em class="j-modle-span">' + modelName.val() + '</em>'
											 + '<a class="j-del-model" href="javascript:void(0);">X</a>'
											 + '</span>');
				} else {
					alert('输入框不能为空！');
				}			
				
				//提示
				$('.c_tags span').tooltip({
					position: {
						my: "center bottom-20",
						at: "center top",
						using: function( position, feedback ) {
							$( this ).css( position );
							$( "<div>" )
								.addClass( "arrow" )
								.addClass( feedback.vertical )
								.addClass( feedback.horizontal )
								.appendTo( this );
							$('.arrow').parent().css({'z-index': '99999'});
						}
					}
				});
				//删除模板
				$(fatherPop + ' .j-modle-box').undelegate('.j-del-model', 'click').delegate('.j-del-model', 'click', function() {
					$(this).parent().remove();
				});
			});
		});
	},
	//上传声音
	uploadSong: function() {
		var curOpera = this;
		var fatherPop = '#'+$(curOpera['initParam']['fatherPop']).attr('id');
		$(fatherPop + ' .j-song-upload').unbind('change').bind('change', function() {
			var filePath = $(this).val().split('\\');
			$(fatherPop + ' .j-song-box').append('<span>'
									+ '<em class="j-song">' + filePath[2] + '</em>'
									+ '<a class="j-liste-test" href="javascript:void(0);">试听</a>'
									+ '<a class="j-del-song" href="javascript:void(0);">删除</a>'
									+ '</span>');
			//删除模板
			$(fatherPop + ' .j-song-box').undelegate('.j-del-song', 'click').delegate('.j-del-song', 'click', function() {
				$(this).parent().remove();
			});
		});
	},
	
	//提交设置
	submitSet: function() {
		var curOpera = this;
		var resultBox1 = curOpera['initParam']['resultBox1'];
		var resultBox2 = curOpera['initParam']['resultBox2'];
		var resultBox3 = curOpera['initParam']['resultBox3'];
		var fatherPop = '#'+$(curOpera['initParam']['fatherPop']).attr('id');
		
		$(fatherPop + ' .j-submit-btn').unbind('click').bind('click', function() {
			//closeAndMask(fatherPop.substr(1));
			$(fatherPop).hide();
			$('.promptMask').hide();
			//result1
			var htmlArr = [];  
			var textArr = [];
			//'.j-hadchecked'是所有checkbox选项的父级容器
			$(fatherPop + ' .j-hadchecked input[type="checkbox"]').each(function() {
				if($(this).prop('checked')) {
					htmlArr.push('<p>·'+$(this).parent().html()+'</p>');
					if($(this).parent().find('input[type="text"]').val() != $(this).parent().find('input[type="text"]').attr('title')) {
						textArr.push($(this).parent().find('input[type="text"]').val());
					}
				}
			});
			$(resultBox1).html('').append(htmlArr);
			$(resultBox1).find('input[type="checkbox"]').remove();
			$(resultBox1).find('input[type="text"]').css({'border': 'none', 'box-shadow': 'none'}).attr('title', '');
			$.each(textArr, function(index) {
				$(resultBox1).find('input[type="text"]:eq('+index+')').val(textArr[index]);
			});
			
			//result2
			var modleArr = [];
			$(fatherPop + ' .j-modle-box .j-modle-span').each(function() {
				modleArr.push($(this)[0].outerHTML);
			});
			$(resultBox2).html('').append(modleArr);
			
			//result3
			var songArr = [];
			$(fatherPop + ' .j-song-box .j-song').each(function() {
				songArr.push($(this)[0].outerHTML);
			});
			$(resultBox3).html('').append(songArr);
			
			$(fatherPop).attr('id', '');
		});
	}
};