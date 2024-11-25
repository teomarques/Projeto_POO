import java.util.ArrayList;

public class ProdutoAlimentar extends Produto {
    private boolean isBiologico;
    private TipoTaxa tipoTaxa;
    private ArrayList<Certificacao> certificacoes;

    /**
     * Construtor da classe ProdutoAlimentar
     */
    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade,
                            double valorUnitario, boolean isBiologico, TipoTaxa tipoTaxa,
                            ArrayList<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.isBiologico = isBiologico;
        this.tipoTaxa = tipoTaxa;
        this.certificacoes = certificacoes != null ? certificacoes : new ArrayList<>();
    }

    // Getters
    public boolean isBiologico() {
        return isBiologico;
    }

    public TipoTaxa getTipoTaxa() {
        return tipoTaxa;
    }

    public ArrayList<Certificacao> getCertificacoes() {
        return certificacoes;
    }

    // Implementação do método abstrato calcularValorComIVA
    @Override
    public double calcularValorComIVA() {
        double taxaIVA = getTaxaIVA() / 100.0;
        double valorComIVA = calcularValorTotal() * (1 + taxaIVA);
        // Desconto para produtos biológicos
        if (isBiologico) {
            valorComIVA *= 0.9; // 10% de desconto
        }
        return valorComIVA;
    }

    // Implementação do método abstrato getTaxaIVA
    @Override
    public double getTaxaIVA() {
        return switch (tipoTaxa) {
            case REDUZIDA -> 6.0;
            case INTERMEDIARIA -> 13.0;
            case NORMAL -> 23.0;
        };
    }
}
