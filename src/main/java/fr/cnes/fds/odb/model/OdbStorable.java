package fr.cnes.fds.odb.model;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public interface OdbStorable {

    default String getOdbClass() {
      return "class:" + this.getClass().getSimpleName();
    }

    void toGraph(OrientGraph graph);

    Object getOdbId();

}
