# PasswordVerifier

## Different verification	Rules. 
1.	- password should be larger than 8 chars
2.	- password should not be null
3.	- password should have one uppercase letter at least
4.	- password should have one lowercase letter at least
5.	- password should have one number at least
## Special Scenarios. 
1.	Add feature: Password is OK if at least three of the previous conditions is true
2.	Add feature: password is never OK if item 1.d is not true.
3.	Assume Each verification takes 1 second to complete. How would you solve items 2 and 3 so tests can run faster?

## Solution
To solve the above problem **Rule design** pattern is used.
[ref](https://tenmilesquare.com/resources/software-development/basic-rules-engine-design-pattern/)

All the verification rules are defined in different classes and main Rule engine class is **ComplexPasswordVerifier** which can be used add and remove Verifiers and various verify() methods:
    

    MultiConditionVerificationResponse verify(String password) throws  VerifierException;
    
    MultiConditionVerificationResponse verify(String password, Verifier primeVerifier) throws VerifierException;

    MultiConditionVerificationResponse verify(String password, int noOfConditions) throws VerifierException;

    MultiConditionVerificationResponse verifyInParallel(String password, int noOfConditions, Verifier primeVerifier) throws VerifierException;

Before execution of verify method its required to add desired verifiers along with min. no of conditions needed and primaryVerifier which is nothing but primary condition which needs to be satisfied.


Execute Main.java
<img width="1237" alt="image" src="https://user-images.githubusercontent.com/14824369/197405761-53e538bf-3289-4c39-9acd-5b6bcf7923d7.png">
Execute MainParallel.java
<img width="986" alt="image" src="https://user-images.githubusercontent.com/14824369/197405902-3169cef8-81be-43bc-a2ee-ec5059c8ad43.png">

**Note:** Main.Java will be slower as delays are added in verification files
