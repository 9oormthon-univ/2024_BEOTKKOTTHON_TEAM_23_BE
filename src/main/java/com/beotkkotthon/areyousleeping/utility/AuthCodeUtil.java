package com.beotkkotthon.areyousleeping.utility;

import org.springframework.stereotype.Component;

@Component
public class AuthCodeUtil {
    public static String generateAuthCode() {
        int authCodeLength = 6;

        StringBuilder authCode = new StringBuilder();

        for (int i = 0; i < authCodeLength; i++) {
            int random = (int) (Math.random() * 62);
            if (random < 10) {
                authCode.append(random);
            } else if (random < 36) {
                authCode.append((char) (random + 55));
            } else {
                authCode.append((char) (random + 61));
            }
        }
        return authCode.toString();
    }

    // 위 코드 Java로 변경
    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        int passwordLength = (int) (Math.random() * 11) + 10;

        for (int i = 0; i < passwordLength; i++) {
            int type = (int) (Math.random() * 3);
            switch (type) {
                case 0:
                    password.append((char) ((int) (Math.random() * 26) + 65));
                    break;
                case 1:
                    password.append((char) ((int) (Math.random() * 26) + 97));
                    break;
                case 2:
                    password.append((char) ((int) (Math.random() * 10) + 48));
                    break;
            }
        }
        return password.toString();
    }
}
