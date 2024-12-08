import java.util.ArrayList;

public class ProdutoAlimentar extends Produto {
    private boolean isBiologico;
    private boolean isVinho;
    private TipoTaxa tipoTaxa;
    private ArrayList<Certificacao> certificacoes;

    /**
     * Construtor da classe ProdutoAlimentar
     * @param codigo
     * @param nome
     * @param descricao
     * @param quantidade
     * @param valorUnitario
     * @param isBiologico
     * @param isVinho
     * @param tipoTaxa
     * @param certificacoes
     */
    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade,
                            double valorUnitario, boolean isBiologico, boolean isVinho, TipoTaxa tipoTaxa,
                            ArrayList<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.isBiologico = isBiologico;
        this.isVinho = isVinho;
        this.tipoTaxa = tipoTaxa;
        this.certificacoes = certificacoes != null ? certificacoes : new ArrayList<>();
    }

    /**
     * Getter para isBiológico
     * @return
     */
    public boolean isBiologico() {
        return isBiologico;
    }

    /**
     * Getter para isVinho
     * @return
     */
    public boolean isVinho() {
        return isVinho;
    }

    /**
     * Getter para getTipoTaxa
     * @return
     */
    public TipoTaxa getTipoTaxa() {
        return tipoTaxa;
    }

    /**
     * Método polimórfico getTaxaIVA da classe abstrata Produto implementado na classe ProdutoAlimentar
     * Calcula tava IVA do produto
     * @param cliente
     * @return
     */
    @Override
    public double getTaxaIVA(Cliente cliente) {
        // Baseia-se na localização do cliente
        double taxaBase = switch (cliente.getLocalizacao()) {
            case CONTINENTE -> switch (tipoTaxa) {
                case REDUZIDA -> 6.0;
                case INTERMEDIARIA -> 13.0;
                case NORMAL -> 23.0;
            };
            case MADEIRA -> switch (tipoTaxa) {
                case REDUZIDA -> 5.0;
                case INTERMEDIARIA -> 12.0;
                case NORMAL -> 22.0;
            };
            case ACORES -> switch (tipoTaxa) {
                case REDUZIDA -> 4.0;
                case INTERMEDIARIA -> 9.0;
                case NORMAL -> 16.0;
            };
        };

        // Ajuste para clientes com 4 ou mais certificações
        if (tipoTaxa == TipoTaxa.REDUZIDA && cliente.getCertificacoesNecessarias().size() >= 4) {
            taxaBase -= 1.0;
        }

        // Ajuste para produtos de vinho na taxa intermediária
        if (tipoTaxa == TipoTaxa.INTERMEDIARIA && isVinho) {
            taxaBase += 1.0;
        }

        return taxaBase;
    }

    /**
     * Método polimórfico calcularValorComIVA da classe abstrata Produto na subclasse ProdutoAlimentar
     * calcula valor do produto com IVA
     * @param cliente
     * @return
     */
    @Override
    public double calcularValorComIVA(Cliente cliente) {
        double taxaIVA = getTaxaIVA(cliente) / 100.0;
        double valorComIVA = calcularValorTotal() * (1 + taxaIVA);

        // Desconto para produtos biológicos
        if (isBiologico) {
            valorComIVA *= 0.9; // Aplica 10% de desconto
        }

        return valorComIVA;
    }

    /**
     * Método polimórfico da classe abstrata Produto implementado na subclasse ProdutoAlimentar
     * Exporta informações do produto como String
     * @return
     */
    @Override
    public String exportarFormato() {
        return String.format("ALIMENTAR,%s,%s,%s,%d,%.2f,%b,%s",
                this.getCodigo(),
                this.getNome(),
                this.getDescricao(),
                this.getQuantidade(),
                this.getValorUnitario(),
                this.isBiologico(),
                this.getTipoTaxa());
    }

}
