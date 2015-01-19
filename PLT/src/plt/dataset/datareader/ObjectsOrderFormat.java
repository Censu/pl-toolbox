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

package plt.dataset.datareader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import plt.dataset.DataSet;
import plt.utils.Preference;

/**
 * Reader for a 2-file format. 
 * Objects file contains the input samples (1 per row)
 * The order file specify the partial orders between input samples (each row a sequence of IDs, from higher to lower)
 *
 * @author Institute of Digital Games, UoM Malta
 */
public class ObjectsOrderFormat implements DataSet {

    private File objects;
    private String objectsSeparator;
    
    private File order;
    private String orderSeparator;
    
    private int orderSkipLines;
    private int orderSkipColumns;
    
    private String[][] features;
    private List<Preference> instnaces;
    private String[] featuresName;
    private boolean isReady;
    private boolean[] numeric;
    private List<Integer> atomicGroups;
    
    private DataFileParseStatus localParseStatus;
    private HashMap<Integer, Integer> mapping;
    private HashMap<Integer, Integer> orderToActualObjID;
    
    private HashMap<Integer, Object> featureToMinValMap;
    private HashMap<Integer, Object> featureToMaxValMap;
    
    private int numOfPreferences;

    public ObjectsOrderFormat() 
    {
        this.isReady = false;
        
        this.objects = null;
        this.order = null;
        
        this.localParseStatus = new DataFileParseStatus();
        this.mapping = new HashMap<>();
        this.orderToActualObjID = new HashMap<>();
        
        featureToMinValMap = new HashMap<>();
        featureToMaxValMap = new HashMap<>();
        
        numOfPreferences = 0;
    }
    
    public void setIData(File idata, String idataSeparator)
    {
        this.objects = idata;
        this.objectsSeparator = idataSeparator;
    }
   
    
    public void setOrderData(File order, String orderSeparator, int orderSkipLines, int orderSkipColumns)
    {
        this.order = order;
        this.orderSeparator = orderSeparator;
        this.orderSkipLines = orderSkipLines;
        this.orderSkipColumns = orderSkipColumns;
    }
    
    public boolean containsIData()
    {
        if(this.objects == null) { return false; } else { return true; }
    }
    
    public boolean containsOrderData()
    {
        if(this.order == null) { return false; } else { return true; }
    }
    
    
    
    private DataFileParseStatus parsingObjectFileError(String message){
    	
        this.localParseStatus.error_iDataFile_reason = message;
        this.localParseStatus.error_iDataFile = 1;
        this.localParseStatus.overallParseResult = false;
        this.isReady = false;
    	return localParseStatus;
    
    }
    
    private DataFileParseStatus parsingOrderFileError(String message){
    	
        this.localParseStatus.error_orderDataFile_reason = message;
        this.localParseStatus.error_orderDataFile = 1;
        this.localParseStatus.overallParseResult = false;
        this.isReady = false;
    	return localParseStatus;
    
    }

    public DataFileParseStatus parseIData()
    {
        this.isReady = false;
        
        Scanner scanner;
        try 
        {
            scanner = new Scanner(new BufferedReader(new FileReader(objects)));
            String platIndieLineSep = System.getProperty("line.separator");
            
            HashMap<Integer, String[]> hastable = new HashMap<>();
            orderToActualObjID.clear();

            boolean first = true;
            Integer firstIndentifier = -1;
            
            int srcFileLineNum = -1;
            int entryNum = 0;

            while (scanner.hasNextLine()) {

                srcFileLineNum++;
                
                String line = scanner.nextLine();
                if (line.startsWith("%") || line.isEmpty()) {
                    continue;
                }

                if(first)
                {
                    if(! line.startsWith("ID"))
                    {
                    	scanner.close();
                        return parsingObjectFileError("No Object ID header and/or column.");

                    }
                    else
                    {
                        if(! line.contains(objectsSeparator))
                        {
                        	scanner.close();
                            return parsingObjectFileError("Items in the column header row are"+platIndieLineSep+"not split with the chosen separator.");

                        }
                        else
                        {
                            String[] headerRes = line.split(objectsSeparator);
                            this.featuresName = Arrays.copyOfRange(headerRes, 1, headerRes.length);
                            first = false;
                            continue;
                        }
                    }
                    
                }


                if(!first)
                {
                    if(! line.contains(objectsSeparator))
                    {
                    	scanner.close();
                        return parsingObjectFileError("Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+" is not split with chosen separator");


                    }
                    else
                    {
                        String[] results = line.split(objectsSeparator);
                        String[] list = Arrays.copyOfRange(results, 1, results.length);

                        if(this.featuresName.length < list.length)
                        {
                        	
                        	scanner.close();
                            return parsingObjectFileError("Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+"contains data for unspecified features.");

          	

                        }
                        else if(this.featuresName.length > list.length)
                        {
                        	
                        	
                        	scanner.close();
                            return parsingObjectFileError("Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+"is missing data for certain features.");
                    	
         
                        }

                        Integer indentifier = -1;
                        try
                        {
                            indentifier = Integer.parseInt(results[0]);
                        }
                        catch(Exception ex)
                        {
                        	
                        	scanner.close();
                            return parsingObjectFileError("Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+"has a corrupt ID value.");
                    	
                        }

                        if (firstIndentifier == -1) {
                            firstIndentifier = indentifier;
                        }
                        hastable.put(indentifier, list);
                        orderToActualObjID.put(entryNum, indentifier);
                        
                        /*System.out.println("Line number: "+(srcFileLineNum+1));

                        if(indentifier == 2647)
                        {
                            System.out.println("Debug");
                            String[] tmpL = hastable.get(2647);
                            System.out.println("EDebug");
                        }*/
                        
                        
                        
                        entryNum++;
                    }
                }
            }
            scanner.close();


            features = new String[hastable.size()][hastable.get(firstIndentifier).length];
            

            int key = 0;
            List<Integer> keySet = new ArrayList<>(hastable.keySet());
            java.util.Collections.sort(keySet);

            for (Integer identifier : keySet) {
                features[key] = hastable.get(identifier);
                //mapping.put(key, identifier);
                mapping.put(identifier, key);
                                
                key++;
            }
            
            
            this.numeric = new boolean[this.features.length];
            for (int i = 0; i < this.features[0].length; i++) {
                this.numeric[i] = true;
                try {
                    for (int j = 0; j < this.features.length; j++) {
                        Double.parseDouble(this.features[j][i]);
                    }
                } catch (NumberFormatException e) {
                    this.numeric[i] = false;
                }
            }
            
            localParseStatus.error_iDataFile = 0;
        }
        catch(FileNotFoundException ex)
        {
            return parsingObjectFileError("File not found "+objects.toString()+".");

        }
        
        
        if((localParseStatus.error_iDataFile == 0)
        &&(localParseStatus.error_orderDataFile == 0))
        {
            localParseStatus.overallParseResult = true;
            this.isReady = true;
            
            calculateMinNMaxForAllData();
        }
        else
        {
            localParseStatus.overallParseResult = false;
            this.isReady = false;
        }
        
        return localParseStatus;
    }
    
    public DataFileParseStatus parseOrderData()
    {
        Scanner scanner;
        try
        {
            this.instnaces = new ArrayList<>();
            this.atomicGroups = new ArrayList<>();
            scanner = new Scanner(new BufferedReader(new FileReader(order)));
            String platIndieLineSep = System.getProperty("line.separator");
            int n = 0;
            int group = 0;
            
            int srcFileLineNum = -1;
            int entryNum = 0;
            numOfPreferences = 0;
            
            while (scanner.hasNextLine()) {

                srcFileLineNum++;
                //System.out.println("Line number: "+(srcFileLineNum+1));
               
                
                
                String line = scanner.nextLine();
                if (line.startsWith("%") || line.isEmpty()) {
                    continue;
                }

                if (n++ < this.orderSkipLines) {
                    continue;
                }

                
                if(! line.contains(orderSeparator))
                {
                	
                	scanner.close();
                	return parsingOrderFileError("Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+" is not split with chosen separator");

                }
                else
                {
                    if(! mapping.isEmpty())
                    { 
                        String[] results = line.split(orderSeparator);
                        String[] parsedList = Arrays.copyOfRange(results, this.orderSkipColumns, results.length);
                        int[] list = new int[parsedList.length];

                        for (int i = 0; i < parsedList.length; i++) {

                            int parsedIdentifier = Integer.parseInt(parsedList[i]);

                            if( ! mapping.containsKey(parsedIdentifier)){
                            	scanner.close();
                            	return parsingOrderFileError("Line "+srcFileLineNum+" refers to Object "+parsedIdentifier+""+platIndieLineSep+"which does not exist in the Object File");
                            }

                            list[i] = mapping.get(parsedIdentifier);
                        }

                        for (Preference p : Preference.listToPairWisePreference(list)) {
                            instnaces.add(p);
                            atomicGroups.add(group);
                        }
                    }

                    numOfPreferences++;
                    
                    group++;

                    entryNum++;
                }
            }
            scanner.close();
            
            localParseStatus.error_orderDataFile = 0;
        } 
        catch (FileNotFoundException ex) 
        {
        	
        	return parsingOrderFileError("File not found: "+order.toString()+".");

        }
        
        
        if((localParseStatus.error_iDataFile == 0)
        &&(localParseStatus.error_orderDataFile == 0))
        {
            localParseStatus.overallParseResult = true;
            this.isReady = true;
            
            calculateMinNMaxForAllData();
        }
        else
        {
            localParseStatus.overallParseResult = false;
            this.isReady = false;
        }
        
        return localParseStatus;
    }
    
    public int getObjActualID(int para_objOrderID) {
        return orderToActualObjID.get(para_objOrderID);
    }
    
    @Override
    public int getNumberOfObjects() {
        if (!this.containsIData()) {
            throw new IllegalStateException("parse() has not been called");
        }
        return features.length;
    }

    @Override
    public int getNumberOfPreferences() {
        if (!this.containsOrderData()) {
            throw new IllegalStateException("parse() has not been called");
        }

        //return instnaces.size();
        return numOfPreferences;
    }

    @Override
    public int getNumberOfFeatures() {
        if (!this.containsIData()) {
            throw new IllegalStateException("parse() has not been called");
        }

        return features[0].length;
    }

    @Override
    public String getFeatureName(int n) {
        if (!this.isReady) {
            throw new IllegalStateException("parse() has not been called");
        }


        if (n >= this.getNumberOfFeatures() || n < 0) {
            throw new IllegalArgumentException();
        }

        if (this.featuresName != null) {
            if (n < this.featuresName.length) {
                return this.featuresName[n];
            }
        }

        return "Unknown";
    }

    @Override
    public String[] getFeatures(int n) {
        if (!this.isReady) {
            throw new IllegalStateException("parse() has not been called");
        }


        if (n >= this.getNumberOfObjects() || n < 0) {
            throw new IllegalArgumentException();
        }

        return features[n];
    }

    @Override
    public String getFeature(int n, int f) {
        if (!this.isReady) {
            throw new IllegalStateException("parse() has not been called");
        }


        if (n >= this.getNumberOfObjects() || n < 0) {
            throw new IllegalArgumentException();
        }

        if (f >= this.getNumberOfFeatures() || f < 0) {
            throw new IllegalArgumentException();
        }

        return features[n][f];
    }

    @Override
    public Preference getPreference(int n) {
        if (!this.isReady) {
            throw new IllegalStateException("parse() has not been called");
        }


        if (n >= this.getNumberOfPreferences() || n < 0) {
            throw new IllegalArgumentException();
        }
        return this.instnaces.get(n);
    }

    @Override
    public int atomicGroup(int n) {
        return this.atomicGroups.get(n);
    }

    @Override
    public boolean isNumeric(int f) {
        return this.numeric[f];
    }

    public String toString() {
        return "{SushiFormatDataSet - Data set: " + super.toString() + "}";
    }
    
    private void calculateMinNMaxForAllData()
    {
        int numObjects = this.getNumberOfObjects();
        int numFeatures = this.getNumberOfFeatures();
        
        for(int c=0; c<numFeatures; c++)
        {
            if(this.isNumeric(c))
            {
                double currentMin = Double.parseDouble(this.getFeature(0, c));
                double currentMax = Double.parseDouble(this.getFeature(0, c));
                
                for(int r=0; r<numObjects; r++)
                {
                    double tmpVal = Double.parseDouble(this.getFeature(r, c));
                    
                    if(tmpVal < currentMin)
                    {
                        currentMin = tmpVal;
                    }
                    
                    if(tmpVal > currentMax)
                    {
                        currentMax = tmpVal;
                    }
                }
                
                featureToMinValMap.put(c, currentMin);
                featureToMaxValMap.put(c, currentMax);
            }
        }
    }
    
    public Object getMinValForFeature(int feature)
    {
        if(featureToMinValMap.containsKey(feature))
        {
            return featureToMinValMap.get(feature);
        }
        else
        {
            return null;
        }
    }
    
    public Object getMaxValForFeature(int feature)
    {
        if(featureToMaxValMap.containsKey(feature))
        {
            return featureToMaxValMap.get(feature);
        }
        else
        {
            return null;
        }
    }
}