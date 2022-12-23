package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new EchoClientHandler(serverSocket.accept()).start();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private BufferedReader inpCurrency1;
        private BufferedReader inpCurrency2;
        private BufferedReader inpAmountOfMoney;
        private PrintWriter outputExchangedCurrency;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                inpCurrency1 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                inpCurrency2 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                inpAmountOfMoney = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outputExchangedCurrency = new PrintWriter(clientSocket.getOutputStream());

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                {
                    if (".".equals(inputLine)) {
                        out.println("good bye");
                        break;
                    }
                    out.println(inputLine);
                    System.out.println(inputLine);
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }

        public static void main(String[] args) {
            EchoClientHandler server = new EchoClientHandler();
            server.start();
        }
    }
}
