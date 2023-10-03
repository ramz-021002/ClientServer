import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(<port>)) {
                System.out.println("Server listening on port <port>...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            File zipFile = new File("send.zip");
            sendFile(clientSocket, zipFile);

            clientSocket.close();
            System.out.println("File sent to client: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(Socket socket, File file) throws IOException {
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(file);
        OutputStream os = socket.getOutputStream();

        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        os.flush();
        fis.close();
    }
}
