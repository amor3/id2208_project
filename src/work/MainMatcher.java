/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work;

import com.predic8.wsdl.Operation;

/**
 *
 * @author AMore
 */
public class MainMatcher {

    public MainMatcher() {
    }

    public void matchThis(String w1, String w2) {
        
        WSDLParsing wsdl1 = new WSDLParsing(w1);
        WSDLParsing wsdl2 = new WSDLParsing(w2);

        System.out.println("__________________");

        for (Operation op1 : wsdl1.getOperations()) {
            for (String type1 : wsdl1.findInputTypesByOperationName(op1.getName())) {
                
                for(Operation op2 : wsdl2.getOperations()){
                    for (String type2 : wsdl2.findInputTypesByOperationName(op2.getName())) {
                        int score = EDMatcher.getEditDistance(type1.toLowerCase(), type2.toLowerCase());
                        if(score <= 2){
                            System.out.println("_____________________");
                            System.out.println("Operation name 1: " + op1.getName() + ", Operation name 2: " + op2.getName());
                            System.out.println("Type 1: " + type1 + ", Type2: " + type2);
                            System.out.println("Edits: " + score);
                        }                        
                    }
                }
            }
        }

    }

}
