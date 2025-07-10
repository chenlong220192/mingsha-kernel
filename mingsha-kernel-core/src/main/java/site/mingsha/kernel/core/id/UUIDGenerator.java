package site.mingsha.kernel.core.id;

import java.util.UUID;

/**
 * @author mingsha
 * @date: 2025-07-10
 */
public class UUIDGenerator {

    /**
     * 
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

}
