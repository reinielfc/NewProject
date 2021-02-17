<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/includes/header.html" %>
<%@include file="/includes/menu.jsp" %>
<section>
    <h2>Log In</h2>
    <p class="val-msg">${message}</p>
    <p>Fill out the fields below to log in to your account:</p>
    <form class="account-form" action="login" method="post">
        <label>Username:</label>
        <input type="text" name="username" value="${user.username}" placeholder="jsmith" required>
        <label>Password:</label>
        <input type="password" name="password" value="${user.password}" placeholder="password" required>
        <input type="hidden" name="action" value="auth">
        <input type="submit" value="Log In">
    </form>
    <form class="acount-form" action="newAccount" method="get">
        <input type="hidden" name="action" value="join">
        <input type="submit" value="New? Create Account">
    </form>
</section>
<%@include file="/includes/footer.html" %>