/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.report;

import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class PreprocessingInfo
{
    String featureName;
    String preprocessingTypeName;

    ArrayList<DataWrapper> otherData;

    public PreprocessingInfo()
    {
        otherData = new ArrayList<DataWrapper>();
    }

    public void addNwEntry(String para_AttributeKey, Object para_StoredObj)
    {
        DataWrapper nwDWrap = new DataWrapper(para_AttributeKey,para_StoredObj);
        otherData.add(nwDWrap);
    }

    class DataWrapper
    {
        String key;
        Object data;

        public DataWrapper(String para_Key, Object para_StoredObj)
        {
            key = para_Key;
            data = para_StoredObj;
        }
    }
}
