package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CidadeSocketCliente {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 2222;
  
  public static void main(String[] args) {
    try (
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);) {
      System.out.println("Conectado ao servidor de cidades em " + SERVER_ADDRESS + ":" + SERVER_PORT);
      System.out.println("Comandos disponíveis:");
      System.out.println("  BUSCAR <prefixo_cidade>");
      System.out.println("  ENCERRAR");
      System.out.println("------------------------------------");

      String userInput;
      while (true) {
        System.out.print("> ");
        userInput = scanner.nextLine().trim();

        if (userInput.isEmpty()) {
          continue;
        }

        if (userInput.matches("BUSCAR \\S+") || userInput.equals("ENCERRAR")) {
          output.println(userInput);
        } else {
          System.out.println("ERRO - Comando desconhecido \"" + userInput + "\"");
          System.out.println("Utilize \"BUSCAR <prefixo>\" ou \"ENCERRAR\"");
          continue;
        }

        System.out.println("Servidor:");
        String serverResponse;
        while ((serverResponse = input.readLine()) != null) {
          if (serverResponse.isEmpty()) {
            break;
          }
          System.out.println(serverResponse);
        }
        System.out.println("------------------------------------");
        if (userInput.equals("ENCERRAR")) {
          break;
        }
      }
    } catch (IOException e) {
      System.err.println("Erro de Input/Output ao conectar/comunicar com o servidor: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Erro no programa: " + e.getMessage());
    }
    System.out.println("Cliente encerrado");
  }
}
