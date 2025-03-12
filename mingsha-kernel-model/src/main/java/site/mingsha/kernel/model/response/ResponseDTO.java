package site.mingsha.kernel.model.response;

import java.io.Serializable;

/**
 * 响应模版
 *
 * @author Ming Sha
 * @create: 2020-05-21 17:49
 */
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 1955991841415898951L;
    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 状态码
     */
    private String  code;

    /**
     * 响应信息
     */
    private String  message;

    /**
     * 响应数据
     */
    private T       data;

    /**
     * TraceId
     */
    private String traceId;

    /**
     *
     * @return
     */
    public static ResponseDTO success() {
        return new ResponseDTO();
    }

    /**
     *
     * @param message
     * @return
     */
    public static ResponseDTO success(String message) {
        return new ResponseDTO(true, null, message, null);
    }

    /**
     *
     * @param data
     * @return
     */
    public static ResponseDTO success(Object data) {
        return new ResponseDTO(true, data);
    }

    /**
     *
     * @param message
     * @param data
     * @return
     */
    public static ResponseDTO success(String message, Object data) {
        return new ResponseDTO(true, null, message, data);
    }

    /**
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseDTO success(String code, String message, Object data) {
        return new ResponseDTO(true, code, message, data);
    }

    /**
     *
     * @return
     */
    public static ResponseDTO fail() {
        return new ResponseDTO(false, null, null, null);
    }

    /**
     *
     * @param message
     * @return
     */
    public static ResponseDTO fail(String message) {
        return new ResponseDTO(false, null, message, null);
    }

    /**
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseDTO fail(String code, String message) {
        return new ResponseDTO(false, code, message, null);
    }

    /**
     *
     */
    private ResponseDTO() {
        this.success = true;
    }

    /**
     *
     * @param success
     * @param data
     */
    private ResponseDTO(boolean success, T data) {
        this(success, null, null, data);
    }

    /**
     *
     * @param message
     */
    private ResponseDTO(String message) {
        this(false, null, message, null);
    }

    /**
     *
     * @param code
     * @param message
     */
    private ResponseDTO(String code, String message) {
        this(false, code, message, null);
    }

    /**
     *
     * @param success
     * @param code
     * @param message
     * @param data
     */
    private ResponseDTO(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
