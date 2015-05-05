package HiveSys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import HiveSys.layout.LoginLayout;

public class Login extends LoginLayout{
	Login(Connection connection)
	{
		ClickListener listener= new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) 
			{
				// TODO Auto-generated method stub
				String userName1 = usrNmeFld.getValue();
			    String password1 = pwFld.getValue();
			    Class.forName("com.mysql.jdbc.Driver");
			    Connection con = DriverManager.getConnection("jdbc:mysql://datahive.com:3306/Hive","daniel", "password");
			    Statement st = con.createStatement();
			    ResultSet rs;
			    rs = st.executeQuery("SELECT * FROM User WHERE UserName='" + userName1 + "' AND password='" +password1 + "'");
			    if (rs.next()) 
			    {
			        session.setAttribute("userid", userName1);
			        response.sendRedirect("FileUpload.java");
			    } 
			    	else 
			    	{
			    		out.println("Invalid password try again");
			    	}
			}
			
		};
		this.btnLogin.addClickListener(listener);
	    
	}
}




