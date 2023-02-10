/**
 * @author Nagarjun Kepulu
 */

/***************************************************/

package com.oracle.ocsdm.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    public static String generateName() {
        Faker faker = new Faker();
        return "Name " + faker.regexify("[A-Za-z0-9 ,_-]{10}");
    }

    public static String generateDescription() {
        Faker faker = new Faker();
        return "Description " + faker.regexify("[ A-Za-z0-9_@./#&+-]{50}");
    }

    public static String generateIPv4Address() {
        Faker faker = new Faker();
        return faker.internet().ipV4Address();
    }

    public static String generateIPv6Address() {
        Faker faker = new Faker();
        return faker.internet().ipV6Address();
    }

    public int generateNumberBetween() {
        Faker faker = new Faker();
        //return faker.number().numberBetween(int x, int y);
        return 0;
    }
}
