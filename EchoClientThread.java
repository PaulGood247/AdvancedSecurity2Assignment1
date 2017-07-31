import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.net.*;
import java.io.*;


public class EchoClientThread extends Thread
{  
   private SSLSocketFactory sslsocketfactory= null;
   private SSLSocket sslsocket=null;
   private AuctionClient       client   = null;
   private DataInputStream  streamIn = null;

   public EchoClientThread(AuctionClient _client, Socket _socket)
   {  
      client   = _client;
	  SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 9999);
      sslsocket   = _socket;
      open();
      start();
   }
   public void open()
   {  try
      {
		  streamIn  = new DataInputStream(socket.getInputStream());
      }
      catch(IOException ioe)
      {
		 System.out.println("Error getting input stream: " + ioe);
         client.stop();
      }
   }
   public void close()
   {  try
      {  if (streamIn != null) streamIn.close();
      }
      catch(IOException ioe)
      {  System.out.println("Error closing input stream: " + ioe);
      }
   }

   public void run()
   {
	   while (true && client!= null){
		  try {

			  client.handle(streamIn.readUTF());
          }
          catch(IOException ioe)
          {
			  client = null;
			  //System.out.println("Listening error: " + ioe.getMessage());

         }
      }
   }
}



