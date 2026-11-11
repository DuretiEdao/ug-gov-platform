import ug.gov.core.api.*;

module ug.gov.app {
    requires ug.gov.core;
    uses IdentityService;
    uses TaxService;
    uses PassportService;
    uses BusinessRegistryService;
}