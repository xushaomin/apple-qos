var swfu;
window.onload = function() {
	swfu = new SWFUpload({

		// Backend Settings
		upload_url : upload_url,
		post_params : {
			"systemSign" : systemSign,
			"filePath" : filePath,
			"param1" : param1
		},

		//启动，用来使用querystring 传递参数，否则无法进行参数传递
		use_query_string : true,

		// File Upload Settings
		//file_size_limit: "2 MB",
		//file_types: "*.jpg",
		//file_types_description: "JPG Images",
		file_upload_limit : "0", // Zero means unlimited
		file_post_name : "filedata",

		// Event Handler Settings - these functions as defined in Handlers.js
		//  The handlers are not part of SWFUpload but are part of my website and control how
		//  my website reacts to the SWFUpload events.
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		//upload_success_handler: uploadSuccess,
		upload_complete_handler : uploadComplete,

		upload_success_handler : function(file, serverData) {
			var serverDataJson = eval("(" + serverData + ")");
			$("#" + param1).val(serverDataJson.url);
			$("#fsUploadProgress").empty();

			pop_succeed("上传提示", "上传成功", function() {
			}, false);
		},

		// Button settings

		button_placeholder_id : "spanButtonPlaceHolder",
		button_width : 93, //按钮宽度
		button_height : 33, //按钮高度
		button_text : '选择上传文件',//按钮文字
		button_text_style : '',//按钮文字样式
		button_text_top_padding : 5,//文字距按钮顶部距离
		button_text_left_padding : 5,//文字距离按钮左边距离
		button_image_url : "/images/btn_upload_add_swf2.png",//按钮背景

		// Flash Settings
		flash_url : "/js/swfupload/Flash/swfupload.swf", // Relative to this file

		custom_settings : {
			//upload_target: "divFileProgressContainer"

			progressTarget : "fsUploadProgress",
			uploadButtonId : "btnUpload",
			cancelButtonId : "btnCancel"
		},

		// Debug Settings
		debug : false
	});
};
