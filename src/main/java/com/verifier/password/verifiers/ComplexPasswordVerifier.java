package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import com.verifier.password.verifiers.exception.InvalidNoOfConditionsException;
import com.verifier.password.verifiers.exception.NoVerifiersAddedException;
import com.verifier.password.verifiers.exception.VerifierException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.verifier.password.VerificationConstants.PASSED;

public class ComplexPasswordVerifier implements MultiConditionVerifier {

    private List<Verifier> verifiers = new ArrayList<>();

    private boolean status = true;

    @Override
    public void addVerifier(Verifier verifier) {
        verifiers.add(verifier);
    }

    public List<Verifier> getVerifiers() {
        return verifiers;
    }

    public void setVerifiers(List<Verifier> verifiers) {
        this.verifiers = verifiers;
    }

    private VerificationResponse setStatus(VerificationResponse response){
        this.status=(!response.isOk())?false:true;
        return response;
    }

    private boolean getStatus(){
        return status;
    }
    @Override
    public MultiConditionVerificationResponse verify(String password) throws VerifierException {
        checkIfVerifiersAreEmpty(null);
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
    @Override
    public MultiConditionVerificationResponse verifyInParallel(String password, int noOfConditions, Verifier primeVerifier) throws VerifierException {
        checkIfVerifiersAreEmpty(primeVerifier);

        MultiConditionVerificationResponse m = new MultiConditionVerificationResponse();
        AtomicInteger count = new AtomicInteger(0);

        //Preparing futures for supplied verifiers
        List<CompletableFuture<VerificationResponse>> verificationFutures = getVerifiers().stream().map(pv -> CompletableFuture.supplyAsync(()->pv.verify(password))).toList();

        //Preparing predicates to stop execution in between
        Predicate<VerificationResponse> verificationResponsePredicate =r -> count.getAndIncrement() < getVerifiers().size();
        if(noOfConditions>0) {
            if(primeVerifier!=null)
           verificationResponsePredicate = r -> count.getAndIncrement() < noOfConditions-1;
            else
                verificationResponsePredicate = r -> count.getAndIncrement() < noOfConditions;
        }


        //If primeVerifier(The condition has to be fulfilled) is supplied, create separate execution future for it.
        if(primeVerifier!=null) {
            CompletableFuture<VerificationResponse> completableFuture
                    = CompletableFuture.supplyAsync(() -> primeVerifier.verify(password));

            try {
                VerificationResponse verificationResponse = completableFuture.get();
                m.setOk(verificationResponse.isOk());
                m.setResponse(Arrays.asList(verificationResponse.getResponse()));

                //in case prime verification failed then return
                if (!verificationResponse.isOk()||noOfConditions==1)
                    return m;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        //If verifiers are supplied then try listening to futures
        if(!CollectionUtils.isEmpty(verificationFutures)) {
            List<String> statusList = verificationFutures.stream().map(verificationFuture -> {
                        try {
                            return verificationFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .takeWhile(verificationResponsePredicate) //execute till noOfConditions
                    .map(r -> setStatus(r)).map(r -> r.getResponse())//set status and response
                    .collect(Collectors.toList());

            //prepare response
            m.setResponse(Stream.concat(statusList.stream(), m.getResponse().stream()).toList());
            long noOfPassedCondition = m.getResponse().stream().filter(s->s==PASSED).count();
            if (noOfPassedCondition>=noOfConditions){
                m.setOk(true);
            }else {
                m.setOk(false);
            }
        }
        return m;
    }

    private void checkIfVerifiersAreEmpty(Verifier primeVerifier) throws NoVerifiersAddedException {
        if(verifiers.size()==0&&primeVerifier==null){
            throw new NoVerifiersAddedException();
        }
    }
}
