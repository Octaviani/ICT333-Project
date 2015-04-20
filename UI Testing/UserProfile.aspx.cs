using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;

public partial class UserProfile : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void btnSubmit_Click(object sender, EventArgs e)
    {
        try
        {
            SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["kblConnectionString1"].ConnectionString);
            SqlCommand cmd = new SqlCommand("Change_Password", con);
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.Parameters.AddWithValue("@UserName", txtUserName.Text.Trim());
            cmd.Parameters.AddWithValue("@OldPassword", txtOldPwd.Text.Trim());
            cmd.Parameters.AddWithValue("@NewPassword", txtNewPwd.Text.Trim());

            cmd.Parameters.Add("@Status", SqlDbType.Int);
            cmd.Parameters["@Status"].Direction = ParameterDirection.Output;
            con.Open();
            cmd.ExecuteNonQuery();
            cmd.Dispose();
            con.Close();
            //read the return value (i.e status) from the stored procedure
            int retVal = Convert.ToInt32(cmd.Parameters["@Status"].Value);
            if (retVal == 1)
            {
                lblStatus.Text = "Password has been changed successfully";
                // Or show in messagebox using: ScriptManager.RegisterStartupScript(this, this.GetType(), "Message", "alert('Password has been changed successfully');", true);
            }
            else
            {
                lblStatus.Text = "Wrong old username/password. Please re-enter.";
                // Or show in messagebox using: ScriptManager.RegisterStartupScript(this, this.GetType(), "Message", "alert('Wrong username/password. Please re-enter.');", true);
            }
        }
        catch (Exception ex)
        {
            ScriptManager.RegisterStartupScript(this, this.GetType(), "Message", "alert('Oops!! following error occured : " + ex.Message.ToString() + "');", true);
            // Response.Write("Oops!! following error occured: " +ex.Message.ToString());           
        }
    }

}