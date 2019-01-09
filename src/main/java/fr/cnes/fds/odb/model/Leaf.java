package fr.cnes.fds.odb.model;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class Leaf implements OdbStorable {

    private String name;
    private Long data;
    private Object odbId;

    public Leaf(String name, Long data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public void toGraph(OrientGraph graph) {

        // Add main vertex
        Vertex vertex = graph.addVertex(getOdbClass(), "name", name, "data", data);
        odbId = vertex.getId();

    }

    @Override
    public Object getOdbId() {
        return odbId;
    }
}
