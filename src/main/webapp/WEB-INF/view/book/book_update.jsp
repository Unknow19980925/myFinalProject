<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Spring Form 表單標籤 -->
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Book Update</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		
		<!-- body content -->
		<div style="padding: 15px">
			
			<table>
				<tr>
					<!-- Book 表單修改 -->
					<td valign="top">
						<sp:form class="pure-form" method="post" modelAttribute="bookDto" action="/book/update/${ bookDto.bookId }" >
							<fieldset>
								<legend>Book 表單修改</legend>
								Book 書號: <sp:input type="number" path="bookId" readonly="true" />
								<p />
								Book 名稱: <sp:input type="text" path="bookName" />
								<sp:errors path="bookName" style="color: red" />
								<p />
								Book 作者: <sp:input type="text" path="author" />
								<sp:errors path="author" style="color: red" />
								<p />
								Book 出版社: <sp:input type="text" path="publisher" />
								<sp:errors path="publisher" style="color: red" />
								<p />
								Book 價錢: <sp:input type="number" path="bookPrice" />
								<sp:errors path="bookPrice" style="color: red" />
								<p />
								<button type="submit" class="pure-button pure-button-primary">修改</button>
							</fieldset>
							
						</sp:form>
						
					</td>
				</tr>
			</table>
			
		</div>
	</body>
</html>