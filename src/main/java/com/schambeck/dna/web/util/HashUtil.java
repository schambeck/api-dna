package com.schambeck.dna.web.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.String.format;

public final class HashUtil {

    private static final String SHA_256 = "SHA-256";
    private static HashUtil INSTANCE;
    private final String algorithm;
    private final MessageDigest digest;

    private HashUtil(String algorithm) {
        this.algorithm = algorithm;
        this.digest = newDigest();
    }

    public static HashUtil getInstance() {
        return getInstance(SHA_256);
    }

    static HashUtil getInstance(String algorithm) {
        if (INSTANCE == null) {
            INSTANCE = new HashUtil(algorithm);
        }
        return INSTANCE;
    }

    public String hash(String[] dna) {
        String joined = String.join("", dna);
        return hash(joined.getBytes(StandardCharsets.UTF_8));
    }

    public synchronized String hash(byte[] bytes) {
        digest.reset();
        digest.update(bytes);
        byte[] hash = digest.digest();
        return toHex(hash).toUpperCase();
    }

    private MessageDigest newDigest() {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(format("Fail to create new MessageDigest of %s", algorithm), e);
        }
    }

    private String toHex(byte[] hash) {
        final StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            final String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
