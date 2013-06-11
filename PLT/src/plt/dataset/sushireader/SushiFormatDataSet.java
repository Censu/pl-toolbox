package plt.dataset.sushireader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.*;
import plt.dataset.DataSet;
import plt.utils.Preference;

/**
 * The SUSHI format Reader Data Set.
 *
 * @author luca
 */
public class SushiFormatDataSet implements DataSet {

    private File idata;
    private String idataSeparator;
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
    
    private UramakiFileParseStatus localParseStatus;
    private HashMap<Integer, Integer> mapping;
    
    private HashMap<Integer, Object> featureToMinValMap;
    private HashMap<Integer, Object> featureToMaxValMap;
    
    private int numOfPreferences;

    public SushiFormatDataSet() 
    {
        this.isReady = false;
        
        this.idata = null;
        this.order = null;
        
        this.localParseStatus = new UramakiFileParseStatus();
        this.mapping = new HashMap<>();
        
        featureToMinValMap = new HashMap<>();
        featureToMaxValMap = new HashMap<>();
        
        numOfPreferences = 0;
    }
    
    public void setIData(File idata, String idataSeparator)
    {
        this.idata = idata;
        this.idataSeparator = idataSeparator;
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
        if(this.idata == null) { return false; } else { return true; }
    }
    
    public boolean containsOrderData()
    {
        if(this.order == null) { return false; } else { return true; }
    }
    

    public UramakiFileParseStatus parseIData()
    {
        this.isReady = false;
        
        Scanner scanner;
        try 
        {
            scanner = new Scanner(new BufferedReader(new FileReader(idata)));
            String platIndieLineSep = System.getProperty("line.separator");
            
            HashMap<Integer, String[]> hastable = new HashMap<>();

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
                        localParseStatus.error_iDataFile_reason = "No Object ID header and/or column.";
                        throw new Exception();
                    }
                    else
                    {
                        if(! line.contains(idataSeparator))
                        {
                            localParseStatus.error_iDataFile_reason = "Items in the column header row are"+platIndieLineSep+"not split with the chosen separator.";
                            throw new Exception();
                        }
                        else
                        {
                            String[] headerRes = line.split(idataSeparator);
                            this.featuresName = Arrays.copyOfRange(headerRes, 1, headerRes.length);
                            first = false;
                            continue;
                        }
                    }
                    
                }


                if(!first)
                {
                    if(! line.contains(idataSeparator))
                    {
                        localParseStatus.error_iDataFile_reason = "Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+" is not split with chosen separator";
                        throw new Exception();
                    }
                    else
                    {
                        String[] results = line.split(idataSeparator);
                        String[] list = Arrays.copyOfRange(results, 1, results.length);

                        if(this.featuresName.length < list.length)
                        {
                            localParseStatus.error_iDataFile_reason = "Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+"contains data for unspecified features.";
                            throw new Exception();
                        }
                        else if(this.featuresName.length > list.length)
                        {
                            localParseStatus.error_iDataFile_reason = "Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+"is missing data for certain features.";
                            throw new Exception();
                        }

                        Integer indentifier = -1;
                        try
                        {
                            indentifier = Integer.parseInt(results[0]);
                        }
                        catch(Exception ex)
                        {
                            localParseStatus.error_iDataFile_reason = "Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+"has a corrupt ID value.";
                            throw new Exception();
                        }

                        if (firstIndentifier == -1) {
                            firstIndentifier = indentifier;
                        }
                        hastable.put(indentifier, list);
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
        catch(Exception ex)
        {
            localParseStatus.error_iDataFile = 1;
            ex.printStackTrace();
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
    
    public UramakiFileParseStatus parseOrderData()
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
                    localParseStatus.error_orderDataFile_reason = "Entry "+entryNum+" at line "+srcFileLineNum+""+platIndieLineSep+" is not split with chosen separator";
                    throw new Exception();
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
                                localParseStatus.error_orderDataFile_reason = "Line "+srcFileLineNum+" refers to Object "+parsedIdentifier+""+platIndieLineSep+"which does not exist in the Object File";
                                throw new Exception();
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
        catch (Exception ex) 
        {
            localParseStatus.error_orderDataFile = 1;
            ex.printStackTrace();
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