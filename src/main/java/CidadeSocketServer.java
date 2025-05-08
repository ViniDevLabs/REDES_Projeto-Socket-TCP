package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CidadeSocketServer {
  private static final int PORT = 2222;
  private static final List<Cidade> cidades = new ArrayList<>();

  private static void loadCidades() {
    URL resourceUrl = CidadeSocketServer.class.getClassLoader().getResource("cities.txt");

    try {
      File cidadesFile = Paths.get(resourceUrl.toURI()).toFile();
      loadCidadesFromFile(cidadesFile);
    } catch (Exception e) {
      System.err.println("Erro ao acessar o arquivo de cidades via URL: " + e.getMessage());
    }
  }

  private static void loadCidadesFromFile(File cidadesFile) {
    try (
        FileReader fileReader = new FileReader(cidadesFile);
        BufferedReader reader = new BufferedReader(fileReader)) {
      String linha;
      while ((linha = reader.readLine()) != null) {
        if (linha.trim().isEmpty())
          continue;

        String[] partes = linha.split("\t");
        int peso = Integer.parseInt(partes[0].trim());
        String nome = partes[1].trim();

        cidades.add(new Cidade(nome, peso));

      }
      Collections.sort(cidades);
    } catch (IOException e) {
      System.err.println("Erro ao ler o arquivo: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    loadCidades();
    if (cidades.isEmpty()) {
      System.err.println("Nenhuma cidade carregada, verifique o arquivo cities.txt");
      return;
    }

    System.out.println("\nServidor iniciando na porta " + PORT);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (true) {
        System.out.println("Aguardando conexão de cliente");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

        new ClienteHandler(clientSocket, cidades).start();
      }
    } catch (IOException e) {
      System.err.println("Erro no servidor" + e.getMessage());
      e.printStackTrace();
    }

  }
}
