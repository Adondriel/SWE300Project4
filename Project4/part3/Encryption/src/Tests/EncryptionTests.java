package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import Main.Encryption;


public class EncryptionTests
{

	@Test
	public void testEncryptionShort(){
		Encryption enc = new Encryption();
		String encrypted = enc.encrypt("Abc");
		assertEquals(24,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("Abc",decrypted);
	}
	
	@Test
	public void testEncryptionLong(){
		Encryption enc = new Encryption();
		String encrypted = enc.encrypt("ThisAVeryLongTextToEncrypt");
		assertEquals(208,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("ThisAVeryLongTextToEncrypt",decrypted);
	}
	
	@Test
	public void testEncryptionNumeric(){
		Encryption enc = new Encryption();
		String encrypted = enc.encrypt("8675309");
		assertEquals(56,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("8675309",decrypted);
	}
	
	@Test
	public void testEncryptionWhiteSpace(){
		Encryption enc = new Encryption();
		String encrypted = enc.encrypt("Carpe Diem");
		assertEquals(80,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("Carpe Diem",decrypted);
	}
	
	@Test
	public void testEncryptionSpecialCharacters(){
		Encryption enc = new Encryption();
		String encrypted = enc.encrypt("Am I going to work or not?");
		assertEquals(208,encrypted.length());
		String decrypted = enc.decrypt(encrypted);
		assertEquals("Am I going to work or not?",decrypted);
	}
	
	@Test
	public void testEncryptionBodyText(){
		Encryption enc = new Encryption();
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
}
