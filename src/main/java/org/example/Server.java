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
        while (true) {
            new EchoClientHandler(serverSocket.accept()).start();
            System.out.println("Server started on port: . Listening for client request.");
        }
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader inpCurrency1;
        private BufferedReader inpCurrency2;
        private BufferedReader inpAmountOfMoney;
        private PrintWriter outExchangedCurrency;
        double exchangedCurrency;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run(){
            System.out.println("Server started on port: %d. Listening for client request.");
            try {
                inpCurrency1 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                inpCurrency2 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                inpAmountOfMoney = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outExchangedCurrency = new PrintWriter(this.clientSocket.getOutputStream(), true);

                /*тут должна быть обработка*/
                String inpCurrency11 = inpCurrency1.readLine();
                System.out.println(inpCurrency11);
                String inpCurrency22 = inpCurrency2.readLine();
                System.out.println(inpCurrency22);
                double AmountOfMoney;
                while ((AmountOfMoney = Double.parseDouble(inpAmountOfMoney.readLine())) != 0) {
                    System.out.println(inpCurrency11);
                    System.out.println(inpCurrency22);
                    System.out.println(AmountOfMoney);

                    switch (inpCurrency11) {
                        case "USD":
                            switch (inpCurrency22) {
                                case "USD":
                                    System.out.println(AmountOfMoney);
                                    exchangedCurrency = AmountOfMoney;
                                    break;
                                case "RUB":
                                    System.out.println(AmountOfMoney * 71.38);
                                    exchangedCurrency = AmountOfMoney * 71.38;
                                    break;
                                case "YEN":
                                    System.out.println(AmountOfMoney * 132.27);
                                    exchangedCurrency = AmountOfMoney * 132.27;
                                    break;
                                case "GBP":
                                    System.out.println(AmountOfMoney * 0.83);
                                    exchangedCurrency = AmountOfMoney * 0.82;
                                    break;
                                default:
                                    exchangedCurrency = 0;
                            }
                            break;
                        case "RUB":
                            switch (inpCurrency22) {
                                case "USD":
                                    System.out.println(AmountOfMoney * 0.14);
                                    exchangedCurrency = AmountOfMoney * 0.014;
                                    break;
                                case "YEN":
                                    System.out.println(AmountOfMoney * 1.85);
                                    exchangedCurrency = AmountOfMoney * 1.85;
                                    break;
                                case "GBP":
                                    System.out.println(AmountOfMoney * 0.012);
                                    exchangedCurrency = AmountOfMoney * 0.012;
                                    break;
                                case "RUB":
                                    System.out.println(AmountOfMoney);
                                    exchangedCurrency = AmountOfMoney;
                                    break;
                                default:
                                    exchangedCurrency = 0;
                            }
                            break;
                        case "YEN":
                            switch (inpCurrency22) {
                                case "USD":
                                    System.out.println(AmountOfMoney * 0.0076);
                                    exchangedCurrency = AmountOfMoney * 0.0076;
                                    break;
                                case "RUB":
                                    System.out.println(AmountOfMoney * 0.54);
                                    exchangedCurrency = AmountOfMoney * 0.54;
                                    break;
                                case "YEN":
                                    System.out.println(AmountOfMoney);
                                    exchangedCurrency = AmountOfMoney;
                                    break;
                                case "GBP":
                                    System.out.println(AmountOfMoney * 0.00063);
                                    exchangedCurrency = AmountOfMoney * 0.00063;
                                    break;
                                default:
                                    exchangedCurrency = 0;
                            }
                            break;
                        case "GBP":
                            switch (inpCurrency22) {
                                case "GBP":
                                    System.out.println(AmountOfMoney);
                                    exchangedCurrency = AmountOfMoney;
                                    break;
                                case "USD":
                                    System.out.println(AmountOfMoney * 1.21);
                                    exchangedCurrency = AmountOfMoney * 1.21;
                                    break;
                                case "RUB":
                                    System.out.println(AmountOfMoney * 86.34);
                                    exchangedCurrency = AmountOfMoney * 86.34;
                                    break;
                                case "YEN":
                                    System.out.println(AmountOfMoney * 159.80);
                                    exchangedCurrency = AmountOfMoney * 159.80;
                                    break;
                            }
                            break;
                    }
                    break;
                }
                /*тут должна быть обработка*/

                if (exchangedCurrency != 0) {
                    System.out.printf("Message: %f\n", exchangedCurrency);
                    sendMessage(exchangedCurrency);
                    System.out.println("> Message sent.");
                } else {
                    sendMessage(0);
                }

                /* Закрываем входные и выходные потоки программы */
                inpCurrency1.close(); inpCurrency2.close();
                inpAmountOfMoney.close(); outExchangedCurrency.close();
                clientSocket.close();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }

        public void sendMessage(double message){
            outExchangedCurrency.println(message);
        }
        public static void main(String[] args) throws IOException{
            int port = 55552;
            System.out.printf("Server started on port: 552 %d. Listening for client request.\n", port);
            Server server = new Server();
            server.start(port);
        }
    }
}
