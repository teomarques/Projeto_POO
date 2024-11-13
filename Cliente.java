/**
 * Super-Classe Cliente
 * @author teomarques
 * @version 1.0
 */
public class Cliente {
    private String nome;
    private String nif;
    private String localizacao;

    public Cliente(String nome, String nif, String localizacao) {
        this.nome = nome;
        this.nif = nif;
        this.localizacao = localizacao;
    }

    public String getNome() { return nome; }
    public String getNif() { return nif; }
    public String getLocalizacao() { return localizacao; }
}