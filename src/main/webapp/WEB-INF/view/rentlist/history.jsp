<%@page import="com.example.demo.model.dto.BookDto"%>
<%@page import="java.util.List"%>
<%@page import="com.example.demo.model.dto.RentItemDto"%>
<%@page import="com.example.demo.model.dto.RentListDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<RentListDto> rentListFinishDtos = (List<RentListDto>) request.getAttribute("rentListFinishDtos");
%>
<%
List<RentListDto> rentListCancelDtos = (List<RentListDto>) request.getAttribute("rentListCancelDtos");
%>
<!-- Jakarta JSTL 表單標籤 -->
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html>
<head>
<meta charset="UTF-8">
<title>History</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
<link rel="stylesheet" href="/css/buttons.css">
<style>
body {
    margin: 0;
    font-family: Arial, sans-serif;
}

header {
    width: 100%;
    background-color: #007BFF;
    color: white;
    padding: 10px 20px;
    text-align: center;
    position: fixed; /* 固定 header */
    top: 0;
}

.container {
    display: flex;
    justify-content: center; /* 水平置中 */
    align-items: center;    /* 垂直置中 */
    height: 100vh;         /* 滿屏高度 */
    box-sizing: border-box;
}

fieldset {
    margin: 0;
    padding: 20px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

table {
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
}

th, td {
    padding: 10px;
    text-align: left;
    border: 1px solid #ddd;
}
</style>
</head>
<body>
	<!-- menu bar include -->
	<%@ include file="/WEB-INF/view/menu.jspf"%>
	<div class="pure-form">
		<fieldset>
			<legend>${ userCert.username } 的歷史訂單</legend>
			<table class="pure-table pure-table-bordered">
				<thead>
					<tr>
						<th>書籍書號</th>
						<th>書籍名稱</th>
						<th>租借訂單號</th>
						<th>使用者號碼</th>
						<th>租借日期</th>
						<th>還書日期</th>
						<th>租金</th>
						<th>訂單狀態</th>
						<th>小計</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (rentListFinishDtos != null) {
						for (RentListDto rentList : rentListFinishDtos) {
							RentItemDto rentItem = rentList.getRentItemDto();
							if (rentItem != null) {
						List<BookDto> books = rentItem.getBooks();
						if (books != null && !books.isEmpty()) {
							for (BookDto book : books) {
					%>
					<tr>
						<td><%=book.getBookId()%></td>
						<td><%=book.getBookName()%></td>
						<td><%=rentList.getRentId()%></td>
						<td><%=rentList.getUserId()%></td>
						<td><%=rentList.getRentDate()%></td>
						<td><%=rentList.getDueDate()%></td>
						<td><%=book.getBookPrice()%></td>
						<td><%=rentList.getRentStatus()%></td>
						<td><%=rentList.getUnitPrice()%></td>
					</tr>
					<%
					}
					}
					}
					}
					}
					%>
				</tbody>
				<tbody>
					<%
					if (rentListCancelDtos != null) {
						for (RentListDto rentList : rentListCancelDtos) {
							RentItemDto rentItem = rentList.getRentItemDto();
							if (rentItem != null) {
						List<BookDto> books = rentItem.getBooks();
						if (books != null && !books.isEmpty()) {
							for (BookDto book : books) {
					%>
					<tr>
						<td><%=book.getBookId()%></td>
						<td><%=book.getBookName()%></td>
						<td><%=rentList.getRentId()%></td>
						<td><%=rentList.getUserId()%></td>
						<td><%=rentList.getRentDate()%></td>
						<td><%=rentList.getDueDate()%></td>
						<td><%=book.getBookPrice()%></td>
						<td><%=rentList.getRentStatus()%></td>
						<td><%=rentList.getUnitPrice()%></td>
					</tr>
					<%
					}
					}
					}
					}
					}
					%>
				</tbody>
			</table>
		</fieldset>
	</div>
</body>
</html>