package com.triprecord.triprecord.global.exception;

import com.triprecord.triprecord.global.util.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomJwtAccessDeniedHandler implements AccessDeniedHandler {
      @Override
      public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
              throws IOException {
            setResponse(response);
      }

      private void setResponse(HttpServletResponse response) throws IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(new ResponseMessage(ErrorCode.FORBIDDEN_ACCESS.getMessage()).toWrite());
      }
}