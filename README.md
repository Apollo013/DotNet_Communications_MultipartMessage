# DotNet_Communications_MultipartMessage

A small app demonstrating how to send a multipart message from a DotNet Console app to a Java Web Service

---

Developed with Visual Studio 2015 Community

---

###Techs
|Tech|
|----|
|C#|
|Java|
---

This solution consists of a .Net console app that gets the a jpg image from your 'MyPictures' folder, and then uses a HttpClient object to sends it to a java web service which in turns saves it to disc.

Make sure you run the java web service first, and then run the console app. Also, make sure you have rights to access the MyPictures folder.

---

###Resources
|Title|Author|Website|
|-----|------|-------|
|[How to post a Multipart http message to a web service in C# and handle it with Java](https://dotnetcodr.com/2013/01/10/how-to-post-a-multipart-http-message-to-a-web-service-in-c-and-handle-it-with-java/)| Andras Nemes | dotnetcodr|
|[MultipartFormDataContent Class](https://msdn.microsoft.com/en-us/library/system.net.http.multipartformdatacontent(v=vs.118).aspx)| | MSDN |
|[Uploading a Photo as Multipart Form Data in C#](https://blog.xdumaine.com/uploading-a-photo-as-multipart-form-data-in-c/)|Xander Dumaine|xdumaine.com|
