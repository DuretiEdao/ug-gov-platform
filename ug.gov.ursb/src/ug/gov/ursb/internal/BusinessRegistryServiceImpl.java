package ug.gov.ursb.internal;

import java.util.ServiceLoader;
import ug.gov.core.api.BusinessRegistryService;
import ug.gov.core.api.IdentityService;
import ug.gov.core.api.TaxService;
import ug.gov.core.audit.AuditLog;

public class BusinessRegistryServiceImpl implements BusinessRegistryService {
    private static final BusinessRegistryServiceImpl INSTANCE = new BusinessRegistryServiceImpl();
    public static BusinessRegistryService provider() { return INSTANCE; }

    private int counter = 80000;

    @Override public String registerBusiness(String name, String ownerNin) {
        IdentityService id = ServiceLoader.load(IdentityService.class).findFirst().orElseThrow();
        TaxService tax = ServiceLoader.load(TaxService.class).findFirst().orElseThrow();

        if (!id.isValid(ownerNin))
            throw new IllegalArgumentException("Owner NIN is not valid: " + ownerNin);
        String tin = tax.hasTin(ownerNin) ? "existing TIN" : tax.registerTaxpayer(ownerNin);
        String reg = "UG-" + (++counter);
        AuditLog.record("URSB", "Registered '" + name + "' as " + reg
                + " (owner " + ownerNin + ", " + tin + ")");
        return reg;
    }
}