package play.demo.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String field;
    private final Integer status ;

    public CustomException(String field,Integer status  ,String message){
        super(message);
        this.field = field ;
        this.status = status ;
    }
}
