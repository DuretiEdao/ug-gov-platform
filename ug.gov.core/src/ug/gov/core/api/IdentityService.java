package ug.gov.core.api;

import java.util.Optional;

public interface IdentityService {
    boolean isValid(String nin);
    Optional<Citizen> lookup(String nin);
}