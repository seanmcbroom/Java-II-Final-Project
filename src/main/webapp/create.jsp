<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <title>Create Poll</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="resources/styles/base.css"/>
    <link rel="stylesheet" type="text/css" href="resources/styles/main.css"/>
</head>

<body>
    <c:import url="header.jsp" />

    <main>
        <form action="submitPoll" method="post">
            <label for="pollTitle">Poll Title:</label><br>
            <input type="text" id="pollTitle" name="pollTitle" required><br><br>

            <label for="option1">Option 1:</label><br>
            <input type="text" id="option1" name="option1" required><br><br>

            <label for="option2">Option 2:</label><br>
            <input type="text" id="option2" name="option2" required><br><br>

            <input type="submit" value="Create Poll">
        </form>
    </main>
</body>