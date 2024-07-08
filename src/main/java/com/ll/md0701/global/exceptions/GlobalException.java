package com.ll.md0701.global.exceptions;

import com.ll.md0701.global.rsData.RsData.RsData;
import com.ll.md0701.standard.base.Empty;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final RsData<Empty> rsData;

    public GlobalException(String resultCode, String msg) {
        super("resultCode=" + resultCode + ",msg=" + msg);
        this.rsData = RsData.of(resultCode, msg);
    }
}