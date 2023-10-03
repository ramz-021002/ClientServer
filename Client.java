import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("<Server-IP>", <port>);
            System.out.println("Connected to server.");

            receiveFile(socket, "received.zip");

            socket.close();
            System.out.println("File received from server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveFile(Socket socket, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream(fileName);

        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        fos.close();
    }
}