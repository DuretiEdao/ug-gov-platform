package ug.gov.nira.internal;

import java.util.Map;
import ug.gov.core.api.Citizen;

final class NiraDatabase {
    private static final Map<String, Citizen> CITIZENS = Map.of(
            "CM85001234ABCD", new Citizen("CM85001234ABCD", "Nakato Sarah",   1985),
            "CM90005678EFGH", new Citizen("CM90005678EFGH", "Okello David",   1990),
            "CF78009999WXYZ", new Citizen("CF78009999WXYZ", "Namubiru Grace", 1978));

    private static final Map<String, String> BIOMETRIC_HASH = Map.of(
            "CM85001234ABCD", "a91f3c", "CM90005678EFGH", "77be02", "CF78009999WXYZ", "c40d5e");

    static Map<String, Citizen> citizens() { return CITIZENS; }
    static String biometricHash(String nin) { return BIOMETRIC_HASH.get(nin); }
}