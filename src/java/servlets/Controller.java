package servlets;

import datastore.DAOSQLite;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Face;

/**
 * All of this application's web pages send their requests to this controller
 * which then updates the model / database as needed and transfers control with
 * data to one the the HTML/JSP view-oriented programs for display.
 *
 * @author John Phillips
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get real path to the sqlite db
        ServletContext sc = this.getServletContext();
        String dbPath = sc.getRealPath("/WEB-INF/employee.db");

        // set default url
        String url = "/home.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }

        // perform action and set url
        if (action.equalsIgnoreCase("home")) {
            url = "/home.html";

        } else if (action.equalsIgnoreCase("createRecord")) {
            int rating;

            // get parameters passed in from the request
            String name = request.getParameter("name");
            String ratingString = request.getParameter("rating");
            String eyebrowRow = request.getParameter("eyebrowRow");
            String eyeRow = request.getParameter("eyeRow");
            String mouthRow = request.getParameter("mouthRow");
            String quote = request.getParameter("quote");
            

            // validate and convert salary string into a double
            if (ratingString == null || ratingString.isEmpty()) {
                rating = 0;
            } else {
                rating = Integer.parseInt(ratingString);
            }

            // store data in an Face object
            Face face = new Face(0, name, rating, eyebrowRow, eyeRow, mouthRow, quote);

            // validate the parameters
            if (name == null || ratingString == null || eyebrowRow == null
                    || eyeRow == null || mouthRow == null || quote == null || name.isEmpty()
                    || ratingString.isEmpty() || eyebrowRow.isEmpty() ||  eyeRow.isEmpty() 
                    || mouthRow.isEmpty() || quote.isEmpty()) {
                url = "/createRecord.jsp";
            } else {
                // insert this data record into the database
                DAOSQLite.createRecord(face, dbPath);
                url = "/home.html";
            }

            // add the user object to the request object so that the data is available on the next page
            request.setAttribute("face", face);
            
        } else if (action.equalsIgnoreCase("report")) {
            List<Face> mydata = null;
            mydata = DAOSQLite.retrieveAllRecordsByName(dbPath);
            request.setAttribute("mydata", mydata);
            url = "/displayRecords.jsp";

        } else if (action.equalsIgnoreCase("updateRecord1")) {
            String idString = request.getParameter("faceId");
            if (idString == null || idString.isEmpty()) {
                url = "/updateRecord1.jsp";
            } else {
                // get face
                Face face = DAOSQLite.retrieveRecordById(Integer.parseInt(idString), dbPath);
                request.setAttribute("face", face);
                url = "/updateRecord2.jsp";
            }

        } else if (action.equalsIgnoreCase("updateRecord2")) {
            int faceId;
            int rating;

            // get parameters passed in from the request
            String faceIdString = request.getParameter("faceId");
            String name = request.getParameter("name");
            String ratingString = request.getParameter("rating");
            String eyebrowRow = request.getParameter("eyebrowRow");
            String eyeRow = request.getParameter("eyeRow");
            String mouthRow = request.getParameter("mouthRow");
            String quote = request.getParameter("quote");

            // validate and convert empId string into an int
            if (faceIdString == null || faceIdString.isEmpty()) {
                faceId = 0;
            } else {
                faceId = Integer.parseInt(faceIdString);
            }

            // validate and convert salary string into a double
            if (ratingString == null || ratingString.isEmpty()) {
                rating = 0;
            } else {
                rating = Integer.parseInt(ratingString);
            }

            // store data in an Face object
            // store data in an Face object
            Face face = new Face(faceId, name, rating, eyebrowRow, eyeRow, mouthRow, quote);

            // validate the parameters
            if (faceIdString == null || name == null || ratingString == null || eyebrowRow == null
                    || eyeRow == null || mouthRow == null || quote == null || name.isEmpty()
                    || ratingString.isEmpty() || eyebrowRow.isEmpty() ||  eyeRow.isEmpty() 
                    || mouthRow.isEmpty() || quote.isEmpty() || faceIdString.isEmpty()) {
                url = "/createRecord.jsp";
            } else {
                // insert this data record into the database
                DAOSQLite.updateRecord(face, dbPath);
                url = "/home.html";
            }

        } else if (action.equalsIgnoreCase("deleteRecord")) {
            String idString = request.getParameter("id");
            if (idString == null || idString.isEmpty()) {
                url = "/deleteRecord.jsp";
            } else {
                // delete this data record from the database
                DAOSQLite.deleteRecord(Integer.parseInt(idString), dbPath);
                url = "/home.html";
            }
            
        } else if (action.equalsIgnoreCase("makeDB")) {
            DAOSQLite.dropTable(dbPath);
            DAOSQLite.createTable(dbPath);
            DAOSQLite.populateTable(dbPath);
            url = "/home.html";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        return "Controller for Patient App";
    }// </editor-fold>

}
