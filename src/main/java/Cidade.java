package main.java;

public class Cidade implements Comparable<Cidade> {
  private String nome;
  private int peso;

  public Cidade(String nome, int peso) {
    this.nome = nome;
    this.peso = peso;
  }

  public String getNome() {
    return nome;
  }

  public int getPeso() {
    return peso;
  }

  @Override
  public int compareTo(Cidade o) {
    return Integer.compare(o.getPeso(), peso);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nome == null) ? 0 : nome.hashCode());
    result = prime * result + peso;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Cidade other = (Cidade) obj;
    if (nome == null) {
      if (other.nome != null)
        return false;
    } else if (!nome.equals(other.nome))
      return false;
    return peso == other.peso;
  }

}
