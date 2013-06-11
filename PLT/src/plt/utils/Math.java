package plt.utils;

import java.util.Random;

/*
 * @author Héctor Pérez Martínez <hpminf@gmail.com>
 */

public class Math {

    static private boolean m_useLast = false;
    static private double m_y2 = 0.0;
    
    static private Random rand = new Random();

  static public double sigmoid(double d, double pi){
    return (1.0/(1.0+java.lang.Math.exp(-pi*d)));
  }


  static synchronized public double getGaussianRandomNumber(double mu, double sigma)
  {
    double x1,x2,w,y1;


    if (m_useLast) {
      y1=m_y2;
      m_useLast = false;
    
    } else {
      do {
        x1 = (2.0*Math.rand.nextDouble()) - 1.0;
        x2 = (2.0*Math.rand.nextDouble()) - 1.0;
        w = (x1*x1) + (x2*x2);
      } while ( w >= 1.0 );

      w = java.lang.Math.sqrt( (-2.0*java.lang.Math.log(w)) / w);
      y1 = x1*w;
      m_y2 = x2*w;
      m_useLast = true;
    }

    return (double)(mu + (y1*sigma));

  }

}