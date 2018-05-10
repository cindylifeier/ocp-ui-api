package gov.samhsa.ocp.ocpuiapi.service;

import java.util.Arrays;

public enum LookUpTypeEnum {
    //In alphabetical order
    ACTION_PARTICIPANT_ROLE,
    ACTION_PARTICIPANT_TYPE,
    ADDRESSTYPE,
    ADDRESSUSE,
    ADMINISTRATIVEGENDER,
    APPOINTMENT_STATUS,
    APPOINTMENT_TYPE,
    APPOINTMENT_PARTICIPATION_STATUS,
    APPOINTMENT_PARTICIPANT_TYPE,
    APPOINTMENT_PARTICIPATION_TYPE,
    APPOINTMENT_PARTICIPANT_REQUIRED,
    CARETEAMCATEGORY,
    CARETEAMREASON,
    CARETEAMSTATUS,
    COMMUNICATION_STATUS,
    COMMUNICATION_CATEGORY,
    COMMUNICATION_NOT_DONE_REASON,
    COMMUNICATION_MEDIUM,
    CONSENT_ACTION,
    CONSENT_CATEGORY,
    CONSENT_STATE_CODES,
    DATE_RANGE,
    DEFINITION_TOPIC,
    FLAG_CATEGORY,
    FLAG_STATUS,
    HEALTHCARESERVICECATEGORY,
    HEALTHCARESERVICETYPE,
    HEALTHCARESERVICEREFERRALMETHOD,
    HEALTHCARESERVICESPECIALITY,
    HEALTHCARESERVICESTATUS,
    LANGUAGE,
    LOCATIONIDENTIFIERSYSTEM,
    LOCATIONPHYSICALTYPE,
    LOCATIONSTATUS,
    ORGANIZATIONIDENTIFIERSYSTEM,
    ORGANIZATIONSTATUS,
    PARTICIPANTROLE,
    PARTICIPANTTYPE,
    PATIENTIDENTIFIERSYSTEM,
    PRACTITIONERIDENTIFIERSYSTEM,
    PRACTITIONERROLES,
    PROVIDER_ROLE,
    PROVIDER_SPECIALTY,
    PUBLICATION_STATUS,
    PURPOSE_OF_USE,
    RELATED_ARTIFACT_TYPE,
    RELATEDPERSONPATIENTRELATIONSHIPTYPES,
    REQUEST_INTENT,
    REQUEST_PRIORITY,
    RESOURCE_TYPE,
    SECURITY_LABEL,
    SECURITY_ROLE_TYPE,
    TASK_PERFORMER_TYPE,
    TASK_STATUS,
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
