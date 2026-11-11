package ug.gov.core.audit;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public final class AuditLog {
    private AuditLog() { }

    public static void record(String module, String action) {
        System.out.printf("   [AUDIT %s] %-16s %s%n",
                LocalTime.now().truncatedTo(ChronoUnit.SECONDS), module, action);
    }
}