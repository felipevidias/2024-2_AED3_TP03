import java.io.File;
import java.util.ArrayList;

public class ArquivoRotulo extends Arquivo<Rotulo> {
    ArvoreBMais<ParRotuloId> arvoreB;

    /* Criando o Arquivo de Rotulo */
    public ArquivoRotulo() throws Exception {
        super(Rotulo.class.getConstructor(), "ArquivoRotulo");
        try {
            arvoreB = new ArvoreBMais<>(ParRotuloId.class.getConstructor(), 5, "./dados/ArvoresRotulos");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    /* CRUD DE ETIQUETA */

    /* Método Publico para a criação de Rotulo. Retorna a Rotulo criada */
    public int create(String nomeRotulo) throws Exception {
        Rotulo rotulo = new Rotulo(nomeRotulo);
        return this.create1(rotulo);
    }

    /* Método Privado da criação de Rotulo. Retorna o ID da Rotulo */
    private int create1(Rotulo rotulo) throws Exception {
        int id = super.create(rotulo);
        rotulo.setId(id);
        try {
            arvoreB.create(new ParRotuloId(rotulo.getNome(), rotulo.getId()));
        } catch (Exception e) {
            System.out.println("Erro na criação de uma nova rotulo");
            System.out.println(e.getMessage());
        }
        return id;
    }

    /*
     * Método de leitura listando as Tarefas da Rotulo passada como parametro.
     * Retorna as Tarefas
     */
    public ArrayList<Tarefa> read(String nomeRotulo) throws Exception {
        ArrayList<Tarefa> t = new ArrayList<>();
        ArquivoTarefas tarefas = new ArquivoTarefas();
        try {
            ArrayList<ParRotuloId> rotulo = arvoreB.read(new ParRotuloId(nomeRotulo));

            /* Se a Rotulo estiver vazia, incapaz de fazer o método */
            if (rotulo.isEmpty()) {
                throw new Exception("Rotulo inxistente");
            }
            t = tarefas.read(rotulo.get(0));
        } catch (Exception e) {
            System.out.println("Erro na leitura do Arquivo");
            System.out.println(e.getMessage());
        }
        return t;
    }

    /*
     * Método de atualização do nome de uma Rotulo. Retornando se foi feito com
     * Sucesso ou Não.
     */
    public boolean update(String nomeRotulo, String novaRotulo) throws Exception {
        Rotulo eti = new Rotulo(novaRotulo);

        try {
            ArrayList<ParRotuloId> rotulo = arvoreB.read(new ParRotuloId(nomeRotulo));
            /* Se a Rotulo estiver vazia, incapaz de fazer o método */
            if (rotulo.isEmpty()) {
                throw new Exception("Rotulo Inexistente");
            }
            eti.setId(rotulo.get(0).getId());

            if (super.update(eti)) {
                System.out.println("Atualizo");
            }

            arvoreB.delete(rotulo.get(0));
            arvoreB.create(new ParRotuloId(eti.getNome(), eti.getId()));
        } catch (Exception e) {
            System.out.println("Erro no update do Arquivo");
            System.out.println(e.getMessage());
        }

        return true;
    }

    /*
     * Método de Deletar Rotulo. Procura pelo nome da Rotulo e a deleta. Retorna
     * booleano
     */
    public boolean delete(String nomeRotulo) throws Exception {
        try {
            ArrayList<ParRotuloId> eti = arvoreB.read(new ParRotuloId(nomeRotulo));

            /* Se a Rotulo estiver vazia, incapaz de fazer o método */
            if (eti.isEmpty()) {
                throw new Exception("Rotulo Inesistente");
            }

            ArquivoTarefas tarefas = new ArquivoTarefas();
            ArrayList<Tarefa> t = tarefas.read(eti.get(0));

            if (!t.isEmpty())
                throw new Exception("Tarefas existentes dentro desta rotulo");

            return super.delete(eti.get(0).getId()) ? arvoreB.delete(eti.get(0)) : false;
        } catch (Exception e) {
            System.out.println("Erro em deletar");
            System.out.println(e.getMessage());
        }
        return false;
    }

    /* Listando as rotulo */
    public ArrayList<Rotulo> listar() throws Exception {
        ArrayList<Rotulo> rotulos = new ArrayList<>();
        try {
            rotulos = super.list();

            if (rotulos.isEmpty())
                throw new Exception("Rotulo ainda não foram criadas");

            for (int i = 0; i < rotulos.size(); i++) {
                System.out.println("Indice: " + rotulos.get(i).getId() + " Nome da Rotulo: "
                        + rotulos.get(i).getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rotulos;
    }

}
