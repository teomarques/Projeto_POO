/**
 * Subclasse de Produto
 * @author teomarques
 * @version 1.0
 */
public class ProdutoFarmacia extends Produto {
    private boolean prescricao;
    private CategoriaFarmacia categoria;
    private String medicoPrescritor;

    public ProdutoFarmacia(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                           boolean prescricao, CategoriaFarmacia categoria, String medicoPrescritor) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.prescricao = prescricao;
        this.categoria = categoria;
        this.medicoPrescritor = medicoPrescritor;
    }

    @Override
    public double getTaxaIVA() {
        if (prescricao) {
            return 6;
        }
        return categoria == CategoriaFarmacia.ANIMAIS ? 22 : 23;
    }
}