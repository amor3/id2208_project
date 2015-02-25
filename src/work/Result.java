/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work;

/**
 *
 * @author johanand
 */
public enum Result {
    
    EXACT(1.0),
    SUBSUMPTION(0.8),
    PLUG_IN(0.6),
    STRUCTURAL(0.5),
    NOT_MATCHED(0.0);

    private final double score;

    private Result(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
    
}
