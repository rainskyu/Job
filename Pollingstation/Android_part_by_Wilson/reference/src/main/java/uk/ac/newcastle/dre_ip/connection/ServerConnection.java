package uk.ac.newcastle.dre_ip.connection;

import android.util.Log;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Server Connection class - defines the parameters for all server connections
 *
 * @author Carlton Shepherd
 */
public class ServerConnection
{
	private HttpURLConnection httpConnection;
	private String xml, urlString;
	private URL url;
	private StringBuilder buf;

	/**
	 * Constructor
	 *
	 * @param url the url
	 * @param xml the xml
	 */
	public ServerConnection(String url, String xml)
	{
		this.urlString = url+"dreipvoting.php";
		this.xml = xml;
	}

	/**
	 * Retrieve XML from server
	 *
	 * @return XML string
	 * @throws Exception the exception
	 */
	public String retrieveXml() throws Exception {

		url = new URL(urlString);
		httpConnection = (HttpURLConnection) url.openConnection();


		httpConnection.setRequestMethod("POST");
		httpConnection.setDoOutput(true);
		httpConnection.setRequestProperty("Content-Type", "text/xml");

		Log.e("test","**** WE ARE HERE 2 ****");

		//Send XML to server
		OutputStreamWriter writer = new OutputStreamWriter(httpConnection.getOutputStream());
		writer.write(xml);
		writer.flush();
		writer.close();

		Log.e("test","**** WE ARE HERE 3 ****");


		buf = new StringBuilder();

		try
		{

			//Retrieve and construct XML from server into a string
//			Log.e("reader",httpConnection.getInputStream()+"");
			InputStreamReader reader = new InputStreamReader(httpConnection.getInputStream());

			Log.e("test","**** WE ARE HERE 4 ****");


			char[] cbuf = new char[ 2048 ];
			int num;
			Log.e("serverconnection","hello");

			while ((num = reader.read( cbuf )) != -1 )
			{
				buf.append( cbuf, 0, num );
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error",e.toString());
			throw new Exception();
		}
		httpConnection.disconnect();
		//Return the retrieved XML in a string
		return buf.toString();
	}
}
