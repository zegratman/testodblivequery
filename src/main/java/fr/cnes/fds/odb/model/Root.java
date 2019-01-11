package fr.cnes.fds.odb.model;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.util.ArrayList;
import java.util.List;

public class Root implements OdbStorable {

    private String name;
    private List<Branch> branches;
    private Object odbId;

    public Root(String name) {
        this.name = name;
        branches = new ArrayList<>();
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

        graph.commit();

    }

    @Override
    public Object getOdbId() {
        return odbId;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
