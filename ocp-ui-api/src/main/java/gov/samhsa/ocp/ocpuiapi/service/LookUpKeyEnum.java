package gov.samhsa.ocp.ocpuiapi.service;

import java.util.Arrays;

public enum LookUpKeyEnum {
    //In alphabetical order
    ADDRESSTYPE,
    ADDRESSUSE,
    IDENTIFIERSYSTEM,
    LOCATIONSTATUS,
    LOCATIONTYPE,
    TELECOMSYSTEM,
    TELECOMUSE,
    USPSSTATES;

    public static boolean contains(String s) {
        return Arrays.stream(values()).anyMatch(key -> key.name().equalsIgnoreCase(s));
    }

}
