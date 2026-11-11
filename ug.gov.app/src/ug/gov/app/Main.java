package ug.gov.app;

import java.lang.module.ModuleDescriptor;
import java.util.ServiceLoader;
import java.util.TreeSet;
import ug.gov.core.api.*;

public class Main {
    public static void main(String[] args) {
        banner("1. Modules resolved at startup");
        ModuleLayer.boot().modules().stream()
                .filter(m -> m.getName().startsWith("ug.gov"))
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .forEach(m -> {
                    ModuleDescriptor d = m.getDescriptor();
                    System.out.println(" * " + m.getName());
                    d.exports().forEach(e -> System.out.println("     exports " + e.source()
                            + (e.isQualified() ? "  TO " + new TreeSet<>(e.targets()) : "  (to everyone)")));
                });

        IdentityService id       = ServiceLoader.load(IdentityService.class).findFirst().orElseThrow();
        TaxService tax           = ServiceLoader.load(TaxService.class).findFirst().orElseThrow();
        PassportService passport = ServiceLoader.load(PassportService.class).findFirst().orElseThrow();
        BusinessRegistryService biz = ServiceLoader.load(BusinessRegistryService.class).findFirst().orElseThrow();

        banner("2. Cross-department workflow through public contracts");
        String sarah = "CM85001234ABCD", david = "CM90005678EFGH", grace = "CF78009999WXYZ";

        id.lookup(sarah).ifPresent(c -> System.out.println(" Citizen: " + c));
        System.out.println(" TIN: " + tax.registerTaxpayer(sarah));
        System.out.printf(" Monthly PAYE on UGX 500,000 = UGX %,.0f%n", tax.computeMonthlyPaye(500_000));
        System.out.println(" Passport: " + passport.issuePassport(david));
        System.out.println(" Business: " + biz.registerBusiness("Kira Fresh Produce Ltd", sarah));

        banner("3. Business rules are enforced across modules");
        attempt("Tax registration with fake NIN", () -> tax.registerTaxpayer("FAKE123"));
        attempt("Passport with failed biometric ", () -> passport.issuePassport(grace));

        banner("4. Strong encapsulation: the JVM blocks unauthorised access");
        reflectIntoModule("ug.gov.nira.internal.IdentityServiceImpl");
        reflectIntoModule("ug.gov.nira.restricted.BiometricVerifier");
    }

    private static void reflectIntoModule(String className) {
        try {
            Class<?> c = Class.forName(className);
            c.getDeclaredConstructors()[0].newInstance();
            System.out.println(" !! UNEXPECTED: access to " + className + " was allowed");
        } catch (Exception e) {
            System.out.println(" BLOCKED " + className + "\n     -> "
                    + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void attempt(String label, Runnable r) {
        try { r.run(); System.out.println(" " + label + " -> allowed"); }
        catch (RuntimeException e) {
            System.out.println(" " + label + " -> REJECTED (" + e.getMessage() + ")");
        }
    }

    private static void banner(String t) { System.out.println("\n=== " + t + " ==="); }
}