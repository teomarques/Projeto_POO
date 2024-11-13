/**
 * Interface Taxável
 * @author teomarques
 * @version 1.0
 */
public interface Taxavel {
    double calcularValorComIVA(); // Calcula o valor total com IVA aplicado
    double getTaxaIVA();          // Obtém a taxa de IVA para o produto
}
