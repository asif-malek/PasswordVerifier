package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import com.verifier.password.verifiers.exception.InvalidNoOfConditionsException;
import com.verifier.password.verifiers.exception.NoVerifiersAddedException;
import com.verifier.password.verifiers.exception.VerifierException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.verifier.password.VerificationConstants.PASSED;

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

    @Override
    public MultiConditionVerificationResponse verify(String password, int noOfConditions) throws VerifierException {

        if(noOfConditions<1){
            throw new InvalidNoOfConditionsException();
        }

        MultiConditionVerificationResponse multiConditionVerificationResponse = this.verify(password);
        if (multiConditionVerificationResponse.isOk()){
            return multiConditionVerificationResponse;
        }

        if(noOfConditions<=Collections.frequency(multiConditionVerificationResponse.getResponse(), PASSED)){
           multiConditionVerificationResponse.setOk(true);
        }
        return multiConditionVerificationResponse;
    }
}
