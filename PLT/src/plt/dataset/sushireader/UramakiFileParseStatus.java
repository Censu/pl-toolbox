package plt.dataset.sushireader;

/**
 *
 * @author Owner
 */
public class UramakiFileParseStatus
{
    public boolean overallParseResult;
    public int error_iDataFile;             // -1 = unknown, 0 = no error, 1 = error.
    public int error_orderDataFile;         // -1 = unknown, 0 = no error, 1 = error.
    
    public String error_iDataFile_reason;
    public String error_orderDataFile_reason;
    
    public UramakiFileParseStatus()
    {
        // Defaults
        
        overallParseResult = false;
        error_iDataFile = -1;
        error_orderDataFile = -1;
        
        error_iDataFile_reason = "Invalid File Format";
        error_orderDataFile_reason = "Invalid File Format";
    }
    
    public UramakiFileParseStatus(boolean para_overallParseResult,
                                  int para_errorIDataFile,
                                  int para_errorOrderDataFile)
    {
        overallParseResult = para_overallParseResult;
        error_iDataFile = para_errorIDataFile;
        error_orderDataFile = para_errorOrderDataFile;
    }
    
    
}
