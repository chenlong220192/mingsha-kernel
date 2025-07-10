package site.mingsha.kernel.logger.model;

/**
 * 日志封装器
 *
 * @author mingsha
 * @date: 2025-07-10
 */
public class MingshaLog {

    private String traceId;
    private String module;
    private String category;
    private String subCategory;
    private String filter1;
    private String message;

    /**
     * 工具类构造方法私有化，防止实例化
     */
    private MingshaLog() {}

    /**
     * 构造器
     *
     * @param builder
     */
    private MingshaLog(Builder builder) {
        traceId = builder.traceId;
        module = builder.module;
        category = builder.category;
        subCategory = builder.subCategory;
        filter1 = builder.filter1;
        message = builder.message;
    }

    /**
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getTraceId() {
        return traceId;
    }

    public String getModule() {
        return module;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getFilter1() {
        return filter1;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 构造器
     */
    public static final class Builder {

        private String traceId;
        private String module;
        private String category;
        private String subCategory;
        private String filter1;
        private String message;

        /**
         *
         */
        public Builder() {
        }

        public Builder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public Builder module(String module) {
            this.module = module;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder subCategory(String subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public Builder filter1(String filter1) {
            this.filter1 = filter1;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public MingshaLog build() {
            return new MingshaLog(this);
        }
    }
}
