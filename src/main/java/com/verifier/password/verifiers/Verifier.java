package com.verifier.password.verifiers;


import com.verifier.password.VerificationResponse;

public interface Verifier {
        VerificationResponse verify(String password);
}
