<%@page import="com.example.demo.model.dto.BookDto"%>
<%@page import="java.util.List"%>
<%@page import="com.example.demo.model.dto.RentItemDto"%>
<%@page import="com.example.demo.model.dto.RentListDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
RentListDto rentListDto = (RentListDto) request.getAttribute("rentListDto");
%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<!-- 格式化庫 -->
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<!-- 功能庫 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">

</head>
<body>

	<!-- menu bar include -->
	<%@ include file="/WEB-INF/view/menu.jspf"%>

	<!-- body content -->
	<div style="padding: 15px">
		<div class="pure-form">
			<fieldset>
				<legend>${ userCert.username }租書清單 </legend>
				<table class="pure-table pure-table-bordered">
					<thead>
						<tr>
							<th>書籍書號</th>
							<th>書籍名稱</th>
							<th>租借號碼</th>
							<th>使用者號碼</th>
							<th>租借日期</th>
							<th>還書日期</th>
							<th>租金</th>
							<th>租借狀態</th>
							<th>小計</th>
						</tr>
					</thead>
					<tbody>
						<%
						if (rentListDto != null && rentListDto.getRentItemDto() != null) {
							List<BookDto> books = rentListDto.getRentItemDto().getBooks();
							if (books != null && !books.isEmpty()) {
								for (BookDto book : books) {
						%>
						<tr>
							<td><%=book.getBookId()%></td>
							<td><%=book.getBookName()%></td>
							<td><%=rentListDto.getRentId()%></td>
							<td><%=rentListDto.getUserId()%></td>
							<td><%=rentListDto.getRentDate()%></td>
							<td><%=rentListDto.getDueDate()%></td>
							<td><%=book.getBookPrice()%></td>
							<td><%=rentListDto.getRentStatus()%></td>
							<td><%=rentListDto.getUnitPrice()%></td>
						</tr>
						<%
						}
						%>
						<%
						}
						%>
						<%
						}
						%>
						<tr>
						<td colspan="6" align="right">總計</td>
						<td><a href="/rentlist/finish"
							class="button-success pure-button">結帳</a></td>
						<td><a href="/rentlist/cancel"
							class="button-warning pure-button">取消租借</a></td>
						</tr>


					</tbody>
				</table>
			</fieldset>
		</div>
	</div>
</body>
</html>