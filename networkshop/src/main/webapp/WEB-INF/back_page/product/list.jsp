<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">
function getTableForm() {
	return document.getElementById('tableForm');
}
function optDelete() {
	if(Pn.checkedCount('ids')<=0) {
		alert("请至少选择一个!");
		return;
	}
	if(!confirm("确定删除吗?")) {
		return;
	}
	var f = getTableForm();
	f.action="o_delete.do";
	f.submit();
}
function optShow() {
	if(Pn.checkedCount('ids')<=0) {
		alert("请至少选择一个!");
		return;
	}
	if(!confirm("确定上架吗?")) {
		return;
	}
	var f = getTableForm();
	f.action="../product/isShow.do";
	f.submit();
}
function changePageNo(){
	$("input[name='pageNo']").val(1);
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='../product/toAdd.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="/product/list.do" method="post" style="padding-top:5px;">
<input type="hidden" value="1" name="pageNo"/>
名称: <input type="text" onkeyup="changePageNo()" value="" name="name"/>
	<select onchange="changePageNo()" name="productId">
		<option value="">请选择品牌</option>
		<option value="1">依琦莲</option>
		<option value="2">凯速（KANSOON）</option>
	</select>
	<select onchange="changePageNo()" name="isShow">
		<option value="1">上架</option>
		<option selected="selected" value="0">下架</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form method="post" id="tableForm">
<input type="hidden" value="" name="pageNo"/>
<input type="hidden" value="" name="queryName"/>
<table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>图片</th>
			<th width="4%">新品</th>
			<th width="4%">热卖</th>
			<th width="4%">推荐</th>
			<th width="4%">上下架</th>
			<th width="12%">操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${pagination.rows}" var="row">
			<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
				<td><input type="checkbox" name="ids" value="${row.productId }"/></td>
				<td>${row.no }</td>
				<td align="center">${row.name }</td>
				<td align="center"><img width="40" height="40" src="${row.img.allUrl }"/></td>
				<td align="center">${row.isNew }</td>
				<td align="center">${row.isHot}</td>
				<td align="center">${row.isCommend }</td>
				<td align="center">${row.isShow}</td>
				<td align="center">
				<a href="../product/toEdit.do?productId=${row.productId}" class="pn-opt">查看</a> | <a href="../product/toEdit.do?productId=${row.productId}" class="pn-opt">修改</a> | <a href="../product/delete.do?productId=${row.productId }" onclick="if(!confirm('您确定删除吗？')) {return false;}" class="pn-opt">删除</a> | <a href="../sku/list.do?productId=${row.productId}&pno=${row.no}" class="pn-opt">库存</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15"><span class="r inb_a page_b">
		<a href="../product/list.do?&amp;beginPage=${pagination.beginPage}">首页</a>
		<c:if test="${pagination.currentPage gt 1}">
			<a href="../product/list.do?&amp;beginPage=${pagination.currentPage-1}">上一页</a>
		</c:if>
		<c:forEach begin="${pagination.beginPage}" end="${pagination.endPage}" var="pageNum">
			<a href="../product/list.do?&amp;beginPage=${pageNum}"><font size="2"><c:out value="${pageNum}"/></font></a>
		</c:forEach>
		<c:if test="${pagination.currentPage lt pagination.endPage}">
			<a href="../product/list.do?&amp;beginPage=${pagination.currentPage+1}"><font size="2">下一页</font></a>
		</c:if>
		<a href="../product/list.do?&amp;beginPage=${pagination.endPage}"><font size="2">尾页</font></a>
		共<var>${pagination.endPage}</var>页 到第<input type="text" size="3" id="PAGENO"/>页 <input type="button" onclick="javascript:window.location.href = '/product/list.do?&amp;isShow=0&amp;pageNo=' + $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip"/>
</span></div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete();"/><input class="add" type="button" value="上架" onclick="optShow();"/><input class="del-button" type="button" value="下架" onclick="optDelete();"/></div>
</form>
</div>
</body>
</html>