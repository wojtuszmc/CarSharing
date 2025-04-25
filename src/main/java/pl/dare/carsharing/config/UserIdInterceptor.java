package pl.dare.carsharing.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HttpServletBean;

public class UserIdInterceptor implements HandlerInterceptor {

    private static final String USER_ID_HEADER = "userId";

    @Autowired
    private UserContext userContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userIdHeader = request.getHeader((USER_ID_HEADER));
        if (userIdHeader != null && !userIdHeader.isEmpty()) {
            try {
                Long userId = Long.parseLong(userIdHeader);
                userContext.setUserId(userId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid userId format in header: " + userIdHeader);
            }
        }
        return true;
    }
}
