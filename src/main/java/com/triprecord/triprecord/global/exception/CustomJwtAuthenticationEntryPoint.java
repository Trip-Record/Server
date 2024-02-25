package com.triprecord.triprecord.global.exception;

import com.triprecord.triprecord.global.util.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
              throws IOException {
            setResponse(response);
      }

      private void setResponse(HttpServletResponse response) throws IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(new ResponseMessage(ErrorCode.INVALID_TOKEN.getMessage()).toWrite());
      }

}