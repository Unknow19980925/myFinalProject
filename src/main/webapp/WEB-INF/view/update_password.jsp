<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>更新密碼</title>
</head>
<body>
    <h2>更新密碼</h2>
  <sp:form class="pure-form" method="post" modelAttribute="userDto" action="/user/update/password"> 
    
        <label for="oldPassword">舊密碼：</label>
        <input type="password" name="oldPassword" id="oldPassword" required><br>

        <label for="newPassword">新密碼：</label>
        <input type="password" name="newPassword" id="newPassword" required><br>

        <button type="submit" class="pure-button pure-button-primary">更新密碼</button>
    </sp:form>
</body>
</html>