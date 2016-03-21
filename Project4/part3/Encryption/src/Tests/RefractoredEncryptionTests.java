package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import Main.RefractoredEncryption;


public class RefractoredEncryptionTests
{

	/*
	 * Old Tests ----------------------------------------------------
	 */
	
	@Test
	/**
	 * Test that the encryption / decryption can handle a simple string
	 */
	public void testEncryptionShort(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String encrypted = enc.encrypt("Abc");
		assertEquals(24,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("Abc",decrypted);
	}
	
	@Test
	/**
	 * Test that the encryption / decryption can handle long strings
	 */
	public void testEncryptionLong(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String encrypted = enc.encrypt("ThisAVeryLongTextToEncrypt");
		assertEquals(208,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("ThisAVeryLongTextToEncrypt",decrypted);
	}
	
	@Test
	/**
	 * Test that the encryption / decryption can handle numbers
	 */
	public void testEncryptionNumeric(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String encrypted = enc.encrypt("8675309");
		assertEquals(56,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("8675309",decrypted);
	}
	
	@Test
	/**
	 * Test that the encryption / decryption can handle spaces
	 */
	public void testEncryptionWhiteSpace(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String encrypted = enc.encrypt("Carpe Diem");
		assertEquals(80,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("Carpe Diem",decrypted);
	}
	
	@Test
	/**
	 * Test that the encryption / decryption can handle the expected set of special characters
	 */
	public void testEncryptionSpecialCharacters(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String encrypted = enc.encrypt("Am I going to work or not?");
		assertEquals(208,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("Am I going to work or not?",decrypted);
	}
	
	@Test
	/**
	 * Test that the encryption / decyrption can handle multipl sentences ( returns are not expected nor supported )
	 */
	public void testEncryptionBodyText(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String encrypted = enc.encrypt(	"Here's the story of a lovely lady "+
										"who was bringing up three very lovely girls "+
										"all of them had hair of gold, like their mother. "+
										"the youngest one in curls.");
		assertEquals(1224,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("Here's the story of a lovely lady "+
				"who was bringing up three very lovely girls "+
				"all of them had hair of gold, like their mother. "+
				"the youngest one in curls.",decrypted);
	}
	
	/*
	 * New Tests -----------------------------------------------------------------------------------
	 */
	
	@Test
	/**
	 * Test that the same encrypter will output different encryptions that
	 * can be decrypted into the same value when the same input is given
	 */
	public void testRandomization(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String encryptedA = enc.encrypt("Word");
		String encryptedB = enc.encrypt("Word");
		assertNotEquals( encryptedA , encryptedB );
		String decryptedA = enc.decrypt( encryptedA );
		String decryptedB = enc.decrypt( encryptedB );
		assertEquals( decryptedA , decryptedB );
		assertEquals( "Word" , decryptedA );
		assertEquals( "Word" , decryptedB );
	}
	
	@Test
	/**
	 * Test that the appropriate grid pairs are returned based on the inputted
	 * string
	 */
	public void testGetCellGrids(){
		RefractoredEncryption enc = new RefractoredEncryption();
		assertEquals("00010203",enc.getCellGrids("ABCD"));
		assertEquals("0045",enc.getCellGrids("Ap"));
		assertEquals("0088",enc.getCellGrids("A."));
		assertEquals("008775",enc.getCellGrids("A %"));
	}
	
	@Test
	/**
	 * Test that the approrpriate binary string is returned based on the inputted
	 * grid pairs
	 */
	public void testGetBinary(){
		RefractoredEncryption enc = new RefractoredEncryption();
		assertEquals("00000000000000010000001000000011",enc.getBinary("00010203"));
		assertEquals("0000000001000101",enc.getBinary("0045"));
		assertEquals("0000000010001000",enc.getBinary("0088"));
		assertEquals("000000001000011101110101",enc.getBinary("008775"));		
	}
	
	@Test
	/**
	 * Test that the appropriate grid pairs are returned based on the inputted
	 * binary string
	 */
	public void testGetNumeric(){
		RefractoredEncryption enc = new RefractoredEncryption();
		assertEquals("00010203",enc.getNumeric("00000000000000010000001000000011"));
		assertEquals("0045",enc.getNumeric("0000000001000101"));
		assertEquals("0088",enc.getNumeric("0000000010001000"));
		assertEquals("008775",enc.getNumeric("000000001000011101110101"));
	}
	
	@Test
	/**
	 * Test that the appropriate string is returned based on the inputted grid pairs
	 */
	public void testGetCellValues(){
		RefractoredEncryption enc = new RefractoredEncryption();
		assertEquals("ABCD",enc.getCellValues("00010203"));
		assertEquals("Ap",enc.getCellValues("0045"));
		assertEquals("A.",enc.getCellValues("0088"));
		assertEquals("A %",enc.getCellValues("008775"));
	}
	
	@Test
	/**
	 * Test that the appropriate string is returned based on the inputted grid pairs
	 */
	public void testMixGrids(){
		RefractoredEncryption enc = new RefractoredEncryption();
		assertEquals("03000210",enc.mixGrids("00010203"));
		assertEquals("0504",enc.mixGrids("0045"));
		assertEquals("0808",enc.mixGrids("0088"));
		assertEquals("050787",enc.mixGrids("008775"));
	}
	
	@Test
	/**
	 * Test that the appropriate string is returned based on the inputted grid pairs
	 */
	public void testUnMixGrids(){
		RefractoredEncryption enc = new RefractoredEncryption();
		assertEquals("00010203",enc.unmixGrids("03000210"));
		assertEquals("0045",enc.unmixGrids("0504"));
		assertEquals("0088",enc.unmixGrids("0808"));
		assertEquals("008775",enc.unmixGrids("050787"));
	}
	
	@Test
	/**
	 * Test that the appropriate random string is returned based on the inputted binary string
	 * and that the same masking does not occurr twice with the same input
	 */
	public void testMaskBinary(){
		RefractoredEncryption enc = new RefractoredEncryption();
		String binA = enc.maskBinary("1001");
		String binB = enc.maskBinary("1001");
		assertEquals( binA.length(), binB.length() );
		assertNotEquals( binA , binB );
		binA = enc.maskBinary("100100000111");
		binB = enc.maskBinary("100100000111");
		assertEquals( binA.length(), binB.length() );
		assertNotEquals( binA , binB );
		binA = enc.maskBinary("0000");
		binB = enc.maskBinary("0000");
		assertEquals( binA.length(), binB.length() );
		assertNotEquals( binA , binB );
		
	}
	
	
	
}
