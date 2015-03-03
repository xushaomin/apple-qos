/**
  *  write by ftt
  *  time 2013.08.23
  */

var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};
var zNodes =[
	{ name:"父节点1 - 展开", open:true,
		children: [
			{ name:"父节点11 - 折叠",
				children: [
					{ name:"叶子节点111"},
					{ name:"叶子节点112"},
					{ name:"叶子节点113"},
					{ name:"叶子节点114"}
				]},
			{ name:"父节点12 - 折叠",
				children: [
					{ name:"叶子节点121"},
					{ name:"叶子节点122"},
					{ name:"叶子节点123"},
					{ name:"叶子节点124"}
				]},
			{ name:"父节点13 - 没有子节点", isParent:true}
		]},
	{ name:"父节点2 - 折叠",
		children: [
			{ name:"父节点21 - 展开", open:true,
				children: [
					{ name:"叶子节点211"},
					{ name:"叶子节点212"},
					{ name:"叶子节点213"},
					{ name:"叶子节点214"}
				]},
			{ name:"父节点22 - 折叠",
				children: [
					{ name:"叶子节点221",
						children: [
							{ name:"叶子节点311"},
							{ name:"叶子节点312"},
							{ name:"叶子节点313"},
							{ name:"叶子节点314"}
						]},
					{ name:"叶子节点222"},
					{ name:"叶子节点223"},
					{ name:"叶子节点224"}
				]},
			{ name:"父节点23 - 折叠",
				children: [
					{ name:"叶子节点231"},
					{ name:"叶子节点232"},
					{ name:"叶子节点233"},
					{ name:"叶子节点234"}
				]}
		]},
	{ name:"父节点3 - 没有子节点", isParent:true}

];

var code;
		
function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	py = $("#py").attr("checked")? "p":"",
	sy = $("#sy").attr("checked")? "s":"",
	pn = $("#pn").attr("checked")? "p":"",
	sn = $("#sn").attr("checked")? "s":"",
	type = { "Y":py + sy, "N":pn + sn};
	zTree.setting.check.chkboxType = type;
	showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}

$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	setCheck();
	$("#py").bind("change", setCheck);
	$("#sy").bind("change", setCheck);
	$("#pn").bind("change", setCheck);
	$("#sn").bind("change", setCheck);
});