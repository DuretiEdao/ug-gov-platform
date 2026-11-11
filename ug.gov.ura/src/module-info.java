import ug.gov.core.api.IdentityService;
import ug.gov.core.api.TaxService;

module ug.gov.ura {
    requires ug.gov.core;
    uses IdentityService;
    provides TaxService with ug.gov.ura.internal.TaxServiceImpl;
}