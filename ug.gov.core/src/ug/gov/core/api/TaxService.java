package ug.gov.core.api;

public interface TaxService {
    String registerTaxpayer(String nin);
    boolean hasTin(String nin);
    double computeMonthlyPaye(double monthlyIncomeUgx);
}