package uk.ac.newcastle.dre_ip.extras;

/**
 * Surrounds a string with CDATA tags
 *
 * @author Carlton Shepherd
 */
public class CdataConverter
{
    /**
     * Gets cdata.
     *
     * @param data the data
     * @return the cdata
     */
    public static String getCdata(String data)
	{
		return "<![CDATA[" + data + "]]>";
	}
}
