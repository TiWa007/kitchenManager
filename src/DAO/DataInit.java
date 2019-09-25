package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataInit {
	
//	private static final String NAME_DB = "test_km";

	public static void dataInit() throws SQLException {
//		Database 
		List<String> sqlList = new ArrayList<String>();
//		String sqlDatabase_drop = "DROP DATABASE IF EXISTS test_km";
		String sqlDatabase_create = "CREATE DATABASE IF NOT EXISTS test_km;";

//		Table member
		String sqlTable_member_drop = "DROP TABLE IF EXISTS `member`;";

		String sqlTable_member_create = "CREATE TABLE IF NOT EXISTS `member` ("
				  + "`member_id` int(11) NOT NULL AUTO_INCREMENT,"
				  + "`room_number` int(11) NOT NULL,"
				  + "`name` varchar(45) NOT NULL,"
				  + "`email` varchar(45) NOT NULL,"
				  + "PRIMARY KEY (`member_id`),"
				 + " UNIQUE KEY `member_id_UNIQUE` (`member_id`),"
				  + "UNIQUE KEY `room_number_UNIQUE` (`room_number`));";

		String sqlTable_member_data = "INSERT INTO `member` VALUES (6,1,'Name1','Name1@email'),"
				+ "(7,2,'Name2','Name2@email'),(8,3,'Name3','Name3@email'),"
				+ "(9,4,'Name4','Name4@email'),(10,5,'Name5','Name5@email'),"
				+ "(11,6,'Name6','Name6@email'),(12,7,'Name7','Name7@email'),"
				+ "(13,8,'Name8','Name8@email'),(14,9,'Name9','Name9@email'),"
				+ "(15,10,'Name10','Name10@email'),(16,11,'Name11','Name11@email'),"
				+ "(17,12,'Name12','Name12@email');";
				
//			Cashier	
		String sqlCashier_delete = "DROP TABLE IF EXISTS `cashier`;";

		String sqlCashier_create = "CREATE TABLE `cashier` (" 
				+ "`cashier_id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`member_id` int(11) NOT NULL,"
						  + "`email` varchar(45) NOT NULL,"
						  + "`password` varchar(45) NOT NULL,"
						  + "`email_smtp_host` varchar(45) NOT NULL,"
						  + "`mobilepay` varchar(45) DEFAULT NULL,"
						  + "`bank_reg` varchar(4) DEFAULT NULL,"
						  + "`bank_account` varchar(10) DEFAULT NULL,"
						  + "`month_fee` double NOT NULL,"
						  + "PRIMARY KEY (`cashier_id`),"
						  + "UNIQUE KEY `cashier_id_UNIQUE` (`cashier_id`),"
						  + "UNIQUE KEY `member_id_UNIQUE` (`member_id`),"
						  + "CONSTRAINT `cashier_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`));";

		String sqlCashier_add = "INSERT INTO `cashier` VALUES (1,8,'Name3@email','password','smtp','00000000','0000','0000000000',20);";

//		Table kitchen_fee_month
		String sqlTable_kitchen_fee_month_drop = "DROP TABLE IF EXISTS `kitchen_fee_month`;";
		
		String sqlTable_kitchen_fee_month_create = "CREATE TABLE `kitchen_fee_month` (" 
				+ "`month_id` int(11) NOT NULL AUTO_INCREMENT,"  
				+ " `month` date NOT NULL,"  
				+ " PRIMARY KEY (`month_id`)," 
				+ " UNIQUE KEY `month_id_UNIQUE` (`month_id`),"  
				+ " UNIQUE KEY `month_UNIQUE` (`month`))";
		
		String sqlTable_kitchen_fee_month_add = "INSERT INTO `kitchen_fee_month` "
				+ "VALUES (10,'2018-09-01'),(11,'2018-10-01'),(12,'2018-11-01'),(13,'2018-12-01'),"
				+ "(14,'2019-01-01'),(15,'2019-02-01'),(16,'2019-03-01'),(17,'2019-04-01'),"
				+ "(18,'2019-05-01'),(19,'2019-06-01'),(20,'2019-07-01'),(21,'2019-08-01'),"
				+ "(22,'2019-09-01'),(24,'2019-10-01'),(25,'2019-11-01'),(26,'2019-12-01'),"
				+ "(27,'2020-01-01'),(28,'2020-02-01'),(29,'2020-03-01');"; 
		
//		Table `kitchen_fee_recipt`
		String sqlTable_kitchen_fee_recipt_drop = "DROP TABLE IF EXISTS `kitchen_fee_recipt`;";	
		String sqlTable_kitchen_fee_recipt_create = "CREATE TABLE `kitchen_fee_recipt` (" + 
				"  `fee_id` int(11) NOT NULL AUTO_INCREMENT," + 
				"  `member_id` int(11) NOT NULL," + 
				"  `month_id` int(11) NOT NULL," + 
				"  `date` date DEFAULT NULL," + 
				"  PRIMARY KEY (`fee_id`)," + 
				"  UNIQUE KEY `recipt_id_UNIQUE` (`fee_id`)," + 
				"  KEY `fk_fee_member_idx` (`member_id`)," + 
				"  KEY `fk_fee_month_idx` (`month_id`)," + 
				"  CONSTRAINT `fk_fee_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)," + 
				"  CONSTRAINT `fk_fee_month` FOREIGN KEY (`month_id`) REFERENCES `kitchen_fee_month` (`month_id`));";
		String sqlTable_kitchen_fee_recipt_add = "INSERT INTO `kitchen_fee_recipt` "
				+ "VALUES (2,6,11,'2018-10-01'),(3,6,12,'2018-11-01'),(4,6,13,'2018-12-01'),"
				+ "(5,6,14,'2019-01-01'),(6,6,15,'2019-02-01'),(7,6,16,'2019-03-01'),"
				+ "(8,6,17,'2019-04-01'),(9,6,18,'2019-05-01'),(10,6,19,'2019-06-01'),"
				+ "(11,6,20,'2019-07-01'),(12,6,21,'2019-08-01'),(16,7,11,'2018-10-01'),"
				+ "(17,7,12,'2018-11-01'),(18,7,13,'2018-12-01'),(19,7,14,'2019-01-01'),"
				+ "(20,7,15,'2019-02-01'),(21,7,16,'2019-03-01'),(26,8,10,'2018-09-01'),"
				+ "(27,8,11,'2018-10-01'),(28,8,12,'2018-11-01'),(29,8,13,'2018-12-01'),"
				+ "(30,8,14,'2019-01-01'),(31,8,15,'2019-02-01'),(32,8,16,'2019-03-01'),"
				+ "(33,8,17,'2019-04-01'),(34,8,18,'2019-05-01'),(35,8,19,'2019-06-01'),"
				+ "(36,8,20,'2019-07-01'),(37,8,21,'2019-08-01'),(38,8,22,'2019-09-01'),"
				+ "(42,9,10,'2018-09-01'),(43,9,11,'2018-10-01'),(44,9,12,'2018-11-01'),"
				+ "(45,9,13,'2018-12-01'),(46,9,14,'2019-01-01'),(47,9,15,'2019-02-01'),"
				+ "(48,9,16,'2019-03-01'),(49,10,10,'2018-09-01'),(50,10,11,'2018-10-01'),"
				+ "(51,10,12,'2018-11-01'),(52,10,13,'2018-12-01'),(53,10,14,'2019-01-01'),"
				+ "(54,11,10,'2018-09-01'),(55,11,11,'2018-10-01'),(56,11,12,'2018-11-01'),"
				+ "(57,11,13,'2018-12-01'),(58,11,14,'2019-01-01'),(59,11,15,'2019-02-01'),"
				+ "(60,11,16,'2019-03-01'),(61,11,17,'2019-04-01'),(62,12,10,'2018-09-01'),"
				+ "(63,12,11,'2018-10-01'),(64,12,12,'2018-11-01'),(65,12,13,'2018-12-01'),"
				+ "(66,12,14,'2019-01-01'),(67,12,15,'2019-02-01'),(68,12,16,'2019-03-01'),"
				+ "(69,12,17,'2019-04-01'),(70,12,18,'2019-05-01'),(71,12,19,'2019-06-01'),"
				+ "(72,12,20,'2019-07-01'),(73,13,10,'2018-09-01'),(74,13,11,'2018-10-01'),"
				+ "(75,13,12,'2018-11-01'),(76,13,13,'2018-12-01'),(77,13,14,'2019-01-01'),"
				+ "(78,13,15,'2019-02-01'),(79,13,16,'2019-03-01'),(80,13,17,'2019-04-01'),"
				+ "(81,13,18,'2019-05-01'),(82,13,19,'2019-06-01'),(83,13,20,'2019-07-01'),"
				+ "(84,14,10,'2018-09-01'),(85,14,11,'2018-10-01'),(86,14,12,'2018-11-01'),"
				+ "(87,14,13,'2018-12-01'),(88,14,14,'2019-01-01'),(89,14,15,'2019-02-01'),"
				+ "(90,14,16,'2019-03-01'),(91,14,17,'2019-04-01'),(92,14,18,'2019-05-01'),"
				+ "(93,14,19,'2019-06-01'),(94,14,20,'2019-07-01'),(95,15,10,'2018-09-01'),"
				+ "(96,15,11,'2018-10-01'),(97,15,12,'2018-11-01'),(98,15,13,'2018-12-01'),"
				+ "(99,15,14,'2019-01-01'),(100,15,15,'2019-02-01'),(101,15,16,'2019-03-01'),"
				+ "(102,16,10,'2018-09-01'),(103,16,11,'2018-10-01'),(104,16,12,'2018-11-01'),"
				+ "(105,16,13,'2018-12-01'),(106,16,14,'2019-01-01'),(107,16,15,'2019-02-01'),"
				+ "(108,16,16,'2019-03-01'),(109,16,17,'2019-04-01'),(110,16,18,'2019-05-01'),"
				+ "(111,16,19,'2019-06-01'),(112,16,20,'2019-07-01'),(113,17,10,'2018-09-01'),"
				+ "(114,17,11,'2018-10-01'),(115,17,12,'2018-11-01'),(116,17,13,'2018-12-01'),"
				+ "(117,17,14,'2019-01-01'),(118,17,15,'2019-02-01'),(119,17,16,'2019-03-01'),"
				+ "(120,17,17,'2019-04-01'),(121,17,18,'2019-05-01'),(122,17,19,'2019-06-01'),"
				+ "(123,17,20,'2019-07-01'),(126,7,10,'2019-09-16'),(127,10,15,'2019-09-16'),"
				+ "(128,6,10,'2019-09-16'),(129,9,17,'2019-09-17'),(130,9,18,'2019-09-17'),"
				+ "(131,9,19,'2019-09-17'),(132,9,20,'2019-09-17'),(133,9,21,'2019-09-17'),"
				+ "(134,11,18,'2019-09-17'),(135,11,19,'2019-09-17'),(136,11,20,'2019-09-17'),"
				+ "(137,11,21,'2019-09-17'),(138,14,21,'2019-09-17'),(139,14,22,'2019-09-17'),"
				+ "(142,7,17,'2019-09-17'),(143,7,18,'2019-09-17'),(144,7,19,'2019-09-17'),"
				+ "(145,7,20,'2019-09-17'),(146,7,21,'2019-09-17'),(147,7,22,'2019-09-17'),"
				+ "(149,16,21,'2019-09-17'),(150,16,22,'2019-09-17'),(151,17,21,'2019-09-19'),"
				+ "(153,12,21,'2019-09-19'),(156,12,21,'2019-09-19'),(159,13,21,'2019-09-19'),"
				+ "(160,13,22,'2019-09-19'),(162,10,16,'2019-09-25'),(163,10,17,'2019-09-25'),"
				+ "(164,10,18,'2019-09-25'),(165,10,19,'2019-09-25'),(166,10,20,'2019-09-25'),"
				+ "(167,10,21,'2019-09-25'),(168,10,22,'2019-09-25'),(169,15,17,'2019-09-25'),"
				+ "(170,15,18,'2019-09-25'),(171,15,19,'2019-09-25'),(172,15,20,'2019-09-25'),"
				+ "(173,15,21,'2019-09-25'),(174,15,22,'2019-09-25'),(175,8,24,'2019-09-25'),"
				+ "(176,10,24,'2019-09-25');";		

//		Table `recipt`
		String sqlTable_recipt_drop = "DROP TABLE IF EXISTS `recipt`;";
		String sqlTable_recipt_create = "CREATE TABLE `recipt` (" + 
				" `recipt_id` int(11) NOT NULL AUTO_INCREMENT," + 
				" `member_id` int(11) NOT NULL," + 
				" `date` date DEFAULT NULL," + 
				" `amount` double NOT NULL," + 
				" `comment` varchar(255) DEFAULT NULL," + 
				" PRIMARY KEY (`recipt_id`)," + 
				" UNIQUE KEY `recipt_id_UNIQUE` (`recipt_id`)," + 
				" KEY `recipt_member_id_idx` (`member_id`)," + 
				" CONSTRAINT `recipt_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)" + 
				")";
		String sqlTable_recipt_add = "INSERT INTO `recipt` VALUES (21,7,'2019-09-04',-9,'Netto'),"
				+ "(22,7,'2019-09-04',-60,'Netto'),(23,14,'2019-08-20',-20,'Netto'),"
				+ "(24,14,'2019-08-12',-20,'Netto'),(26,16,'2019-08-11',-7,'Netto'),"
				+ "(27,8,'2019-08-01',-29,'Netto'),(28,12,'2019-07-29',-60,'Netto'),"
				+ "(29,7,'2019-07-29',-30,'Netto'),(30,17,'2019-07-28',140,'Jan to July Kitchen fee'),"
				+ "(31,17,'2019-07-28',-102,'Netto'),(32,6,'2019-07-18',100,'Jan to May Kitchen fee'),"
				+ "(33,14,'2019-07-15',60,'May to July kitchen fee'),"
				+ "(34,13,'2019-07-15',80,'April to July kitchen fee'),(35,9,'2019-07-10',-50,'Netto'),"
				+ "(36,16,'2019-07-10',80,'April to July kitchen fee'),(37,14,'2019-07-10',-24,'Netto'),"
				+ "(38,7,'2019-07-10',-30,'Netto'),(39,10,'2019-07-10',-55,'Netto'),"
				+ "(41,13,'2019-07-02',2000,'Initial balance'),"
				+ "(42,14,'2019-09-17',40,'Kitchen Fee Aug and Sep'),"
				+ "(44,11,'2019-09-17',20,'Kitchen Fee Sep'),(46,16,'2019-09-17',40,'Kitchen Fee Aug Sep'),"
				+ "(47,17,'2019-09-19',40,'Kitchen Fee Aug Sep'),(49,13,'2019-09-19',40,'Kitchen Fee Aug Sep');";
		
//		sqlList.add(sqlDatabase_drop);
		sqlList.add(sqlDatabase_create);
//		Drop
		sqlList.add(sqlTable_kitchen_fee_recipt_drop);
		sqlList.add(sqlTable_kitchen_fee_month_drop);
		sqlList.add(sqlTable_recipt_drop);
		sqlList.add(sqlCashier_delete);
		sqlList.add(sqlTable_member_drop);
		
//		Create & Add
			
		sqlList.add(sqlTable_member_create);
		sqlList.add(sqlTable_member_data);
		
		sqlList.add(sqlCashier_create);
		sqlList.add(sqlCashier_add);
		
		sqlList.add(sqlTable_kitchen_fee_month_create);
		sqlList.add(sqlTable_kitchen_fee_month_add);
		
		sqlList.add(sqlTable_kitchen_fee_recipt_create);
		sqlList.add(sqlTable_kitchen_fee_recipt_add);
		
		sqlList.add(sqlTable_recipt_create);
		sqlList.add(sqlTable_recipt_add);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBConnection.getConnection();
			for (String sql : sqlList) {
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		dataInit();
	}

}
