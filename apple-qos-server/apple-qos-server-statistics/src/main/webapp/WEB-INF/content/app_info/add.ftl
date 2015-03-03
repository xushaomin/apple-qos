<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>添加软件</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			
		},
		messages: {
			
		},
		submitHandler:function(form){
            form.submit();
        }
	});
	
	$("#btn_pop_submitA").click(function(){
 		$inputForm.submit();
	});
	
	
});
</script>

</head>

<body>
<form id="inputForm" method="post" action="save">
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="appName require" class="tit">软件名称：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="appName" id="appName" realValue="请输入系统平台名称" maxlength="32" />
               		</li>

                	<li class="clearfix">
                		<label for="appCode" class="tit">软件编码：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="appCode" id="appCode" realValue="请输入系统编码" maxlength="32" />
                	</li>

                	<li class="clearfix">
                		<label for="platform" class="tit">所属平台：<span class=" red">*</span></label>
                		<select class="c_select required" id="platform" name="platform" style="width:215px;">
                     		<option value="">请选择</option>
                     		<option value="1">WinCE车机平台</option>
                     		<option value="2">Android车机平台</option>
                     		<option value="3">Android移动平台</option>
                     		<option value="4">Windows桌面平台</option>
                 		</select>
                	</li>
                	
                	<li class="clearfix">
                		<label for="platform" class="tit">所属应用：<span class=" red">*</span></label>
                		<select class="c_select" id="groupId" name="groupId" style="width:215px;">
                     		<option value="">请选择</option>
                     		<#list GROUP_LIST as group>
                     		<option value="${group.groupId}">${group.groupCode}-${group.groupName}</option>
                     		</#list>
                 		</select>
                	</li>
                	
                	<li class="clearfix">
                		<label for="state" class="tit">状态：<span class=" red">*</span></label>
               			<input type="radio" name="state" value="1" />启用
                		<input type="radio" name="state" value="0" checked="checked" />停用
                	</li>

                	<li class="clearfix">
                    	<label for="appDesc" class="tit">软件描述：</label>
                    	<span class="textarea_show">
                    		<textarea class="c_textarea wordCount" name="appDesc" cols="" id="appDesc" rows="" maxlength="100" showCount="appDescLen"></textarea>
                    		<span class="in_num_text" style="color:red;"  id="appDescLen">0/100</span>
                    		<span class="in_num_text" >/100</span>
                    	</span>
                	</li>

            </ul>

        </div>

    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitA" class="btn_pop_submitA" href="javascript:void(0)">确定</a>
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>


</form>


</body>
</html>