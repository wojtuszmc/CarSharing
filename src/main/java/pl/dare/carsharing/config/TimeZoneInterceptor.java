package pl.dare.carsharing.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.core.convert.converter.Converter;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String timeZone = request.getHeader("X-Time-Zone");
        if(timeZone != null && !timeZone.isEmpty()) {
            try {
                ZoneId.of(timeZone);
            } catch (DateTimeException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return false;
            }
        }
        return true;
    }

    @Bean
    public Converter<String, ZonedDateTime> zonedDateTimeConverter() {
        return new Converter<String, ZonedDateTime>() {
            @Override
            public ZonedDateTime convert(String source) {
                return ZonedDateTime.parse(source);
            }
        };
    }
}
