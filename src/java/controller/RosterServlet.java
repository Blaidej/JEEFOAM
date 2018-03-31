/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import edu.saintpaul.csci2466.foam.model.Athlete;
import edu.saintpaul.csci2466.foam.roster.Roster;
import edu.saintpaul.csci2466.foam.roster.RosterException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AthleteBean;

/**
 *
 * @author blaid
 */
public class RosterServlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        String url = "roster.jsp";
        //String pageName = null;
        log("ServletMAIN.TOP.43");
        List<String> errMsgs = new ArrayList<>();
        try
        {

            //1. get info -- 
            String action = request.getParameter("action");

            String nationalID = request.getParameter("nationalID");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String DateOfBirth = request.getParameter("DateOfBirth");

            // perform action and set URL to appropriate page
            Roster roster = Roster.getInstance();
            AthleteBean bean;
            Athlete specificAthlete;

            //2. validate info
            //have to  check first if it's null
            if (action != null)
            {
                switch (action.toLowerCase())
                {
                    case "update":
                        /* if the user presses the update button, we need to 
                            update the info for the athlete*/
                        //We call athleteBuilder to validate and store into bean
                        bean = athleteBuilder(false, nationalID, firstName,
                                lastName, DateOfBirth, errMsgs);

                        if (errMsgs.isEmpty())
                        {
                            roster.update(bean);
                            break;
                        }
                    /*If there are errors it goes into the edit case
                          to be able to repopulate the fields on the edit page.
                     */

                    case "edit":
                        /*  if the case is edit, we will need to get
                            the selected info from the roster and 
                            display the Edit Athlete Page.
                            We can use the roster.find(NationalID)
                            We need to feed the same  NationalID from the line
                            we where already on
                         */
                        String nationalIDSpecific = request.getParameter(
                                "nationalIDSpecific");
                        specificAthlete = roster.find(nationalIDSpecific);
                        request.setAttribute("specificAthlete", specificAthlete);
                        url = "editAthlete.jsp";
                        break;
                    case "add":
                        /* if the case is add, we need to go through and validate
                           the user data and tell the user what was wrong if they
                           didn't enter some data, except for date 
                         */

                        bean = athleteBuilder((roster.isOnRoster(nationalID)) ? 
                                /*  if national ID is already on roster, user
                                    needs to choose a different nationalID
                                */
                                errMsgs.add(
                                        "Please choose a different National ID")
                                : //if national ID isn't on roster, call function
                                true, nationalID, firstName, lastName,
                                DateOfBirth, errMsgs);

                        if (errMsgs.isEmpty())
                        {
                            roster.add(bean);
                        } else
                        {
                            url = "addAthlete.jsp";
                        }
                        break;

                    case "cancel":
                        /* if the user presses the Cancel button from the Add 
                        screen, they get sent back to the Athlete Roster page
                         */
                        url = "roster.jsp";
                        log("Case:Switch:CANCEL");
                        break;
                    case "delete":
                        /*  if the case is delete, we will need to get
                            the selected info from the roster and 
                            display the Delete Athlete Page.
                            We can use the roster.find(NationalID)
                            We need to feed the same  NationalID from the line
                            we are on
                         */
                        log("Case:Switch:DELETE");
                        String nationalIDDelete = request.getParameter(
                                "nationalIDSpecific");
                        specificAthlete = roster.find(nationalIDDelete);
                        request.setAttribute("specificAthlete", specificAthlete);
                        url = "deleteAthlete.jsp";
                        break;
                    case "confirmdelete":
                        /* confirmDelete is entered when you are on the 
                           deleteAthlete page, and are saying delete for sure.
                        */
                        String nationalIDConfirm = request.getParameter(
                                "nationalIDSpecific");
                        if (roster.delete(nationalIDConfirm))
                        {
                            url = "roster.jsp";

                        } else
                        {
                            errMsgs.add("Record for Athlete Not found");
                            url = "roster.jsp";
                        }
                        break;

                }

            }

            //3. do it
            List<Athlete> athletes = roster.findAll();

            //4. 
            request.setAttribute("athletes", athletes);
            request.setAttribute("errMsgs", errMsgs);

        } catch (RosterException ex)
        {
            Logger.getLogger(RosterServlet.class.getName()).log(Level.SEVERE,
                    null, ex);
            request.setAttribute("errMsgs", "Error: unable to get roster");
        }

        //5.forward control (url variable used here to choose destination)
        request.getRequestDispatcher(url).forward(request, response);

    }

    /**
     * Athlete Builder provides validation for user entered data and builds the
     * Athlete bean to be sent back and added to the roster
     *
     * @param urlFlag flag to send the data to roster(true) or stay on edit
     * screen (false) if they mis-entered something.
     * @param nationalID user's submitted user ID
     * @param firstName user's submitted first name
     * @param lastName user's submitted last name
     * @param DateOfBirth user's submitted date of Birth/ Not necessary
     * @param errMsgs a list possibly contained error messages
     * @return bean that holds the Athlete's info.
     */
    public AthleteBean athleteBuilder(boolean urlFlag, String nationalID,
                                      String firstName, String lastName,
                                      String DateOfBirth, List<String> errMsgs) {

        AthleteBean bean = new AthleteBean();

        /*  Validate each field is not null or empty
            and add to bean, or continue validation.
         */
        if (!(nationalID == null || nationalID.trim().isEmpty()))
        {
            //make a pattern matcher for NationalID validation
            Pattern pattern = Pattern.compile("[a-z]{2}\\d{3}");
            Matcher matcher = pattern.matcher(nationalID);
            //if the nationalID is not empty, test the match
            if (matcher.matches())
            {

                //if the pattern match is successful, add to bean
                bean.setNationalID(nationalID);
            } else
            {

                /*  if pattern doesn't match, true = there was never any id 
                    entered, false, means pattern didn't match.
                 */
                String error = (matcher.matches()) ? "No NationalID is entered"
                        : "National ID needs to pattern match \"aa999\". ";

                errMsgs.add(error);

            }
        }
        if (!(firstName == null || firstName.trim().isEmpty()))
        {
            bean.setFirstName(firstName);
        } else
        {
            errMsgs.add("No First Name entered");

        }

        if (!(lastName == null || lastName.trim().isEmpty()))
        {
            bean.setLastName(lastName);
        } else
        {
            errMsgs.add("No Last Name entered");

        }
        if (!(DateOfBirth == null || DateOfBirth.trim().isEmpty()))
        {
            /*
                urlFlag is true for if date is submitted MM/dd/yyyy
                urlFlag is false if date is submitted as yyyy-MM-dd
            */
            if (urlFlag == true)
            {
                try
                {

                    DateTimeFormatter formatter = DateTimeFormatter.
                            ofPattern(
                                    "MM/dd/yyyy");
                    LocalDate parsedDate = LocalDate.parse(DateOfBirth,
                            formatter);
                    bean.setDateOfBirth(parsedDate);

                } catch (Exception e)
                {
                    log("date not entered");
                }
            }
            if (urlFlag == false)
            {
                try
                {
                    DateTimeFormatter ISO_LOCAL_DATE
                            = DateTimeFormatter.ISO_LOCAL_DATE;

                    LocalDate parsedDate2 = LocalDate.parse(DateOfBirth,
                            ISO_LOCAL_DATE);
                    bean.setDateOfBirth(parsedDate2);

                } catch (Exception e)
                {
                    log("date not entered in correct format");
                }
            }
        }
        return bean;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
