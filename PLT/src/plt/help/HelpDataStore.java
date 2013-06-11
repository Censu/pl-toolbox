
package plt.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author user
 */
public class HelpDataStore 
{
    String helpSectionID;
    HashMap<String,String> toolTipHelp;
    HashMap<String,ExtensiveHelp> extensiveHelp;
    
    public HelpDataStore(String para_helpSectionID,
                         HashMap<String,String> para_toolTipHelp,
                         HashMap<String,ExtensiveHelp> para_extensiveHelpMap)
    {
        helpSectionID = para_helpSectionID;
        toolTipHelp = para_toolTipHelp;
        extensiveHelp = para_extensiveHelpMap;
    }
    
    public void setExtensiveHelp(HashMap<String,ExtensiveHelp> para_ExtensiveHelpMap)
    {
        extensiveHelp = para_ExtensiveHelpMap;
    }
    
    public String getToolTip(String para_toolID)
    {
        if(toolTipHelp.containsKey(para_toolID))
        {
            return toolTipHelp.get(para_toolID);
        }
        else
        {
            return "No ToolTip available for '"+para_toolID+"'";
        }
    }
    
    public ExtensiveHelp getExtensiveHelp(String para_toolID)
    {
        if(extensiveHelp.containsKey(para_toolID))
        {
            return extensiveHelp.get(para_toolID);
        }
        else
        {
            return null;//"No Extensive Help available for '"+para_toolID+"'";
        }
    }
    
    public String constructHtml(ArrayList<String> para_helpItemsToInclude)
    {
        // Style constants:
        String sectionHtmlStyle_start = "<p><span style=\"font-family: 'Helvetica Neue'; \"><strong>";
        String sectionHtmlStyle_end = "</strong></span></p>";
        String subsectionHtmlStyle_start = "<p><u><span style=\\\"font-family: 'Helvetica Neue'; \\\" class=\\\"Apple-tab-span\\\">	</span><span style=\\\"font-family: 'Helvetica Neue'; \\\">";
        String subsectionHtmlStyle_end = "</span></u></p>";
        String normTextHtmlStyle_start = "<p><span style=\\\"font-family: 'Helvetica Neue'; \\\">";
        String normTextHtmlStyle_end = "</span><p>";
        
        
        String htmlStr = "";
        
        htmlStr += "<div>";
        
        for(int i=0; i<para_helpItemsToInclude.size(); i++)
        {            
            String helpEntryName = para_helpItemsToInclude.get(i);
            
            if(extensiveHelp.containsKey(helpEntryName))
            {
                htmlStr += sectionHtmlStyle_start;
                htmlStr += helpEntryName;
                htmlStr += sectionHtmlStyle_end;
                
                
                ExtensiveHelp exHelp = extensiveHelp.get(helpEntryName);
                
                HashMap<String,String> reqSections = exHelp.getHelpFromAllSections();
                
                for(Map.Entry<String,String> pair : reqSections.entrySet())
                {
                    htmlStr += subsectionHtmlStyle_start;
                    htmlStr += pair.getKey();
                    htmlStr += subsectionHtmlStyle_end;
                    
                    String subsectionText = pair.getValue();
                    
                    //String[] paragraphs = subsectionText.split(""+Character.LINE_SEPARATOR+""+Character.LINE_SEPARATOR);
                    
                    ArrayList<String> paragraphs = extractParagraphs(subsectionText);
                    
                    /*subsectionText = applyHtmlEscapeCharacters(subsectionText);
                    
                    subsectionText = subsectionText.replace("\n", normTextHtmlStyle_end + "<p></p>" + normTextHtmlStyle_start);
                    
                    htmlStr += normTextHtmlStyle_start;
                    htmlStr += subsectionText;
                    htmlStr += normTextHtmlStyle_end;*/
                    
                    //String[] splitStrs = subsectionText.split((""+Character.LINE_SEPARATOR +""+ Character.LINE_SEPARATOR));
                    
                    
                    for(int counter=0; counter<paragraphs.size(); counter++)
                    {
                        String tmpStr = paragraphs.get(counter);
                        
                        tmpStr = tmpStr.replace("<", "&lt;");
                        tmpStr = tmpStr.replace(">", "&gt;");

                        htmlStr += normTextHtmlStyle_start;
                        htmlStr += tmpStr;
                        htmlStr += normTextHtmlStyle_end;
                    }
                    
                    
                    
                    if((pair.getKey()).equals("Options"))
                    {
                        System.out.println("'"+pair.getValue()+"'");
                    }
                    
                    //htmlStr += (pair.getKey() +"\\n"+ pair.getValue() + "\\n");
                }
            }
        }
        
        htmlStr += "</div>";
        
        return htmlStr;
    }
    
    private String applyHtmlEscapeCharacters(String para_strToChange)
    {
        String nwStr = para_strToChange;
        
        nwStr = nwStr.replace("<", "&lt;");//"&#060;");
        nwStr = nwStr.replace(">", "&gt;");//"&#062;");
        nwStr = nwStr.replace("&", "&amp;");//"&#38;");
        
        return nwStr;
    }
    
    private ArrayList<String> extractParagraphs(String para_sourceStr)
    {   
        ArrayList<String> paragraphList = new ArrayList<String>();
        
        CharSequence inputStr;
        //inputStr = "a\r\rb";              // Mac
        inputStr = "a\r\n\r\nb";          // Windows
        //inputStr = "a\n\nb";              // Unix

        // Compile the pattern
        String patternStr = "(^.*\\S+.*$)+";
        Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(para_sourceStr);

        // Read the paragraphs
        while (matcher.find()) {
            // Get the paragraph
            String paragraph = matcher.group();
            paragraphList.add(paragraph);
        }
        
        return paragraphList;
        
        /*ArrayList<String> paragraphList = new ArrayList<String>();
        
        String tmpStr = "";
        for(int i=0; i<para_sourceStr.length(); i++)
        {
            char nxtChar = para_sourceStr.charAt(i);
            
            if(("\\n\\r").equals(Character.LINE_SEPARATOR))
            {
                System.out.println("BINGO SON");
            }
            
            if((i == (para_sourceStr.length()-1))|| 
                    ((nxtChar == Character.LINE_SEPARATOR)
            &&(para_sourceStr.charAt(i+1)== Character.LINE_SEPARATOR))
                            )
            {
                paragraphList.add(tmpStr);
            }
            else
            {
                tmpStr += nxtChar;
            }
        }
        
        
        return paragraphList;*/
    }
}
