<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>View Polls</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="resources/styles/base.css"/>
    <link rel="stylesheet" type="text/css" href="resources/styles/main.css"/>
</head>

<body>
    <c:import url="header.jsp" />

    <main>
        <div>
            <h2>Existing Polls</h2>
            <ul>
                <c:forEach var="poll" items="${pollList}">
                    <li>${poll.title} - <a href="pollResults?id=${poll.id}">View Results</a></li>
                </c:forEach>
            </ul>
        </div>
    </main>
</body>