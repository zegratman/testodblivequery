package fr.cnes.fds.odb.model;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

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
    public void toGraph(OrientGraph graph) {

        // Add main vertex
        Vertex vertex = graph.addVertex(getOdbClass(), "name", name);
        odbId = vertex.getId();

        // Add branches
        branches.parallelStream().forEach(branch -> {
            branch.toGraph(graph);
            graph.addEdge(null, vertex, graph.getVertex(branch.getOdbId()), "refers");
        });

        // Add leaves
        leaves.parallelStream().forEach(leaf -> {
            leaf.toGraph(graph);
            graph.addEdge(null, vertex, graph.getVertex(leaf.getOdbId()), "owns");
        });

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
