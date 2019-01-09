package fr.cnes.fds.odb;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestOdbConnection {

    @Test
    public void testConnection() {
        try {
            OdbConnection odbConnection = OdbConnection.getInstance();
            assertNotNull(odbConnection);
            OrientGraph orientGraph = odbConnection.getOrientGraph();
            assertNotNull(orientGraph);
            orientGraph.shutdown();
            odbConnection.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Connection should be OK.");
        }
    }

}
