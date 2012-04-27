/* Wei Shi, weishi, 15437 */
package data;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class User {
	private int id;
	private String username;
	private byte[] password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public void setPassword(String _password) {
		this.password = computeHash(_password);
	}

	public boolean samePassword(String _password) {
		return Arrays.equals(this.password, computeHash(_password));
	}

	private byte[] computeHash(String pwd) {

		byte[] bytesOfPwd;
		MessageDigest md;
		try {
			bytesOfPwd = pwd.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			return md.digest(bytesOfPwd);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
