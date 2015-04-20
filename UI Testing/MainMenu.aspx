<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="MainMenu.aspx.cs" Inherits="MainMenu" %>

<asp:Content ID="Content1" ContentPlaceHolderID="title" Runat="Server">
    MainMenu
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="head" Runat="Server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Content1" Runat="Server">      
        <ul>
            <li><a href="UserProfile.aspx" target="_blank">MyProfile</a></li>
            <li>New Search
                <ul>
                    <li>Search: <input id="Text1" type="text" /> <input id="SearchSub" type="submit" value="submit" /></li>
                </ul>
            </li>
            <li>Upload New Document
                <ul>
                    <li>Upload: <input id="File1" type="file" /> <input id="UploadSub" type="submit" value="Upload" /></li>
                </ul>
            </li>
            <li><a href="Admin.aspx" target="_blank">Admin Page</a></li>
        </ul>
</asp:Content>

