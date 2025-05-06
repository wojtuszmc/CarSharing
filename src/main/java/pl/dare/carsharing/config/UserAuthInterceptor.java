package pl.dare.carsharing.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserAuthInterceptor implements HandlerInterceptor {

    private static final String USER_ID_HEADER = "userId";

    @Autowired
    private UserContext userContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            UserAuth userAuth = handlerMethod.getMethodAnnotation((UserAuth.class));

            if (userAuth != null) {
                Long userId = extractUserIdFromRequest(request);

                userContext.setUserId(userId);
            }
        }
        return true;
    }

    private Long extractUserIdFromRequest(HttpServletRequest request) {
        String userIdHeader = request.getHeader("X-User-ID");
        if (userIdHeader != null && !userIdHeader.isEmpty()) {
            try {
                return Long.parseLong(userIdHeader);
            } catch (NumberFormatException e) {

            }
        }
        return 123L;
    }
}
