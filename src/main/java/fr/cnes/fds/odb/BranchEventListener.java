package fr.cnes.fds.odb;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.record.ORecordOperation;
import com.orientechnologies.orient.core.sql.query.OLiveResultListener;

public class BranchEventListener implements OLiveResultListener {

    @Override
    public void onLiveResult(int i, ORecordOperation oRecordOperation) throws OException {
        System.out.println("New result from server for live query " + i);
        System.out.println("operation: " + ORecordOperation.getName(oRecordOperation.type));
        System.out.println("content: "+ oRecordOperation.record);
    }

    @Override
    public void onError(int i) {
        System.out.println("Live query terminate due to error");
    }

    @Override
    public void onUnsubscribe(int i) {
        System.out.println("Live query terminate with unsubscribe");
    }
}
