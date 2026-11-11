import ug.gov.core.api.IdentityService;
import ug.gov.core.api.PassportService;

module ug.gov.passport {
    requires ug.gov.core;
    requires ug.gov.nira;
    uses IdentityService;
    provides PassportService with ug.gov.passport.internal.PassportServiceImpl;
}