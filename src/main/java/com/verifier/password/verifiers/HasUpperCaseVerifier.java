package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.apache.commons.lang3.StringUtils;

import static com.verifier.password.VerificationConstants.*;

public class HasUpperCaseVerifier implements Verifier{

    @Override
    public VerificationResponse verify(String password) {

         return password!=null&&password.matches(".*[A-Z].*")?new VerificationResponse(true,PASSED):new VerificationResponse(false, STRING_DOES_NOT_CONTAIN_AN_UPPER_CASE_CHAR);
    }
}
