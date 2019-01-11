package fr.cnes.fds.odb;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class TestOdbConnection {

    @Test
    public void testConnection() {
        try {
            OdbConnection odbConnection = OdbConnection.getInstance();
            assertNotNull(odbConnection);
            OrientGraphNoTx orientGraph = odbConnection.getOrientGraphNoTx();
            assertNotNull(orientGraph);
            orientGraph.shutdown();
            odbConnection.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Connection should be OK.");
        }
    }

    @Test
    public void testAddGetRemoveVertex() {
        try {

            // Get connection and graph
            OrientGraphNoTx orientGraph = OdbConnection.getInstance().getOrientGraphNoTx();
            assertNotNull(orientGraph);

            // Adding vertex
            Vertex vertex = orientGraph.addVertex(null);
            assertNotNull(vertex);

            Object vertexId = vertex.getId();

            vertex.setProperty("name", "test_vertex");
            orientGraph.commit();

            // Getting vertex
            Iterator<Vertex> vertexIterator = orientGraph.getVertices("name", "test_vertex").iterator();
            assertTrue(vertexIterator.hasNext());
            Vertex testVertex = vertexIterator.next();
            Object otherVertexId = testVertex.getId();

            assertEquals(vertexId, otherVertexId);

            // Remove vertex
            orientGraph.removeVertex(testVertex);
            vertexIterator = orientGraph.getVertices("name", "test_vertex").iterator();
            assertFalse(vertexIterator.hasNext());

            // CLosing
            orientGraph.commit();
            orientGraph.shutdown();


        } catch (IOException e) {
            e.printStackTrace();
            fail("Connection should be OK.");
        }
    }

}
