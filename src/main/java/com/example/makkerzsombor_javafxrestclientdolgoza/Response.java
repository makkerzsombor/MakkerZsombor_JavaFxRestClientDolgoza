package com.example.makkerzsombor_javafxrestclientdolgoza;

public class Response {
    private int response;
    private String content;

    public Response(int response, String content) {
        this.response = response;
        this.content = content;
    }

    public int getResponseCode() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
