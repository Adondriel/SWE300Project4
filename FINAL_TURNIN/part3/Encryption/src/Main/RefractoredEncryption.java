package Main;
/**
 * @author Josh McMillen
 * Refractored by: Adam Pine, Benjamin Uleau, and Josh McMillen
 * 
 * Created Constructor, moved GridA's declaration into it and renamed GridA to "characterMatrix"
 * 	- "Rename a varaiable with a clearer more informative name"
 *  - "Data Hiding"
 * Removed "characterMatrix's" initialization from encrypt() and decrypt() into constructor
 * 	- "Remove Duplicate Code"
 * Moved DEFAULT into constructor and renamed to "characters"
 * 	- "Rename a variable with a clearer more informative name"
 * Removed Magic Number of 9 and replaced with private constants ROWS and COLS
 * 	- "Replace a magic number with a named constant"
 * Extracted operations within encrypt() to methods defining their function
 * 	- getCellGrids() from line 21 to 29 in Encryption.java
 * 		- "Method Extraction"
 * 	- mixGrids() from line 35 to 46 in Encryption.java
 * 		- "Method Extraction"
 * 	- maskBinary() from line 47 to 59 in Encryption.java
 * 		- "Method Extraction"
 * Extracted operations within decrypt() to methods defining their function
 * 	- unmaskBinary() from line 90 to 98 in Encryption.java
 * 		- "Method Extraction"
 *  - unmixGrids() from line 102 to 110 in Encryption.java
 *  	- "Method Extraction"
 *  - getCellValue() from 112 to 116 in Encryption.java
 *  	- "Method Extraction"
 * Switched costly switch statement with array mapping in getBinary()
 * 	- avoided Java's Integer.toBinaryString() to avoid the having to pad
 * 	- "Substitute a simple algorithm for a complex algorithm"
 * Switched costly switch statement with Java's Integer.parseInt() in getNumeric()
 * 	- "Substitute a simple algorithm for a complex algorithm"
 * Replaced redundant two step ( two loop ) process within encrypt() with single loop process
 * 	- from lines 21 to 45 in Encryption.java ( then extracted to mixGrids() )
 * 	- "Substitute a simple algorithm for a complex algorithm"
 * Removed UPPER and LOWER constant
 * 	- used ASCII conversion as opposed to looping through strings
 *  - seen in maskBinary() and unmaskBinary()
 * 	- "Substitute a simple algorithm for a complex algorithm"
 * 	- "Remove Wasteful Variables"
 * Modified getCellGrids Inner Loops  into proper Row * Column form &
 * Modified all i & j or j & k counters to row & col counters where appropriate
 * 	- "Fight Row Column Swap Error"
 * Removed / Replaced all "temp" variables where possible
 * 	- "Add a Parameter"
 * 	- "Convert multipe same use variables to a single-use variable"
 * Uniformed the formatting for easier readability
 */
public class RefractoredEncryption
{	
	private int ROWS = 9;
	
	private int COLS = 9;
	
	private char[][] characterMatrix;
	
	public RefractoredEncryption(){
		
		String characters =	"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',!@#$%^&*()-_=+? .";
		
		characterMatrix = new char[ROWS][COLS];
		
		for( int row = 0; row < ROWS; row++ ){
			
			for( int col = 0; col < COLS; col++ ){
				
				characterMatrix[ row ][ col ] = characters.charAt( ( row * ROWS ) + col );
				
			}
			
		}
		
	}
	
	public String encrypt( String inText ){
		
		String encryptedText = "";
		
		encryptedText = getCellGrids( inText );
		
		encryptedText = mixGrids( encryptedText );
		
		encryptedText = getBinary( encryptedText );
		
		encryptedText = maskBinary( encryptedText );
		
		return encryptedText;		
	}
	
	public String getCellGrids( String inText ){
		
		String outText = "";
		
		for( int i = 0; i < inText.length(); i++ ){
			
			for( int row = 0; row < ROWS; row++ ){
				
				for( int col = 0; col < COLS; col++ ){
					
					if( characterMatrix[ row ][ col ] == inText.charAt( i ) ){
						
						outText += row + "" + col;
						
					}
					
				}
				
			}
			
		}
		
		return outText;
	}
	
	public String mixGrids( String inText ){
		
		String outText = "";
		
		for( int i = 0; i < inText.length() / 2; i++ ){
			
			outText += inText.charAt( i ) + "" + inText.charAt( inText.length() - 1 - i );
			
		}
		
		return outText;
	}
	
	public String getBinary( String text ){
		
		String[] binary = { "0000" , "0001" , "0010" , "0011" , "0100" , "0101" , "0110" , "0111" , "1000" , "1001" };
		
		String out = "";
		
		for(int i = 0; i < text.length(); i++ ){
			
			out += binary[ Character.getNumericValue( text.charAt( i ) ) ];
			
		}
		
		return out;
	}

	public String maskBinary( String inText ){
		
		String outText = "";
		
		for( int i = 0; i < inText.length(); i++ ){
			
			if( inText.charAt(i) == '0' ){
				
				int rand = (int)( Math.random() * 26 ) + 97;
				
				outText += Character.toString( ( char ) rand );
				
			}else{
				
				int rand = (int)( Math.random() * 26 ) + 65;
				
				outText += Character.toString( ( char ) rand );
				
			}
			
		}
		
		return outText;
	}	
	
	public String decrypt( String inText ){
		
		String decryptedText = "";
		
		decryptedText = unmaskBinary( inText );
		decryptedText = getNumeric( decryptedText );
		decryptedText = unmixGrids( decryptedText );
		decryptedText = getCellValues( decryptedText );
		
		return decryptedText;
	}

	public String unmaskBinary(String inText ){
		
		String outText = "";
		
		for( int i = 0; i < inText.length(); i++ ){
			
			if( ( int ) inText.charAt( i ) > 96 ){
				
				outText += "0";
				
			}else{
				
				outText += "1";
				
			}
		}
		
		return outText;
	}
	
	public String getNumeric( String inText ){
		
		String outText = "";
		
		for( int i = 0; i < inText.length(); i += 4 ){
			
			outText += Integer.parseInt( inText.substring( i , i + 4 ) , 2 );	
			
		}
		
		return outText;
	}
	
	public String unmixGrids( String inText ){
		
		String outTextLeft = "";
		
		String outTextRight = "";
		
		for( int i = 0; i <= inText.length() - 2 ; i += 2 ){
			
			outTextLeft += inText.charAt( i );
			
			outTextRight = inText.charAt( i + 1 ) + outTextRight;
			
		}
		
		return outTextLeft + outTextRight;
	}
	
	public String getCellValues( String inText ){
		
		String outText = "";
		
		for( int i = 0; i <= inText.length() - 2; i += 2 ){
			
			outText += characterMatrix[ Character.getNumericValue( inText.charAt( i ) ) ][ Character.getNumericValue( inText.charAt( i + 1 ) ) ];
			
		}
		
		return outText;
	}
	
}
