package fr.cnes.fds.odb;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import fr.cnes.fds.odb.model.Root;
import org.junit.Test;

import java.io.IOException;

public class TestTreeGenerator {

    @Test
    public void testTreeGenerator() {

        // Tree generator
        TreeGenerator treeGenerator = new TreeGenerator(1000, 10);

        // generate
        Root root = treeGenerator.generateTree();

        // Get graph
        try {
            OrientGraph graph = OdbConnection.getInstance().getOrientGraph();
            root.toGraph(graph);
            graph.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
