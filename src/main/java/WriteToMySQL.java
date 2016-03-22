import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mike on 3/16/16.
 */
public class WriteToMySQL {
    Connection conn = null;
    String street;
    String city;
    String zip;
    String lat;
    String lon;
    String garbage;
    String state = "MN";

    public WriteToMySQL(){

    }

    //this constructor assumes the address is bad since no speed was sent to it
    public WriteToMySQL(String address){
        String sql = String.format("insert into badaddresses " +
                "(badaddress)" +
                "values ('%s')",
            address);
        executeStatement(sql);
    }

    public WriteToMySQL(String address, String actualAddress, String speed){
        String[] addressSplit = address.split(",");
        String[] actualAddressSplit = actualAddress.split(",");
        street = actualAddressSplit[0];
        city = actualAddressSplit[1];
        zip = actualAddressSplit[2].split(" ")[1];
        lat = addressSplit[4];
        lon = addressSplit[5];
        garbage = addressSplit[6];

        String sql = String.format("insert into clspeed " +
                "(street, city, state, zip, speed, emm_lat, emm_lng, emm_acc)" +
                "values ('%s', '%s', '%s', '%s', %s, %s, %s, '%s')",
            street, city, state, zip, speed, lat, lon, garbage);

        executeStatement(sql);
    }

    protected void executeStatement(String sql){
        try {
            conn =
                DriverManager.getConnection("jdbc:mysql://192.168.1.211/clspeed?" +
                    "user=clspeed&password=clspeed");

            conn.createStatement().execute(sql);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

}
