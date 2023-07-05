package gamil.abbas51813.com.arabicsigntranslator;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class SimpleClient extends AsyncTask<Void, Void, Void>{
    Socket socket;
    Bitmap imageTosend;
    DataInputStream dis;
    int choice;

    public SimpleClient(Bitmap img,int choice) {

        imageTosend = img;
        this.choice=choice;
      }
    @Override
    protected Void doInBackground(Void... voids) {
        socket = null;

        try {

// Open your connection to a server, at port 1254
            socket = new Socket("172.20.10.2", 13085);
            if(choice==1){
                sendImage();
            }else {

                recievePrediction();
            }

// When done, just close the connection and exit
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
    private void sendImage() //sending image to the server
    {
        try {
            // Get an input file handle from the socket and write the output
            OutputStream os = socket.getOutputStream();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageTosend.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            os.write(size);
            os.write(byteArrayOutputStream.toByteArray());
            os.flush();
            //Thread.sleep(120000);
            socket.close();


        } catch (Exception e) {
        }
    }
    public void recievePrediction(){
        try {
        // Get an input file handle from the socket and read the input
        DataInputStream is = new DataInputStream(socket.getInputStream());

        String tag = is.readLine();

        MainActivity.predicted=tag;
        } catch (Exception e) {
        }
    }
}
