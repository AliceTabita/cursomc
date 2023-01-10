package com.alice.cursomc.resources.exceptions;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
    @Serial
    private static final long serialVersionUID = 1L;
    private List<FieldMessage> errors=new ArrayList<>();

    public ValidationError(Integer status, String msg,Long timeStamp){
        super(status,msg,timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void setList(List<FieldMessage> list) {
        this.errors = list;
    }
    public void addError(String fieldName,String msg){
        errors.add(new FieldMessage(fieldName,msg));
    }
}
