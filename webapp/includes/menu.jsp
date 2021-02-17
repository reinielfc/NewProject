<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--Navigation Bar-->
    <nav>
        <ul>
            <li><a href="./">Home</a></li>
            <li><a href="./about">About</a></li>
            <li><a href="./contact">Contact</a></li>
            <c:choose>
                <c:when test="${sessionScope.firstName != null}">
                    <li class="float-right"><a href="./logout">Log Out</a></li>
                    <li class="float-right"><a>Hello ${sessionScope.firstName}!</a></li>
                </c:when>
                <c:otherwise>
                    <li class="float-right"><a href="./login">Log In</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>
