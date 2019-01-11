package fr.cnes.fds.odb;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.io.IOException;
import java.util.Properties;

public class OdbConnection {

    private static final String ODB_PROPS = "odb.properties";
    private static final String ODB_MODE = "remote";

    private static OdbConnection odbConnection;
    private static OrientGraphFactory ORIENT_GRAPH_FACTORY;
    private static ODatabaseDocumentTx ORIENT_DB_DOC;

    private OdbConnection() {

    }

    private void init() throws IOException {
        Properties properties = new Properties();
        properties.load(OdbConnection.class.getClassLoader().getResourceAsStream(ODB_PROPS));
        StringBuilder odbUrlBuilder = new StringBuilder();
        String login = properties.getProperty("odb.login");
        String passwd = properties.getProperty("odb.passwd");
        String odbUrl = odbUrlBuilder
                .append(ODB_MODE)
                .append(":")
                .append(properties.getProperty("odb.host"))
                .append(":")
                .append(properties.getProperty("odb.port"))
                .append("/")
                .append(properties.getProperty("odb.dbname"))
                .toString();
        ORIENT_GRAPH_FACTORY = new OrientGraphFactory(odbUrl, login, passwd);
        ORIENT_DB_DOC = new ODatabaseDocumentTx(odbUrl).open(login, passwd);
    }

    public static final OdbConnection getInstance() throws IOException {
        if (odbConnection == null) {
            odbConnection = new OdbConnection();
            odbConnection.init();
        }
        return odbConnection;
    }

    public OrientGraphNoTx getOrientGraphNoTx() {
        return ORIENT_GRAPH_FACTORY.getNoTx();
    }

    public ODatabaseDocumentTx getOrientDbDoc() {
        return ORIENT_DB_DOC;
    }

    public void closeConnection() {
        ORIENT_GRAPH_FACTORY.close();
        ORIENT_DB_DOC.close();
    }

}
