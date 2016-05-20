import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by mike on 3/16/16.
 */
public class WriteToMySQL {
    LoadProperties loadProperties = LoadProperties.getInstance();
    Properties properties = loadProperties.getProperties();
    Connection conn = null;
    String street;
    String city;
    String zip;
    String lat;
    String lon;
    String garbage;
    String state = properties.getProperty("state");

    public WriteToMySQL(){

    }

    protected void executeStatement(String sql){
        try {
            conn =
                DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?" +
                        "user=%s&password=%s",
                    properties.getProperty("databaseServer"),
                    properties.getProperty("databaseSchemaName"),
                    properties.getProperty("databaseUsername"),
                    properties.getProperty("databasePassword")));

            conn.createStatement().execute(sql);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.println("Bad sql: " + sql);
        }
    }

}
