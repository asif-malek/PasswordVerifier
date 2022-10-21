package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

public class LengthVerifier implements Verifier {

    public LengthVerifier(int length) {
        LENGTH = length;
    }

    private final int LENGTH;


    @Override
    public VerificationResponse verify(String password) {
        boolean ok = password.length()<=LENGTH;
        String response = (ok)?"Passed":"Password length is larger than "+LENGTH;
        return new VerificationResponse(ok,response);
    }


}
