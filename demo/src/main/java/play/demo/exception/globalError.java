package play.demo.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import play.demo.utils.ApiResponse;
import play.demo.utils.ErrorItem;

@ControllerAdvice
public class globalError {
        @ExceptionHandler(CustomException.class)
        public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException ex) {
                ErrorItem errorItem = new ErrorItem(ex.getField(), ex.getMessage());
                return ResponseEntity.status(ex.getStatus())
                                .body(new ApiResponse<>(false, List.of(errorItem), null));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

                List<ErrorItem> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(err -> new ErrorItem(err.getField(), err.getDefaultMessage()))
                                .collect(Collectors.toList());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse<>(false, errors, null));
        }
}

// MethodArgumentNotValidException