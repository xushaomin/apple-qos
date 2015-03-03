
$().ready( function() {
	
	var $body = null;
	var dialogIdNumber = 0;
	var dialogZIndex = 100;
	var messageIdNumber = 0;
	
	$.dialog = function (settings) {
		
		var dialogId;
		
		if (settings.id != null) {
			dialogId = settings.id;
		} else {
			dialogId = "dialog" + dialogIdNumber;
			dialogIdNumber ++;
		}
		if (settings.content == null) {
			settings.content = "";
		}
		if (settings.width == null || settings.width == "auto") {
			settings.width = 320;
		}
		if (settings.height == null) {
			settings.height = "auto";
		}
				
		if ($body == null) {
			$body = $("body");
		}
		
		var dialogHtml = "";
		
		if (settings.modal == true) {
			dialogHtml += '<div id="dialogOverlay' + dialogId + '" class="dialogOverlay"></div>';
		}
		
		if (settings.className != null) {
			dialogHtml += '<div id="' + dialogId + '" class="baseDialog ' + settings.className + '"><div class="dialogWrap"></div><div class="dialogMain">';
		} else {
			dialogHtml += '<div id="' + dialogId + '" class="baseDialog"><div class="dialogWrap"></div><div class="dialogMain">';
		}
		
		if (settings.title != null) {
			dialogHtml += '<div id="dialogTitle' + dialogId + '" class="dialogTitle">' + settings.title + '</div><div id="dialogClose' + dialogId + '" class="dialogClose"></div>';
		} else {
			dialogHtml += '<div id="dialogClose' + dialogId + '" class="dialogClose"></div>';
		}
		
		if (settings.type != null) {
			dialogHtml += '<div id="dialogContent' + dialogId + '" class="dialogContent dialog' + settings.type + 'Icon">' + settings.content + '</div>';
		} else {
			dialogHtml += '<div id="dialogContent' + dialogId + '" class="dialogContent">' + settings.content + '</div>';
		}
		
		if (settings.ok != null || settings.cancel != null || settings.process != null) {
			dialogHtml += '<div id="dialogButtonArea' + dialogId + '" class="dialogButtonArea">';
		}
		
		if (settings.ok != null) {
			dialogHtml += '<input type="button" id="dialogOk' + dialogId + '" class="formButton" value="' + settings.ok + '" hidefocus="true" />';
		}
		if (settings.process != null) {
			dialogHtml += '<input type="button" id="dialogProcess' + dialogId + '" class="formButton" value="' + settings.process + '" hidefocus="true" />';
		}
		if (settings.cancel != null) {
			dialogHtml += '<input type="button" id="dialogCancel' + dialogId + '" class="formButton" value="' + settings.cancel + '" hidefocus="true" />';
		}
		
		if (settings.ok != null || settings.cancel != null || settings.process != null) {
			dialogHtml += '</div>';
		}
		
		if(!window.XMLHttpRequest) {
			dialogHtml += '<iframe id="dialogIframe' + dialogId + '" class="dialogIframe"></iframe>';
		}
		
		dialogHtml += '</div></div>';
		
		$body.append(dialogHtml);
		
		var dialogX;
		var dialogY;
		
		var $dialogOverlay = $("#dialogOverlay" + dialogId);
		var $dialog = $("#" + dialogId);
		var $dialogTitle = $("#dialogTitle" + dialogId);
		var $dialogClose = $("#dialogClose" + dialogId);
		var $dialogOk = $("#dialogOk" + dialogId);
		var $dialogCancel = $("#dialogCancel" + dialogId);
		var $dialogProcess = $("#dialogProcess" + dialogId);
		
		
		
		//var topLeft = "";
		//if (settings.top != null) {
		//	topLeft += "top:" + settings.top;
		//}
		//if (settings.left != null) {
		//	topLeft += "left:" + settings.left;
		//}
		
		
		$dialog.css({"width": settings.width, "height": settings.height, "margin-left": - parseInt(settings.width / 2), "z-index": dialogZIndex ++, top:settings.top, left:settings.left});
		
		if(!window.XMLHttpRequest) {
			var $dialogIframe = $("#dialogIframe" + dialogId);
			$dialogIframe.css({"width": $dialog.width() + 20, "height": $dialog.height() + 20});
		}
		
		function dialogClose() {
			$dialogOverlay.remove();
			$dialog.remove();
		}
		
		if (settings.autoCloseTime != null) {
			setTimeout(dialogClose, settings.autoCloseTime);
		}
		
		$dialogClose.click( function() {
			if ($.isFunction(settings.cancelCallback)) {
				if (settings.cancelCallback.apply() != false) {
					dialogClose();
				}
			} else {
				dialogClose();
			}
		});
		
		$dialogOk.click( function() {
			if ($.isFunction(settings.okCallback)) {
				if (settings.okCallback.apply() != false) {
					dialogClose();
				}
			} else {
				dialogClose();
			}
		});
		
		$dialogCancel.click( function() {
			if ($.isFunction(settings.cancelCallback)) {
				if (settings.cancelCallback.apply() != false) {
					dialogClose();
				}
			} else {
				dialogClose();
			}
		});
		
		$dialogProcess.click( function() {			
			if ($.isFunction(settings.processCallback)) {
				
				if (settings.processCallback.apply() != false) {
					dialogClose();
				}
			} else {
				dialogClose();
			}
		});
		
		$dialogTitle.mousedown(function (event) {
			$dialog.css({"z-index": dialogZIndex ++});
			var offset = $(this).offset();
			if(!window.XMLHttpRequest) {
				dialogX = event.clientX - offset.left + 6;
				dialogY = event.clientY - offset.top + 6;
			} else {
				dialogX = event.pageX - offset.left + 6;
				dialogY = event.pageY - offset.top + 6;
			}
			
			$(document).bind("mousemove", function(event) {
				$dialog.css({"top": event.clientY - dialogY, "left": event.clientX - dialogX, "margin": 0});
			});
			return false;
		});
		
		$(document).mouseup(function() {
			$(document).unbind("mousemove");
		});
		
		$dialog.keypress(function(event) {
			if(event.keyCode == 13) {
				if ($.isFunction(settings.okCallback)) {
					if (settings.okCallback.apply() != false) {
						dialogClose();
					}
				} else {
					dialogClose();
				}
			}  
		});
		
		$dialogOverlay.show();
		$dialog.show();
		$dialog.focus();
		
		return dialogId;
	};
	
	$.closeDialog = function (dialogId) {
		var $dialogOverlay = $("#dialogOverlay" + dialogId);
		var $dialog = $("#" + dialogId);
		
		$dialogOverlay.remove();
		$dialog.remove();
	};
	
	$.message = function (settings) {
	
		if (settings.content == null) {
			settings.content = "";
		}
		
		if ($body == null) {
			$body = $("body");
		}
		
		var messageId = "message" + messageIdNumber;
		messageIdNumber ++;
		
		var messageHtml;
		
		if (settings.type != null) {
			messageHtml = '<div id="' + messageId + '" class="baseMessage"><div class="messageContent message' + settings.type + 'Icon">' + settings.content + '</div></div>';
		} else {
			messageHtml = '<div id="' + messageId + '" class="baseMessage"><div class="messageContent">' + settings.content + '</div></div>';
		}
		
		$body.append(messageHtml);
		
		var $message = $("#" + messageId);
		
		$message.css({"margin-left": "-" + parseInt($message.width() / 2) + "px"}).show();
		
		setTimeout(function() {
			$message.animate({left: 0, opacity: "hide"}, "slow", function() {
				$message.remove();
			});
		}, 2000);
		
		return messageId;
	};

});

function openAddFrame(name){
	art.dialog.open('add', {
		id: 'addFrame',
		title: name,
		close: function () {}
	}, false);
}

function openEditFrame(id, name){
	art.dialog.open('edit?id=' + id, {
		id: 'addFrame',
		title: name,
		close: function () {}
	}, false);
}