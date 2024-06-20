package com.rubin.rpan.storage.exception;

import java.util.Objects;

public class StorageParamException extends RuntimeException {

    /**
     * 错误信息
     */
    private String msg;

    public StorageParamException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageParamException that = (StorageParamException) o;
        return Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }

    @Override
    public String toString() {
        return "StorageParamException{" +
                "msg='" + msg + '\'' +
                '}';
    }

}
