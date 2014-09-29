package basicGift;

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;

/**
 * This class will get a list of products from the Zappos API and return a list of products whose combination length is less than a desired amount and whose
 * combined price is less than a specified amount
 * 
 * @author Danny Royer
 *
 * @version: 1.0
 *
 * @Bugs: The getPowerSet method is not working correctly. For some reason the calculation that adds total prices is not getting results it should, causing
 * the class to provide a list shorter than is the actual case.
 */

public final class GiftFinder {
	//Constants used to make API calls
	private static final String API_KEY = "&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73";
	private static final String SEARCH = "http://api.zappos.com/Search";
	private static final String LIMIT = "?limit=100";
	private static final String SORT = "&sort={\"price\":\"asc\"}";
	
	/**
	 * This method will find all the combinations of Zappos items that meet maxPrice and with no more than numGift products
	 * This method will do so by getting the list of products from the Zappos API, filtering the results so that only products who are cheaper
	 * than maxPrice are in the list, then generate the powerset of that list that meets the specified conditions by calling getPowerSet
	 * 
	 * @param numGifts
	 * @param maxPrice
	 * @return
	 */
	public static ArrayList<ArrayList<ZapposItem>> getGifts( int numGifts, double maxPrice ) {
		String cheapRequest = SEARCH + LIMIT + SORT + API_KEY;
		Gson gson = new Gson( );
		ArrayList<ZapposItem> itemList = new ArrayList<ZapposItem>( );
		double cheapestPrice = 0;
		
		//Make things a bit easier and limit the results so the most expensive item we can afford is the first item
		try {
			//Get the response
			String response = convertURLToJSON( cheapRequest );
					
			//Convert the JSON into a list of ZapposItems using Google's Gson class
			SearchResponse theResponse = gson.fromJson( response, SearchResponse.class );
			itemList = theResponse.getResults();
			
			//Find the most expensive thing we can get by adding the prices of the cheapest things we can get
			for( int i = 0; i < numGifts; i++ ) {
				cheapestPrice += itemList.get( i ).getPriceD( );
			}
			
			//Now filter the list
			itemList = getFilteredList( itemList, maxPrice - cheapestPrice );
			
//			for( ZapposItem zi : itemList ) {
//				System.out.println( "The item is: " + zi.toString() );
//			}
		} catch( IOException e ) {
			e.printStackTrace();
		}
		
		//Now get the list of combinations of items that can be bought
		return getPowerSet( numGifts, maxPrice, itemList );
	}
	
	/**
	 * This method will generate a power set based on the list of products gotten from the Zappos API, however this method will only return sets whose length
	 * are less than numGifts and whose combined price is less than maxPrice
	 * 
	 * @param numGifts
	 * @param maxPrice
	 * @param itemList
	 * @return
	 */
	private static ArrayList<ArrayList<ZapposItem>> getPowerSet( int numGifts, double maxPrice, ArrayList<ZapposItem> itemList ) {
		ArrayList<ArrayList<ZapposItem>> powerSet = new ArrayList<ArrayList<ZapposItem>>( );
		powerSet.add( new ArrayList<ZapposItem>( ) ); //Add the empty set
		double priceTotal = 0;
		
		//Generate all the powerset of the list and only add the ones that are length < numGifts with total price < maxPrice
		for( ZapposItem zi : itemList ) {
			ArrayList<ArrayList<ZapposItem>> newSet = new ArrayList<ArrayList<ZapposItem>>( );
			
			
			
			for( ArrayList<ZapposItem> subSet : powerSet ) {
				//Copy all of the current powerset's subsets
				newSet.add( subSet );
				
				//Plus the subsets appended with the current item
				ArrayList<ZapposItem> newSubset = new ArrayList<ZapposItem>( subSet );
				newSubset.add( zi );
				
				//Get the current total price
				priceTotal = 0;
				for( ZapposItem zi2 : newSubset ) {
					priceTotal += zi2.getPriceD();
				}
				System.out.println( "Price Total: " + priceTotal );
				//Determine if this combination should be added
				if( newSubset.size() <= numGifts && priceTotal <= maxPrice ) {
					newSet.add( newSubset );
				}
			}
			
			powerSet = newSet;
		}
		
		return powerSet;
	}
	
	/**
	 * This method will filter the list of products gotten from the zappos API by removing all items that are more expensive than the max price
	 * 
	 * @Note: This method is necessary because Zappos's filter part of the search API uses a regex and I have no time to figure out how to convert
	 * currentPrice < maxPrice into a regex 
	 * 
	 * @param originalList
	 * @param maxPrice
	 * @return
	 */
	private static ArrayList<ZapposItem> getFilteredList( ArrayList<ZapposItem> originalList, double maxPrice ) {
		ArrayList<ZapposItem> filteredList = new ArrayList<ZapposItem>( );
		
		//Go through the list and only add things that are less than or equal to our maxPrice
		for( ZapposItem zi : originalList ) {
			
			//Check to see if this item should be added to the list
			if( zi.getPriceD( ) <= maxPrice ) {
				filteredList.add( zi );
			}
		}
		
		return filteredList;
	}
	
	/**
	 * This method will be used to get the product data from the Zappos Api and convert it into JSON
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	private static String convertURLToJSON( String urlString ) throws IOException {
		//List of needed variables
		URL url = new URL( urlString );
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		String line;
		StringBuilder responseString = new StringBuilder( );
		BufferedReader br;
		
		//First check to see if the search was a success
		if( connection.getResponseCode() != 200 ) {
			throw new IOException( connection.getResponseMessage() );
		}
		
		//Next only add lines that aren't null, aka cut out the empty lines
		br = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
		
		while( (line = br.readLine() ) != null ) {
			responseString.append( line );
		}
		
		//Close the things that need to be closed
		br.close( );
		connection.disconnect();
		
		return responseString.toString();
	}
}
