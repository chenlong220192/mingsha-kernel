package site.mingsha.kernel.core.exception;

/**
 * @author Ming Sha
 * @create: 2020-05-21 19:56
 */
public class BizException extends RuntimeException {

    /**
     * 异常编号
     */
    private String code;

    /**
     * 异常错误信息，由实际抛出异常的类定义。
     */
    private String message;

    /**
     *
     * @param message
     */
    public BizException(String message) {
        super(message);
        this.message = message;
    }

    /**
     *
     * @param code
     * @param message
     */
    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
