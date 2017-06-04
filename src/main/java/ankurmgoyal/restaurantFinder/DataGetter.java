package ankurmgoyal.restaurantFinder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;
import com.sun.jersey.api.client.ClientResponse;

public class DataGetter {
	
	private static String user;
	private static String pw;
	private static String token;
	
	public static void main(String[] args){
		try{
			setProps();
			System.out.println("Running query...");
			makeCall();
			System.out.println("Finished.");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void makeCall() throws LongRunningQueryException, SodaError{
		Soda2Consumer consumer = Soda2Consumer.newConsumer("https://data.sfgov.org/",user,pw,token);
		
		SoqlQuery   testQuery = new SoqlQueryBuilder()
		        .setWhereClause("neighborhood_code='03G'")
		        .build();
		
		ClientResponse rs = consumer.query("fk72-cxc3", HttpLowLevel.JSON_TYPE, testQuery);
		System.out.println(rs.getStatus());
		System.out.println(rs.getLength());
		
	}
	
	public static void setProps() throws IOException{
		Properties prop = new Properties();
    	InputStream input = null;
    	input = new FileInputStream("config.properties");
    	prop.load(input);
		user = prop.getProperty("account");
		pw = prop.getProperty("password");
		token = prop.getProperty("token");
	}
}
