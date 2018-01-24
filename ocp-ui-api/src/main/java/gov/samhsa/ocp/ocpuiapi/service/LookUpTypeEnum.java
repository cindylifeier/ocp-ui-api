package gov.samhsa.ocp.ocpuiapi.service;

import java.util.Arrays;

public enum LookUpTypeEnum {
    //In alphabetical order
    ADDRESSTYPE,
    ADDRESSUSE,
    LOCATIONIDENTIFIERSYSTEM,
    LOCATIONSTATUS,
    LOCATIONTYPE,
    LOCATIONPHYSICALTYPE,
    ORGANIZATIONIDENTIFIERSYSTEM,
    PATIENTIDENTIFIERSYSTEM,
    PRACTITIONERIDENTIFIERSYSTEM,
    PRACTITIONERROLES,
    TELECOMSYSTEM,
    TELECOMUSE,
    USPSSTATES;

    public static boolean contains(String s) {
        return Arrays.stream(values()).anyMatch(key -> key.name().equalsIgnoreCase(s));
    }

}
