package pl.dare.carsharing.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    private static final String USER_ID_HEADER = "User-ID";
    private static final Logger logger = LoggerFactory.getLogger(UserAuthInterceptor.class);

    @Autowired
    private UserContext userContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            UserAuth userAuth = handlerMethod.getMethodAnnotation((UserAuth.class));

            Long userId = extractUserIdFromRequest(request);
            userContext.setUserId(userId);

            if (userAuth != null) {
                userContext.setAuthRequired(true);

                if (userAuth.required() && userId == null) {
                    logger.warn("Authentication required but no valid user ID found for: {}",
                            request.getRequestURI());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                } else {
                    userContext.setAuthRequired(false);
                }

                logger.debug("Request processed - UserId: {}, AuthRequired: {}, URI: {}",
                        userId, userContext.isAuthRequired(), request.getRequestURI());

            }

            return true;
        }
    }

    private Long extractUserIdFromRequest(HttpServletRequest request) {
        String userIdHeader = request.getHeader("USER_ID_HEADER");
        if (userIdHeader != null && !userIdHeader.trim().isEmpty()) {
            try {
                Long userId = Long.parseLong(userIdHeader.trim());
                logger.debug("User ID extracted from header: {}", userId);
                return userId;
            } catch (NumberFormatException e) {
                logger.warn("Invalid user ID format in header: {}", userIdHeader);
            }
        }
        return extractFromJwtToken(request);
    }

    private Long extractFromJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader !=null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                logger.debug("JWT token found but parsing not implemented yet");
            } catch (Exception e) {
                logger.debug("Could not extract ");
            }
        }
    }
}