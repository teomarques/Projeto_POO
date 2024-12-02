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
        carregarDadosTexto();
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
                        case 1:
                            criarOuEditarCliente();
                            break;
                        case 2:
                            listarClientes();
                            break;
                        case 3:
                            criarFatura();
                            break;
                        case 4:
                            listarFaturas();
                            break;
                        case 5:
                            visualizarFatura();
                            break;
                        case 6:
                            exibirEstatisticas();
                            break;
                        case 7:
                            System.out.println("Encerrando o programa.");
                            salvarDadosTexto();
                            break;
                        default:
                            System.out.println("Opção inválida! Tente novamente.");
                            entradaValida = false; // Permite repetir o menu
                    }

                } catch (Exception e) {
                    System.out.println("Erro: Por favor, insira um número inteiro válido.");
                    scanner.nextLine(); // Limpa o buffer para evitar loop infinito
                }
            }

        } while (opcao != 7);
    }

    private static void mostrarMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Criar e editar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Criar fatura");
        System.out.println("4. Listar faturas");
        System.out.println("5. Visualizar fatura");
        System.out.println("6. Estatísticas");
        System.out.println("7. Sair");
    }

    // CRIAR OU EDITAR CLIENTE -------------------------------------------------------------------------------------------

    /**
     * Método para criar ou editar cliente e atualizar arrayList clientes
     */
    private void criarOuEditarCliente() {
        boolean Valido = false;
        while(!Valido) {
            try {
                System.out.print("Digite o nome do cliente: ");
                String nome = scanner.nextLine();

                System.out.print("Digite o NIF do cliente: ");
                String nif = scanner.nextLine();
                do {
                        System.out.print("O NIF deve conter apenas números. Digite novamente: ");
                        nif = scanner.nextLine();
                } while(!nif.matches("\\d+"));

                System.out.print("Digite a localização (Continente, Madeira ou Açores): ");
                String localizacao = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                while(!localizacao.equals("CONTINENTE") && !localizacao.equals("MADEIRA") && !localizacao.equals("AÇORES")) {
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
    private void criarFatura() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados. Cadastre um cliente antes de criar uma fatura.");
            return;
        }

        // escolher cliente do arrayList
        System.out.println("Selecione o cliente para a fatura:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, clientes.get(i).getNome());
        }
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consumir a nova linha
        Cliente cliente = clientes.get(clienteIndex);

        Fatura fatura = new Fatura(faturas.size() + 1, cliente, new Date());

        String opcao;
        do {
            System.out.println("Deseja adicionar um produto alimentar ou de farmácia? (alimentar/farmacia/nao)");
            while(true) {
                opcao = scanner.nextLine().toLowerCase();
                if(opcao.equalsIgnoreCase("alimentar") || opcao.equalsIgnoreCase("farmacia") || opcao.equalsIgnoreCase("nao")) {
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
     * @return
     */
    private ProdutoFarmacia criarProdutoFarmacia() {
        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
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

        System.out.print("Nome do médico (deixe vazio se não houver prescrição): ");
        String medicoPrescritor = scanner.nextLine();

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
    private static final String FILE_PATH = "dados.txt";

    private static void salvarDadosTexto() {
        File ficheiro = new File(FILE_PATH);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheiro))) {
            // Escrever os clientes
            bw.write("CLIENTES:");
            bw.newLine();
            for (Cliente cliente : clientes) {
                bw.write(String.format("%s,%s,%s,%s",
                        cliente.getNome(), cliente.getNif(), cliente.getLocalizacao(), cliente.getTaxaPadrao()));
                bw.newLine();
            }

            // Escrever as faturas
            bw.write("FATURAS:");
            bw.newLine();
            for (Fatura fatura : faturas) {
                StringBuilder sb = new StringBuilder();
                sb.append(fatura.getNumeroFatura()).append(",")
                        .append(fatura.getCliente().getNome()).append(",")
                        .append(fatura.getCliente().getLocalizacao());

                for (Produto produto : fatura.getProdutos()) {
                    sb.append(";").append(produto.getNome()).append(",")
                            .append(produto.getQuantidade()).append(",")
                            .append(produto.getValorUnitario());
                }
                bw.write(sb.toString());
                bw.newLine();
            }

            System.out.println("Dados salvos no ficheiro de texto com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados no ficheiro de texto: " + e.getMessage());
        }
    }

    private static void carregarDadosTexto() {
        File ficheiro = new File(FILE_PATH);

        if (!ficheiro.exists()) {
            System.out.println("Ficheiro de dados não encontrado. Começando com dados vazios.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            boolean lendoClientes = false;
            boolean lendoFaturas = false;

            while ((linha = br.readLine()) != null) {
                if (linha.equals("CLIENTES:")) {
                    lendoClientes = true;
                    lendoFaturas = false;
                    continue;
                } else if (linha.equals("FATURAS:")) {
                    lendoClientes = false;
                    lendoFaturas = true;
                    continue;
                }

                if (lendoClientes) {
                    String[] partes = linha.split(",");
                    String nome = partes[0];
                    String nif = partes[1];
                    String localizacao = partes[2];
                    TipoTaxa taxa = TipoTaxa.valueOf(partes[3]);
                    clientes.add(new Cliente(nome, nif, localizacao, taxa));
                } else if (lendoFaturas) {
                    String[] partes = linha.split(";");
                    String[] cabecalho = partes[0].split(",");

                    int numeroFatura = Integer.parseInt(cabecalho[0]);
                    String nomeCliente = cabecalho[1];
                    String localizacao = cabecalho[2];

                    Cliente cliente = clientes.stream()
                            .filter(c -> c.getNome().equals(nomeCliente) && c.getLocalizacao().equals(localizacao))
                            .findFirst()
                            .orElse(null);

                    if (cliente == null) {
                        System.out.println("Cliente não encontrado ao carregar a fatura: " + nomeCliente);
                        continue;
                    }

                    Fatura fatura = new Fatura(numeroFatura, cliente, new Date()); // Data padrão
                    for (int i = 1; i < partes.length; i++) {
                        String[] produtoInfo = partes[i].split(",");
                        String nomeProduto = produtoInfo[0];
                        int quantidade = Integer.parseInt(produtoInfo[1]);
                        double valorUnitario = Double.parseDouble(produtoInfo[2]);

                        Produto produto = new ProdutoAlimentar(nomeProduto, nomeProduto, "", quantidade, valorUnitario, false, TipoTaxa.NORMAL, new ArrayList<>());
                        fatura.adicionarProduto(produto);
                    }

                    faturas.add(fatura);
                }
            }

            System.out.println("Dados carregados do ficheiro de texto com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados do ficheiro de texto: " + e.getMessage());
        }
    }
    private static void exibirEstatisticas() {
        int totalFaturas = faturas.size();
        int totalProdutos = 0;
        double totalSemIVA = 0;
        double totalComIVA = 0;

        // Iterar sobre as faturas para calcular os totais
        for (Fatura fatura : faturas) {
            totalProdutos += fatura.getProdutos().size();  // Contando os produtos
            totalSemIVA += fatura.calcularTotalSemIVA();  // Somando valores sem IVA
            totalComIVA += fatura.calcularTotalComIVA();  // Somando valores com IVA
        }

        double totalIVA = totalComIVA - totalSemIVA;

        System.out.println("\nEstatísticas:");
        System.out.printf("Número de Faturas: %d%n", totalFaturas);
        System.out.printf("Número de Produtos: %d%n", totalProdutos);
        System.out.printf("Valor Total Sem IVA: %.2f%n", totalSemIVA);
        System.out.printf("Valor Total do IVA: %.2f%n", totalIVA);
        System.out.printf("Valor Total Com IVA: %.2f%n", totalComIVA);
    }

    
}

