package com.dopmn.ridwan.gymguy;

import com.orm.SugarRecord;

/**
 * Created by ridwan on 4/15/16.
 */
public class UserHistory extends SugarRecord {
    int count;
    String timestamp;

    public UserHistory() { }

    public UserHistory(int count, String timestamp){
        this.count = count;
        this.timestamp = timestamp;
    }
}
