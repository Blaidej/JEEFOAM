package model;


/*
 * Name: Blaine Jacques
 * CSCI2466 HW2: Freedonia Olympic Athlete Bean
 */
import edu.saintpaul.csci2466.foam.model.Athlete;
import static edu.saintpaul.csci2466.foam.model.Athlete.INVALID_DATE;
import static edu.saintpaul.csci2466.foam.model.Athlete.OLYMPIC_OPENING_DATE;
import static edu.saintpaul.csci2466.foam.model.Athlete.UNSET_DATE;
import java.time.*;

/**
 * This Bean is built for the Freedonia Olympic Committee to manage it's
 * athletes. Freedonia Olympic athlete Management System (FOAM). The FOAM System
 * is a web based application that will allow users to view, add, edit, and
 * delete athletes from the roster. This bean implements the Athlete interface
 * and provides setters and getters for the athlete's NationalID, FirstName,
 * LastName, and DateOfBirth which helps calculate an athlete's age.
 *
 * @author Blaine Jacques
 */
//implements serializable via Athlete
public class AthleteBean implements Athlete {

    //Properties
    private String NationalID; //Athletes NationalID
    private String FirstName; //Athletes First Name
    private String LastName; //Athletes Last Name
    private LocalDate DateOfBirth; //Athletes Date of Birth

    /**
     * Default no-arg constructor
     */
    public AthleteBean() {

    }

    /**
     * Getter for the Athletes National ID
     *
     * @return the National ID of the Athlete.
     */
    @Override
    public String getNationalID() {
        return NationalID;
    }

    /**
     * Setter for the Athletes National ID
     *
     * @param NationalID the National ID of the Athlete.
     */
    public void setNationalID(String NationalID) {
        this.NationalID = NationalID;
    }

    /**
     * Getter for the Athlete's first name.
     *
     * @return the first name of the Athlete.
     */
    @Override
    public String getFirstName() {
        return FirstName;
    }

    /**
     * Setter for the Athlete's first name.
     *
     * @param FirstName the first name of the Athlete.
     */
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     * Getter for the last name
     *
     * @return the last name of the Athlete.
     */
    @Override
    public String getLastName() {
        return LastName;
    }

    /**
     * Setter for the Athlete's last name.
     *
     * @param LastName the last name of the Athlete.
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    /**
     * Getter for the Athlete's Date of birth
     *
     * @return the athlete's date of birth
     */
    @Override
    public LocalDate getDateOfBirth() {
        return DateOfBirth;
    }

    /**
     * Setter for the Athlete's Date of birth
     *
     * @param DateOfBirth the Athlete's date of birth
     */
    public void setDateOfBirth(LocalDate DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    /**
     * Getter for the Age, calculates the age via the birth date and the number
     * of years between them. If the date of birth is left empty it returns
     * UNSET_DATE, and if the date is after the date Olympic Opening date, then
     * the Athlete wouldn't even be born yet, which returns INVALID_DATE.
     *
     * @return the age calculated via the Athlete's date of birth, if not
     * provided, it returns UNSET_DATE. If date of birth is entered after the
     * date of the Olympics opening, then it returns INVALID_DATE.
     */
    @Override
    public int getAge() {
        /* the dateOfBirth is null, the date is unset;
           if the date isn't null, then check if it's after the opening date, 
           if he 
        */
        return DateOfBirth == null ? UNSET_DATE
                : DateOfBirth.isAfter(OLYMPIC_OPENING_DATE) ? INVALID_DATE
                : DateOfBirth.until(OLYMPIC_OPENING_DATE).getYears();

    }

    @Override
    public String toString() {
        return "AthleteBean{" + "NationalID=" + NationalID + ", FirstName=" +
                FirstName + ", LastName=" + LastName + ", DateOfBirth=" +
                DateOfBirth + '}';
    }

    
}
