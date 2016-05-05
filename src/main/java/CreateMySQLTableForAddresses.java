import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by mike on 3/16/16.
 */
public class CreateMySQLTableForAddresses {
    LoadProperties loadProperties = new LoadProperties();
    Properties properties = loadProperties.getProperties();


    public void createAddressTable(){
        String charset = "CHARACTER SET utf8 COLLATE utf8_general_ci";

        String sql = String.format("create table if not exists %2$s" +
            "(street varchar(100) %1$s, city varchar(100) %1$s, state varchar(2) %1$s, zip int(5), "
            + "emm_lat decimal(12,10), emm_lng decimal(12,10), emm_acc varchar(20) %1$s)",
            charset,
            properties.getProperty("databaseTableName"));

        executeStatement(sql);
    }

    public void populateAddressTable(){

    }

    protected void executeStatement(String sql){
        LoadProperties loadProperties = new LoadProperties();
        Properties properties = loadProperties.getProperties();
        Connection conn = null;

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
        }
    }

}
