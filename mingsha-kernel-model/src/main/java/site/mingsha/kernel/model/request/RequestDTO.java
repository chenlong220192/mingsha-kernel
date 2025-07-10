package site.mingsha.kernel.model.request;

import java.io.Serializable;

/**
 * 通用请求DTO
 * @author mingsha
 */
public class RequestDTO implements Serializable {
    
    private static final long serialVersionUID = 3116972610165045577L;
    private String traceId;

    public static RequestDTO newInstance(){
        return new RequestDTO();
    }

    public String getTraceId() {
        return traceId;
    }

    public RequestDTO setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "traceId='" + traceId + '\'' +
                '}';
    }

}
