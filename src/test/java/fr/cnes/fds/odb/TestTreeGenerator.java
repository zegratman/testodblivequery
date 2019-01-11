package fr.cnes.fds.odb;

import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import fr.cnes.fds.odb.model.Root;
import org.junit.Test;

import java.io.IOException;

public class TestTreeGenerator {

    @Test
    public void testTreeGenerator() {

        // Tree generator
        TreeGenerator treeGenerator = new TreeGenerator(500000, 15);

        // generate
        Root root = treeGenerator.generateTree();

        // Get graph
        try {
            OrientGraphNoTx graph = OdbConnection.getInstance().getOrientGraphNoTx();
            long initTime = System.currentTimeMillis();
            root.toGraph(graph);
            System.out.println("Time spent : " + (System.currentTimeMillis() - initTime));
            graph.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
