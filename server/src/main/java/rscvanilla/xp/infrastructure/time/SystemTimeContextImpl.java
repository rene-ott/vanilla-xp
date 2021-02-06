package rscvanilla.xp.infrastructure.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.Instant;

@Service
public class SystemTimeContextImpl implements SystemTimeContext {

    public static final String KEY = "REQUEST_TIME";

    private Instant tempTimeStamp;

    private RequestAttributes getRequestContext() {
        return RequestContextHolder.getRequestAttributes();
    }

    public void setRequestTime(Instant time) {
        var requestTime = getRequestTime();
        if (requestTime != null) {
            throw new IllegalStateException("Setting time when request time is set is forbidden.");
        }
        getRequestContext().setAttribute(KEY, time, RequestAttributes.SCOPE_REQUEST);
    }

    @Override
    public void clearRequestTime() {
        getRequestContext().removeAttribute(KEY, RequestAttributes.SCOPE_REQUEST);
    }

    public void setTempTime(Instant time) {
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

    public void clearTempTime() {
        if (tempTimeStamp == null) {
            throw new IllegalStateException("Timestamp is already cleared.");
        }

        tempTimeStamp = null;
    }
}
