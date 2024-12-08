import java.io.Serializable;

/**
 * Classe abstrata Produto
 */
public abstract class Produto implements Serializable {
    private String codigo;
    private String nome;
    private String descricao;
    private int quantidade;
    private double valorUnitario;

    /**
     * Construtor da classe Produto
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

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Calcula o valor total sem IVA (quantidade * valor unitário)
     */
    public double calcularValorTotal() {
        return quantidade * valorUnitario;
    }

    /**
     * Calcula o valor total com IVA.
     * Método abstrato que deve ser implementado pelas subclasses.
     */
    public abstract double calcularValorComIVA(Cliente cliente);

    /**
     * Retorna a taxa de IVA do produto.
     * Método abstrato que deve ser implementado pelas subclasses.
     */
    public abstract double getTaxaIVA(Cliente cliente);

    /**
     * Método abstrato polimórfico para exportar informações específicas de um produto
     */
    public abstract String exportarFormato();
}
