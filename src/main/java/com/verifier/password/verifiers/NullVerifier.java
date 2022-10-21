package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.apache.commons.lang3.StringUtils;

public class NullVerifier implements Verifier{
    @Override
    public VerificationResponse verify(String password) {

        return StringUtils.isEmpty(password)?new VerificationResponse(false,"String is Null"):new VerificationResponse(true,"Passed");
    }
}
