import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.*;

/**
 *
 */
public class CLSpeedRunner {
    static Integer THREADS = 10;

    private static Connection getConnectionFactory() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("192.168.1.211");
        factory.setPort(5672);
        return factory.newConnection();
    }

    public static void main(String[] args) throws IOException, TimeoutException, ExecutionException, InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        Calendar cal = Calendar.getInstance();
        String currDate = dateFormat.format(cal.getTime());

        CreateMySQLTable createMySQLTable = new CreateMySQLTable();

        createMySQLTable.createNewTable(currDate);

        int messageCount;

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        Connection conn = getConnectionFactory();

        Channel channel = conn.createChannel();

        channel.basicQos(1);

        messageCount = channel.queueDeclare("clspeed", true, false, false, null).getMessageCount();

        channel.close();

        for (int i = 0; i < messageCount; i++) {
            executor.submit(new CLSpeed(conn, currDate));
        }
    }

}


