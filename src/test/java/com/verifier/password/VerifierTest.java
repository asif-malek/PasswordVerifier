package com.verifier.password;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class VerifierTest {


    @Test
   public void testVerify() {

       Verifier mock = new Verifier(){

            @Override
            public VerificationResponse verify() {
                return new VerificationResponse() {
                };
            }
        };
        System.out.println("hi");
        assertNotNull(mock.verify());


    }

}