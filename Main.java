import java.io.*;
import java.util.*;

/**
 * Classe Main
 */
public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private final ArrayList<Fatura> faturas = new ArrayList<>();

    /**
     * Método principal chama instância da Main
     *
     * @param args
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.executar();
    }

    // MÉTODO EXECUTAR --------------------------------------------------------------------------------------------------

    /**
     * Método instancia da main Executar
     */
    public void executar() {
        int opcao = -1;
        do {
            mostrarMenu();
            boolean entradaValida = false;

            while (!entradaValida) {
                try {
                    System.out.print("Digite uma opção: ");
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha após nextInt()
                    entradaValida = true;

                    // Processar a opção selecionada
                    switch (opcao) {
                        case 1 -> criarOuEditarCliente();
                        case 2 -> listarClientes();
                        case 3 -> criarOuEditarFatura();
                        case 4 -> listarFaturas();
                        case 5 -> visualizarFatura();
                        case 6 -> importarInfos();
                        case 7 -> exportarInfos();
                        case 8 -> System.out.println("Encerrando o programa.");
                        default -> {
                            System.out.println("Opção inválida! Tente novamente.");
                            entradaValida = false; // Permite repetir o menu
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Por favor, insira um número inteiro válido.");
                    scanner.nextLine(); // Limpa o buffer
                }
            }
        } while (opcao != 8);
    }

    private void mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Criar e editar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Criar e editar faturas");
        System.out.println("4. Listar faturas");
        System.out.println("5. Visualizar fatura");
        System.out.println("6. Importar faturas");
        System.out.println("7. Exportar faturas");
        System.out.println("8. Sair");
    }

    // CRIAR OU EDITAR CLIENTE -------------------------------------------------------------------------------------------

    /**
     * Método para criar ou editar cliente e atualizar arrayList clientes
     */
    private void criarOuEditarCliente() {
        boolean Valido = false;
        while (!Valido) {
            try {
                System.out.print("Digite o nome do cliente: ");
                String nome = scanner.nextLine();

                System.out.print("Digite o NIF do cliente: ");
                String nif = scanner.nextLine();
                while (!nif.matches("\\d+") || nif.length() != 9) {
                    System.out.print("O NIF deve conter apenas 9 números. Digite novamente: ");
                    nif = scanner.nextLine();
                }

                System.out.print("Digite a localização (Continente, Madeira ou Açores): ");
                String localizacao = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                while (!localizacao.equals("CONTINENTE") && !localizacao.equals("MADEIRA") && !localizacao.equals("AÇORES")) {
                    System.out.println("Localização inválida. Digite apenas uma as seguintes: Continente, Madeira ou Açores ");
                    localizacao = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                }

                TipoTaxa taxaPadrao;
                while (true) {
                    try {
                        System.out.print("Escolha a taxa padrão do cliente (REDUZIDA, INTERMEDIARIA, NORMAL): ");
                        taxaPadrao = TipoTaxa.valueOf(scanner.nextLine().toUpperCase());
                        break; // Sai do loop se a entrada for válida
                    } catch (IllegalArgumentException e) {
                        System.out.println("Taxa Padrão inválida. Digite apenas uma das seguintes: REDUZIDA, INTERMEDIARIA ou NORMAL.");
                    }
                }


                Cliente cliente = new Cliente(nome, nif, localizacao, taxaPadrao);

                System.out.println("Deseja adicionar certificações? (s/n)");
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    for (Certificacao cert : Certificacao.values()) {
                        System.out.printf("Adicionar certificação %s? (s/n): ", cert);
                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                            cliente.adicionarCertificacao(cert);
                        }
                    }
                }

                clientes.add(cliente);
                System.out.println("Cliente adicionado com sucesso.");
                Valido = true;

                // Lógica para criar o cliente com os dados fornecidos

            } catch (IllegalArgumentException e) {
                Valido = false;
                System.out.println("Erro: Taxa padrão inválida. Escolha entre REDUZIDA, INTERMEDIARIA ou NORMAL.");
            } catch (NoSuchElementException e) {
                Valido = false;
                System.out.println("Erro: Entrada não disponível ou fluxo encerrado.");
            } catch (IllegalStateException e) {
                Valido = false;
                System.out.println("Erro: O scanner foi fechado antes da leitura.");
            } catch (NullPointerException e) {
                Valido = false;
                System.out.println("Erro interno: Scanner não inicializado.");
            }
        }
    }

    // MÉTODO LISTAR CLIENTES -------------------------------------------------------------------------------------------

    /**
     * Método para listar os clientes
     */
    private void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.printf("Nome: %s, NIF: %s, Localização: %s, Taxa Padrão: %s%n",
                        cliente.getNome(), cliente.getNif(), cliente.getLocalizacao(), cliente.getTaxaPadrao());
            }
        }
    }

    // CRIAR FATURA ----------------------------------------------------------------------------------------------------

    /**
     * Método para criar fatura
     */
    private void criarOuEditarFatura() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados. Cadastre um cliente antes de criar uma fatura.");
            return;
        }

        // escolher cliente do arrayList
        System.out.println("Selecione o cliente para a fatura:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, clientes.get(i).getNome());
        }

        Cliente cliente;
        while (true) {
            try {
                int clienteIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // Consumir a nova linha
                if (clientes.get(clienteIndex) != null) {
                    cliente = clientes.get(clienteIndex);
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.print("Opção inválida. Por favor, selecione novamente.");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e);
            }
        }

        Fatura fatura = new Fatura(faturas.size() + 1, cliente, new Date());

        String opcao;
        do {
            System.out.println("Deseja adicionar um produto alimentar ou de farmácia? (alimentar/farmacia/nao)");
            while (true) {
                opcao = scanner.nextLine().toLowerCase();
                if (opcao.equalsIgnoreCase("alimentar") || opcao.equalsIgnoreCase("farmacia") || opcao.equalsIgnoreCase("nao")) {
                    break;
                }
                System.out.println("Opção incorreta. Digite novamente: (alimentar/farmacia/nao)");
            }
            if (opcao.equals("alimentar")) {
                ProdutoAlimentar produto = criarProdutoAlimentar();
                fatura.adicionarProduto(produto);
            } else if (opcao.equals("farmacia")) {
                ProdutoFarmacia produto = criarProdutoFarmacia();
                fatura.adicionarProduto(produto);
            }
        } while (!opcao.equals("nao"));

        faturas.add(fatura);
        System.out.println("Fatura criada com sucesso.");
    }

    // CRIAR PRODUTO ALIMENTAR -----------------------------------------------------------------------------------------

    /**
     * Método para criar produto alimentar
     *
     * @return
     */
    private ProdutoAlimentar criarProdutoAlimentar() {
        String codigo;

        while (true) {
            try {
                System.out.print("Código do produto (apenas números): ");
                codigo = scanner.nextLine();
                if (!codigo.matches("\\d+")) { // Verifica se o código contém apenas números
                    throw new IllegalArgumentException("O código do produto deve conter apenas números.");
                }
                break; // Sai do loop se o código for válido
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        String nome;
        while (true) {
            try {
                System.out.print("Nome do produto: ");
                nome = scanner.nextLine();
                if (nome.trim().isEmpty()) {
                    throw new IllegalArgumentException("O nome do produto não pode estar vazio.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        System.out.print("Descrição do produto: ");
        String descricao = scanner.nextLine();

        int quantidade;
        while (true) {
            try {
                System.out.print("Quantidade: ");
                quantidade = Integer.parseInt(scanner.nextLine());
                if (quantidade <= 0) {
                    throw new IllegalArgumentException("A quantidade deve ser maior que 0.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erro: A quantidade deve ser um número inteiro válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        double valorUnitario;
        while (true) {
            try {
                System.out.print("Valor unitário: ");
                valorUnitario = Double.parseDouble(scanner.nextLine());
                if (valorUnitario <= 0) {
                    throw new IllegalArgumentException("O valor unitário deve ser maior que 0.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erro: O valor unitário deve ser um número decimal válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        System.out.print("É biológico? (s/n): ");
        boolean isBiologico = scanner.nextLine().equalsIgnoreCase("s");

        TipoTaxa tipoTaxa;
        while (true) {
            try {
                System.out.print("Tipo de taxa (REDUZIDA, INTERMEDIARIA, NORMAL): ");
                tipoTaxa = TipoTaxa.valueOf(scanner.nextLine().toUpperCase());
                break; // Sai do loop se o tipo de taxa for válido
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Tipo de taxa inválido. Escolha entre REDUZIDA, INTERMEDIARIA ou NORMAL.");
            }
        }

        ArrayList<Certificacao> certificacoes = new ArrayList<>();
        System.out.println("Deseja adicionar certificações? (s/n)");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            for (Certificacao cert : Certificacao.values()) {
                try {
                    System.out.printf("Adicionar certificação %s? (s/n): ", cert);
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        certificacoes.add(cert);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao adicionar certificação: " + cert);
                }
            }
        }

        return new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, isBiologico, tipoTaxa, certificacoes);
    }

    // MÉTODO CRIAR PRODUTO FARMACIA ----------------------------------------------------------------------------------------

    /**
     * Método para criar novo produto de Farmácia
     *
     * @return
     */
    private ProdutoFarmacia criarProdutoFarmacia() {
        String codigo;
        while (true) {
            try {
                System.out.print("Código do produto (apenas números): ");
                codigo = scanner.nextLine();
                if (!codigo.matches("\\d+")) { // Verifica se o código contém apenas números
                    throw new IllegalArgumentException("O código do produto deve conter apenas números.");
                }
                break; // Sai do loop se o código for válido
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        String nome;
        while (true) {
            try {
                System.out.print("Nome do produto: ");
                nome = scanner.nextLine();
                if (nome.trim().isEmpty()) {
                    throw new IllegalArgumentException("O nome do produto não pode estar vazio.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        System.out.print("Descrição do produto: ");
        String descricao = scanner.nextLine();

        int quantidade;
        while (true) {
            try {
                System.out.print("Quantidade: ");
                quantidade = Integer.parseInt(scanner.nextLine());
                if (quantidade < 1) {
                    System.out.println("A quantidade deve ser maior que 0. Tente novamente.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido para a quantidade.");
            }
        }

        double valorUnitario;
        while (true) {
            try {
                System.out.print("Valor unitário: ");
                valorUnitario = Double.parseDouble(scanner.nextLine());
                if (valorUnitario <= 0) {
                    System.out.println("O valor unitário deve ser maior que 0. Tente novamente.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido para o valor unitário.");
            }
        }

        System.out.print("Possui prescrição? (s/n): ");
        boolean prescricao = scanner.nextLine().equalsIgnoreCase("s");

        String medicoPrescritor = "";
        if (prescricao) {
            System.out.print("Nome do médico que forneceu a prescrição: ");
            medicoPrescritor = scanner.nextLine();
        }

        CategoriaFarmacia categoria;
        while (true) {
            try {
                System.out.print("Categoria (BELEZA, BEM_ESTAR, BEBES, ANIMAIS, OUTRO): ");
                String categoriaInput = scanner.nextLine().trim().replace(" ", "_").toUpperCase();
                categoria = CategoriaFarmacia.valueOf(categoriaInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Categoria inválida. Por favor, escolha entre BELEZA, BEM_ESTAR, BEBES, ANIMAIS ou OUTRO.");
            }
        }

        return new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoria, medicoPrescritor.isEmpty() ? null : medicoPrescritor);
    }

    // MÉTODO LISTAR FATURAS ----------------------------------------------------------------------------------------------

    /**
     * Método para listar todas as Faturas
     */

    private void listarFaturas() {
        if (faturas.isEmpty()) {
            System.out.println("Não há faturas cadastradas.");
        } else {
            System.out.println("Lista de Faturas:");
            for (Fatura fatura : faturas) {
                System.out.printf("Fatura #%d, Cliente: %s, Data: %s%n",
                        fatura.getNumeroFatura(), fatura.getCliente().getNome(), fatura.getData());
                System.out.println("Produtos na Fatura:");

                for (Produto produto : fatura.getProdutos()) {
                    exibirInformacoesProduto(produto);
                }

                System.out.printf("Total Sem IVA: %.2f | Total Com IVA: %.2f%n%n",
                        fatura.calcularTotalSemIVA(), fatura.calcularTotalComIVA());
            }
        }
    }

    // MÉTODO VISUALIZAR FATURA ---------------------------------------------------------------------------------------------

    /**
     * Método para visualizar Fatura
     */
    private void visualizarFatura() {
        System.out.print("Digite o número da fatura para visualizar: ");
        int numeroFatura = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        Fatura fatura = faturas.stream()
                .filter(f -> f.getNumeroFatura() == numeroFatura)
                .findFirst()
                .orElse(null);

        if (fatura == null) {
            System.out.println("Fatura não encontrada.");
        } else {
            fatura.listarProdutos();
        }
    }

    // MÉTODO EXIBIR INFORMAÇÕES PRODUTO ------------------------------------------------------------------------------------

    /**
     * Método para exibir as informações de um produto específico
     *
     * @param produto
     */
    private void exibirInformacoesProduto(Produto produto) {
        System.out.printf("Produto: %s | Descrição: %s | Quantidade: %d | Valor Total: %.2f | Valor com IVA: %.2f%n",
                produto.getNome(), produto.getDescricao(), produto.getQuantidade(),
                produto.calcularValorTotal(), produto.calcularValorComIVA());

        if (produto instanceof ProdutoAlimentar) {
            ProdutoAlimentar alimentar = (ProdutoAlimentar) produto;
            System.out.println("  - Tipo de Taxa: " + alimentar.getTipoTaxa());
            System.out.println("  - Biológico: " + (alimentar.isBiologico() ? "Sim" : "Não"));
        } else if (produto instanceof ProdutoFarmacia) {
            ProdutoFarmacia farmacia = (ProdutoFarmacia) produto;
            System.out.println("  - Categoria: " + farmacia.getCategoria());
            System.out.println("  - Prescrição: " + (farmacia.isPrescricao() ? "Sim" : "Não"));
        }
    }

    // MÉTODO PARA IMPORTAR INFORMAÇÕES DE CLIENTES E INFOS DE UM ARQUIVO .TXT ---------------------------------------------------------------

    /**
     * Método para importar informações de clientes e faturas de um arquivo de texto.
     */
    private void importarInfos() {
        String caminhoArquivo = "infos.txt"; // Alterar para o caminho real do arquivo

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean lendoClientes = false;
            boolean lendoFaturas = false;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.equalsIgnoreCase("CLIENTES")) {
                    lendoClientes = true;
                    lendoFaturas = false;
                    continue;
                }

                if (linha.equalsIgnoreCase("FATURAS")) {
                    lendoClientes = false;
                    lendoFaturas = true;
                    continue;
                }

                if (lendoClientes) {
                    importarCliente(linha);
                } else if (lendoFaturas) {
                    importarFatura(linha);
                }
            }

            System.out.println("Importação concluída.");

        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo não encontrado.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para importar um cliente.
     */
    private void importarCliente(String linha) {
        try {
            String[] dados = linha.split(";");
            if (dados.length < 4) {
                System.out.println("Linha inválida para cliente: " + linha);
                return;
            }

            String nif = dados[0];
            String nome = dados[1];
            String localizacao = dados[2];
            TipoTaxa taxaPadrao = TipoTaxa.valueOf(dados[3].toUpperCase());

            Cliente cliente = new Cliente(nome, nif, localizacao, taxaPadrao);
            clientes.add(cliente);

            System.out.println("Cliente " + nome + " importado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao importar cliente: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para importar uma fatura.
     */
    private void importarFatura(String linha) {
        try {
            String[] dados = linha.split(";");
            if (dados.length < 4) {
                System.out.println("Linha inválida para fatura: " + linha);
                return;
            }

            int numeroFatura = Integer.parseInt(dados[0]);
            String nifCliente = dados[1];
            Date data = new Date(Long.parseLong(dados[2]));

            // Busca o cliente pelo NIF
            Cliente cliente = clientes.stream()
                    .filter(c -> c.getNif().equals(nifCliente))
                    .findFirst()
                    .orElse(null);

            if (cliente == null) {
                System.out.println("Cliente com NIF " + nifCliente + " não encontrado. Ignorando fatura.");
                return;
            }

            // Cria a fatura
            Fatura fatura = new Fatura(numeroFatura, cliente, data);

            // Adiciona produtos à fatura
            for (int i = 3; i < dados.length; i++) {
                String[] produtoInfo = dados[i].split(",");
                if (produtoInfo.length < 5) {
                    System.out.println("Produto inválido na linha: " + linha);
                    continue;
                }

                String tipoProduto = produtoInfo[0];
                String codigo = produtoInfo[1];
                String nome = produtoInfo[2];
                String descricao = produtoInfo[3];
                int quantidade = Integer.parseInt(produtoInfo[4]);
                double valorUnitario = Double.parseDouble(produtoInfo[5]);

                if (tipoProduto.equalsIgnoreCase("ALIMENTAR")) {
                    boolean isBiologico = Boolean.parseBoolean(produtoInfo[6]);
                    TipoTaxa tipoTaxa = TipoTaxa.valueOf(produtoInfo[7].toUpperCase());
                    ProdutoAlimentar produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, isBiologico, tipoTaxa, new ArrayList<>());
                    fatura.adicionarProduto(produto);
                } else if (tipoProduto.equalsIgnoreCase("FARMACIA")) {
                    boolean prescricao = Boolean.parseBoolean(produtoInfo[6]);
                    CategoriaFarmacia categoria = CategoriaFarmacia.valueOf(produtoInfo[7].toUpperCase());
                    ProdutoFarmacia produto = new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoria, null);
                    fatura.adicionarProduto(produto);
                } else {
                    System.out.println("Tipo de produto desconhecido: " + tipoProduto);
                }
            }

            faturas.add(fatura);
            System.out.println("Fatura #" + numeroFatura + " importada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao importar fatura: " + e.getMessage());
        }
    }

    // MÉTODO PARA EXPORTAR FATURAS PARA UM ARQUIVO --------------------------------------------------------------------------------

    /**
     * Método para exportar informações de clientes e faturas para o arquivo infos.txt.
     * Apenas adiciona informações que não estão presentes no arquivo.
     */
    private void exportarInfos() {
        String caminhoArquivo = "infos.txt"; // Alterar para o caminho real se necessário
        Set<String> clientesExistentes = new HashSet<>();
        Set<String> faturasExistentes = new HashSet<>();

        // Carregar o conteúdo atual do arquivo para evitar duplicados
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean lendoClientes = false;
            boolean lendoFaturas = false;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.equalsIgnoreCase("CLIENTES")) {
                    lendoClientes = true;
                    lendoFaturas = false;
                    continue;
                }

                if (linha.equalsIgnoreCase("FATURAS")) {
                    lendoClientes = false;
                    lendoFaturas = true;
                    continue;
                }

                if (lendoClientes) {
                    clientesExistentes.add(linha);
                } else if (lendoFaturas) {
                    faturasExistentes.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Aviso: Não foi possível ler o arquivo existente. Ele será criado.");
        }

        // Escrever os dados no arquivo (preservando os existentes e adicionando os novos)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            // Exportar novos clientes
            if (!clientesExistentes.isEmpty()) {
                System.out.println("Adicionando novos clientes...");
            } else {
                bw.write("CLIENTES");
                bw.newLine();
            }

            for (Cliente cliente : clientes) {
                String linhaCliente = String.format("%s;%s;%s;%s",
                        cliente.getNif(),
                        cliente.getNome(),
                        cliente.getLocalizacao(),
                        cliente.getTaxaPadrao());
                if (!clientesExistentes.contains(linhaCliente)) {
                    bw.write(linhaCliente);
                    bw.newLine();
                    System.out.println("Cliente exportado: " + cliente.getNome());
                }
            }

            // Exportar novas faturas
            if (!faturasExistentes.isEmpty()) {
                System.out.println("Adicionando novas faturas...");
            } else {
                bw.write("FATURAS");
                bw.newLine();
            }

            for (Fatura fatura : faturas) {
                StringBuilder linhaFatura = new StringBuilder();
                linhaFatura.append(fatura.getNumeroFatura()).append(";")
                        .append(fatura.getCliente().getNif()).append(";")
                        .append(fatura.getData().getTime()); // Salva a data como timestamp

                for (Produto produto : fatura.getProdutos()) {
                    linhaFatura.append(";").append(produto.exportarFormato());
                }

                if (!faturasExistentes.contains(linhaFatura.toString())) {
                    bw.write(linhaFatura.toString());
                    bw.newLine();
                    System.out.println("Fatura exportada: #" + fatura.getNumeroFatura());
                }
            }

            System.out.println("Informações exportadas com sucesso para o arquivo: " + caminhoArquivo);

        } catch (IOException e) {
            System.out.println("Erro ao exportar informações: " + e.getMessage());
        }
    }
}
