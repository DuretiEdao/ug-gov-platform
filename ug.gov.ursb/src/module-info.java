import ug.gov.core.api.BusinessRegistryService;
import ug.gov.core.api.IdentityService;
import ug.gov.core.api.TaxService;

module ug.gov.ursb {
    requires ug.gov.core;
    uses IdentityService;
    uses TaxService;
    provides BusinessRegistryService with ug.gov.ursb.internal.BusinessRegistryServiceImpl;
}