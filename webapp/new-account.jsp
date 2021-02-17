<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/includes/header.html" %>
<%@include file="/includes/menu.jsp" %>
<section>
    <h2>Create Account</h2>
    <p style="color: red;">${message}</p>
    <p>Fill out the fields below to create your account:</p>
    <form class="account-form" action="newAccount" method="post">
        <label>First Name:</label>
        <input type="text" name="firstName" value="${user.firstName}" placeholder="John">
        <label>Last Name:</label>
        <input type="text" name="lastName" value="${user.lastName}" placeholder="Smith">
        <label>Email:</label>
        <input type="email" name="email" value="${user.email}" placeholder="jsmith@email.com" required>
        <label>Username:</label>
        <input type="text" name="username" value="${user.username}" placeholder="jsmith" required>
        <label>Password:</label>
        <input type="password" name="password" value="${user.password}" placeholder="password" required>
        <input type="hidden" name="action" value="add">
        <input type="submit" value="Create Account">
    </form>
</section>
<%@include file="/includes/footer.html" %>