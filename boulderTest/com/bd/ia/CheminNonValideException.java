package boulderTest.com.bd.ia;

/**
 * Created by marin on 30/03/2017.
 */
public class CheminNonValideException extends Exception {
    int numTour;

    public CheminNonValideException(int numTour){
        this.numTour = numTour;
    }

    public int getNumTour(){
        return numTour;
    }


}
