import ug.gov.core.api.IdentityService;

module ug.gov.nira {
    requires transitive ug.gov.core;
    exports ug.gov.nira.restricted to ug.gov.passport;
    provides IdentityService with ug.gov.nira.internal.IdentityServiceImpl;
}