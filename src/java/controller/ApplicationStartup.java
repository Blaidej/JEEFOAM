/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import edu.saintpaul.csci2466.foam.roster.Roster;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author blaid
 */
public class ApplicationStartup implements ServletContextListener
{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String virtualPath = sc.getInitParameter("ContextRosterPath");
        String realPath = sc.getRealPath(virtualPath);
        Roster.initialize(realPath);
        sc.log("Roster Initialized in ServletContextListtener");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
