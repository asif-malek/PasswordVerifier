package com.verifier.password;

public class VerificationResponse {
    private boolean ok;
    private String response;

    public VerificationResponse(boolean ok, String response) {
        this.ok = ok;
        this.response = response;
    }

    public boolean isOk() {
        return ok;
    }

    public String getResponse() {
        return response;
    }
}
