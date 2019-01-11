package fr.cnes.fds.odb;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OLiveQuery;
import com.orientechnologies.orient.core.sql.query.OLiveResultListener;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;

public class TestBranchLiveQuery {

    @Test
    public void testBranchLiveQuery() {

        // Get connection
        try {
            ODatabaseDocumentTx orientDbDoc = OdbConnection.getInstance().getOrientDbDoc();

            OLiveResultListener listener = new BranchEventListener();

            List<ODocument> results =
                    orientDbDoc
                            .query(new OLiveQuery<ODocument>("LIVE SELECT * FROM Branch WHERE name LIKE '%25%42%'", listener));

            int token = results.get(0).field("token");
            System.out.println(token);

            try {
                sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // unsub
                orientDbDoc.command(new OCommandSQL("live unsubscribe " + token)).execute();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
