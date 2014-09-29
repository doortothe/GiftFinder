package basicGift;

import java.text.DecimalFormat;

/**
 * This class will hold the data of a product from Zappos. It can hold and return just about every relevant thing about
 * the product. All the set methods have error handling so that a product's price can't be set to a negative number, etc.
 * 
 * @Note: Due to the way Gson works, I had to include two versions of original price, price, and percentOff. The original
 * versions are Strings but I can't use those for my purposes, I need numbers. So I created a second set of variables
 * as doubles to hold those numbers. I suppose making another set of variables is unnecessary, I just need to create
 * a second get method that can convert the String to a double, but that's not the way I like to code. If this is a bad
 * coding practice or if there are ways for me to get around this issue completely, I'd really appreciate it if you could
 * tell me.
 * 
 * @author Danny Royer
 * 
 * @version: 1.0
 * 
 * @bugs: None so far
 * 
 * @List of things to improve: the redundant variables, the set() methods of the string versions of the redundant variables
 */
public class ZapposItem {
	//String variables
	private String productId;
	private String brandName;
	private String productName;
	private String thumbnailImageUrl;
	private String originalPrice;
	private String price;
	private String percentOff;
	private String productURL;
	private String defaultImageUrl;
	private String emailAddress;
	
	//Redundant double variables
	private double originalPriceD;
	private double priceD;
	private double percentOffD;
	
	/**
	 * Constructor that fills in some of the variables but not all, why these variables specifically are used I'm not 
	 * sure but I'd rather focus my energy to making the program, I will experiment with why these variables are needed
	 * after the project is complete.
	 * 
	 * @param productName
	 * @param percentOff
	 * @param productId
	 * @param brandName
	 */
	public ZapposItem( String productName, String percentOff, String productId, String brandName ) {
		this.productName = productName;
		this.percentOff = percentOff;
		this.productId = productId;
		this.brandName = brandName;
	}
	
	/**
	 * Constructor where no variables are filled in at the start. Just as with the first semester, I don't know why
	 * these specific variables are being filled it but that's for me to find out later
	 */
	public ZapposItem( ) {
		this.productId = "";
		this.productName = "";
		this.brandName = "";
		this.originalPrice = "";
		this.percentOff = "0%";
		this.price = "";
		this.productURL = "";
	}

	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the thumbnailImageUrl
	 */
	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}

	/**
	 * @param thumbnailImageUrl the thumbnailImageUrl to set
	 */
	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}

	/**
	 * @return the originalPrice
	 */
	public String getOriginalPrice() {
		return originalPrice;
	}

	/**
	 * @param originalPrice the originalPrice to set.
	 * @Note: This method will check to see if the user entered a negative number, and replace it with 0
	 * and check to see if the new originalPrice was entered with a $ or without and will update the double
	 * version of the variable so there's no inconsistency
	 */
	public void setOriginalPrice(String originalPrice) {
		
		//Check to see if there's a "-" in the String, which indicates a negative value which is not possible
		if( price.contains( "-" ) ) {
			
			//Set the price to 0
			this.price = "$0.00";
			this.priceD = 0.00;
			
			//Check to see if there's a $ in front of the string
		} else if( originalPrice.startsWith( "$" ) ) { 
			
			//If there is update the double version so there's no inconsistency
			DecimalFormat df = new DecimalFormat( "0.00" );
			
			//There's got to be a better way to do this
			this.originalPriceD = Double.parseDouble( df.format( Double.parseDouble( originalPrice.substring( 1 ) ) ) );
			
			//If there isn't a $ at the beginning of the string
		} else {
			
			DecimalFormat df = new DecimalFormat( "0.00" );
			
			//There's got to be a better way to do this
			this.originalPriceD = Double.parseDouble( df.format( Double.parseDouble( originalPrice ) ) );
		}
		
		//Set the original price string with proper formatting
		this.originalPrice = "$" + this.originalPriceD;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 * @Note: This method will check to see if the user entered a negative number, and replace it with 0
	 * and check to see if the new price was entered with a $ or without and will update the double
	 * version of the variable so there's no inconsistency
	 */
	public void setPrice(String price) {
		
		//Check to see if there's a "-" in the String, which indicates a negative value which is not possible
		if( price.contains( "-" ) ) {
			
			//Set the price to 0
			this.priceD = 0.00;
			
		} else if( price.startsWith( "$" ) ) { //Check to see if there's a $ in front of the string
			
			//If there is update the double version so there's no inconsistency
			DecimalFormat df = new DecimalFormat( "0.00" );
			
			//There's got to be a better way to do this
			this.priceD = Double.parseDouble( df.format( Double.parseDouble( price.substring( 1 ) ) ) );
			
		} else { //If there isn't a $ at the beginning of the string
			
			//Update the double version so there's no inconsistency
			DecimalFormat df = new DecimalFormat( "0.00" );
			
			//There's got to be a better way to do this
			this.priceD = Double.parseDouble( df.format( Double.parseDouble( price ) ) );
		}
		
		//Set the string version of the variable with proper formatting
		this.price = "$" + this.priceD;
	}

	/**
	 * @return the percentOff
	 */
	public String getPercentOff() {
		return percentOff;
	}

	/**
	 * @param percentOff the percentOff to set
	 * @Note: This method will check to see if the user entered a negative number, and replace it with 0
	 * and check to see if the new price was entered with a % or without and will update the double
	 * version of the variable so there's no inconsistency
	 */
	public void setPercentOff(String percentOff) {
		
		//Check to see if there's a "-" in the String, which indicates a negative value which is not possible
		if( percentOff.contains( "-" ) ) {
			
			//Set the percent off to 0
			this.percentOffD = 0.00;
			
		} else if( percentOff.contains( "%" ) ) { //Check to see if there's a % in the string
			
			//If there is update the double version so there's no inconsistency
			DecimalFormat df = new DecimalFormat( "0.00" );
					
			//There's got to be a better way to do this
			this.percentOffD = Double.parseDouble( df.format( Double.parseDouble( percentOff.substring( 0, percentOff.length( ) - 1 ) ) ) );
			
		} else { //If there isn't a % in the string
			
			DecimalFormat df = new DecimalFormat( "0.00" );
					
			//There's got to be a better way to do this
			this.percentOffD = Double.parseDouble( df.format( Double.parseDouble( percentOff ) ) );
		}
				
		//Set the string version of the variable with proper formatting
		this.percentOff = this.percentOffD + "%";
	}

	/**
	 * @return the productURL
	 */
	public String getProductURL() {
		return productURL;
	}

	/**
	 * @param productURL the productURL to set
	 */
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	/**
	 * @return the defaultImageUrl
	 */
	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}

	/**
	 * @param defaultImageUrl the defaultImageUrl to set
	 */
	public void setDefaultImageUrl(String defaultImageUrl) {
		this.defaultImageUrl = defaultImageUrl;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the originalPriceD
	 * @Note: This method will instantiate originalPriceD in case it hasn't yet
	 */
	public double getOriginalPriceD( ) {
		
		if( originalPriceD == 0.0 ) {
			
			//If the original price hasn't been set yet
			this.originalPriceD = Double.parseDouble( originalPrice.substring( 1 ).replaceAll(",", "" ) );
		}
		
		return originalPriceD;
	}

	/**
	 * @param originalPriceD the originalPriceD to set
	 * @Note: This method will make sure that a negative number wasn't passed and update the string version so there's
	 * no inconsistency 
	 */
	public void setOriginalPriceD( double originalPriceD ) {
		
		//Check to see if an invalid number was passed
		if( originalPriceD < 0 ) {
			
			//Its impossible to sell something at a negative value
			originalPriceD = 0;
			
		} else {
			
			this.originalPriceD = originalPriceD;
		}
		
		//Set the string version so there's no inconsistency
		DecimalFormat df = new DecimalFormat( "0.00" );
		this.originalPrice = "$" + df.format( originalPriceD );
	}

	/**
	 * @return the priceD
	 * @Note: This method will instantiate priceD if it hasn't been already
	 */
	public double getPriceD() {
		
		if( priceD == 0.0 ) {
			//If the price hasn't been set yet
			//Remove formatting to get the conversion done correctly
			this.priceD = Double.parseDouble( price.substring( 1 ).replaceAll(",", "" ) );
		}
		
		return priceD;
	}

	/**
	 * @param priceD the priceD to set
	 * @Note: This method will make sure that a negative number wasn't passed and update the string version so there's
	 * no inconsistency
	 */
	public void setPriceD(double priceD) {
		
		//Check to see if an invalid number was passed
		if( priceD < 0 ) {
					
			//Its impossible to sell something at a negative value
			priceD = 0;
					
		} else {
					
			this.priceD = priceD;
		}
		
		//Set the string version so there's no inconsistency
		DecimalFormat df = new DecimalFormat( "0.00" );
		this.price = "$" + df.format( priceD );
	}

	/**
	 * @return the percentOffD
	 * @Note: This method will instantiate priceD if it hasn't been already
	 */
	public double getPercentOffD() {
		
		if( percentOffD == 0.0 ) {
			
			//If the price hasn't been set yet
			this.percentOffD = Double.parseDouble( percentOff.substring( 0, percentOff.length( ) - 1 ) );
		}
		
		return percentOffD;
	}

	/**
	 * @param percentOffD the percentOffD to set
	 */
	public void setPercentOffD( double percentOffD ) {
		
		//Check to see if an invalid number was passed
		if( percentOffD < 0 ) {
			
			//Its impossible for something to have a negative discount
			percentOffD = 0;
		} else if( percentOffD > 100 ) {
			
			//Its impossible for something to be more than 100% off
			percentOffD = 100;
		} else {
			
			this.percentOffD = percentOffD;
		}
		
		//Set the string version so there's no inconsistency
		DecimalFormat df = new DecimalFormat( "0.00" );
		this.percentOff = df.format( percentOffD ) + "%";
	}

	/**
	 * This method will return the variables that are needed for this program in a nicely formatted string
	 * 
	 * @Note: The variables that are needed are: brandName, productName, price, percentOff, and originalPrice
	 */
	public String toString( ) {
		return brandName + " - " + productName + " : " + price + " - " + percentOff + " off of " + originalPrice + "\n";
	}
}
