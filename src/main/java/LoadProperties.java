/* Copyright 2008 - 2016: Thomson Reuters Global Resources. All Rights Reserved. Proprietary and Confidential
   information of TRGR Disclosure, Use or Reproduction without the written authorization of TRGR is prohibited. */

import java.io.*;
import java.util.Properties;

/**
 *
 */
public class LoadProperties {

    private static LoadProperties instance = null;
    private static Properties prop = new Properties();

    public static LoadProperties getInstance() {
        if (instance == null) {
            instance = new LoadProperties();
        }

        return instance;
    }

    private LoadProperties() {
        InputStream input = null;

        try {

            if (new File("src/main/config/local.properties").exists()) {
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
