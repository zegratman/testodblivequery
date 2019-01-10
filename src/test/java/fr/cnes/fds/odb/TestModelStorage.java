package fr.cnes.fds.odb;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import fr.cnes.fds.odb.model.Branch;
import fr.cnes.fds.odb.model.Leaf;
import fr.cnes.fds.odb.model.Root;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class TestModelStorage {

    @Test
    public void testStoreRoot() {

        try {

            // Get graph
            OrientGraph graph = OdbConnection.getInstance().getOrientGraph();

            // Create Root
            Root root = new Root("test_root");

            // Add branches and leaves
            root.getBranches().add(new Branch("branch1"));
            root.getBranches().add(new Branch("branch2"));

            Branch branch = new Branch("branch3");
            branch.getBranches().add(new Branch("branch4"));
            branch.getLeaves().add(new Leaf("leaf1", 1L));
            branch.getLeaves().add(new Leaf("leaf2", 2L));
            root.getBranches().add(branch);

            // Storage
            root.toGraph(graph);

            assertNotNull(root.getOdbId());
            assertNotNull(branch.getOdbId());

            graph.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testResetDb() {
        try {

            // Get graph
            OrientGraph graph = OdbConnection.getInstance().getOrientGraph();

            graph.getVertices().forEach(vertex -> graph.removeVertex(vertex));

            graph.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
