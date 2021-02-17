<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@include file="/includes/header.html" %>
<%@include file="/includes/menu.jsp" %>
<section>
    <h2>Log Out</h2>
    <p>Hi, ${sessionScope.firstName}. Are you sure that you want to log out?</p>
    <table>
        <tr>
            <td>
                <form class="acount-form" action="logout" method="get">
                    <input type="hidden" name="action" value="leave">
                    <input type="submit" value="Yes">
                </form>
            </td>
            <td>
                <form class="acount-form" action="logout" method="get">
                    <input type="hidden" name="action" value="stay">
                    <input type="submit" value="No">
                </form>
            </td>
        </tr>
    </table>
</section>
<%@include file="/includes/footer.html" %>