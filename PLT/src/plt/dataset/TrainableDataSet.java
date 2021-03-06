/*                   GNU LESSER GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.


  This version of the GNU Lesser General Public License incorporates
the terms and conditions of version 3 of the GNU General Public
License, supplemented by the additional permissions listed below.

  0. Additional Definitions.

  As used herein, "this License" refers to version 3 of the GNU Lesser
General Public License, and the "GNU GPL" refers to version 3 of the GNU
General Public License.

  "The Library" refers to a covered work governed by this License,
other than an Application or a Combined Work as defined below.

  An "Application" is any work that makes use of an interface provided
by the Library, but which is not otherwise based on the Library.
Defining a subclass of a class defined by the Library is deemed a mode
of using an interface provided by the Library.

  A "Combined Work" is a work produced by combining or linking an
Application with the Library.  The particular version of the Library
with which the Combined Work was made is also called the "Linked
Version".

  The "Minimal Corresponding Source" for a Combined Work means the
Corresponding Source for the Combined Work, excluding any source code
for portions of the Combined Work that, considered in isolation, are
based on the Application, and not on the Linked Version.

  The "Corresponding Application Code" for a Combined Work means the
object code and/or source code for the Application, including any data
and utility programs needed for reproducing the Combined Work from the
Application, but excluding the System Libraries of the Combined Work.

  1. Exception to Section 3 of the GNU GPL.

  You may convey a covered work under sections 3 and 4 of this License
without being bound by section 3 of the GNU GPL.

  2. Conveying Modified Versions.

  If you modify a copy of the Library, and, in your modifications, a
facility refers to a function or data to be supplied by an Application
that uses the facility (other than as an argument passed when the
facility is invoked), then you may convey a copy of the modified
version:

   a) under this License, provided that you make a good faith effort to
   ensure that, in the event an Application does not supply the
   function or data, the facility still operates, and performs
   whatever part of its purpose remains meaningful, or

   b) under the GNU GPL, with none of the additional permissions of
   this License applicable to that copy.

  3. Object Code Incorporating Material from Library Header Files.

  The object code form of an Application may incorporate material from
a header file that is part of the Library.  You may convey such object
code under terms of your choice, provided that, if the incorporated
material is not limited to numerical parameters, data structure
layouts and accessors, or small macros, inline functions and templates
(ten or fewer lines in length), you do both of the following:

   a) Give prominent notice with each copy of the object code that the
   Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the object code with a copy of the GNU GPL and this license
   document.

  4. Combined Works.

  You may convey a Combined Work under terms of your choice that,
taken together, effectively do not restrict modification of the
portions of the Library contained in the Combined Work and reverse
engineering for debugging such modifications, if you also do each of
the following:

   a) Give prominent notice with each copy of the Combined Work that
   the Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the Combined Work with a copy of the GNU GPL and this license
   document.

   c) For a Combined Work that displays copyright notices during
   execution, include the copyright notice for the Library among
   these notices, as well as a reference directing the user to the
   copies of the GNU GPL and this license document.

   d) Do one of the following:

       0) Convey the Minimal Corresponding Source under the terms of this
       License, and the Corresponding Application Code in a form
       suitable for, and under terms that permit, the user to
       recombine or relink the Application with a modified version of
       the Linked Version to produce a modified Combined Work, in the
       manner specified by section 6 of the GNU GPL for conveying
       Corresponding Source.

       1) Use a suitable shared library mechanism for linking with the
       Library.  A suitable mechanism is one that (a) uses at run time
       a copy of the Library already present on the user's computer
       system, and (b) will operate properly with a modified version
       of the Library that is interface-compatible with the Linked
       Version.

   e) Provide Installation Information, but only if you would otherwise
   be required to provide such information under section 6 of the
   GNU GPL, and only to the extent that such information is
   necessary to install and execute a modified version of the
   Combined Work produced by recombining or relinking the
   Application with a modified version of the Linked Version. (If
   you use option 4d0, the Installation Information must accompany
   the Minimal Corresponding Source and Corresponding Application
   Code. If you use option 4d1, you must provide the Installation
   Information in the manner specified by section 6 of the GNU GPL
   for conveying Corresponding Source.)

  5. Combined Libraries.

  You may place library facilities that are a work based on the
Library side by side in a single library together with other library
facilities that are not Applications and are not covered by this
License, and convey such a combined library under terms of your
choice, if you do both of the following:

   a) Accompany the combined library with a copy of the same work based
   on the Library, uncombined with any other library facilities,
   conveyed under the terms of this License.

   b) Give prominent notice with the combined library that part of it
   is a work based on the Library, and explaining where to find the
   accompanying uncombined form of the same work.

  6. Revised Versions of the GNU Lesser General Public License.

  The Free Software Foundation may publish revised and/or new versions
of the GNU Lesser General Public License from time to time. Such new
versions will be similar in spirit to the present version, but may
differ in detail to address new problems or concerns.

  Each version is given a distinguishing version number. If the
Library as you received it specifies that a certain numbered version
of the GNU Lesser General Public License "or any later version"
applies to it, you have the option of following the terms and
conditions either of that published version or of any later version
published by the Free Software Foundation. If the Library as you
received it does not specify a version number of the GNU Lesser
General Public License, you may choose any version of the GNU Lesser
General Public License ever published by the Free Software Foundation.

  If the Library as you received it specifies that a proxy can decide
whether future versions of the GNU Lesser General Public License shall
apply, that proxy's public statement of acceptance of any version is
permanent authorization for you to choose that version for the
Library.*/


package plt.dataset;

import java.util.*;
import plt.utils.Preference;

/**
 * A TrainableDataset is a desired input for a preference learning algorithm.
 * The difference between a DataSet and a TrainableDataset, is that the first must 
 * be considered an input as it is imported from a stream or a database, the second instead is wrapper of the first that can be
 * used as inputs for the preference learning algorithm of this toolbox. PreprocessedDataSet for example is a TrainableDataset created
 * by a DataSet and a list of preprocessing operation to apply to it.
 * An important difference is that the features of a DataSet are String of a TrainableDataset are Double.
 * 
 * Pre PLT v1.0
 * @author Luca Querella <lucq@itu.dk>
 */
public abstract class TrainableDataSet {
    
    private DataSet dataSet;
    
    public TrainableDataSet(DataSet d) {
        this.dataSet = d;
    } 
    
    public DataSet getDataSet()
    {
        return dataSet;
    }
    
     /**
     * Given a number n in [0..this.getNumberOfObjects()-1] and a number f in [0..this.getNumberOfFeatures()-1]
     * returns the fth feature of the nth object
     * @param n a number that identify the object
     * @param f a number that identify the feature
     * @return the fth feature of the nth object
     */
        public abstract double getFeature(int n, int f);

    
    /**
     * Given a number in [0..this.getNumberOfObjects()-1] returns all the feature of the nth object
     * @param n a number that identify the object
     * @return an array with all the features of the nth object
     */
    public abstract double[] getFeatures(int n);

    /**
     * Given a number in [0..this.getNumberOfIstances()-1] returns the nth preference.
     * A preference is defined between to objects.
     * @param n a number that identify the instance.
     * @return a preference between two objects. 
     */
    public abstract Preference getPreference(int n);
    
    /**
     * Given an instance n, it return its own "atomic group".
     * An atomic group is a set of instance that can not be divided in subset 
     * @param n a number that identify the instance.
     * @return a number that identify the atomic group.
     */
    public abstract int atomicGroup(int n);

    
    /**
     * Returns the number of the feature for the objects in the dataset
     * A feature is a property of an object and in the dataset all the 
     * objects have the same number and types of features
     * 
     * @return the number of the features for the object in the dataset
     */
    public abstract int getNumberOfFeatures();

    /**
    *  Returns the number of the preferences in the dataset
    * 
    *  @return the number of instances in the dataset
    */
    public abstract int getNumberOfPreferences();

        
    /**
     * Two trainable datasets are considered equals if created from the same DataSet, with the same number
     * of objects, the same number of instances, and the same features for each object
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        TrainableDataSet other= (TrainableDataSet)o;
        
        if (other.dataSet != this.dataSet) {
            return false;
        }
        
        if (other.getNumberOfPreferences() != this.getNumberOfPreferences()) {
            return false;
        }
        if (other.getNumberOfFeatures() != this.getNumberOfFeatures()) {
            return false;
        }
        
        for (int i=0; i<this.getNumberOfPreferences(); i++) {
            if (!other.getPreference(i).equals(this.getPreference(i))) {
                return false;
            }
        }
        
        return true;
        
    }
    
    
    /**
     * create a subset of trainableDataSet given a vector of instances
     * @param subset
     * @return 
     */
    public TrainableDataSet subSet(final int[] subset) {

        if (subset.length < 1) {
            throw new IllegalArgumentException();
        }
        
        HashMap<Integer,Set<Integer>> subHash = new HashMap<>();
        
        for (int i=0; i<subset.length; i++) {
            int j = this.atomicGroup(subset[i]);
            if (subHash.get(j) == null) {
                subHash.put(j, new HashSet<Integer>());
            }
            subHash.get(j).add(subset[i]);
        }
        
        HashMap<Integer,Set<Integer>> hash = new HashMap<>();

        for (int i =0; i< this.dataSet.getNumberOfPreferences(); i++) {
            int j =this.dataSet.atomicGroup(i);
            if (hash.get(j) == null) {
                hash.put(j, new HashSet<Integer>());
            }
            hash.get(j).add(i);
        }
        
        for (Integer i : subHash.keySet()) {
            if (hash.get(i).size() != subHash.get(i).size()) {
                throw  new IllegalArgumentException();
            }
        }

        final TrainableDataSet father = this;
        return new TrainableDataSet(this.dataSet) {
            
            @Override
            public Preference getPreference(int n) {
                if (n >= subset.length || n <0) {
                    throw new IllegalArgumentException(n + " is not a valid instance");
                }
                
                return father.getPreference(subset[n]);
            }
            
            @Override
            public int atomicGroup(int n) {
                if (n >= subset.length || n <0) {
                    throw new IllegalArgumentException(n + " is not a valid instance");
                }
                
                return father.atomicGroup(subset[n]);
            }


            @Override
            public int getNumberOfPreferences() {
                return subset.length;
            }

            @Override
            public double[] getFeatures(int n) {
                return father.getFeatures(n);
            }

            @Override
            public double getFeature(int n, int f) {
                return father.getFeature(n, f);
            }

            @Override
            public int getNumberOfFeatures() {
                return father.getNumberOfFeatures();
            }
        };
    }
    
    /**
     * create a subset of trainableDataSet given a set of instances
     * @param subset
     * @return 
     */
    public TrainableDataSet subSet(Set<Integer> subset) {
        int[] output = new int[subset.size()];
        int counter=0;
        for (Integer i: subset) {
            output[counter++] = i;
        }
        
        return this.subSet(output);
    }
    
    /**
     * @return all the atomicsGropus of the trainableDataSet
     */
    public List<Set<Integer>> atomicGroups () {
        int n = this.getNumberOfPreferences();
        HashMap<Integer,Set<Integer>> hash = new HashMap<>();
        for (int i=0; i<n; i++) {
            int atomicGroup = this.dataSet.atomicGroup(i);
            Set<Integer> set = hash.get(atomicGroup);
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(i);
            hash.put(atomicGroup, set);
        }
        
        return new LinkedList<>(hash.values());
    }
 
    @Override
    public String toString() {
        return "{Trainable Dataset - number of feature(s): " + this.getNumberOfFeatures() +
                " number of istance(s): " + this.getNumberOfPreferences() + "}";
    }

    public int getNumberOfObjects() {
        return this.dataSet.getNumberOfObjects();
    }
    
}