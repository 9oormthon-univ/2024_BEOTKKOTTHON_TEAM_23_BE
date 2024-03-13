package com.beotkkotthon.areyousleeping.security.handler.logout;

import com.beotkkotthon.areyousleeping.dto.global.ExceptionDto;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.utility.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomLogoutResultHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 애초에 로그인이 안된 경우
        if (authentication == null) {
            setFailureResponse(response);
            return;
        }
        setSuccessResponse(response);
    }

    private void setSuccessResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", null);
        result.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(result));
    }

    private void setFailureResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.NOT_FOUND.value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(ErrorCode.NOT_FOUND_LOGIN_USER));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
