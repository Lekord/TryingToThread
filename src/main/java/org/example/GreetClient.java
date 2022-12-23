package org.example;

import javax.print.SimpleDoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private PrintWriter outCurrency1;
    private PrintWriter outCurrency2;
    private PrintWriter outAmountOfMoney;
    private BufferedReader inpExchangedCurrency;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outCurrency1 = new PrintWriter(clientSocket.getOutputStream());
        outCurrency2 = new PrintWriter(clientSocket.getOutputStream());
        inpExchangedCurrency = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outAmountOfMoney = new PrintWriter(clientSocket.getOutputStream());
    }

    public void sendCurrency1(String currency1){
        outCurrency1.println(currency1);
    }
    public void sendCurrency2(String currency2){
        outCurrency2.println(currency2);
    }

    public void getExchangedCurrency() throws IOException{
        inpExchangedCurrency.readLine();
    }

    public void sendAmountOfMoney(String amountOfMoney){
        outAmountOfMoney.println(amountOfMoney);
    }

    public void sendMessage(String msg) throws IOException{
        out.println(msg);
        String resp = in.readLine();
        System.out.printf("Sent message %s", resp);
    }

    public void getMessage() throws IOException{
        String response = in.readLine();
        System.out.println(response);
    }
    public void stopConnection() throws IOException{
        in.close();
        out.close();
        clientSocket.close();
    }



    public static void main(String[] args){
        try {
            GreetClient client = new GreetClient();
            client.startConnection("127.0.0.1", 55552);
            Scanner value1 = new Scanner(System.in);
            String currency1;
            Scanner value2 = new Scanner(System.in);
            String currency2;
            Scanner value3 = new Scanner(System.in);
            String amountOfMoney;

            while (true) {
                System.out.println("Последнее обновление курсов производилось 22.12.2022 в 5:07.");
                System.out.print("Введите курс валюты, какую хотите перевести (USD, RUB, YEN, GBP): ");
                currency1 = value1.nextLine();
                if (Objects.equals(currency1, "USD") ||
                        Objects.equals(currency1, "RUB") ||
                        Objects.equals(currency1, "YEN") ||
                        Objects.equals(currency1, "GBP")) {
                    client.sendCurrency1(currency1);
                } else {
                    System.out.println("Введено некорректное значение, программа будет перезапущена...");
                    client.stopConnection();
                    break;
                }

                System.out.print("Введите курс валюты, в какую хотите перевести: ");
                currency2 = value2.nextLine();
                if (Objects.equals(currency2, "USD") ||
                        Objects.equals(currency2, "RUB") ||
                        Objects.equals(currency2, "YEN") ||
                        Objects.equals(currency2, "GBP")) {
                    client.sendCurrency2(currency2);
                } else {
                    System.out.println("Введено некорректное значение, программа будет перезапущена...");
                    client.stopConnection();
                    break;
                }

                System.out.printf("Введите сумму в %s, которую хотите перевести в %s: ", currency1, currency2);
                amountOfMoney = value3.nextLine();
                client.sendAmountOfMoney(amountOfMoney);
                System.out.println("> Message sent.");
                break;
            }
            client.getExchangedCurrency();
        } catch(Exception exception) {
            System.out.println("Сервер перезапускается или выключен, попробуйте перезапустить программу.");
        }
    }
}