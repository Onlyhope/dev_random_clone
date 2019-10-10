package com.aaron;

import java.security.MessageDigest;
import java.util.Random;

public class EntropyPool {

    private MessageDigest md;
    private byte[] entropyPool;

    public EntropyPool(MessageDigest messageDigest) {
        this.md = messageDigest;
        entropyPool = new byte[md.getDigestLength()];
    }

    public void mix(byte[] chaos) {
        for(byte b: chaos) {
            Random rand = new Random();
            int j = rand.nextInt(entropyPool.length);
            entropyPool[j] = (byte) (entropyPool[j] << 1); // Arbitrary Decision to shift 1
            entropyPool[rand.nextInt(entropyPool.length)] = b;
        }
        entropyPool = md.digest(entropyPool);
    }

    public byte[] getEntropy() {
        return entropyPool;
    }

}
