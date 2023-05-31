package org.neoment.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertiesManager {

    private Properties props;
    public PropertiesManager(String filename) throws IOException {
        var rootPath = System.getProperty("user.dir")+"/";
        var settingsPath = rootPath + filename;

        this.props = new Properties();
        this.props.load(new FileInputStream(settingsPath));
    }

    public String getPass() {
        return this.props.getProperty("passwd");
    }

    public String getUser() {
        return this.props.getProperty("user");
    }

    public String getUri() {
        return this.props.getProperty("uri");
    }
}
