package com.verifier.password.verifiers;

import java.util.List;

public interface MasterConditionVerifier {

    void addVerifiers(Verifier verifier);

    List<Verifier> getVerifiers();
}
