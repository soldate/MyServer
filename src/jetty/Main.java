package jetty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public abstract class Main {

	private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String dbName="jdbcDemoDB";
	//private static final String connectionURL = "jdbc:derby:" + dbName + ";create=true";
	private static final String connectionURL = "jdbc:derby:memory:" + dbName + ";create=true";
	private static Connection conn;
	private static Main main;
	private JSONObject json;
	
	protected Main() {
		String createString = "CREATE TABLE Log  "
				  +  "(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
				  +  " WHO VARCHAR(256), "
				  +  " WHEN TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
				  +  " JSON VARCHAR(2048) NOT NULL) " ;
		
		try {
			if (conn == null) conn = DriverManager.getConnection(connectionURL);
			Statement s = conn.createStatement();
			//s.execute("DROP TABLE Log");
			//s = conn.createStatement();
			s.execute(createString);
			
		}  catch (Throwable e)  {   
			e.printStackTrace();
		}		
	}

	void execute(String sql) throws SQLException {
		Statement s = conn.createStatement();
		s.execute(sql);		
	}
	
	ResultSet executeQuery(String sql) throws SQLException {
		Statement s = conn.createStatement();
		return s.executeQuery(sql);		
	}
	
	static Main getInstance() {
		if (main == null) main = new your.Main();
		return main;
	}
	
	void setJSON(JSONObject json) {
		this.json = json;
	} 
	
	protected JSONObject getJSON() {
		return json;
	} 
	
	protected void print(String s) {
		json.put("text", s);
	}
	
	public void main(String[] args, HttpServletRequest request, HttpServletResponse response) {
		main(args);
	}
	
	public abstract void main(String[] args);
}