package basicGift;

import java.util.ArrayList;

/**
 * This class will be used in conjunction with Google's Gson class to convert JSON code into a list of Zappos Items that I can use
 * @author Danny
 *
 */

public class SearchResponse {
	private String statusCode;
	private ArrayList<ZapposItem> results;
	
	public SearchResponse( String statusCode, ArrayList<ZapposItem> results ) {
		this.statusCode = statusCode;
		this.results = results;
	}
	
	public String getStatusCode( ) {
		return statusCode;
	}
	
	public ArrayList<ZapposItem> getResults( ) {
		if( results.size( ) > 0 ) {
			return results;
		} else {
			return new ArrayList<ZapposItem>( );
		}
	}
}
