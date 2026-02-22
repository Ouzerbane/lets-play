package play.demo.exception;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import play.demo.utils.ApiResponse;
import play.demo.utils.ErrorItem;
import tools.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        ErrorItem error = new ErrorItem(
                "authorization",
                "You do not have permission to access this resource"
        );

        ApiResponse<Object> apiResponse =
                new ApiResponse<>(false, List.of(error), null);

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}