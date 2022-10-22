package com.verifier.password.verifiers;

import java.util.ArrayList;
import java.util.List;

public class MultiConditionVerificationResponse {
    private boolean ok;
    private List<String> response = new ArrayList<>();

    public MultiConditionVerificationResponse(boolean ok, List<String> response) {
        this.ok = ok;
        this.response = response;
    }

    public MultiConditionVerificationResponse(){}

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

    public boolean isOk() {
        return ok;
    }


    public List<String> getResponse() {
        return response;
    }


}
