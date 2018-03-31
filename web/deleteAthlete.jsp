<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : deleteAthlete
    Created on : Feb 27, 2018, 8:50:30 PM
    Author     : Blaine Jacques
    This page sends the it's info to RosterServlet to get delete an 
    athlete from the AthleteRoster.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Athlete</title>
    </head>
    <body>

        <h1>Delete Athlete</h1>

        <!-- This tests if there are any error messages and prints them -->
        <c:if test="${errMsgs.size() > 0}" var="errMsg">
            <h2><span style="color:red">Error</span></h2>
            <c:forEach var="errMsg" items="${errMsgs}">
                <span style="color:red">${errMsg}</span><br>
            </c:forEach>
        </c:if>


        <!-- Form to input user info -->
        <form action="viewRoster">
            <table border="1">
                <tbody>


                    <tr>
                        <td><label>National ID</label></td>
                        <td>
                            <label>${specificAthlete.getNationalID()}</label> 

                        </td>
                    </tr>
                    <tr>
                        <td><label>First Name</label></td>
                        <td>
                            <label>${specificAthlete.getFirstName()}</label> 
                        </td>
                    </tr>
                    <tr>
                        <td><label>Last Name</label></td>
                        <td>
                            <label>${specificAthlete.getLastName()}</label> 
                        </td>
                    </tr>
                    <tr>
                        <td><label>Date Of Birth</label></td>
                        <td>
                            <label>${specificAthlete.getDateOfBirth()}</label>
                            
                        </td>
                    </tr>

                        <tr>
                            <td colspan="2">
                                <p>Are you sure you want to remove this athlete from the roster?</p> 
                                <input type="hidden" name="nationalIDSpecific" 
                                       value="<c:out value='${specificAthlete.getNationalID()}'/>">
                                <input type="hidden" name="action" 
                                       value="confirmDelete">
                                <input type="submit" value="Delete" name="action" />
                                <input type="submit" value="Cancel" name ="action" />
                            </td>
                        </tr>
                    </tbody>

                </table>
            </form>









        </body>
    </html>
