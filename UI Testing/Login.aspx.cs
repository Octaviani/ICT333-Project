using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;
using System.Web.Security;

public partial class Login : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void ValidateUser(object sender, EventArgs e)
    {
        int Id = 0;
        string kblConnectionString1 = ConfigurationManager.ConnectionStrings["kblConnectionString1"].ConnectionString;
        using (SqlConnection con = new SqlConnection(kblConnectionString1))
        {
            using (SqlCommand cmd = new SqlCommand("Validate_User"))
            {
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@Username", Login1.UserName);
                cmd.Parameters.AddWithValue("@Password", Login1.Password);
                cmd.Connection = con;
                con.Open();
                Id = Convert.ToInt32(cmd.ExecuteScalar());
                con.Close();
            }
            switch (Id)
            {
                case -1:
                    Login1.FailureText = "Username and/or password is incorrect.";
                    break;
                default:
                    FormsAuthentication.RedirectFromLoginPage(Login1.UserName, Login1.RememberMeSet);
                    break;
            }
        }
    }
}