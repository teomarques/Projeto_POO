/**
 * Classe Abstrata Produto
 * @author teomarques
 * @version 1.0
 */
public abstract class Produto implements Taxavel {
    protected String codigo;
    protected String nome;
    protected String descricao;
    protected int quantidade;
    protected double valorUnitario;

    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public double calcularValorTotal() {
        return quantidade * valorUnitario;
    }

    @Override
    public abstract double getTaxaIVA();

    @Override
    public double calcularValorComIVA() {
        return calcularValorTotal() * (1 + getTaxaIVA() / 100);
    }

    public String getNome() { return nome; }
}
