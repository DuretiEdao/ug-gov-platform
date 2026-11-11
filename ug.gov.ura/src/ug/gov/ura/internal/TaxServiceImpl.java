package ug.gov.ura.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import ug.gov.core.api.IdentityService;
import ug.gov.core.api.TaxService;
import ug.gov.core.audit.AuditLog;

public class TaxServiceImpl implements TaxService {
    private static final TaxServiceImpl INSTANCE = new TaxServiceImpl();
    public static TaxService provider() { return INSTANCE; }

    private final Map<String, String> tinByNin = new HashMap<>();
    private int counter = 1000;

    private IdentityService identity() {
        return ServiceLoader.load(IdentityService.class).findFirst()
                .orElseThrow(() -> new IllegalStateException("No identity provider deployed"));
    }

    @Override public String registerTaxpayer(String nin) {
        if (!identity().isValid(nin))
            throw new IllegalArgumentException("Cannot register taxpayer: invalid NIN " + nin);
        String tin = tinByNin.computeIfAbsent(nin, n -> "1" + (++counter) + "UG");
        AuditLog.record("URA", "TIN " + tin + " issued to " + nin);
        return tin;
    }

    @Override public boolean hasTin(String nin) { return tinByNin.containsKey(nin); }

    @Override public double computeMonthlyPaye(double income) {
        if (income <= 235_000) return 0;
        if (income <= 335_000) return (income - 235_000) * 0.10;
        if (income <= 410_000) return 10_000 + (income - 335_000) * 0.20;
        return 25_000 + (income - 410_000) * 0.30;
    }
}