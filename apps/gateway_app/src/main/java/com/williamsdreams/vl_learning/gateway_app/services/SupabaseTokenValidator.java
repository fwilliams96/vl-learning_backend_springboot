//package com.williamsdreams.vl_learning.gateway_app.services;
//
//import com.nimbusds.jose.JWSVerifier;
//import com.nimbusds.jose.crypto.MACVerifier;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//
//public class SupabaseTokenValidator {
//
//    private final String jwtSecret;
//
//    public SupabaseTokenValidator(String jwtSecret) {
//        this.jwtSecret = jwtSecret;
//    }
//
//    public JWTClaimsSet validateToken(String token) throws Exception {
//        // Parsear el token JWT
//        SignedJWT signedJWT = SignedJWT.parse(token);
//        // Crear el verificador utilizando el JWT_SECRET
//        JWSVerifier verifier = new MACVerifier(jwtSecret);
//        // Verificar la firma
//        if (!signedJWT.verify(verifier)) {
//            throw new RuntimeException("Invalid token signature");
//        }
//        // Retornar las claims del token
//        return signedJWT.getJWTClaimsSet();
//    }
//}
