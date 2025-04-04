package com.baeldung.library.util;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class for generating random Strings
 *
 * @author speter555
 * @since 0.1.0
 */
public class RandomUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtil.class);

    /** Constant <code>DATE_2013_01_01=1356998400000l</code> */
    public static long DATE_2013_01_01 = 1356998400000l;

    private static final int RADIX = 36;
    // [0-9a-zA-Z]
    /** Constant <code>MAX_NUM_SYS=62</code> */
    public static final int MAX_NUM_SYS = 62;
    // [a-z]
    /** Constant <code>LOWERCASE</code> */
    public static final char[] LOWERCASE;
    // [A-Z]
    /** Constant <code>UPPERCASE</code> */
    public static final char[] UPPERCASE;
    // [0-9a-zA-Z]
    /** Constant <code>ALL_LETTER</code> */
    public static final char[] ALL_LETTER;
    /** Constant <code>ALL_LETTER_STRING=""</code> */
    public static final String ALL_LETTER_STRING;
    /** Constant <code>generatedIndex=0</code> */
    public static int generatedIndex = 0;
    /** Constant <code>PID=</code> */
    public static final int PID;
    /** Constant <code>PID62=""</code> */
    protected static final String PID62;
    /** Constant <code>PID36=""</code> */
    protected static final String PID36;

    /* init */
    static {
        UPPERCASE = new char[26];
        for (int i = 65; i < 65 + 26; i++) {
            UPPERCASE[i - 65] = (char) i;
        }
        LOWERCASE = new char[26];
        for (int i = 97; i < 97 + 26; i++) {
            LOWERCASE[i - 97] = (char) i;
        }
        ALL_LETTER = new char[MAX_NUM_SYS];
        for (int i = 48; i < 48 + 10; i++) {
            ALL_LETTER[i - 48] = (char) i;
        }
        for (int i = 10; i < 10 + 26; i++) {
            ALL_LETTER[i] = UPPERCASE[i - 10];
        }
        for (int i = 10 + 26; i < 10 + 26 + 26; i++) {
            ALL_LETTER[i] = LOWERCASE[i - 10 - 26];
        }
        ALL_LETTER_STRING = new String(ALL_LETTER);
        PID = Integer.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        /* pid */
        // maxpid = 238327
        PID62 = paddL(convertToRadix(PID, MAX_NUM_SYS), 3, '0').substring(0, 3);
        // maxpid
        PID36 = paddL(convertToRadix(PID, RADIX), 3, '0').substring(0, 3);
    }

    private static final Random RANDOM = new Random();

    /**
     * Default constructor, constructs a new object.
     */
    public RandomUtil() {
        super();
    }

    /**
     * Generates fix 16 length id.
     *
     * @return generated id
     */
    public static String generateId() {
        int xInd = getNextIndex();
        Date xDate = new Date();
        xDate.setTime(xDate.getTime() - DATE_2013_01_01);
        /* time based */
        String xRes = convertToRadix(xDate.getTime(), RADIX);
        // 8888 is sufficient with 8 characters :) 7 characters are enough for up to 2081.
        xRes = paddL(xRes, 8, '0');

        StringBuilder builder = new StringBuilder();
        builder.append(xRes);

        // nano, we truncate the last 4 characters because they change within milliseconds
        Long nano = System.nanoTime();
        String xNano = convertToRadix(nano, RADIX);
        builder.append(xNano.substring(xNano.length() - 4, xNano.length()));

        /* random */
        // 2
        builder.append(paddL(convertToRadix(RANDOM.nextInt(RADIX * RADIX), RADIX), 2, '0'));
        /* generation index */
        builder.append(paddL(convertToRadix(xInd, RADIX), 2, '0'));

        return builder.toString();
    }

    /**
     * Generates sequential index. Restarts sequence when value greater than 1295.
     *
     * @return next index
     */
    protected static synchronized int getNextIndex() {
        generatedIndex++;
        // MAX a ZZ
        if (generatedIndex > RADIX * RADIX - 1) {
            generatedIndex = 0;
        }
        return generatedIndex;
    }

    /**
     * Applies left padding to given text.
     *
     * @param str
     *            text to pad
     * @param length
     *            pad length
     * @param padd
     *            pad charachter
     * @return left-padded text
     */
    protected static String paddL(String str, int length, char padd) {
        /*
         * String result = str; while (result.length() < length) { result = padd + result; } return result;
         */
        return StringUtils.leftPad(str, length, padd);
    }

    /**
     * Converts input {@code long} to any number system. Highest available number system is 62.
     *
     * @param inNum
     *            {@code long} to convert
     * @param radix
     *            radix of the number system
     * @return converted number {@link String}
     */
    protected static String convertToRadix(long inNum, long radix) {
        if (radix == 0) {
            return null;
        }
        long dig;
        long numDivRadix;
        long num = inNum;
        String result = "";
        do {
            numDivRadix = num / radix;
            dig = ((num % radix) + radix) % radix;
            result = ALL_LETTER[(int) dig] + result;
            num = numDivRadix;
        } while (num != 0);
        return result;
    }

    /**
     * Generating random {@link String} token
     *
     * @return generated token
     */
    public static String generateToken() {
        String token = StringUtils.left(UUID.randomUUID() + generateId(), 48);
        LOGGER.debug("Generated token: [{}]", token);
        return token;
    }

}
