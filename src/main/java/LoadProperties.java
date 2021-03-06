import java.io.*;
import java.util.Properties;

public class LoadProperties {

    private static LoadProperties instance = null;
    private static Properties prop = new Properties();

    /**
     * Singleton design pattern. My original implementation was loading the properties file
     * every time the class was called. Didn't notice a performance hit, but seemed like
     * a bad practice.
     */
    public static LoadProperties getInstance() {
        if (instance == null) {
            instance = new LoadProperties();
        }

        return instance;
    }

    private LoadProperties() {
        InputStream input = null;

        try {
            if (new File("resources/properties/local.properties").exists()) {
                input = new FileInputStream("resources/properties/local.properties");
                System.out.println("Using internal properties file");
            } else if (new File("local.properties").exists()) {
                input = new FileInputStream("local.properties");
                System.out.println("Using external properties file");
            } else {
                throw new FileNotFoundException();
            }


            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Properties getProperties(){
        return prop;
    }

}
