package Config;

import Interfaces.ConfigInterface;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ATMConfig implements ConfigInterface {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ATMConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Unable to find " + CONFIG_FILE);
            } else {
                props.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getDB_URL() {
        return props.getProperty("DB_URL");
    }

    @Override
    public String getDB_USER() {
        return props.getProperty("DB_USER");
    }

    @Override
    public String getDB_PASSWORD() {
        return props.getProperty("DB_PASSWORD");
    }

}
