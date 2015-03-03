<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>服务排名</title>

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

	<form id="listForm" action="rank" method="post">
	
    <!-- start of con_search -->
	<div class="con_search">
    	<span class="con_box_BL"></span>
        
        <!-- start of con_search_top -->
        <div class="con_search_top clearfix">
        	<div class="con_search_top_L left">
                <p>
                    <span class="group"><label>调用方：</label>
                    	<input name="consumerAppName" class="c_input_text" type="text" realValue="调用方" value="${(consumerAppName)!''}" />
                    </span>
                    <span class="group"><label>服务方：</label>
                    	<input name="providerAppName" class="c_input_text" type="text" realValue="服务方" value="${(providerAppName)!''}" />
                    </span>
                </p>
                <p>
                    <span class="group"><label>日期：</label>
                    	<input name="startDate" class="c_input_text" type="text" realValue="日期" onFocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" value="${(startDate)!''}" />
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
        	<th width="5%" >序号</th>
        	<th width="12%" orderField="longitude">调用方</th>
        	<th width="12%" orderField="longitude">服务方</th>
        	
        	<th width="8%" orderField="longitude">发生时间</th>
        	
        	<th width="6%" orderField="longitude">总次数</th>
        	<th width="6%" orderField="longitude">失败次数</th>
        	<th width="6%" orderField="longitude">成功率%</th>
        	
			<th width="6%" orderField="longitude">总平均<br />耗时</th>
			<th width="6%" orderField="longitude">成功<br />均耗时</th>
			<th width="6%" orderField="longitude">失败<br />均耗时</th>
			<th width="6%" orderField="longitude">成功<br />最大耗时</th>
			
			<th width="15%" orderField="radius">操作</th>
        </tr>
        <#list list as info>
        <tr class="even">
        	<td><!--<input type="checkbox" name="ids" value="${info.id}" />-->${info.id}</td>
        	
        	<td>${(info.consumerAppName)!}<a class="btn_icon btn_view" href="javascript:;" operatId="${info.consumerAppId}" title="详情"></a></td>
			<td>${(info.providerAppName)!}<a class="btn_icon btn_view" href="javascript:;" operatId="${info.providerAppId}" title="详情"></a></td>
			
			<td>${info.statDate?string('yyyy-MM-dd')}</td>
			
			<td>${(info.totalSumNumber)!}</td>
			<td>${(info.failSumNumber)!}</td>
			<td>
				<#if (info.totalSuccPer < 0.8)>
					<font color="red">${(info.totalSuccPer * 100)!}%</front>
				<#else>
					${(info.totalSuccPer * 100)!}%
				</#if>
			</td>
			
			<td>${(info.totalAvgElapsed)!}</td>
			<td>${(info.succAvgElapsed)!}</td>
			<td>${(info.failAvgElapsed)!}</td>
			<td>${(info.succMaxElapsed)!}</td>
			
			<td>
				<a class="" href="/min_stat/list?consumerAppName=${(info.consumerAppName)!}&providerAppName=${(info.providerAppName)!}&statDate=${info.statDate?string('yyyy-MM-dd')}" title="明细">明细</a>|
				<a class="" href="/day_stat_code/list?consumerAppName=${(info.consumerAppName)!}&providerAppName=${(info.providerAppName)!}&startDate=${info.statDate?string('yyyy-MM-dd')}&endDate=${info.statDate?string('yyyy-MM-dd')}" title="返回码">返回码</a>|
				<a class="" href="/day_stat_node/list?consumerAppName=${(info.consumerAppName)!}&providerAppName=${(info.providerAppName)!}&startDate=${info.statDate?string('yyyy-MM-dd')}&endDate=${info.statDate?string('yyyy-MM-dd')}" title="节点">节点</a>|
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