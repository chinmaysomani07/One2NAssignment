import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

class TestForImplementedRedisCommands {

	@Test
	void test_setDataInDataBase() {
		ImplementRedisCommands test = new ImplementRedisCommands();
		String dbCommand = "SET NAME CHINMAY";
		Jedis connectToDatabase = new Jedis("localhost");
		String myDataArray[] = dbCommand.split(" ");
		test.setDataInDataBase(dbCommand, connectToDatabase);
		assertTrue(connectToDatabase.get(myDataArray[1]).toString() != null);
	}

	@Test
	void test_getDataFromDataBase() {
		ImplementRedisCommands test = new ImplementRedisCommands();
		String dbCommand = "GET NAME CHINMAY";
		Jedis connectToDatabase = new Jedis("localhost");
		String myDataArray[] = dbCommand.split(" ");
		connectToDatabase.set("NAME", "CHINMAY");
		assertEquals("CHINMAY", test.getDataFromDatabase(dbCommand, connectToDatabase));
	}

	@Test
	void test_deleteDataFromDatabase() {
		ImplementRedisCommands test = new ImplementRedisCommands();
		String dbCommand = "DEL SURNAME";
		Jedis connectToDatabase = new Jedis("localhost");
		String myDataArray[] = dbCommand.split(" ");
		test.deleteDataFromDatabase(dbCommand, connectToDatabase);
		assertNull(connectToDatabase.get(myDataArray[1]));
	}

	@Test
	void test_incrementData() {
		ImplementRedisCommands test = new ImplementRedisCommands();
		String dbCommand = "INCR COUNTER";
		Jedis connectToDatabase = new Jedis("localhost");
		String myDataArray[] = dbCommand.split(" ");
		connectToDatabase.set("COUNTER", "0");
		test.incrementData(dbCommand, connectToDatabase);
		assertEquals("1", connectToDatabase.get("COUNTER"));
	}

	@Test
	void test_incrementBySomeValue() {
		ImplementRedisCommands test = new ImplementRedisCommands();
		String dbCommand = "INCRBY COUNTER 10";
		Jedis connectToDatabase = new Jedis("localhost");
		String myDataArray[] = dbCommand.split(" ");
		connectToDatabase.set("COUNTER", "0");
		test.incrementBySomeValue(dbCommand, connectToDatabase);
		assertEquals("10", connectToDatabase.get("COUNTER"));
	}
}
