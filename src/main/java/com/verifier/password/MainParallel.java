package com.verifier.password;

import com.verifier.password.verifiers.*;
import com.verifier.password.verifiers.exception.VerifierException;

import java.util.Scanner;

import static com.verifier.password.VerificationConstants.PASSED;

public class MainParallel {

    public static void main(String[] args) throws VerifierException {
        ComplexPasswordVerifier passwordVerifier = new ComplexPasswordVerifier();

        //Adding different verifiers
        passwordVerifier.addVerifier(new MinLengthVerifier(8));
        passwordVerifier.addVerifier(new HasUpperCaseVerifier());
        passwordVerifier.addVerifier(new HasLowerCaseVerifier());
        passwordVerifier.addVerifier(new NullVerifier());
        passwordVerifier.addVerifier(new HasNumberVerifier());

        while(true) {
            Scanner sc = new Scanner(System.in); //System.in is a standard input stream
            System.out.print("Enter a password: ");
            String password = sc.nextLine();
            printIfPasswordIsAccepted(password, passwordVerifier.verifyInParallel(password,3,new MinLengthVerifier(8)));
        }
    }

    static void printIfPasswordIsAccepted(String password,MultiConditionVerificationResponse response){
        if(response.isOk()){
            System.out.println(password+" is Ok password");
            return;
        }
        System.out.println(password+" failed verification "+response.getResponse().stream().filter(s->s!=PASSED).toList());
    }
}
