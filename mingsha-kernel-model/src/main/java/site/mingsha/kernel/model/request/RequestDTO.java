package site.mingsha.kernel.model.request;

import java.io.Serializable;

/**
 * 基础请求参数封装
 *
 * @author Ming Sha
 * @create: 2020-05-21 17:49
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
