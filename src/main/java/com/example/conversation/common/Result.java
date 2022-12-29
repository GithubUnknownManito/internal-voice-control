package com.example.conversation.common;

public class Result<T> {
    public Result(){}

    public Result(int status){
        this.status = status;
    }

    public Result(int status, T data){
        this.status = status;
        this.data = data;
    }

    public Result(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public Result(int status, Throwable msg){
        this.status = status;
        this.msg = msg.toString();
    }

    private int status;
    private T data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static Result success(){
        return new Result(0);
    }
    public static <E> Result<E> success(E data){
        return new Result(0, data);
    }

    public static Result fail(){
        return new Result(500);
    }

    public static Result fail(String msg){
        return new Result(500, msg);
    }
    public static Result fail(Throwable msg){
        return new Result(500, msg);
    }


}
