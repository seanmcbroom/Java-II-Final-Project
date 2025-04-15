<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Poll Results</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="resources/styles/base.css"/>
    <link rel="stylesheet" type="text/css" href="resources/styles/main.css"/>
</head>
<body>
    <c:import url="header.jsp" />
    <main>
        <c:if test="${empty poll}">
            <p>Poll not found.</p>
        </c:if>
        
        <c:if test="${not empty poll}">
            <h3>${poll.title}</h3>
            
            <!-- Compute the total votes from all poll options -->
            <c:set var="totalVotes" value="0" scope="page" />
            <c:forEach var="option" items="${poll.options}">
                <c:set var="totalVotes" value="${totalVotes + option.voteCount}" scope="page" />
            </c:forEach>
            
            <table border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>Option</th>
                    <th>Votes</th>
                    <th>Percentage</th>
                </tr>
                <c:forEach var="option" items="${poll.options}">
                    <tr>
                        <td>${option.optionText}</td>
                        <td>${option.voteCount}</td>
                        <td>
                            <c:choose>
                                <c:when test="${totalVotes > 0}">
                                    <fmt:formatNumber value="${(option.voteCount * 1.0 / totalVotes) * 100}" pattern="##0.00" />%
                                </c:when>
                                <c:otherwise>0.00%</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <p>Total Votes: ${totalVotes}</p>
        </c:if>
    </main>
</body>
</html>
