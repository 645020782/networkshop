<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/back_page/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='../brand/toAdd.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="../brand/list.do" method="post" style="padding-top:5px;">
品牌名称: <input type="text" name="brandName" value="${brandName}"/>
	<select name="isDisplay" >
		<option value="1">是</option>
		<option value="0">不是</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="checkBox('ids',this.checked)"/></th>
			<th>品牌ID</th>
			<th>品牌名称</th>
			<th>品牌图片</th>
			<th>品牌描述</th>
			<th>排序</th>
			<th>是否可用</th>
			<th>操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${pagination.rows}" var="row">
			<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
				<td><input type="checkbox" value="${row.brandId }" name="ids"/></td>
				<td align="center">${row.brandId }</td>
				<td align="center">${row.brandName }</td>
				<td align="center"><img width="40" height="40" src="${row.allUrl}"/></td>
				<td align="center">${row.description }</td>
				<td align="center">${row.sort }</td>
				<td align="center"><c:if test="${row.isDisplay == 1 }">是</c:if><c:if test="${row.isDisplay == 0 }">不是</c:if></td>
				<td align="center">
				<a class="pn-opt" href="../brand/toEdit.do?brandId=${row.brandId}&brandName=${row.brandName}">修改</a> | <a class="pn-opt"  href="../brand/delete.do?brandId=${row.brandId }&brandName=${row.brandName}">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15"><span class="r inb_a page_b">
		<a href="../brand/list.do?&amp;beginPage=${pagination.beginPage}">首页</a>
		<c:if test="${pagination.currentPage gt 1}">
			<a href="../brand/list.do?&amp;beginPage=${pagination.currentPage-1}">上一页</a>
		</c:if>
		<c:forEach begin="${pagination.beginPage}" end="${pagination.endPage}" var="pageNum">
			<a href="../brand/list.do?&amp;beginPage=${pageNum}"><font size="2"><c:out value="${pageNum}"/></font></a>
		</c:forEach>
		<c:if test="${pagination.currentPage lt pagination.endPage}">
			<a href="../brand/list.do?&amp;beginPage=${pagination.currentPage+1}"><font size="2">下一页</font></a>
		</c:if>
		<a href="../brand/list.do?&amp;beginPage=${pagination.endPage}"><font size="2">尾页</font></a>
		共<var>${pagination.endPage}</var>页 到第<input type="text" size="3" id="PAGENO"/>页 <input type="button" onclick="javascript:window.location.href = '/product/list.do?&amp;isShow=0&amp;pageNo=' + $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip"/>
</span></div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete();"/></div>
</div>
</body>
</html>