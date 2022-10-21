package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

import java.util.List;

public interface MultiConditionVerifier{

    void addVerifiers(Verifier verifier);

    List<Verifier> getVerifiers();

    MultiConditionVerificationResponse verify(String password);

}
