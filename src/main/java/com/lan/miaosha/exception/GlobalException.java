package com.lan.miaosha.exception;

import com.lan.miaosha.result.CodeMsg;
import lombok.Data;

@Data
public class GlobalException extends RuntimeException{
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
