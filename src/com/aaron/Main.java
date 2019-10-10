package com.aaron;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    private static long counter = 0L;

    public static void main(String[] args) {

        try {
            System.out.println("Starting entropy pool...");
            EntropyPool entropyPool = new EntropyPool(MessageDigest.getInstance("SHA-512"));
            System.out.println("Initial Entropy: " + bytesToHex(entropyPool.getEntropy()));

            MouseMixer mouseMixer = new MouseMixer(entropyPool);
            new Thread(mouseMixer::run).start();
            System.out.println("Please move mouse randomly");

            System.out.println("Press q to exit program");
            while (true) {
                Scanner in = new Scanner(System.in);
                System.out.printf("How many chunks of bytes(%d) do you want: ", entropyPool.getEntropy().length);
                String input = in.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    System.exit(0);
                }

                try {
                    int chunks = Integer.parseInt(input);
                    MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
                    StringBuilder entropySb = new StringBuilder(new String(entropyPool.getEntropy()));
                    for (int i = 0; i < chunks; i++) {
                        entropySb.append(counter++);
                        System.out.write(sha512.digest(entropySb.toString().getBytes()));
                    }
                    System.out.println(System.lineSeparator());
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter an integer");
                    continue;
                }
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

    }

    public static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
