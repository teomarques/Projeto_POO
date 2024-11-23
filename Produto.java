/**
 * Classe Abstrata Produto implementa taxavel
 * @author teomarques
 * @version 1.0
 */
public abstract class Produto implements Taxavel {
    protected String codigo;
    protected String nome;
    protected String descricao;
    protected int quantidade;
    protected double valorUnitario;

    /**
     * setter para todos os atributos de Produto
     * @param codigo
     * @param nome
     * @param descricao
     * @param quantidade
     * @param valorUnitario
     */
    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    /**
     * método para calcular valor total da compra
     * @return
     */
    public double calcularValorTotal() {
        return quantidade * valorUnitario;
    }

    /**
     * método abstrato implementa getTaxaIVA da interface Taxavel
     * @return
     */
    @Override
    public abstract double getTaxaIVA();

    /**
     * método abstrato implementa calcularValorComIVA da interface Taxavel
     * @return
     */
    @Override
    public double calcularValorComIVA() {
        return calcularValorTotal() * (1 + getTaxaIVA() / 100);
    }

    /**
     * Método getter getNome
     * @return
     */
    public String getNome() { return nome; }
}
