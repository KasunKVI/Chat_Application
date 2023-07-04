package lk.ijse.chat_app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomServer {

    ServerSocket serverSocket;
    List<Socket> sockets = new ArrayList<>();
    String massage="";
    DataOutputStream dataOutputStream;

    private static boolean isServerRunning = false;

    public void startServer(){

        if (!isServerRunning) {

            isServerRunning=true;

            new Thread(() -> {

                try {

                    serverSocket = new ServerSocket(3005);
                    System.out.println("Server Started");

                    while (true) {


                        Socket socket = serverSocket.accept();
                        System.out.println("Client accepted");

                        sockets.add(socket);
                        handleClients(socket);

                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        }

    }

    public void  handleClients(Socket socket){


        new Thread(()->{

            try {

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

               do {

                    try {
                        massage = dataInputStream.readUTF();

                        broadcastMassage(massage);

                    }catch(EOFException e){

                        break;

                    }

                } while (!massage.equalsIgnoreCase("finish"));

                sockets.remove(socket);
                closeSocket(socket);

            } catch (IOException e) {

                throw new RuntimeException(e);
            }}).start();

    }

    private void broadcastMassage(String massage) {

        for (Socket socket : sockets){

            try {

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(massage);
                dataOutputStream.flush();

            } catch (IOException e) {
                sockets.remove(socket);
                throw new RuntimeException(e);

            }
        }

    }

    private void closeSocket(Socket socket) {

        try {
            socket.close();
            System.out.println("Local Socket Closed  :  "+socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        ChatRoomServer chatRoomServer = new ChatRoomServer();
        chatRoomServer.startServer();

    }

}
