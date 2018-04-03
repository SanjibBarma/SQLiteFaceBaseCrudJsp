package datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Face;

/**
 * DAOSQLite Data Access Object for an SQLite database
 *
 * @author John Phillips
 * @version 0.3 on 2015-11-03
 */
public class DAOSQLite {

    protected final static String DRIVER = "org.sqlite.JDBC";
    protected final static String JDBC = "jdbc:sqlite";

    /**
     * Inserts an record into the database table. Note the use of a
     * parameterized query to prevent SQL Injection attacks.
     *
     * @param face the object to insert
     * @param dbPath the path to the SQLite database
     */
    public static void createRecord(Face face, String dbPath) {
        String q = "insert into face (faceId, name, rating, eyebrowRow, "
                + "eyeRow, mouthRow, quote) values (null, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, face.getName());
            ps.setInt(2, face.getRating());
            ps.setString(3, face.getEyebrowRow());
            ps.setString(4, face.getEyeRow());
            ps.setString(5, face.getMouthRow());
            ps.setString(6, face.getQuote());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieve a record given an faceId.
     *
     * @param userId the unique id number of the record to retrieve
     * @param dbPath the path to the SQLite database
     * @return Face object
     */
    public static Face retrieveRecordById(int faceId, String dbPath) {
        String q = "select faceId, name, rating, eyebrowRow, eyeRow, mouthRow, quote from face where faceId=?";
        Face face = null;
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setInt(1, faceId);
            face = myQuery(conn, ps).get(0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return face;
    }

    /**
     * Retrieve all of the records in the database as a list sorted by lastName,
     * firstName.
     *
     * @param dbPath the path to the SQLite database
     * @return list of objects
     */
    public static List<Face> retrieveAllRecordsByName(String dbPath) {
        String q = "select * from face order by rating";
        List<Face> list = null;
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            list = myQuery(conn, ps);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

//    /**
//     * Update a record from the database given an face object. Note the use
//     * of a parameterized query to prevent SQL Injection attacks.
//     *
//     * @param face the face record to update
//     * @param dbPath the path to the SQLite database
//     */
    public static void updateRecord(Face face, String dbPath) {
        String q = "update face set name=?, rating=?, eyebrowRow=?, eyeRow=?, mouthRow=?, quote=? where faceId=?";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, face.getName());
            ps.setInt(2, face.getRating());
            ps.setString(3, face.getEyebrowRow());
            ps.setString(4, face.getEyeRow());
            ps.setString(5, face.getMouthRow());
            ps.setString(6, face.getQuote());
            ps.setInt(7, face.getFaceId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete a record from the database given its id. Note the use of a
     * parameterized query to prevent SQL Injection attacks.
     *
     * @param id the id of the record to delete
     * @param dbPath the path to the SQLite database
     */
    public static void deleteRecord(int id, String dbPath) {
        String q = "delete from face where faceId = ?";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new face table.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void createTable(String dbPath) {
        String q = "create table face ("
                + "faceId integer not null primary key autoincrement, "
                + "name varchar(20) not null, "
                + "rating integer not null, "
                + "eyebrowRow varchar(5) not null, "
                + "eyeRow varchar(5) not null, "
                + "mouthRow varchar(5) not null, "
                + "quote varchar(60) not null); ";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Drops the face table erasing all of the data.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void dropTable(String dbPath) {
        final String q = "drop table if exists face";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Populates the table with sample data records.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void populateTable(String dbPath) {
        Face p;
        p = new Face(0, "Inquisitive Eyebrows", 50, " ~ ^ ", " o O ","   = ","Innnnteresting.");
        DAOSQLite.createRecord(p, dbPath);
        p = new Face(0, "FishBoy", 50, "     ", "0   0","  ^  ","Beauitful FishBoy.");
        DAOSQLite.createRecord(p, dbPath);
        p = new Face(0, "Puckerface", 50, " , , ", " > < ","   * ","Or a cat's butt.");
        DAOSQLite.createRecord(p, dbPath);
    }

    /**
     * A helper method that executes a prepared statement and returns the result
     * set as a list of objects.
     *
     * @param conn a connection to the database
     * @param ps a prepared statement
     * @return list of objects from the result set
     */
    protected static List<Face> myQuery(Connection conn, PreparedStatement ps) {
        List<Face> list = new ArrayList();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int faceId = rs.getInt("faceId");
                String name = rs.getString("name");
                int rating = rs.getInt("rating");
                String eyebrowRow = rs.getString("eyebrowRow");
                String eyeRow = rs.getString("eyeRow");
                String mouthRow = rs.getString("mouthRow");
                String quote = rs.getString("quote");
                Face p = new Face(faceId, name, rating, eyebrowRow, eyeRow, mouthRow, quote);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Creates a connection to the SQLite database.
     *
     * @param dbPath the path to the SQLite database
     * @return connection to the database
     */
    protected static Connection getConnectionDAO(String dbPath) {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(JDBC + ":" + dbPath);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
