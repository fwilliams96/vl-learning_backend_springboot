package com.williamsdreams.vl_learning.auth.application.check_expiration_date;

import com.williamsdreams.vl_learning.auth.application.find_claims.JwtClaimsFinder;
import com.williamsdreams.vl_learning.auth.domain.JWToken;
import com.williamsdreams.vl_learning.auth.domain.JwtClaims;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtExpirationDateChecker {

    private static final ZoneId MADRID_TIMEZONE = ZoneId.of("Europe/Madrid");

    private final JwtClaimsFinder jwtClaimsFinder;

    public boolean isExpired(JWToken token) {
        JwtClaims jwtClaims;
        try {
            jwtClaims = jwtClaimsFinder.find(token);
            return jwtClaims.getExpiration().isBefore(LocalDateTime.now(MADRID_TIMEZONE));
        }
        catch (SignatureException e) {
            log.error("SignatureException while checking expiration date", e);
            return true;
        }
        catch (Exception e) {
            log.error("Error while checking expiration date", e);
            return true;
        }
    }

}
