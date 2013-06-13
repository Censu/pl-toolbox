
package plt.json;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Testing GIT Push.

public class JsonObjIO
{
    public Object readObjFromFile(String para_sourceFilePath, Object para_sampleObj)
    {
        FileReader fReader = obtainFReader(para_sourceFilePath);
        
        Gson gson = new Gson();
        Object extractedObj = gson.fromJson(fReader, para_sampleObj.getClass());
        
        return extractedObj;
    }
    
    public void writeObjToFile(String para_destFilePath, Object para_reqObj)
    {
        FileWriter fWriter = obtainFWriter(para_destFilePath);
        
        try
        {
            Gson gson = new Gson();
            String jsonResStr = gson.toJson(para_reqObj);
            fWriter.write(jsonResStr);

            fWriter.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
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
    
    private FileWriter obtainFWriter(String para_filePath)
    {
        FileWriter fWriter;
        try
        {
            fWriter = new FileWriter(para_filePath);
            return fWriter;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
