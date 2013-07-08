/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.report;

import java.util.Calendar;
import plt.utils.TimeHelper;

/**
 *
 * @author Owner
 */
public class ExpMetaData
{
    // Experiment MetaData.
    String name;
    String timestamp_format;
    String start_timestamp;
    String end_timestamp;
    String duration;
    
    public ExpMetaData(String para_expName,
                    String para_timestampFormat,
                    Calendar para_cStart,
                    Calendar para_cEnd)
    {
        name = para_expName;
        timestamp_format = para_timestampFormat;      
        start_timestamp = TimeHelper.createTimestampStr(para_cStart);
        end_timestamp = TimeHelper.createTimestampStr(para_cEnd);
        duration = TimeHelper.calculateDuration(para_cStart, para_cEnd);
    }
}
