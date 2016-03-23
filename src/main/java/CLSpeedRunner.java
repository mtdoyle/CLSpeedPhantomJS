import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.*;

/**
 *
 */
public class CLSpeedRunner {
    static Integer THREADS = 10;
    static LoadProperties loadProperties = new LoadProperties();
    static Properties properties = loadProperties.getProperties();

    private static Connection getConnectionFactory() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(properties.getProperty("rabbitmqUsername"));
        factory.setPassword(properties.getProperty("rabbitmqPassword"));
        factory.setVirtualHost(properties.getProperty("rabbitmqVirtualHost"));
        factory.setHost(properties.getProperty("rabbitmqServer"));
        factory.setPort(Integer.valueOf(properties.getProperty("rabbitmqPort")));
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException, TimeoutException, ExecutionException, InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        Calendar cal = Calendar.getInstance();
        String currDate = dateFormat.format(cal.getTime());

        CreateMySQLTable createMySQLTable = new CreateMySQLTable();

        createMySQLTable.createNewResultsTable(currDate);
        createMySQLTable.createNewBadAddressesTable(currDate);

        int messageCount;

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        Connection conn = getConnectionFactory();

        Channel channel = conn.createChannel();

        channel.basicQos(1);

        messageCount = channel.queueDeclare(properties.getProperty("rabbitmqChannel"), true, false, false, null).getMessageCount();

        channel.close();

        for (int i = 0; i < messageCount; i++) {
            executor.submit(new CLSpeed(conn, currDate));
        }
    }

}


