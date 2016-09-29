using System;
using System.IO;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;

namespace DotNet_Communications_MultipartMessage
{
    class Program
    {
        static void Main(string[] args)
        {
            // Grab the first file in 'My Pictures' and send it
            var myPicturesPath = Environment.GetFolderPath(Environment.SpecialFolder.MyPictures);
            var imageFile = GetFirstImage(myPicturesPath);
            Send(imageFile);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="imageFile"></param>
        private static void Send(FileInfo imageFile)
        {
            // Get file contents
            byte[] fileContents = File.ReadAllBytes(imageFile.FullName);

            // Package file contents into a transportable format
            MultipartFormDataContent content = new MultipartFormDataContent("--gc0p");
            ByteArrayContent byteContent = new ByteArrayContent(fileContents);
            byteContent.Headers.Add("Content-Type", "application/octet-stream");
            content.Add(byteContent, "this is the name of the content", imageFile.Name);

            // Construct request message
            Uri serviceAddress = new Uri(@"http://localhost:5555/");
            HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, serviceAddress);
            request.Headers.ExpectContinue = false; // Some servers don’t know how to handle the ‘Continue’ value and they will throw an exception
            request.Content = content;

            // Send it
            HttpClient client = new HttpClient();
            try
            {
                Task<HttpResponseMessage> httpRequest = client.SendAsync(request, HttpCompletionOption.ResponseContentRead, CancellationToken.None);

                // Get the response
                HttpResponseMessage response = httpRequest.Result;
                //HttpStatusCode status = response.StatusCode;
                HttpContent responseContent = response.Content;

                if (responseContent != null)
                {
                    Task<String> stringContentsTask = responseContent.ReadAsStringAsync();
                    String stringContents = stringContentsTask.Result;
                    Console.WriteLine(stringContents);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        /// <summary>
        /// Simply gets the first jpg image from 'My Pictures' folder
        /// </summary>
        /// <param name="directory"></param>
        /// <returns></returns>
        private static FileInfo GetFirstImage(string directory)
        {
            DirectoryInfo imageDir = new DirectoryInfo(directory);
            try
            {
                if (imageDir.Exists)
                {
                    // Get a list of all 'jpg' file and return the full name of the first one
                    var images = imageDir.GetFiles("*.jpg", SearchOption.AllDirectories);
                    return (images.Length > 0) ? images[0] : null;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return null;
        }
    }
}
