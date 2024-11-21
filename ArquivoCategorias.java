import java.util.ArrayList;

public class ArquivoCategorias extends Arquivo<Categoria> {
    ArvoreBMais<ParNomeId> arvoreB;

    /* Criando o Arquivo de Categorias */
    public ArquivoCategorias() throws Exception {
        super(Categoria.class.getConstructor(), "ArquivoCategoria");
        try {
            arvoreB = new ArvoreBMais<>(ParNomeId.class.getConstructor(), 5, "./dados/ArvoreCategorias");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    /* CRUD DE CATEGORIA */

    /* Método Publico para a criação de Categoria. Retorna a Categoria criada */
    public int create(String nomeCategoria) throws Exception {
        Categoria categoria = new Categoria(nomeCategoria);
        return this.create1(categoria);
    }

    /* Método Privado da criação de Categoria. Retorna o ID da Categoria */
    public int create1(Categoria categoria) throws Exception {
        int id = super.create(categoria);
        categoria.setId(id);
        try {
            ParNomeId parNomeId = new ParNomeId(categoria.getNome(), categoria.getId());
            arvoreB.create(parNomeId);
            System.out.println("Categoria inserida na árvore B+: " + categoria.getNome() + " com ID: " + id);

            // Testando a leitura após inserir
            ArrayList<ParNomeId> categoriasNaArvore = arvoreB.read(parNomeId);
            if (categoriasNaArvore.isEmpty()) {
                System.out.println("Erro: Categoria não encontrada após inserção!");
            } else {
                System.out.println("Categoria encontrada após inserção: " + categoriasNaArvore.get(0).getNome());
            }

        } catch (Exception e) {
            System.out.println("Erro na criação de uma nova categoria");
            System.out.println(e.getMessage());
        }
        return id;
    }

    /*
     * Método de leitura listando as Tarefas da Categoria passada como parametro.
     * Retorna as Tarefas
     */
    public ArrayList<Tarefa> read(String nomeCategoria) throws Exception {
        ArrayList<Tarefa> t = new ArrayList<>();
        ArquivoTarefas tarefas = new ArquivoTarefas();
        try {
            ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));

            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (categorias.isEmpty()) {
                throw new Exception("Categoria inxistente");
            }
            t = tarefas.read(categorias.get(0));
        } catch (Exception e) {
            System.out.println("Erro na leitura do Arquivo");
            System.out.println(e.getMessage());
        }
        return t;
    }

    /*
     * Método de atualização do nome de uma Categoria. Retornando se foi feito com
     * Sucesso ou Não.
     */
    public boolean update(String nomeCategoria, String novaCategoria) throws Exception {
        Categoria cat = new Categoria(novaCategoria);

        try {
            ArrayList<ParNomeId> categorias = arvoreB.read(new ParNomeId(nomeCategoria));
            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (categorias.isEmpty()) {
                throw new Exception("Categoria Inexistente");
            }
            cat.setId(categorias.get(0).getId());

            if (super.update(cat)) {
                System.out.println("Atualizo");
            }

            arvoreB.delete(categorias.get(0));
            arvoreB.create(new ParNomeId(cat.getNome(), cat.getId()));
        } catch (Exception e) {
            System.out.println("Erro no update do Arquivo");
            System.out.println(e.getMessage());
        }

        return true;
    }

    /*
     * Método de Deletar Categoria. Procura pelo nome da Categoria e a deleta.
     * Retorna booleano
     */
    public boolean delete(String nomeCategoria) throws Exception {
        try {
            // Cria o objeto ParNomeId com o nome da categoria
            ParNomeId parNomeId = new ParNomeId(nomeCategoria);
            System.out.println("Tentando deletar a categoria: " + nomeCategoria);

            // Exibe o valor sendo lido na árvore
            System.out.println("Lendo da árvore B+ com chave: " + parNomeId);

            // Tenta ler a categoria na árvore B+
            ArrayList<ParNomeId> cat = arvoreB.read(parNomeId);

            if (cat.isEmpty()) {
                System.out.println("Categoria não encontrada na árvore B+: " + nomeCategoria);
                throw new Exception("Categoria Inexistente");
            }

            // Categoria encontrada, imprime detalhes
            System.out.println(
                    "Categoria encontrada na árvore B+: " + cat.get(0).getNome() + " com ID: " + cat.get(0).getId());

            // Verifica se há tarefas associadas à categoria
            ArquivoTarefas tarefas = new ArquivoTarefas();
            ArrayList<Tarefa> t = tarefas.read(cat.get(0));

            if (!t.isEmpty()) {
                System.out.println("Não é possível excluir, pois existem tarefas associadas à categoria.");
                throw new Exception("Tarefas existentes dentro desta categoria");
            }

            // Deleta a categoria na árvore B+ e no armazenamento
            return super.delete(cat.get(0).getId()) ? arvoreB.delete(cat.get(0)) : false;
        } catch (Exception e) {
            System.out.println("Erro em deletar");
            System.out.println(e.getMessage());
        }
        return false;
    }

    /* Listando as Categorias */
    public ArrayList<Categoria> listar() throws Exception {
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            categorias = super.list();

            if (categorias.isEmpty())
                throw new Exception("Categorias ainda não foram criadas");

            for (int i = 0; i < categorias.size(); i++) {
                System.out.println("Indice " + categorias.get(i).getId() + " - Nome da Categoria: "
                        + categorias.get(i).getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return categorias;
    }
}
