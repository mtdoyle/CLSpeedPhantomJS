/* Copyright 2008 - 2016: Thomson Reuters Global Resources. All Rights Reserved. Proprietary and Confidential
   information of TRGR Disclosure, Use or Reproduction without the written authorization of TRGR is prohibited. */

import java.io.*;
import java.util.Properties;

/**
 *
 */
public class LoadProperties {

    public Properties getProperties(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src/main/config/local.properties");

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

        return prop;
    }

}
