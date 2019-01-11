package fr.cnes.fds.odb.model;

import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

public interface OdbStorable {

    default String getOdbClass() {
      return "class:" + this.getClass().getSimpleName();
    }

    void toGraph(OrientGraphNoTx graph);

    Object getOdbId();

}
