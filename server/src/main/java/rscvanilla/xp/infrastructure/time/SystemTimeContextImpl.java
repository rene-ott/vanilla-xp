package rscvanilla.xp.infrastructure.time;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.Instant;

@Component
public class SystemTimeContextImpl implements SystemTimeContext {

    public static final String KEY = "REQUEST_TIME";

    private Instant tempTimeStamp;

    public void setTime(Instant time) {
        var requestContext = getRequestContext();
        if (requestContext != null) {
            setRequestTime(requestContext, time);
        } else {
            setTempTime(time);
        }
    }

    private RequestAttributes getRequestContext() {
        return RequestContextHolder.getRequestAttributes();
    }

    private void setRequestTime(RequestAttributes requestContext, Instant time) {
        var requestTime = getRequestTime();
        if (requestTime != null) {
            throw new IllegalStateException("Setting time when request time is set is forbidden.");
        }
        requestContext.setAttribute(KEY, time, RequestAttributes.SCOPE_REQUEST);
    }

    private void setTempTime(Instant time) {
        if (this.tempTimeStamp != null) {
            throw new IllegalStateException("Temp time is already set.");
        }

        tempTimeStamp = time;
    }

    public Instant getTime() {
        var requestTime = getRequestTime();

        if (requestTime != null)
            return requestTime;

        if (tempTimeStamp != null)
            return tempTimeStamp;

        throw new IllegalStateException("Missing request and temp time");
    }

    private Instant getRequestTime() {

        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        var requestAttribute = requestAttributes.getAttribute(KEY, RequestAttributes.SCOPE_REQUEST);
        if (requestAttribute == null) {
            return null;
        }

        return (Instant) requestAttribute;
    }

    public void clearTime() {
        if (tempTimeStamp == null) {
            throw new IllegalStateException("Timestamp is already cleared.");
        }

        tempTimeStamp = null;
    }
}
