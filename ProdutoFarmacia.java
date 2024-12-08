/**
 * @author teomarques
 * @version 1.0
 */
public class ProdutoFarmacia extends Produto {
    private boolean prescricao;
    private CategoriaFarmacia categoria;
    private String medicoPrescritor;

    /**
     * construtor da subclasse ProdutoFarmacia de Produto
     * @param codigo
     * @param nome
     * @param descricao
     * @param quantidade
     * @param valorUnitario
     * @param prescricao
     * @param categoria
     * @param medicoPrescritor
     */
    public ProdutoFarmacia(String codigo, String nome, String descricao, int quantidade,
                           double valorUnitario, boolean prescricao, CategoriaFarmacia categoria,
                           String medicoPrescritor) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.prescricao = prescricao;
        this.categoria = categoria;
        this.medicoPrescritor = medicoPrescritor;
    }

    /**
     * getter para prescricao
     * @return
     */
    public boolean isPrescricao() {
        return prescricao;
    }

    /**
     * getter para categoria
     * @return
     */
    public CategoriaFarmacia getCategoria() {
        return categoria;
    }

    /**
     * getter para medicoPrescritor
     * @return
     */
    public String getMedicoPrescritor() {
        return medicoPrescritor;
    }

    /**
     * Método polimórfico getTaxaIVA da classe abstrata Produto implementado na classe ProdutoFarmacia
     * Calcula tava IVA do produto
     * @param cliente
     * @return
     */
    @Override
    public double getTaxaIVA(Cliente cliente) {
        // Base na localização do cliente
        double taxaBase = switch (cliente.getLocalizacao()) {
            case CONTINENTE -> prescricao ? 6.0 : switch (categoria) {
                case BELEZA -> 23.0;
                case BEM_ESTAR -> 23.0;
                case BEBES -> 23.0;
                case ANIMAIS -> 22.0;
                case OUTRO -> 23.0;
            };
            case MADEIRA -> prescricao ? 5.0 : switch (categoria) {
                case BELEZA -> 23.0;
                case BEM_ESTAR -> 23.0;
                case BEBES -> 23.0;
                case ANIMAIS -> 22.0;
                case OUTRO -> 23.0;
            };
            case ACORES -> prescricao ? 4.0 : switch (categoria) {
                case BELEZA -> 23.0;
                case BEM_ESTAR -> 23.0;
                case BEBES -> 23.0;
                case ANIMAIS -> 22.0;
                case OUTRO -> 23.0;
            };
        };

        return taxaBase; // Retornar a taxa de IVA
    }

    /**
     * Método polimórfico calcularValorComIVA da classe abstrata Produto na subclasse ProdutoFarmacia
     * calcula valor do produto com IVA
     * @param cliente
     * @return
     */
    @Override
    public double calcularValorComIVA(Cliente cliente) {
        double taxaIVA = getTaxaIVA(cliente) / 100.0; // Obter taxa com base no cliente
        return calcularValorTotal() * (1 + taxaIVA); // Calcular valor total com IVA
    }


    /**
     * Método polimórfico da classe abstrata Produto implementado na subclasse ProdutoFarmacia
     * Exporta informações do produto como String
     * @return
     */
    @Override
    public String exportarFormato() {
        return String.format("FARMACIA,%s,%s,%s,%d,%.2f,%b,%s,%s",
                this.getCodigo(),
                this.getNome(),
                this.getDescricao(),
                this.getQuantidade(),
                this.getValorUnitario(),
                this.isPrescricao(),
                this.getCategoria(),
                this.getMedicoPrescritor() != null ? this.getMedicoPrescritor() : "N/A");
    }
}
