package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

import java.util.concurrent.TimeUnit;

import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.VerificationConstants.STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR;

public abstract class RegexVerifier implements Verifier{


    private String regex;
    private String failResponse;

    public RegexVerifier(String regex, String failResponse) {
        this.regex = regex;
        this.failResponse = failResponse;
    }

    @Override
    public VerificationResponse verify(String password) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return password!=null&&password.matches(regex)?new VerificationResponse(true,PASSED):new VerificationResponse(false, failResponse);
    }
}
