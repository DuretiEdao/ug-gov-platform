package ug.gov.nira.internal;

import java.util.Optional;
import ug.gov.core.api.Citizen;
import ug.gov.core.api.IdentityService;
import ug.gov.core.audit.AuditLog;

public class IdentityServiceImpl implements IdentityService {
    private static final IdentityServiceImpl INSTANCE = new IdentityServiceImpl();
    public static IdentityService provider() { return INSTANCE; }

    @Override public boolean isValid(String nin) {
        return NiraDatabase.citizens().containsKey(nin);
    }

    @Override public Optional<Citizen> lookup(String nin) {
        AuditLog.record("NIRA", "Identity lookup for " + nin);
        return Optional.ofNullable(NiraDatabase.citizens().get(nin));
    }
}