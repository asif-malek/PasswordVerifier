package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.apache.commons.lang3.StringUtils;

import static com.verifier.password.VerificationConstants.*;

public class HasNumberVerifier extends RegexVerifier{

    public HasNumberVerifier() {
        super(".*\\d.*", STRING_DOES_NOT_CONTAIN_A_NUMBER);
    }


}
