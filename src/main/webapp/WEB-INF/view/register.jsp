<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
<link>
<title>Register</title>
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
	height: 100vh;          /* 滿屏高度 */
	box-sizing: border-box;
}

form {
	padding: 20px;
	background: white;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	width: 400px; /* 固定表單寬度 */
}

fieldset {
	border: none;
	padding: 0;
	margin: 0;
}

.form-group {
	display: flex;
	align-items: center;
	margin-bottom: 15px;
}

.form-group label {
	width: 120px; /* 固定标签宽度 */
	font-weight: bold;
}

.form-group input,
.form-group select {
	flex: 1;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

button {
	margin-top: 15px;
	padding: 10px 15px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.button-warning {
	background-color: #FFC107;
	color: white;
}

.button-warning:hover {
	background-color: #E0A800;
}

.button-success {
	background-color: #28A745;
	color: white;
}

.button-success:hover {
	background-color: #218838;
}
</style>

<!-- menu bar include -->
<%@ include file="/WEB-INF/view/menu.jspf"%>

<!-- body content -->
<div class="container">
	<form class="pure-form" method="post" action="/user/add">
		<fieldset>
			<legend>使用者註冊</legend>
		用戶名稱:<input type="text" name="username"
				placeholder="請輸入使用者名稱" required /><br /> 
			用戶密碼:<input
				type="password" name="password" placeholder="請輸入密碼" required /><br />
			電子郵件:<input type="email" name="email" placeholder="請輸入電子郵件"
				required />
			<p />
			選擇角色: <select name="role">
				<option value="ROLE_ADMIN">管理者</option>
				<option value="ROLE_USER">使用者</option>
			</select>
			<p />
			<button type="reset" class="button-warning pure-button">重置</button>
			<button type="submit" class="button-success pure-button">確認</button>
		</fieldset>
	</form>

</div>
</body>
</html>