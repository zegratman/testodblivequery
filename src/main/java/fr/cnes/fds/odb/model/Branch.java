package fr.cnes.fds.odb.model;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.util.ArrayList;
import java.util.List;

public class Branch implements OdbStorable {

    private String name;
    private List<Branch> branches;
    private List<Leaf> leaves;
    private Object odbId;

    public Branch(String name) {
        this.name = name;
        branches = new ArrayList<>();
        leaves = new ArrayList<>();
    }

    @Override
    public void toGraph(OrientGraphNoTx graph) {

        // Add main vertex
        Vertex vertex = graph.addVertex(getOdbClass(), "name", name);
        odbId = vertex.getId();

        // Add branches
        branches.forEach(branch -> {
            branch.toGraph(graph);
            graph.addEdge(null, vertex, graph.getVertex(branch.getOdbId()), "refers");
        });

        // Add leaves
        leaves.forEach(leaf -> {
            leaf.toGraph(graph);
            graph.addEdge(null, vertex, graph.getVertex(leaf.getOdbId()), "owns");
        });

        /// VERY IMPORTANT : allows big computation.
        graph.commit();

    }

    @Override
    public Object getOdbId() {
        return odbId;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public List<Leaf> getLeaves() {
        return leaves;
    }

}
