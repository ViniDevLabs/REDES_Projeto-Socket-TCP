# DIM0438 - Redes de Computadores

## Descrição

Este projeto foi desenvolvido para a disciplina **DIM0438 - Redes de Computadores** (UFRN). Trata-se de uma aplicação de autocomplete de cidades utilizando sockets TCP. A aplicação permite que vários clientes se conectem simultaneamente a um servidor para buscar nomes de cidades com base em um prefixo fornecido.

## Funcionalidade

O servidor realiza o autocomplete de cidades a partir de um prefixo enviado pelos clientes. A comunicação entre o cliente e o servidor segue um protocolo proprietário com os seguintes comandos:

- `BUSCAR <prefixo_cidade>`: realiza a busca de cidades que começam com o prefixo especificado.
- `ENCERRAR`: encerra a conexão com o servidor.

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

- `src/`: contém os arquivos fonte do projeto, incluindo o servidor e o cliente, além do arquivo cities.txt contendo a lista de cidades.
- `out/`: contém os arquivos compilados.
- `build.sh`: script para compilar o projeto em ambiente Linux.
- `build.bat`: script para compilar o projeto em ambiente Windows.

## Compilação

### Linux

Para compilar o projeto em um ambiente Linux, execute o comando:

```bash
./build.sh
```

### Windows

Para compilar o projeto em um ambiente Windows, execute o comando:

```pwsh
./build.bat
```

## Execução

### Servidor

Para iniciar o servidor, utilize o comando:

```bash
java -cp out main.java.CidadeSocketServer
```

O servidor será iniciado na porta `2222`.

### Cliente

Para iniciar um cliente, utilize o comando:

```bash
java -cp out main.java.CidadeSocketCliente
```

É possível conectar vários clientes simultaneamente ao servidor.

## Exemplo de Uso

1. Inicie o servidor.
2. Conecte um ou mais clientes.
3. No cliente, envie o comando:

```bash
BUSCAR Sha
```

O servidor responderá com todas as cidades que começam com o prefixo "Sha".

4. Para encerrar a conexão, envie o comando:

```bash
ENCERRAR
```
