<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Polls</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="resources/styles/base.css"/>
    <link rel="stylesheet" type="text/css" href="resources/styles/main.css"/>
</head>
<body>
    <c:import url="header.jsp" />
    <main>
        <c:if test="${empty pollList}">
            <p>No polls available.</p>
        </c:if>
        
        <c:forEach var="poll" items="${pollList}">
            <div class="poll">
                <h3>${poll.title}</h3>
                
                <form action="votePoll" method="post">
                    <input type="hidden" name="pollId" value="${poll.pollId}" />
                    
                    <c:forEach var="option" items="${poll.options}">
                        <input type="radio" id="option${option.optionId}" 
                               name="optionId" value="${option.optionId}" required>
                        <label for="option${option.optionId}">${option.optionText}</label><br/>
                    </c:forEach>
                    
                    <input type="submit" value="Vote" />
                    
                    <button type="button" onclick="window.open('results?pollId=${poll.pollId}', '_blank')">View Results</button>
                </form>
            </div>
            <hr/>
        </c:forEach>
    </main>
</body>
</html>
