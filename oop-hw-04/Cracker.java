// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private String HashedPass;
	private int length;
	private static CountDownLatch countDownLatch;
	public Cracker(String HashedPass, int length, int num) {
		this.HashedPass = HashedPass;
		this.length = length;
		countDownLatch = new CountDownLatch(num);
		for (int i = 0; i < num; i++) {
			int start = i * CHARS.length/num;
			int end = CHARS.length/num + i * CHARS.length/num - 1;
			if(i == num - 1) {
				start = CHARS.length/num + (i - 1)* CHARS.length/num;
				end = CHARS.length - 1;
			}
			new Thread(new worker(start, end)).start();
		}
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	private String generatePass(String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] Hashed = md.digest(pass.getBytes());
			return hexToString(Hashed);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private class worker extends Thread{
		private int startInd;
		private int endInd;

		public worker(int startInd, int endInd){
			this.startInd = startInd;
			this.endInd = endInd;
		}
		private void createPassOnthat(String generated){
			if(generated.length() > length){
				return;
			}
			if(generatePass(generated).equals(HashedPass)){
				System.out.println(generated);
			}
			for(char ch: CHARS){
				createPassOnthat(generated + ch);
			}
		}
		@Override
		public void run() {
			for(int i = startInd; i <= endInd; i++){
				createPassOnthat(CHARS[i] + "");
			}
			countDownLatch.countDown();
		}
	}
	
	public static void main(String[] args) {
		if (args.length == 0 || args.length > 3) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		} else if(args.length == 1){
			String pass = args[0];
			Cracker cracker = new Cracker("", 0, 0);
			System.out.println(cracker.generatePass(pass));
		} else {
			// args: targ len [num]
			String targ = args[0];
			int len = Integer.parseInt(args[1]);
			int num = 1;
			if (args.length > 2) {
				num = Integer.parseInt(args[2]);
			}
			// a! 34800e15707fae815d7c90d49de44aca97e2d759
			// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

			// YOUR CODE HERE
			Cracker cracker = new Cracker(targ, len, num);
			try {
				countDownLatch.await();
			} catch (Exception ignored){}
			System.out.println("all done");
		}
	}
}
