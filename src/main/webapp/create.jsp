<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Poll</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="resources/styles/base.css"/>
        <link rel="stylesheet" type="text/css" href="resources/styles/main.css"/>
        <script>
            let optionCount = 2;
            function addOption() {
                optionCount++;
                const container = document.getElementById("optionsContainer");

                // Create label element for new option.
                let newLabel = document.createElement("label");
                newLabel.htmlFor = "option" + optionCount;
                newLabel.textContent = "Option " + optionCount + ":";

                // Create new input element for new option.
                let newInput = document.createElement("input");
                newInput.type = "text";
                newInput.id = "option" + optionCount;
                newInput.name = "options";
                newInput.required = true;

                // Append elements for spacing.
                container.appendChild(newLabel);
                container.appendChild(document.createElement("br"));
                container.appendChild(newInput);
                container.appendChild(document.createElement("br"));
                container.appendChild(document.createElement("br"));
            }
        </script>
    </head>
    <body>
        <c:import url="header.jsp" />
        <main>
            <c:if test="${not empty errorMessage}">
                <div class="error">${errorMessage}</div>
            </c:if>
            <form action="submitPoll" method="post">
                <label for="pollTitle">Poll Title:</label><br>
                <input type="text" id="pollTitle" name="pollTitle" required><br><br>

                <div id="optionsContainer">
                    <label for="option1">Option 1:</label><br>
                    <input type="text" id="option1" name="options" required><br><br>

                    <label for="option2">Option 2:</label><br>
                    <input type="text" id="option2" name="options" required><br><br>
                </div>
                
                <div class="flex-row">
                    <input type="submit" value="Create Poll">
                    <button type="button" onclick="addOption()">Add Option</button>
                </div>
            </form>
        </main>
    </body>
</html>
