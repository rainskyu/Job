package uk.ac.newcastle.dre_ip.parsers;

/**
 * Trims out any extraneous information apart from the raw XML
 *
 * @author carlton
 */
public class XmlTrimmer
{
    /**
     * Trim string method
     *
     * @param xml the xml
     * @return Trimmed string
     */
    public static String trimXml(String xml)
	{
		char[] charXml = xml.toCharArray();
		int i = 0;
		while(charXml[i] != '<' && i < charXml.length)
		{
			charXml[i] = ' ';
			i++;
		}
		
		xml = new String(charXml);
		xml = xml.trim();
		return xml;
	}
}
