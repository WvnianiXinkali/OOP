// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts

	private static BlockingQueue<Transaction> work;
	private static ArrayList<Account> accounts;
	private static CountDownLatch countDownLatch;
	private static final Transaction nullTrans = new Transaction(-1, 0, 0);

	public Bank(int numWorkers){
		work = new LinkedBlockingQueue<>();
		accounts = new ArrayList<>();
		for (int i = 0; i < ACCOUNTS; i++) {
			accounts.add(new Account(this, i, 1000));
		}
		countDownLatch = new CountDownLatch(numWorkers);
		for(int i = 0; i < numWorkers; i++){
			new Thread(new worker()).start();
		}
	}
	
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				
				// YOUR CODE HERE
				Transaction newTransaction = new Transaction(from, to, amount);
				work.add(newTransaction);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) {
		readFile(file);
		for(int i = 0; i < numWorkers; i++){
			work.add(nullTrans);
		}
		try {
			countDownLatch.await();
		} catch (Exception ignored){}
	}

	private class worker extends Thread{
		@Override
		public void run() {
			while (true) {
				try {
					Transaction trans = work.take();
					if(trans.equals(nullTrans)){
						break;
					} else {
						accounts.get(trans.getFrom()).changeBalance(trans.getAmount() * (-1));
						accounts.get(trans.getTo()).changeBalance(trans.getAmount());
					}
				} catch (Exception Ignored){}
			}
			countDownLatch.countDown();
		}
	}

	
	
	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}
		
		// YOUR CODE HERE
		Bank bank = new Bank(numWorkers);
		bank.processFile(file, numWorkers);
		for(int i = 0; i < ACCOUNTS; i++){
			System.out.println(Bank.accounts.get(i).toString());
		}
	}
}

