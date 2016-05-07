import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by mike on 3/16/16.
 */
public class CreateMySQLTable {
    LoadProperties loadProperties = LoadProperties.getInstance();
    Properties properties = loadProperties.getProperties();


    public void createNewResultsTable(String currDate){
        String charset = "CHARACTER SET utf8 COLLATE utf8_general_ci";

        String sql = String.format("create table if not exists %3$s_%1$s" +
            "(street varchar(100) %2$s, city varchar(100) %2$s, state varchar(2) %2$s, zip int(5), "
            + "speed decimal(5,1), emm_lat decimal(12,10), emm_lng decimal(12,10), emm_acc varchar(20) %2$s)",
            currDate,
            charset,
            properties.getProperty("databaseTableName"));

        executeStatement(sql);
    }

    public void createNewBadAddressesTable(String currDate){
        String charset = "CHARACTER SET utf8 COLLATE utf8_general_ci";

        String sql = String.format("create table if not exists %3$s_%4$s_%1$s" +
                "(original_address varchar(200) %2$s, cl_resolved_address varchar(200) %2$s)",
            currDate,
            charset,
            properties.getProperty("databaseBadAddressTableName"),
            properties.getProperty("databaseTableName"));

        executeStatement(sql);
    }

    public void createCenturyLinkResolvedAddressTable(){
        String charset = "CHARACTER SET utf8 COLLATE utf8_general_ci";

        String sql = String.format("create table if not exists cl_resolved_addresses" +
                "(original_address varchar(200) %1$s, cl_resolved_address varchar(200) %1$s)",
            charset);

        executeStatement(sql);
    }

    protected void executeStatement(String sql){
        LoadProperties loadProperties = LoadProperties.getInstance();
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
