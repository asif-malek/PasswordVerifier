package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.apache.commons.lang3.StringUtils;

import static com.verifier.password.VerificationConstants.*;

public class HasUpperCaseVerifier extends RegexVerifier{

    public HasUpperCaseVerifier() {
        super(".*[A-Z].*", STRING_DOES_NOT_CONTAIN_AN_UPPER_CASE_CHAR);
    }
}
