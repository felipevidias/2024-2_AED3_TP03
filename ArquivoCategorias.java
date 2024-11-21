import java.io.File;
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
    private int create1(Categoria categoria) throws Exception {
        int id = super.create(categoria);
        categoria.setId(id);
        try {
            arvoreB.create(new ParNomeId(categoria.getNome(), categoria.getId()));
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
                System.out.println("Atualizado");
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
            ArrayList<ParNomeId> cat = arvoreB.read(new ParNomeId(nomeCategoria));

            /* Se a Categoria estiver vazia, incapaz de fazer o método */
            if (cat.isEmpty()) {
                throw new Exception("Categoria Inesistente");
            }

            ArquivoTarefas tarefas = new ArquivoTarefas();
            ArrayList<Tarefa> t = tarefas.read(cat.get(0));

            if (!t.isEmpty())
                throw new Exception("Tarefas existentes dentro desta categoria");

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
                System.out.println("Indice: " + categorias.get(i).getId() + " Nome da Categoria: "
                        + categorias.get(i).getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return categorias;
    }
}
