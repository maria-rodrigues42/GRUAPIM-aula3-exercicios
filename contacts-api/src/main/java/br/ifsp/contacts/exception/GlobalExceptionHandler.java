package br.ifsp.contacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

// classe para interpretar os erros de validação @Valid
public class GlobalExceptionHandler {

    //para erro 400
    @ResponseStatus(HttpStatus.BAD_REQUEST) //retornaa http 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errorResponse = new HashMap<>();

        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        errorResponse.put("Erro", errorMessage);

        return errorResponse;
    }

    //para contato não encontrato 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handlerResourceNotFound(ResourceNotFoundException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Erro", ex.getMessage());
        return errorResponse;
    }

    //para erro no servidor 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handlerGenericException(Exception ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Erro", "Ocorreu um erro interno no servidor. Tente novamente");
        return errorResponse;
    }
}
