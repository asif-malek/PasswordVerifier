package com.verifier.password.verifiers;

import java.util.ArrayList;
import java.util.List;

public class ComplexPasswordVerifier implements MasterConditionVerifier {

    private List<Verifier> verifiers = new ArrayList<>();

    @Override
    public void addVerifiers(Verifier verifier) {
        verifiers.add(verifier);
    }

    public List<Verifier> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(List<Verifier> verifiers) {
        this.verifiers = verifiers;
    }
}
