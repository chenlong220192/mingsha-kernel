package site.mingsha.kernel.core.id;

import java.util.UUID;

/**
 * @author Ming Sha
 * @create: 2020-05-25 23:08
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
