public class ProdutoFarmacia extends Produto {
    private boolean prescricao;
    private CategoriaFarmacia categoria;
    private String medicoPrescritor;

    /**
     * Construtor da classe ProdutoFarmacia
     */
    public ProdutoFarmacia(String codigo, String nome, String descricao, int quantidade,
                           double valorUnitario, boolean prescricao, CategoriaFarmacia categoria,
                           String medicoPrescritor) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.prescricao = prescricao;
        this.categoria = categoria;
        this.medicoPrescritor = medicoPrescritor;
    }

    // Getters
    public boolean isPrescricao() {
        return prescricao;
    }

    public CategoriaFarmacia getCategoria() {
        return categoria;
    }

    public String getMedicoPrescritor() {
        return medicoPrescritor;
    }

    // Implementação do método abstrato calcularValorComIVA
    @Override
    public double calcularValorComIVA() {
        double taxaIVA = getTaxaIVA() / 100.0;
        return calcularValorTotal() * (1 + taxaIVA);
    }

    // Implementação do método abstrato getTaxaIVA
    @Override
    public double getTaxaIVA() {
        if (prescricao) {
            return 6.0; // IVA reduzido para produtos com prescrição médica
        }
        return switch (categoria) {
            case BELEZA -> 23.0;
            case BEM_ESTAR -> 18.0;
            case BEBES -> 13.0;
            case ANIMAIS -> 22.0;
            case OUTRO -> 23.0;
        };
    }
}
