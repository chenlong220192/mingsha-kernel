package site.mingsha.kernel.logger.pattern;

/**
 * @author Ming Sha
 * @create: 2020-05-27 13:57
 */
public interface LogPattern {

    /**
     * 推荐
     */
    String INFO                  = "Time(ms):%d%nRequest:%s%nResponse:%s%n";
    String WARN                  = "Time(ms):%d%nRequest:%s%nMessage:%s%n";
    String ERROR                 = "Time(ms):%d%nRequest:%s%nException:%s%n";

    String INFO_NO_REQ           = "Time(ms):%d%nResponse:%s%n";
    String WARN_NO_REQ           = "Time(ms):%d%nMessage:%s%n";
    String ERROR_NO_REQ          = "Time(ms):%d%nException:%s%n";

    String INFO_NO_RESP          = "Time(ms):%d%nRequest:%s%n";
    String WARN_NO_RESP          = "Time(ms):%d%nMessage:%s%n";
    String ERROR_NO_RESP         = "Time(ms):%d%nException:%s%n";

    /* -------------------------------------------------------- */

    String INFO_NO_TIME          = "Request:%s%nResponse:%s%n";
    String WARN_NO_TIME          = "Request:%s%nMessage:%s%n";
    String ERROR_NO_TIME         = "Request:%s%nException:%s%n";

    String INFO_NO_TIME_NO_REQ   = "Response:%s%n";
    String WARN_NO_TIME_NO_REQ   = "Message:%s%n";
    String ERROR_NO_TIME_NO_REQ  = "Exception:%s%n";

    String INFO_NO_TIME_NO_RESP  = "Request:%s%n";
    String WARN_NO_TIME_NO_RESP  = "Message:%s%n";
    String ERROR_NO_TIME_NO_RESP = "Exception:%s%n";

    /* -------------------------------------------------------- */

    /**
     * 推荐
     */
    String INFO_HTTP             = "Time(ms):%d%nUrl:%s%nRequest:%s%nResponse:%s%n";
    String WARN_HTTP             = "Time(ms):%d%nUrl:%s%nRequest:%s%nMessage:%s%n";
    String ERROR_HTTP            = "Time(ms):%d%nUrl:%s%nRequest:%s%nException:%s%n";

    String INFO_HTTP_NO_REQ      = "Time(ms):%d%nUrl:%s%nResponse:%s%n";
    String WARN_HTTP_NO_REQ      = "Time(ms):%d%nUrl:%s%nMessage:%s%n";
    String ERROR_HTTP_NO_REQ     = "Time(ms):%d%nUrl:%s%nException:%s%n";

    /* -------------------------------------------------------- */

    String TIME                  = "Time(ms):%d";
    String TIME_MESSAGE          = "Time(ms):%d Message:%s";

    /* -------------------------------------------------------- */

}
