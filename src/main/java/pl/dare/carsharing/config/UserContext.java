package pl.dare.carsharing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {
    @Setter
    @Getter
    private Long userId;
    private boolean isAuthRequired = false;

    public boolean hasAuthenticatedUser() {
        return userId != null;
    }

    public boolean isAnonymousUser() {
        return userId == null;
    }

    public Long getRequiredUserId() {
        if (userId == null) {
            throw new UnauthorizedException("User authentication reqiured");
        }
        return userId;
    }

    public Long getUserIdOrDefault(Long defaultValue) {
        if (userId != null) {
            return userId;
        } else {
            return defaultValue;
        }
    }

    public boolean isAuthRequired() {
        return isAuthRequired;
    }

    public void setAuthRequired(boolean authRequired) {
        isAuthRequired = authRequired;
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}
