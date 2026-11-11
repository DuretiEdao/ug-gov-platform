package ug.gov.nira.restricted;

import ug.gov.core.audit.AuditLog;
import ug.gov.nira.internal.BiometricAccess;

public final class BiometricVerifier {
    private BiometricVerifier() { }

    public static boolean verifyFingerprint(String nin, String scannedHash) {
        AuditLog.record("NIRA-BIOMETRIC", "Fingerprint check for " + nin);
        return scannedHash != null && scannedHash.equals(BiometricAccess.hashFor(nin));
    }
}