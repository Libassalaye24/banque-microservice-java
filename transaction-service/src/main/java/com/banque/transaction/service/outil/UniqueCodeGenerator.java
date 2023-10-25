package com.banque.transaction.service.outil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UniqueCodeGenerator {

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final int RANDOM_CODE_LENGTH = 4;

    public static String generateUniqueCodeByDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String timestamp = dateFormat.format(new Date());

        StringBuilder randomCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < RANDOM_CODE_LENGTH; i++) {
            randomCode.append(random.nextInt(10)); // Generate a random digit (0-9)
        }
        return timestamp + randomCode.toString();
    }


    public static String generateUniqueCodeUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, 10);
    }



}
