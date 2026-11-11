package ug.gov.passport.internal;

import java.util.ServiceLoader;
import ug.gov.core.api.Citizen;
import ug.gov.core.api.IdentityService;
import ug.gov.core.api.PassportService;
import ug.gov.core.audit.AuditLog;
import ug.gov.nira.restricted.BiometricVerifier;

public class PassportServiceImpl implements PassportService {
    private static final PassportServiceImpl INSTANCE = new PassportServiceImpl();
    public static PassportService provider() { return INSTANCE; }

    private int serial = 500000;

    @Override public String issuePassport(String nin) {
        IdentityService identity = ServiceLoader.load(IdentityService.class).findFirst().orElseThrow();
        Citizen c = identity.lookup(nin)
                .orElseThrow(() -> new IllegalArgumentException("Unknown citizen " + nin));

        String simulatedScan = nin.equals("CM85001234ABCD") ? "a91f3c"
                : nin.equals("CM90005678EFGH") ? "77be02" : "bad000";
        if (!BiometricVerifier.verifyFingerprint(nin, simulatedScan))
            throw new SecurityException("Biometric verification failed for " + nin);

        String no = "B" + (++serial);
        AuditLog.record("PASSPORT", "Passport " + no + " issued to " + c.fullName());
        return no;
    }
}