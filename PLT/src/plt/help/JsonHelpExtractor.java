
package plt.help;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;



/**
 *
 * @author user
 */
public class JsonHelpExtractor implements IHelpExtractor
{
    @Override
    public HashMap<String, String> extractToolTipFile(String para_filePath)
    {
        
        FileReader fReader = obtainFReader(para_filePath);
        
        HashMap<String,String> strHMap = new HashMap<String,String>();
        Gson gson = new Gson();
        strHMap = gson.fromJson(fReader,strHMap.getClass());
        
        return strHMap;
    }

    @Override
    public HashMap<String, String> extractExtensiveHelpFile(String para_filePath, String[] para_reqHelpItemIDs)
    {
        FileReader fReader = obtainFReader(para_filePath);
        
        HashMap<String,String> strHMap = new HashMap<String,String>();
        Gson gson = new Gson();
        strHMap = gson.fromJson(fReader,strHMap.getClass());
        
        
        HashMap<String,String> retHMap = new HashMap<String,String>();
        for(int i=0; i<para_reqHelpItemIDs.length; i++)
        {
            String tmpKey = para_reqHelpItemIDs[i];
            
            if(strHMap.containsKey(tmpKey))
            {
                retHMap.put(tmpKey, strHMap.get(tmpKey));
            }
        }
        
        
        return retHMap;
    }
    
    public HashMap<String,ExtensiveHelp> extractExtensiveHelpFile(String para_filePath)
    {
        FileReader fReader = obtainFReader(para_filePath);
        
        HashMap<String,ExtensiveHelp> eMap = new HashMap<String,ExtensiveHelp>();
        Gson gson = new Gson();
        Type reqType = new TypeToken<HashMap<String,ExtensiveHelp>>() {}.getType();
        eMap = gson.fromJson(fReader, reqType);
        
        return eMap;
    }
    
    public void writeExtensiveHelpFile(String para_fileName)
    {
        
        
        try
        {
            FileWriter fWriter = new FileWriter(para_fileName);
            
            String helpItemID1 = "HELP ITEM";
            HashMap<String,String> sections1 = new HashMap<>();
            sections1.put("Section 1.1", "This is section 1 of "+helpItemID1);
            sections1.put("Section 1.2", "This is section 2 of "+helpItemID1);
            ArrayList<String> sectionOrderList1 = new ArrayList<>();
            sectionOrderList1.add("Section 1.1");
            sectionOrderList1.add("Section 1.2");
            ExtensiveHelp tmpEHelp1 = new ExtensiveHelp(sectionOrderList1,sections1);

            String helpItemID2 = "HELP ITEM 2";
            HashMap<String,String> sections2 = new HashMap<>();
            sections2.put("Section 2.1", "This is section 1 of "+helpItemID2);
            sections2.put("Section 2.2", "This is section 2 of "+helpItemID2);
            ArrayList<String> sectionOrderList2 = new ArrayList<>();
            sectionOrderList2.add("Section 2.1");
            sectionOrderList2.add("Section 2.2");
            ExtensiveHelp tmpEHelp2 = new ExtensiveHelp(sectionOrderList2, sections2);

            HashMap<String,ExtensiveHelp> eMap = new HashMap<>();
            eMap.put(helpItemID1, tmpEHelp1);
            eMap.put(helpItemID2, tmpEHelp2);

            Gson gson = new Gson();
            String jsonResStr = gson.toJson(eMap);
            fWriter.write(jsonResStr);
            
            fWriter.close();
        }
        catch(IOException ex)
        {
            System.out.println("IOException");
            ex.printStackTrace();
            return;
        }      
    }
    
    private FileReader obtainFReader(String para_filePath)
    {
        FileReader fReader;
        try
        {
            fReader = new FileReader(para_filePath);
            return fReader;
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
