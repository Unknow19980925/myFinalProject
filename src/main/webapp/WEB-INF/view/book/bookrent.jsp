
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Jakarta JSTL 表單標籤 -->
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<head>
<meta charset="UTF-8">
<title>Book Rent</title>
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
			<table class="pure-table pure-table-bordered">
				<thead>
					<tr>
						<th>書號</th>
						<th>書名</th>
						<th>作者</th>
						<th>出版社</th>
						<th>價錢</th>
						<th>借閱</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="bookDto" items="${ bookDtos }">
						<tr>
							<td>${ bookDto.bookId }</td>
							<td>${ bookDto.bookName }</td>
							<td>${ bookDto.author }</td>
							<td>${ bookDto.publisher }</td>
							<td>${ bookDto.bookPrice }</td>
							<td>
								<form action="/rentlist/rent/${bookDto.bookId}" method="post">
									<button type="submit" class="button-success pure-button">租閱</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</div>
</body>