package com.joosangah.paymentservice.common.util;

import java.util.Random;

public class EventTrigger {

    public static boolean isEventTriggered(int percentage) {
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1; // 1부터 100 사이의 난수 생성

        return randomNumber <= percentage; // 난수가 지정한 확률보다 작거나 같으면 발동
    }
}
