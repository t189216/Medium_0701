package com.ll.md0701.global.rsData.RsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Getter
public class RsData<T> {
    @NonNull
    String resultCode;

    @NonNull
    int statusCode;

    @NonNull
    String msg;

    @NonNull
    T data;

    public static <T> RsData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        int statusCode = Integer.parseInt(resultCode.split("-", 2)[0]);

        return new RsData<>(resultCode, statusCode, msg, data);
    }

    @NonNull
    public boolean isSuccess() {
        return getStatusCode() >= 200 && getStatusCode() < 400;
    }

    @NonNull
    public boolean isFail() {
        return !isSuccess();
    }
}