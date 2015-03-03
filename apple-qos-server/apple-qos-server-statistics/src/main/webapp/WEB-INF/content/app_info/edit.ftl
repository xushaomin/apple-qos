<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>编辑应用</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			"code": {
				required: true,
				remote: "check_code?oldCode=${(info.code)!}"
			}
		},
		messages: {
			"code": {
				remote: "编码已存在"
			}
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
<form id="inputForm" method="post" action="update">
	<input type="hidden" name="id" value="${info.id}" />
	<input type="hidden" name="disorder" value="${info.disorder}" />
	
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="name require" class="tit">名称：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="name" value="${(info.name)!}" realValue="请输入名称" maxlength="200" />
               		</li>

                	<li class="clearfix">
                		<label for="code" class="tit">编码：<span class=" red">*</span></label>
                		<input readonly class="c_input_text required" type="text" style="width:200px;" name="code" value="${info.code}" realValue="请输入编码" maxlength="200" />
                	</li>

                	

					<li class="clearfix">
                		<label for="platform" class="tit">状态：<span class=" red">*</span></label>
               			<input type="radio" name="state" value="1" <#if info.state == 1> checked="checked" </#if> />启用
                		<input type="radio" name="state" value="0" <#if info.state == 0> checked="checked" </#if> />停用
                	</li>            
            
                	<li class="clearfix">
	                    <label for="remark" class="tit">描述：</label>
	                    <span class="textarea_show">
	                    	<textarea class="c_textarea wordCount" name="remark" cols="" id="remark" rows="" maxlength="100" showCount="remarkLen">${(info.remark)!}</textarea>
	                    	<span class="in_num_text" style="color:red;" id="remarkLen">0/100</span>
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