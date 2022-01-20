package com.schambeck.dna.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.lang.String.format;

public class HashUtil {

    static final String SHA_256 = "SHA-256";
    private static HashUtil INSTANCE;
    private final MessageDigest digest;

    HashUtil(String algorithm) {
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(format("Fail to create new MessageDigest of %s", algorithm), e);
        }
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeBytes(dna, baos);
        return hash(baos.toByteArray());
    }

    public String hash(byte[] bytes) {
        digest.update(bytes);
        byte[] hash = digest.digest();
        return toHex(hash).toUpperCase();
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

    void writeBytes(String[] dna, OutputStream baos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(dna);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(format("Fail to convert string array to bytes: %s", Arrays.toString(dna)));
        }
    }

}
