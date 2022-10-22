package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import com.verifier.password.verifiers.exception.NoVerifiersAddedException;
import com.verifier.password.verifiers.exception.VerifierException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComplexPasswordVerifier implements MultiConditionVerifier {

    private List<Verifier> verifiers = new ArrayList<>();

    private boolean status = true;

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

    private VerificationResponse setStatus(VerificationResponse response){
        if(!response.isOk()){
            this.status=false;
        }
        return response;
    }

    private boolean getStatus(){
        return status;
    }
    @Override
    public MultiConditionVerificationResponse verify(String password) throws VerifierException {
        if(verifiers.size()==0){
            throw new NoVerifiersAddedException();
        }
        MultiConditionVerificationResponse response = new MultiConditionVerificationResponse();
        response.setResponse(verifiers.stream().map(verifier->verifier.verify(password)).map(r ->setStatus(r)).map( r -> r.getResponse()).collect( Collectors.toList() ));
        response.setOk(getStatus());
     return   response;

    }

    @Override
    public MultiConditionVerificationResponse verify(String password, Verifier primeVerifier) throws VerifierException {


        MultiConditionVerificationResponse multiConditionVerificationResponse = new MultiConditionVerificationResponse();;
        VerificationResponse primeResponse = primeVerifier.verify(password);
        multiConditionVerificationResponse.setResponse(Arrays.asList(primeResponse.getResponse()));

        if(!primeResponse.isOk()){
            multiConditionVerificationResponse.setOk(false);
        }else{
            if(verifiers.size()>0) {
                multiConditionVerificationResponse =  verify(password);
                multiConditionVerificationResponse.getResponse().add(primeResponse.getResponse());
            }else{
                multiConditionVerificationResponse.setOk(true);
            }
        }


        return multiConditionVerificationResponse;

    }
}
