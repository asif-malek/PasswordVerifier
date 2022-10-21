package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComplexPasswordVerifier implements MultiConditionVerifier {

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

    @Override
    public MultiConditionVerificationResponse verify(String password) {
     return   new MultiConditionVerificationResponse(true,verifiers.stream().map( verifier -> verifier.verify(password).getResponse()).collect( Collectors.toList() ));

    }
}
