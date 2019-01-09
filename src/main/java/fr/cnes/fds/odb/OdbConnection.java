package fr.cnes.fds.odb;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import java.io.IOException;
import java.util.Properties;

public class OdbConnection {

    private static final String ODB_PROPS = "odb.properties";
    private static final String ODB_MODE = "remote";

    private static OdbConnection odbConnection;
    private static OrientGraphFactory ORIENT_GRAPH_FACTORY;

    private OdbConnection() {

    }

    private void init() throws IOException {
        Properties properties = new Properties();
        properties.load(OdbConnection.class.getClassLoader().getResourceAsStream(ODB_PROPS));
        StringBuilder odbUrlBuilder = new StringBuilder();
        String odbUrl = odbUrlBuilder
                .append(ODB_MODE)
                .append(":")
                .append(properties.getProperty("odb.host"))
                .append(":")
                .append(properties.getProperty("odb.port"))
                .append("/")
                .append(properties.getProperty("odb.dbname"))
                .toString();
        ORIENT_GRAPH_FACTORY = new OrientGraphFactory(odbUrl, properties.getProperty("odb.login"), properties.getProperty("odb.passwd"));
    }

    public static final OdbConnection getInstance() throws IOException {
        if (odbConnection == null) {
            odbConnection = new OdbConnection();
            odbConnection.init();
        }
        return odbConnection;
    }

    public OrientGraph getOrientGraph() {
        return ORIENT_GRAPH_FACTORY.getTx();
    }

    public void closeConnection() {
        ORIENT_GRAPH_FACTORY.close();
    }

}
