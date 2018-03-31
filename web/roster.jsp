<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : roster
    Created on : Feb 27, 2018, 7:18:32 PM
    Author     : blaid
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        
        <title>Roster</title>
    </head>
    
        <h1>Athlete Roster</h1>
       <!-- This tests if there are any error messages and prints them -->
        <c:if test="${errMsgs.size() > 0}" var="errMsg">
            <h2><span style="color:red">Error</span></h2>
            <c:forEach var="errMsg" items="${errMsgs}">
                <span style="color:red">${errMsg}</span><br>
            </c:forEach>
        </c:if>
        <table border="1">
            <thead>
                <tr>
                    <th>National ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Date Of Birth</th>
                    <th>Age</th>
                    <th colspan="2">Modify Records</th>
                </tr>
            </thead>
            <!-- Prints out the athletes info, looping through the roster-->
            <tbody>
                <c:forEach var="athlete" items="${athletes}">
                    <tr>
                        <td><c:out value="${athlete.nationalID}"/> </td>
                        <td><c:out value="${athlete.firstName}"/></td>
                        <td><c:out value="${athlete.lastName}"/></td>
                        <td><c:out value="${athlete.dateOfBirth}"/></td>
                        <td>
                            <!-- 
                                test if output for age is unset or invalid
                                and output nothing to the screen in that case
                            -->
                            <c:choose>
                                <c:when test="${athlete.age < 0}">
                                    <p></p>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${athlete.age}"/>
                                </c:otherwise>
                            </c:choose>    
                        </td>
                        <td>
                            <form action="viewRoster" method="post">
                                <input type="hidden" name="nationalIDSpecific" 
                                       value="<c:out value='${athlete.nationalID}'/>">
                                <input type="hidden" name="action" 
                                       value="Edit">
                                <input type="submit" value="Edit">
                            </form>
                        </td>
                        <td>
                            <form action="viewRoster" method="post">
                                <input type="hidden" name="nationalIDSpecific" 
                                    value="<c:out value='${athlete.nationalID}'/>">
                                   
                                <input type="hidden" name="action" 
                                    value="delete">
                                
                                <input type="submit" value="Delete">
                            </form>
                        </td>
                    </tr></c:forEach>
                <td colspan="7">
                    <form action="addAthlete.jsp" method="post">
                        <input type="submit" value="Add New Athlete" name="action" />
                    </form>
                </td>
            </tbody>

</table>





</html>
