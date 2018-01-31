package gov.samhsa.ocp.ocpuiapi.service;

import java.util.Arrays;

public enum LookUpTypeEnum {
    //In alphabetical order
    ADDRESSTYPE,
    ADDRESSUSE,
    ADMINISTRATIVEGENDER,
    HEALTHCARESERVICECATEGORY,
    HEALTHCARESERVICETYPE,
    LANGUAGE,
    LOCATIONIDENTIFIERSYSTEM,
    LOCATIONPHYSICALTYPE,
    LOCATIONSTATUS,
    LOCATIONTYPE,
    ORGANIZATIONIDENTIFIERSYSTEM,
    ORGANIZATIONSTATUS,
    PATIENTIDENTIFIERSYSTEM,
    PRACTITIONERIDENTIFIERSYSTEM,
    PRACTITIONERROLES,
    TELECOMSYSTEM,
    TELECOMUSE,
    USCOREBIRTHSEX,
    USCOREETHNICITY,
    USCORERACE,
    USPSSTATES;

    public static boolean contains(String s) {
        return Arrays.stream(values()).anyMatch(key -> key.name().equalsIgnoreCase(s));
    }

}
