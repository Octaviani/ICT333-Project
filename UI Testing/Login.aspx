<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Login.aspx.cs" Inherits="Login" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>KBL Optika Login Page</title>
    <script src="Scripts/jquery-1.9.1.js"></script>
</head>
<body>
    <div>
        <h1>KBL Optika</h1>
    </div>
    <form id="form1" runat="server">
        <div>
            <asp:Login ID = "Login1" runat = "server" OnAuthenticate= "ValidateUser" Height="232px" LoginButtonText="Login" TitleText="Login" UserNameLabelText="Username:" UserNameRequiredErrorMessage="Username is required." Width="382px"></asp:Login>
        </div>
    </form>
</body>
</html>
