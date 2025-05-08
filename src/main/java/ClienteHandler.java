package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClienteHandler extends Thread {
  private final Socket clientSocket;
  private final List<Cidade> cidades;

  public ClienteHandler(Socket socket, List<Cidade> cidades) {
    this.clientSocket = socket;
    this.cidades = cidades;
  }

  @Override
  public void run() {
    try (
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);) {
      String linhaInput;
      while ((linhaInput = input.readLine()) != null) {
        System.out.println("Cliente \"" + clientSocket.getInetAddress() + "\": " + linhaInput);
        String[] comandoPartes = linhaInput.split(" ", 2);

        switch (comandoPartes[0]) {
          case "BUSCAR": {
            if (comandoPartes.length > 1) {
              String prefixo = comandoPartes[1];
              handleBusca(prefixo, output);
            } else {
              output.println("ERRO - Formato inválido. Uso: BUSCAR <prefixo>\n");
            }
            break;
          }

          case "ENCERRAR": {
            output.println("Conexão encerrada\n");
            System.out.println("Cliente \"" + clientSocket.getInetAddress() + "\" solicitou encerramento");
            return;
          }

          default: {
            output.println("ERRO - Comando desconhecido \"" + comandoPartes[0] + "\"");
            output.println("Utilize \"BUSCAR <prefixo>\" ou \"ENCERRAR\"\n");
          }
        }
      }

    } catch (IOException e) {
      System.err
          .println("Erro ao comunicar com o cliente \"" + clientSocket.getInetAddress() + "\":" + e.getMessage());
    } finally {
      try {
        clientSocket.close();
        System.out.println("Conexão com o cliente \"" + clientSocket.getInetAddress() + "\" fechada.");
      } catch (IOException e) {
        System.err.println("Erro ao fechar socket do cliente: " + e.getMessage());
      }
    }
  }

  private void handleBusca(String prefixo, PrintWriter output) {
    List<Cidade> resultados = cidades.stream()
        .filter(c -> c.getNome()
            .toLowerCase()
            .startsWith(prefixo.toLowerCase()))
        .toList();

    if (resultados.isEmpty()) {
      output.println("Nenhum resultado");
    } else {
      output.println("Resultados totais obtidos - " + resultados.size());
      output.println("Imprimindo " + (resultados.size() < 10 ? resultados.size() : 10) + " resultados");
      resultados.stream().limit(10).forEach(cidade -> output.println(cidade.getNome()));
    }
    output.println();
  }
}