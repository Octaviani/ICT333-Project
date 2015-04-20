<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="UserProfile.aspx.cs" Inherits="UserProfile" %>

<asp:Content ID="Content1" ContentPlaceHolderID="title" Runat="Server">
    MyProfile
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="head" Runat="Server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Content1" Runat="Server">
    <div>
    <fieldset style="width:350px;">
    <legend>Change password</legend>
    <table>
    <tr>
    <td>User Name: * </td>
    <td>
        <asp:TextBox ID="txtUserName" runat="server"></asp:TextBox><br />
        <asp:RequiredFieldValidator ID="rfvuserName" runat="server"
            ErrorMessage="Please enter User Name" ControlToValidate="txtUserName"
            Display="Dynamic" ForeColor="Red" SetFocusOnError="True"></asp:RequiredFieldValidator>
        </td>
    </tr>
    <tr>
    <td>Old Password: * </td>
    <td>
        <asp:TextBox ID="txtOldPwd" runat="server" TextMode="Password"></asp:TextBox><br />
        <asp:RequiredFieldValidator ID="rfvOldPwd" runat="server"
            ErrorMessage="Please enter old password" ControlToValidate="txtOldPwd"
            Display="Dynamic" ForeColor="Red" SetFocusOnError="True"></asp:RequiredFieldValidator>
        </td>
    </tr>
     <tr>
    <td>New Password: * </td>
    <td>
        <asp:TextBox ID="txtNewPwd" runat="server" TextMode="Password"></asp:TextBox><br />
        <asp:RequiredFieldValidator ID="rfvNewPwd" runat="server"
            ErrorMessage="Please enter new password" ControlToValidate="txtNewPwd"
            Display="Dynamic" ForeColor="Red" SetFocusOnError="True"></asp:RequiredFieldValidator>
         </td>
    </tr>
         <tr>
    <td>Confirm Password: * </td>
    <td>
        <asp:TextBox ID="txtConfirmPwd" runat="server" TextMode="Password"></asp:TextBox><br />
        <asp:RequiredFieldValidator ID="rfvConfirmPwd" runat="server"
            ErrorMessage="Please re-enter password to confirm"
            ControlToValidate="txtConfirmPwd" Display="Dynamic" ForeColor="Red"
            SetFocusOnError="True"></asp:RequiredFieldValidator>
        <asp:CompareValidator ID="cmvConfirmPwd" runat="server"
            ControlToCompare="txtNewPwd" ControlToValidate="txtConfirmPwd"
            Display="Dynamic" ErrorMessage="New and confirm password didn't match"
            ForeColor="Red" SetFocusOnError="True"></asp:CompareValidator>
             </td>
    </tr>
     <tr>
    <td>
        &nbsp;</td><td>
        <asp:Button ID="btnSubmit" runat="server" Text="Change Password"
             onclick="btnSubmit_Click" /></td>
    </tr>
     <tr>
    <td colspan="2">
        <asp:Label ID="lblStatus" runat="server" Text=""></asp:Label>
         </td>
    </tr>
    </table>
    </fieldset>   
    </div>
    </asp:Content>

