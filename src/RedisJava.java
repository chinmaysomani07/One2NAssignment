import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisJava {
	public static void main(String[] args) throws Exception {
		MyMethodsClass myMethodClass = new MyMethodsClass();
		Scanner sc = new Scanner(System.in);
		try {
			Jedis connectToDatabase = new Jedis("localhost"); // used to connect to Redis database running on
																// "localhost"
			Transaction transaction = connectToDatabase.multi();
			System.out.println("Welcome to Command Line");
			System.out.println("Press");
			System.out.println("--> 1 for performing database operations.");
			System.out.println("--> 2 for if you directly want to check Cases");
			int inputForChecks = sc.nextInt();
			// to manually enter the commands.
			if (inputForChecks == 1) {
				while (true) {
					try {
						// runs in an infinite loop as user my want to give multiple commands
						System.out.println("Press ");
						System.out.println("--> 1 for giving command input.");
						System.out.println("--> 0 to exit.");
						int inputChoice = sc.nextInt();
						if (inputChoice == 1) {
							System.out.println("Enter your Command:");
							BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
							String dbCommand = reader.readLine();
							String myInputCommand = dbCommand.toLowerCase();
							String myDataArray[] = dbCommand.split(" ");
							System.out.println("My input command is : " + myInputCommand);
							if (myDataArray[0].equalsIgnoreCase("set")) {
								myMethodClass.setDataInDataBase(dbCommand, connectToDatabase);
							} else if (myDataArray[0].equalsIgnoreCase("get")) {
								myMethodClass.getDataFromDatabase(dbCommand, connectToDatabase);
							} else if (myDataArray[0].equalsIgnoreCase("del")) {
								myMethodClass.deleteDataFromDatabase(dbCommand, connectToDatabase);
							} else if (myDataArray[0].equalsIgnoreCase("incr")) {
								myMethodClass.incrementData(dbCommand, connectToDatabase);
							} else if (myDataArray[0].equalsIgnoreCase("incrby")) {
								myMethodClass.incrementBySomeValue(dbCommand, connectToDatabase);
							} else if (myDataArray[0].equalsIgnoreCase("multi")) {
								myMethodClass.multiTransactions(connectToDatabase, transaction);
							} else if (myDataArray[0].equalsIgnoreCase("compact")) {
								myMethodClass.compactCommand(connectToDatabase);
							} else {
								throw new Exception("Not a valid command, please check your command.");
							}
						} else if (inputChoice == 0) {
							System.out.println("Thank you for using, exiting now!!!");
							break;
						} else {
							System.out.println(+inputChoice + " is not a valid input, please enter valid input.");
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			} else if (inputForChecks == 2) {
				System.out.println("Enter the case choice you want to execute......");
				System.out.println("PRESS:");
				System.out.println("1 for Case 1:  SET key-values and then GET them. You can also\r\n" + "DEL a key");

				System.out.println("2 for Case 2: Basic numeric operations and error cases");
				System.out.println("3 for Case 3: Command spanning multiple sub-commands HAPPY PATH");
				System.out.println("4 for Case 3: Command spanning multiple sub-commands DISCARD");
				System.out.println("5 for Case 4: Generate compacted command output EXAMPLE 1");
				System.out.println("6 for Case 4: Generate compacted command output EXAMPLE 2");
				int caseChoice = sc.nextInt();
				if (caseChoice == 1) {
					myMethodClass.Case1();
				} else if (caseChoice == 2) {
					myMethodClass.Case2();
				} else if (caseChoice == 3) {
					myMethodClass.Case3HappyPath();
				} else if (caseChoice == 4) {
					myMethodClass.Case3Discard();
				} else if (caseChoice == 5) {
					myMethodClass.Case4Eg1();
				} else if (caseChoice == 6) {
					myMethodClass.Case4Eg2();
				}
			} else {
				System.out.println("Please enter the correct choice.");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}