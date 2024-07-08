package com.ll.md0701.global.rsData.RsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Getter
public class RsData<T> {
    String resultCode;
    int statusCode;
    String msg;
    T data;

    public static <T> RsData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        int statusCode = Integer.parseInt(resultCode.split("-", 2)[0]);

        return new RsData<>(resultCode, statusCode, msg, data);
    }

    public boolean isSuccess() {
        return getStatusCode() >= 200 && getStatusCode() < 400;
    }

    public boolean isFail() {
        return !isSuccess();
    }
}