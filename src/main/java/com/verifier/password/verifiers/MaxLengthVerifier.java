package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

import java.util.concurrent.TimeUnit;

import static com.verifier.password.VerificationConstants.*;

public class MaxLengthVerifier implements Verifier {



    public MaxLengthVerifier(int length) {
        LENGTH = length;
    }

    private final int LENGTH;


    @Override
    public VerificationResponse verify(String password) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("finished Max Length verification");
        boolean ok = password.length()<=LENGTH;
        String response = (ok)?PASSED: LENGTH_IS_LARGER_THAN +LENGTH;
        return new VerificationResponse(ok,response);
    }


}
