package io.sskrishna.rest.util;

import java.util.UUID;

public class IdGenerator {
    public static String id() {
        return UUID.randomUUID().toString();
    }

    public static String id(int length) {
        String temp = UUID.randomUUID().toString();
        while (temp.length() < length) {
            temp += UUID.randomUUID().toString();
        }
        return temp.substring(0, length).replaceAll("-", "");
    }

    public static String testId() {
        String id = id();
        return "(TEST)" + IdGenerator.id().substring(id.length() - 25);
    }
}
