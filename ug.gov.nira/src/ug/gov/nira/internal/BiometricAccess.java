package ug.gov.nira.internal;

public final class BiometricAccess {
    private BiometricAccess() { }
    public static String hashFor(String nin) { return NiraDatabase.biometricHash(nin); }
}