/**
 * @author teomarques
 * @version 1.0
 */

import java.util.ArrayList;

/**
 * Subclasse ProdutoAlimentar de Produto
 */
public class ProdutoAlimentar extends Produto {
    private boolean isBiologico;
    private TipoTaxa tipoTaxa;
    private ArrayList<Certificacao> certificacoes;

    /**
     * Método setter ProdutoAlimentar para os atributos da classe ProdutoAlimentar
     * @param codigo
     * @param nome
     * @param descricao
     * @param quantidade
     * @param valorUnitario
     * @param isBiologico
     * @param tipoTaxa
     * @param certificacoes
     */
    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                            boolean isBiologico, TipoTaxa tipoTaxa, ArrayList<Certificacao> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.isBiologico = isBiologico;
        this.tipoTaxa = tipoTaxa;
        this.certificacoes = certificacoes;
    }

    /**
     * implementação específica do método abstrato getTaxaIVA de Taxavel para Produto
     * @return
     */
    @Override
    public double getTaxaIVA() {
        double taxaBase = switch (tipoTaxa) {
            case REDUZIDA -> 6;
            case INTERMEDIARIA -> 13;
            case NORMAL -> 23;
        };
        if (tipoTaxa == TipoTaxa.REDUZIDA && certificacoes.size() == 4) {
            taxaBase -= 1;
        } else if (tipoTaxa == TipoTaxa.INTERMEDIARIA && certificacoes.contains(Certificacao.GMP)) {
            taxaBase += 1;
        }
        return taxaBase;
    }

    /**
     * implementação específica do método abstrato calcularValorComIVA de Taxavel para Produto
     * @return
     */
    @Override
    public double calcularValorComIVA() {
        double valorComIVA = super.calcularValorComIVA();
        return isBiologico ? valorComIVA * 0.9 : valorComIVA;
    }
}
