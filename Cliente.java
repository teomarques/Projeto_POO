import java.util.ArrayList;

/**
 * Classe Cliente
 * @author teomarques
 * @version 1.0
 */
public class Cliente {
    private String nome;
    private String nif;
    private Localizacao localizacao;
    private TipoTaxa taxaPadrao; // Conexão adicional com TipoTaxa
    private ArrayList<Certificacao> certificacoesNecessarias; // Conexão adicional com Certificacao

    /**
     * método setter Cliente para atributos da classe Cliente
     * @param nome
     * @param nif
     * @param localizacao
     * @param taxaPadrao
     */
    public Cliente(String nome, String nif, Localizacao localizacao, TipoTaxa taxaPadrao) {
        this.nome = nome;
        this.nif = nif;
        this.localizacao = localizacao;
        this.taxaPadrao = taxaPadrao;
        this.certificacoesNecessarias = new ArrayList<>();
    }

    /**
     * método getter para Nome
     * @return
     */
    public String getNome() { return nome; }
    /**
     * método getter para NIF
     * @return
     */
    public String getNif() { return nif; }
    /**
     * método getter para Localizacao
     *
     * @return
     */
    public Localizacao getLocalizacao() { return localizacao; }
    /**
     * método getter para TaxaPadrao
     * @return
     */
    public TipoTaxa getTaxaPadrao() { return taxaPadrao; }
    /**
     * método getter para CertificacoesNecessarias
     * @return
     */
    public ArrayList<Certificacao> getCertificacoesNecessarias() {
        return certificacoesNecessarias;
    }

    /**
     * Adiciona uma certificação ao cliente, caso ele ainda não a possua.
     * @param certificacao Certificação a ser adicionada.
     */
    public void adicionarCertificacao(Certificacao certificacao) {
        if (!certificacoesNecessarias.contains(certificacao)) {
            certificacoesNecessarias.add(certificacao);
            System.out.println("Certificação " + certificacao + " adicionada ao cliente " + nome);
        } else {
            System.out.println("O cliente já possui a certificação " + certificacao);
        }
    }
}
