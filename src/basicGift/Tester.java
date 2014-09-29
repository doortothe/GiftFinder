package basicGift;

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;

/**
 * This class is used to test out various parts of the program. That is why I have left certain code commented, its code I used to test the program in its early
 * stages and I've kept it in case I need to test that specific thing again
 * 
 * @author Danny
 *
 */

public class Tester {
	private static final String API_KEY = "&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73";
	private static final String SEARCH = "http://api.zappos.com/Search";
	private static final String LIMIT = "?limit=100";
	private static final String SORT = "&sort={\"price\":\"asc\"}";
	private static final String FILTER = "&filters={\"price\":[\"6.5\"]}";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String URLRequest = SEARCH + LIMIT + SORT + API_KEY;
//
//		
//		Gson gson = new Gson( );
//		ArrayList<ZapposItem> itemList = new ArrayList<ZapposItem>( );
//		
//		try {
//			String response = convertURLToJSON( URLRequest );
//			
//			SearchResponse theResponse = gson.fromJson( response, SearchResponse.class );
//			itemList = theResponse.getResults();
//			
//			for( ZapposItem zi : itemList ) {
//				System.out.println( "The price is: " + zi.getPriceD() );
//			}
//		} catch( IOException e ) {
//			e.printStackTrace();
//		}
		
		//Lets hope this works
		Scanner input = new Scanner( System.in );
		System.out.println( "Please enter the number of gifts " );
		int numGifts = input.nextInt();
		
		System.out.println( "Please enter the max price " );
		double maxPrice = input.nextDouble( );
		
		ArrayList<ArrayList<ZapposItem>> results = GiftFinder.getGifts( numGifts, maxPrice );
		
		//Print the results
		System.out.println( "The results: " );
		
		for( ArrayList<ZapposItem> cb : results ) {
			System.out.println( "Here's one combo you can use " );
			for( ZapposItem zi : cb ) {
			System.out.println( zi.toString() );
			}
		}
	}
	
	private static String convertURLToJSON( String urlString ) throws IOException {
		URL url = new URL( urlString );
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		String line;
		StringBuilder responseString = new StringBuilder( );
		BufferedReader br;
		
		if( connection.getResponseCode() != 200 ) {
			throw new IOException( connection.getResponseMessage() );
		}
		
		br = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
		
		
		while( (line = br.readLine() ) != null ) {
			responseString.append( line );
		}
		
		br.close( );
		connection.disconnect();
		
		return responseString.toString();
	}

}
