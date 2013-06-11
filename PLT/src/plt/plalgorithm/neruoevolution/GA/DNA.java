package plt.plalgorithm.neruoevolution.GA;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author luca
 */
public class DNA implements Cloneable {
    public double[] vector;
    private Random random = new Random();
    

    public DNA(int size) {
        this.vector = new double[size];
        
        for(int i=0; i<size; i++) this.vector[i] = this.random.nextDouble();      
    }
    
    public DNA(double[] vector) {
        this.vector = vector.clone();     
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DNA d = (DNA) super.clone();
        d.vector = (double[]) this.vector.clone();
        return d;
    }
    

    @Override
    public boolean equals(Object o) {
        DNA other = (DNA) o;
        return Arrays.equals(this.vector, other.vector);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.vector);
    }
    
}
