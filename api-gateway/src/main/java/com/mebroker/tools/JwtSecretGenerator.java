package com.mebroker.tools;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Base64URL (safe for JWT)
        String secret = Encoders.BASE64URL.encode(key.getEncoded());

        System.out.println(secret);
    }
}
