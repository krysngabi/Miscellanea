package com.krsoft.abovebytes.utils;

import java.util.HashMap;
import java.util.Map;

public class MiscellaneaConstants {
    private static final String PAYMENT_STATUS_VALUE = "Paiement";

    public static final Map<String, String> OPERATIONS_MAP = new HashMap<>();
    static{
        OPERATIONS_MAP.put("2", PAYMENT_STATUS_VALUE);
    }
}
