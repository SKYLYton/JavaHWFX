package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Server {
    private List<ClientHandler> clients;
    private AuthService authService;

    private int PORT = 8189;
    ServerSocket server = null;
    Socket socket = null;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");

                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = "";
        String nickReceiver = "";

        if (msg.startsWith("/w") && msg.replaceAll("[^ ]", "").length() >= 2) {
            String[] msgSplit = msg.split("\\s", 3);
            nickReceiver = msgSplit[1];
            message = String.format("%s -> %s: %s", sender.getNickname(), nickReceiver, msgSplit[2]);

            boolean isSent = false;

            for (ClientHandler c : clients) {
                if (c.getNickname().equals(nickReceiver)) {
                    c.sendMsg(message);
                    isSent = true;
                    break;
                }
            }
            sender.sendMsg(isSent ? message : String.format("Сообщение не доставлено, пользователя с ником %s не существует", nickReceiver));

        } else if (!msg.startsWith("/w")) {
            message = String.format("%s: %s", sender.getNickname(), msg);

            for (ClientHandler c : clients) {
                c.sendMsg(message);
            }
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

}
