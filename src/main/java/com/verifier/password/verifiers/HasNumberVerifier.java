package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.apache.commons.lang3.StringUtils;

import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.VerificationConstants.STRING_DOES_NOT_CONTAIN_A_NUMBER;

public class HasNumberVerifier implements Verifier{



    @Override
    public VerificationResponse verify(String password) {

        return password!=null&&password.matches(".*\\d.*")?new VerificationResponse(true,PASSED):new VerificationResponse(false, STRING_DOES_NOT_CONTAIN_A_NUMBER);
    }
}
