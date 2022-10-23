package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

import java.util.concurrent.TimeUnit;

import static com.verifier.password.VerificationConstants.*;

public class MinLengthVerifier implements Verifier {



    public MinLengthVerifier(int length) {
        LENGTH = length;
    }

    private final int LENGTH;


    @Override
    public VerificationResponse verify(String password) {
        try {
            TimeUnit.SECONDS.sleep(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean ok = password.length()>=LENGTH;
        String response = (ok)?PASSED: LENGTH_IS_SMALLER_THAN +LENGTH;
        return new VerificationResponse(ok,response);
    }


}
