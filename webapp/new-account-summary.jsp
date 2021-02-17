<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/includes/header.html" %>
<%@include file="/includes/menu.jsp" %>
<section>
    <h2>Account Summary</h2>

    <p>Your account has been created.</p>

    <p>Here's the information you entered:</p>
    <label>First Name:</label>
    <span>${user.firstName}</span><br>
    <label>Last Name:</label>
    <span>${user.lastName}</span><br>
    <label>Email:</label>
    <span>${user.email}</span><br>
    <label>Username:</label>
    <span>${user.username}</span><br>

    <p>To create another account, click on the Back
    button in your browser or the Return button below.</p>


    <form action="" method="get">
        <input type="hidden" name="action" value="join">
        <input type="submit" value="Return">
    </form>

</section>
<%@include file="/includes/footer.html" %>