/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work;

import com.predic8.wsdl.Operation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AMore
 */
public class MainMatcher {

    private final WSDLParsing outputWsdl;
    private final WSDLParsing inputWsdl;
    private final MatchedWebServiceType matchedWebServiceType;
    private static final double TRESHOLD = 0.8;
    
    public MainMatcher(String wsdlFileOut, String wsdlFileIn) {
        outputWsdl = new WSDLParsing(wsdlFileOut);
        inputWsdl = new WSDLParsing(wsdlFileIn);
        matchedWebServiceType = new MatchedWebServiceType();
    }

    public void matchThis(String w1, String w2) {
        
        WSDLParsing wsdl1 = new WSDLParsing(w1);
        WSDLParsing wsdl2 = new WSDLParsing(w2);

        System.out.println("__________________");
        WordnetMatcher matcher = new WordnetMatcher();
        
        List<Operation> operations1 = wsdl1.getOperations();
        List<Operation> operations2 = wsdl2.getOperations();
        
        for (Operation op1 : operations1) {
            
            List<String> types1 = wsdl1.findOutputTypesByOperationName(op1.getName());
            for (String type1 : types1) {
                
                for(Operation op2 : operations2){
                    
                    List<String> types2 = wsdl2.findInputTypesByOperationName(op2.getName());
                    for (String type2 : types2) {
                        
                        double score = EDMatcher.getSimilarity(type1.toLowerCase(), type2.toLowerCase());
//                        boolean match = matcher.matchWord(type1.toLowerCase(), type2.toLowerCase());
                        
                        if(score > 0.8 /*|| match*/ ){

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
    
    
    
    
    public WSMatchingType match() {
        matchedWebServiceType.setInputServiceName("inserviceName");
        matchedWebServiceType.setOutputServiceName("outserviceName");
        List<Operation> outOperations = outputWsdl.getOperations();
        List<Operation> inOperations = inputWsdl.getOperations();
        for (Operation outOperation : outOperations) {
            for (Operation inOperation : inOperations) {
                MatchedOperationType matchedOperationType = getMatchingOperation(outOperation, inOperation);
                if (matchedOperationType != null) {
                    matchedWebServiceType.getMacthedOperation().add(matchedOperationType);
                }
            }
        }
        
        matchedWebServiceType.wsScore = 666;
        WSMatchingType wSMatchingType = new WSMatchingType();
        wSMatchingType.getMacthing().add(matchedWebServiceType);
        return wSMatchingType;
    }
    
    // returns null if score is <= 0.8
    private MatchedOperationType getMatchingOperation(Operation opOut, Operation opIn) {
        List<String> outTypes = outputWsdl.findOutputTypesByOperationName(opOut.getName());
        List<String> inTypes = inputWsdl.findInputTypesByOperationName(opIn.getName());
        
        List<MatchedElementType> matchedElementTypes = new ArrayList<>();
        double score = 0;
        for (String outType : outTypes) {
            for (String inType : inTypes) {
                MatchedElementType matchedElementType = getMatchedElementType(outType, inType);
                score += matchedElementType.getScore();
                matchedElementTypes.add(matchedElementType);
            }
        }
        
        double opScore = score / matchedElementTypes.size();
        if (/*opScore > TRESHOLD*/ true) {
            MatchedOperationType matchedOperationType = new MatchedOperationType();
            matchedOperationType.setOpScore(opScore);
            matchedOperationType.setInputOperationName(opIn.getName());
            matchedOperationType.setOutputOperationName(opOut.getName());
            matchedOperationType.macthedElement = matchedElementTypes;
            return matchedOperationType;
        }
        
        return null;
    }
    
    // returns the two elements and the score
    private MatchedElementType getMatchedElementType(String outType, String inType) {
        MatchedElementType matchedElementType = new MatchedElementType();
        matchedElementType.setInputElement(inType);
        matchedElementType.setOutputElement(outType);
        matchedElementType.setScore(EDMatcher.getSimilarity(outType, inType));
        return matchedElementType;
    }

}
