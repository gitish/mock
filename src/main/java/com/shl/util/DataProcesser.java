package com.shl.util;

import java.util.HashMap;
import java.util.Map;

public class DataProcesser {
    /*
     * Private constructor to avoid instantiation
     */
    private DataProcesser() {

    }

    private static DataProcesser _dataProcesser = null;
    private static Map<String, String> dataMap = new HashMap<String, String>();
    /*
     * Early initialisation to avoid synchronisation
     */
    public static final String USER = "USER";
    public static final String USER_STATIC = "USER-STATIC";

    public static final String SUBS = "SUBS";
    public static final String SUBS_STATIC = "SUBS-STATIC";

    public static final String _MAIL_TO = "MAIL_TO";


    /*
     * initialize the
     */
    public static DataProcesser getInstance() {
        if (_dataProcesser == null) {
            synchronized (DataProcesser.class) {
                if (_dataProcesser == null) {
                    _dataProcesser = new DataProcesser();
                    init();
                }
            }
        }
        return _dataProcesser;
    }

    private static void init() {
        dataMap.put(USER, "1234,Shailendra,Shail,E151PJ,07448148978,mr.shailendra.shail@gmail.com");
        dataMap.put(USER_STATIC, "12345,Shailja,Kumari,E151PJ,07448148978,shailja.shail@gmail.com");
        dataMap.put(SUBS, "S1234,1234,BroadBand");
        dataMap.put(SUBS_STATIC, "S12345,1234,BroadBand");
    }

    public String getData(String key) {
        return dataMap.get(key);
    }

    public void setData(String key, String data) {
        dataMap.put(key, data);
    }

}
