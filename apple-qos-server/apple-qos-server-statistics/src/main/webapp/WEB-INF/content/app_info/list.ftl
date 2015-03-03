<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>应用管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
<script language="javascript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	function callback() {
	 	$("#searchButton").click();
	}
</script>


</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="listForm" action="list" method="post">
	
    <!-- start of con_search -->
	<div class="con_search">
    	<span class="con_box_BL"></span>
        
        <!-- start of con_search_top -->
        <div class="con_search_top clearfix">
        	<div class="con_search_top_L left">
                <p>
                    <span class="group"><label>关键字：</label>
                    	<input name="keyword" class="c_input_text" type="text" realValue="调用方" value="${(keyword)!''}" />
                    </span>
            		<span class="group"><a id="searchButton" href="javascript:;" class="btn_search">搜索</a></span>

                </p>
            </div>
        </div>
        <!-- end of con_search_top -->
        
        <span class="con_box_BR"></span>
    </div>
    <!-- end of con_search -->
    
	<div class="c_tipsA">截止到今日：已记录统计 <span class="red">${list?size}</span> 个 </div>
    
    
    <!-- start of table_list -->
    <table id="listTable" class="table_list list">
        <tr>
        	<th width="5%">序号</th>
        	<th width="12%">编码</th>
        	<th width="12%">名称</th>
        	<th width="6%" class="sort" orderField="asc" name="totalSumNumber">排序</th>
        	<th width="6%" class="sort" orderField="asc" name="totalSumNumber">状态</th>
        	<th width="8%">创建时间</th>			
			<th width="15%">操作</th>
        </tr>
        <#list list as info>
        <tr class="even">
        	<td><!--<input type="checkbox" name="ids" value="${info.id}" />-->${info.id}</td>
        	
			<td>${(info.code)!}</td>
			<td>${(info.name)!}</td>
			<td>${(info.disorder)!}</td>
			<td>${(info.state)!}</td>
			<td>${info.createTime?string('yyyy-MM-dd')}</td>
			
			<td>
				<a class="btn_icon btn_edit"   href="javascript:;" operatId="${info.id}" title="编辑"></a>
                <a class="btn_icon btn_detail" href="javascript:;" operatId="${info.id}" title="详情"></a>

			</td>
        </tr>
        </#list>
        
        
    </table>
    <!-- end of table_list -->			
	
	<#if (list?size > 0)>
    
    	<!-- start of table_bottom -->
	    <div class="table_bottom clearfix">
	    	<!--
	    	<div class="table_bottom_checkbox left">
	    		<input id="selectAll" name="" type="checkbox" value=""><a id="deleteAll" class="btn" href="javascript:void(0);">删除选中</a>
	    	</div>
	    	-->
	        
	         <!-- start of 分页 -->
	   		<@paging pageNumber = page.pageNo totalPages = page.totalPage>
				<#include "../commons/pager.ftl">
			</@paging>
	        <!-- end of 分页 -->
	    </div>
	    <!-- end of table_bottom -->
			
	<#else>
		<div class="noRecord">没有找到任何记录!</div>
	</#if>
	
    </form>
</div>
<!-- end of con_right_main -->
</body>
</html>