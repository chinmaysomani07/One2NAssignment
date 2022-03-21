import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class MyMethodsClass {

	Scanner sc = new Scanner(System.in);

	// this method is used to SET the data in database
	public void setDataInDataBase(String dbCommand, Jedis connectToDatabase) {
		try {
			String myDataArray[] = dbCommand.split(" ");
			System.out.println("The key is: " + myDataArray[1]);
			System.out.println("The value is: " + myDataArray[2]);
			connectToDatabase.set(myDataArray[1], myDataArray[2]);
			System.out.println("Operation of Setting Data Successful!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this method is used to GET the data from database
	public String getDataFromDatabase(String dbCommand, Jedis connectToDatabase) {
		String getValue = "";
		try {
			String myDataArray[] = dbCommand.split(" ");
			System.out.println("The search key is: " + myDataArray[1]);
			System.out.println("The corresponding value is : " + connectToDatabase.get(myDataArray[1]));
			System.out.println("Operation of Getting Data Successful!");
			getValue = connectToDatabase.get(myDataArray[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getValue;
	}

	// this method is used to delete data from database
	public void deleteDataFromDatabase(String dbCommand, Jedis connectToDatabase) {
		try {
			String myDataArray[] = dbCommand.split(" ");
			System.out.println("The search key to delete is: " + myDataArray[1]);
			connectToDatabase.del(myDataArray[1]);
			System.out.println("The data has been deleted successfully.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this method is used to increment data by 1
	public void incrementData(String dbCommand, Jedis connectToDatabase) {
		try {
			String myDataArray[] = dbCommand.split(" ");
			System.out.println("The search key whose value to increment is: " + myDataArray[1]);
			connectToDatabase.incr(myDataArray[1]);
			System.out.println("The operation of incrementing data is successful!");
			System.out.println("The new value of " + myDataArray[1] + " is:" + connectToDatabase.get(myDataArray[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this method is used to increment data by a particular number
	public void incrementBySomeValue(String dbCommand, Jedis connectToDatabase) {
		try {
			String myDataArray[] = dbCommand.split(" ");
			System.out.println("The search key whose value to increment is: " + myDataArray[1]);
			System.out.println("Amount by which value should increase is: " + myDataArray[2]);
			connectToDatabase.incrBy(myDataArray[1], Long.parseLong(myDataArray[2]));
			System.out.println("The operation of incrementing value by " + myDataArray[2] + " is successful!");
			System.out.println("The new value of " + myDataArray[1] + " is:" + connectToDatabase.get(myDataArray[1]));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this method is used for MULTI transaction
	public void multiTransactions(Jedis connectToDatabase, Transaction transaction) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("You are executing Multi Command. Press");
			System.out.println("--> 1 for entering commands.");
			System.out.println("--> 0 for exiting");
			int choice = sc.nextInt();
			if (choice == 1) {
				System.out.println("Enter the command:");
				String dbCommand = reader.readLine();
				String myDataArray[] = dbCommand.split(" ");
				if (myDataArray[0].equalsIgnoreCase("set")) {
					transaction.set(myDataArray[1], myDataArray[2]);
				} else if (myDataArray[0].equalsIgnoreCase("incr")) {
					transaction.incr(myDataArray[1]);
				} else if (myDataArray[0].equalsIgnoreCase("get")) {
					System.out.println(connectToDatabase.get(myDataArray[1]));
				} else if (myDataArray[0].equalsIgnoreCase("discard")) {
					transaction.discard();
				} else if (myDataArray[0].equalsIgnoreCase("exec")) {
					transaction.exec();
				} else if (myDataArray[0].equalsIgnoreCase("incrby")) {
					transaction.incrBy(myDataArray[1], Long.parseLong(myDataArray[2]));
				}
			} else {
				break;
			}
		}
	}

	// this method is for COMPACT
	public void compactCommand(Jedis connectToDatabase) {
		try {
			Set<String> keySet = connectToDatabase.keys("*");
			if (keySet.size() > 0) {
				for (String key : keySet) {
					System.out.println("SET " + key + " " + connectToDatabase.get(key));
					connectToDatabase.set(key, connectToDatabase.get(key));
				}
			} else {
				throw new Exception("No keys preset!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// used only in case 3.
	public void setInMultiTransaction(Jedis connectToDatabase, Transaction transaction) throws Exception {
		try {
			connectToDatabase = new Jedis("localhost");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Conenction successful");
			System.out.println("Input the SET command:");
			String dbCommand = reader.readLine();
			String myDataArray[] = dbCommand.split(" ");
			if (myDataArray[0].equalsIgnoreCase("set")) {
				transaction.set(myDataArray[1], myDataArray[2]);
			} else {
				throw new Exception("You did not enter the correct SET command.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// used only in case 3.
	public void incrMultiTransaction(Jedis connectToDatabase, Transaction transaction) throws Exception {
		try {
			connectToDatabase = new Jedis("localhost");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Connection successful");
			System.out.println("Enter the INCR command:");
			String dbCommand = reader.readLine();
			String myDataArray[] = dbCommand.split(" ");
			if (myDataArray[0].equalsIgnoreCase("incr")) {
				transaction.incr(myDataArray[1]);
			} else {
				throw new Exception("You did not enter the correct INCR command.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Case1() throws IOException {
		Jedis connectToDatabase = new Jedis("localhost");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You've called CASE1");
		try {
			System.out.println("Enter the first SET command: ");
			String dbCommand1 = reader.readLine();
			String myDataArray1[] = dbCommand1.split(" ");
			if (myDataArray1[0].equalsIgnoreCase("set")) {
				setDataInDataBase(dbCommand1, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct SET command.");
			}

			System.out.println("Enter the second SET command: ");
			String dbCommand2 = reader.readLine();
			String myInputCommand2 = dbCommand2.toLowerCase();
			String myDataArray2[] = dbCommand2.split(" ");
			if (myDataArray2[0].equalsIgnoreCase("set")) {
				setDataInDataBase(dbCommand2, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct SET command.");
			}

			System.out.println("Enter the GET command for dbCommand1 : ");
			String dbCommand3 = reader.readLine();
			String myDataArray3[] = dbCommand3.split(" ");
			if (myDataArray3[0].equalsIgnoreCase("get")) {
				getDataFromDatabase(dbCommand3, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct GET command.");
			}

			System.out.println("Enter DEL command for dbCommand2: ");
			String dbCommand4 = reader.readLine();
			String myDataArray4[] = dbCommand4.split(" ");
			if (myDataArray4[0].equalsIgnoreCase("del")) {
				deleteDataFromDatabase(dbCommand4, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct DEL command.");
			}

			System.out.println("Enter GET command for dbCommand2 (should return (nil)): ");
			String dbCommand5 = reader.readLine();
			String myDataArray5[] = dbCommand5.split(" ");
			if (myDataArray5[0].equalsIgnoreCase("get")) {
				getDataFromDatabase(dbCommand5, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct GET command.");
			}

			System.out.println("Enter SET command again for dbCommand2");
			String dbCommand6 = reader.readLine();
			String myDataArray6[] = dbCommand6.split(" ");
			if (myDataArray6[0].equalsIgnoreCase("set")) {
				setDataInDataBase(dbCommand6, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct SET command.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Case2() throws Exception {
		Jedis connectToDatabase = new Jedis("localhost");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You've called CASE2");
		try {
			System.out.println("Enter the SET command for setting the counter to any number: ");
			String dbCommand1 = reader.readLine();
			String myDataArray1[] = dbCommand1.split(" ");
			if (myDataArray1[0].equalsIgnoreCase("set")) {
				setDataInDataBase(dbCommand1, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct SET command.");
			}

			System.out.println("Enter the INCR command for the key:");
			String dbCommand2 = reader.readLine();
			String myDataArray2[] = dbCommand2.split(" ");

			// now check if key exists? if it exists then increment it by 1, else throw
			// error

			boolean keyExists = connectToDatabase.exists(myDataArray2[1]);
			boolean correctIncrCommand = false;
			if (myDataArray2[0].equalsIgnoreCase("incr")) {
				correctIncrCommand = true;
			}
			if (keyExists && correctIncrCommand) {
				incrementData(dbCommand2, connectToDatabase);
			} else {
				throw new Exception("Either the key does not exist! or you did not enter the correct INCR command.");
			}

			System.out.println("Enter the GET command to get the counter value:");
			String dbCommand3 = reader.readLine();
			String myDataArray3[] = dbCommand3.split(" ");
			if (myDataArray3[0].equalsIgnoreCase("get")) {
				getDataFromDatabase(dbCommand3, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct GET command.");
			}

			System.out.println("Enter the INCRBY command:");
			String dbCommand4 = reader.readLine();
			String myDataArray4[] = dbCommand4.split(" ");
			if (myDataArray4[0].equalsIgnoreCase("incrby")) {
				incrementBySomeValue(dbCommand4, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct INCRBY command.");
			}

			System.out.println("Enter the INCR command:");
			String dbCommand5 = reader.readLine();
			String myDataArray5[] = dbCommand5.split(" ");
			if (myDataArray5[0].equalsIgnoreCase("incr")) {
				incrementData(dbCommand5, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct INCR command.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Case3HappyPath() throws Exception {
		Jedis connectToDatabase = new Jedis("localhost");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You've called CASE3HAPPYPATH");

		System.out.println("Setting up the Multi command.");
		Transaction transaction = connectToDatabase.multi();
		incrMultiTransaction(connectToDatabase, transaction);
		setInMultiTransaction(connectToDatabase, transaction);
		System.out.println("Executing all the commands.....");
		List<Object> resultOfTransaction = transaction.exec();
		System.out.println("The results of transactions are:" + resultOfTransaction);
		System.out.println("Executed all the commands.");
	}

	public void Case3Discard() throws Exception {
		Jedis connectToDatabase = new Jedis("localhost");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You've called CASE3DISCARD");
		System.out.println("Setting up the Multi command.");
		Transaction transaction = connectToDatabase.multi();
		incrMultiTransaction(connectToDatabase, transaction);
		setInMultiTransaction(connectToDatabase, transaction); // according to problem statement give SET key1 value1 as
																// i/p to set command
		transaction.discard();
		System.out.println(connectToDatabase.get("key1"));
	}

	public void Case4Eg1() throws IOException, Exception {

		Jedis connectToDatabase = new Jedis("localhost");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You've called CASE4 EXAMPLE1");
		try {
			System.out.println("Enter the SET command for setting the counter to any number: ");
			String dbCommand1 = reader.readLine();
			String myDataArray1[] = dbCommand1.split(" ");
			if (myDataArray1[0].equalsIgnoreCase("set")) {
				setDataInDataBase(dbCommand1, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct SET command.");
			}

			System.out.println("Enter the INCR command for the key:");
			String dbCommand2 = reader.readLine();
			String myDataArray2[] = dbCommand2.split(" ");
			if (myDataArray2[0].equalsIgnoreCase("incr")) {
				incrementData(dbCommand2, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct INCR command.");
			}

			System.out.println("Enter the INCR command for the key:");
			String dbCommand3 = reader.readLine();
			String myDataArray3[] = dbCommand3.split(" ");
			if (myDataArray3[0].equalsIgnoreCase("incr")) {
				incrementData(dbCommand3, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct INCR command.");
			}

			System.out.println("Enter the SET command:");
			String dbCommand4 = reader.readLine();
			String myDataArray4[] = dbCommand1.split(" ");
			if (myDataArray4[0].equalsIgnoreCase("set")) {
				setDataInDataBase(dbCommand4, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct SET command.");
			}

			System.out.println("Enter the GET command to get the counter value:");
			String dbCommand5 = reader.readLine();
			String myInputCommand5 = dbCommand5.toLowerCase();
			if (myInputCommand5.contains("get")) {
				getDataFromDatabase(dbCommand5, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct GET command.");
			}

			System.out.println("Enter the INCR command for the key:");
			String dbCommand6 = reader.readLine();
			String myDataArray6[] = dbCommand6.split(" ");
			if (myDataArray6[0].equalsIgnoreCase("incr")) {
				incrementData(dbCommand6, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct INCR command.");
			}

			System.out.println("Enter the COMPACT command:");
			String dbCommand7 = reader.readLine();
			String myDataArray7[] = dbCommand7.split(" ");
			if (myDataArray7[0].equalsIgnoreCase("compact")) {
				compactCommand(connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct COMPACT command.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Case4Eg2() throws IOException, Exception {
		Jedis connectToDatabase = new Jedis("localhost");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You've called CASE4 EXAMPLE2");
		try {
			System.out.println("Enter the INCR command: (KEY DOES NOT EXISTS AND IT WILL MAKE A NEW KEY)");
			String dbCommand1 = reader.readLine();
			String myDataArray1[] = dbCommand1.split(" ");
			if (myDataArray1[0].equalsIgnoreCase("incr")) {
				incrementData(dbCommand1, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct INCR command.");
			}

			System.out.println("Enter the INCRBY command for the previous key:");
			String dbCommand2 = reader.readLine();
			String myDataArray2[] = dbCommand2.split(" ");
			if (myDataArray2[0].equalsIgnoreCase("incrby")) {
				incrementBySomeValue(dbCommand2, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct INCRBY command.");
			}

			System.out.println("Enter the GET command to get the counter value:");
			String dbCommand3 = reader.readLine();
			String myInputCommand3 = dbCommand3.toLowerCase();
			if (myInputCommand3.contains("get")) {
				getDataFromDatabase(dbCommand3, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct GET command.");
			}

			System.out.println("Enter DEL command for above key: ");
			String dbCommand4 = reader.readLine();
			String myInputCommand4 = dbCommand4.toLowerCase();
			if (myInputCommand4.contains("del")) {
				deleteDataFromDatabase(dbCommand4, connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct DEL command.");
			}

			System.out.println("Enter the COMPACT command:");
			String dbCommand5 = reader.readLine();
			String myDataArray5[] = dbCommand5.split(" ");
			if (myDataArray5[0].equalsIgnoreCase("compact")) {
				compactCommand(connectToDatabase);
			} else {
				throw new Exception("You did not enter the correct COMPACT command.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
