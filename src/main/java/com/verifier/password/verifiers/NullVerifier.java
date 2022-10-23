package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.apache.commons.lang3.StringUtils;

import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.VerificationConstants.STRING_IS_NULL;

public class NullVerifier implements Verifier{



    @Override
    public VerificationResponse verify(String password) {
        return StringUtils.isEmpty(password)?new VerificationResponse(false, STRING_IS_NULL):new VerificationResponse(true,PASSED);
    }
}
