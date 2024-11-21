
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

  // Arquivo declarado fora de main() para ser poder ser usado por outros métodos

  public static void main(String[] args) throws Exception {
    CrudTarefas crudTarefas = new CrudTarefas();
    CrudCategorias crudCategorias = new CrudCategorias();
    CrudRotulos crudRotulos = new CrudRotulos();
    /* Criando o Scanner e Resposta para ler a entrada do usuário */
    Scanner scanf = new Scanner(System.in);
    int resposta = 0;
    while (true) {
      try {

        /* Printando na telas as Opções */
        System.out.println("---MENU---");

        System.out.println("0) Sair......");
        System.out.println("1) Tarefas...");
        System.out.println("2) Categorias");
        System.out.println("3) Rotulos.");

        resposta = scanf.nextInt();

        /* Switch de acordo com a escolha do usuário */
        switch (resposta) {
          case 0:
            scanf.close();
            System.out.println("Saindo...");
            System.exit(0);
          case 1:
            crudTarefas.iniciarTarefas();
            break;
          case 2:
            crudCategorias.iniciarCategoria();
            break;
          case 3:
            crudRotulos.iniciarRotulo();
          default:
            System.out.println("Opção Inválida");
        }

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }

  }

  public static class CrudTarefas {
    public static ArquivoTarefas arquivoTarefas;
    public static ArquivoCategorias arquivoCategorias;
    public static ArquivoRotulo arquivoRotulo;
    Scanner scanf = new Scanner(System.in);

    // Método para iniciar as operações de tarefas
    public void iniciarTarefas() throws Exception {
      arquivoTarefas = new ArquivoTarefas();
      arquivoCategorias = new ArquivoCategorias();
      arquivoRotulo = new ArquivoRotulo();
      int resposta = 0;
      System.out.println("---TAREFAS---");

      System.out.println("1) Incluir..................");
      System.out.println("2) Buscar...................");
      System.out.println("3) Alterar..................");
      System.out.println("4) Excluir..................");
      System.out.println("5) Atualizar Rotulo.......");
      System.out.println("6) Retornar ao Menu Anterior");

      resposta = scanf.nextInt();

      switch (resposta) {
        case 1:
          criarTarefa();
          break;
        case 2:
          listarTarefas();
          break;
        case 3:
          atualizarTarefa();
          break;
        case 4:
          Deletar();
          break;
        case 5:
          atualizarRotulo();
          break;
        case 6:
          break;
        default:
          System.out.println("Opção Invalida");
      }

    }

    /* Interface Deletando Tarefa */
    public void Deletar() throws Exception {

      try {

        String termo;
        int numeroTarefa = -1;
        scanf.nextLine();
        ArrayList<Tarefa> tarefas = null;
        while (tarefas == null || tarefas.size() == 0) {
          System.out.println("Digite o termo que deseja pesquisar no banco de tarefas: ");
          termo = scanf.nextLine();
          tarefas = listarTarefas(termo);
          if (tarefas == null || tarefas.size() == 0) {
            System.out.println("Erro ao buscar tarefas, tente novamente com um termo diferente");
          }
        }
        while (numeroTarefa < 0 || numeroTarefa > tarefas.size()) {
          System.out.println(
              "Digite o número da Tarefa que deseja deletar\nObs: digite 0 para cancelar (favor ignorar a mensagem de erro)");
          numeroTarefa = scanf.nextInt();
          if (numeroTarefa < 0 || numeroTarefa > tarefas.size()) {
            System.out.println("Tarefa não encontrada, tente novamente");
          }
        }
        if (arquivoTarefas.delete(tarefas.get(numeroTarefa - 1))) {
          System.out.println("Tarefa deletada com sucesso");
        } else {
          System.out.println("Erro ao deletar a tarefa");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    /* Interface Listando Tarefa */

    public ArrayList<Tarefa> listarTarefas(String termo) throws Exception {
      ArrayList<Tarefa> tarefas = null;
      try {

        termo = termo.toLowerCase();
        tarefas = arquivoTarefas.listar(termo);
        for (int i = 0; i < tarefas.size(); i++) {
          System.out.println("Tarefa " + (i + 1) + ":\n" + "Nome da Tarefa: " + tarefas.get(i).getNome() + "\n"
              + "Data de Inicio: "
              + tarefas.get(i).getInicio() + "\n" + "Data de Fim: " + tarefas.get(i).getFim() + "\n" +
              "Status: " + tarefas.get(i).getStatus() + "\n" + "Prioridade: " + tarefas.get(i).getPrioridade() + "\n");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      return tarefas;
    }

    public void listarTarefas() throws Exception {
      try {
        scanf.nextLine();
        System.out.println("Digite o termo que deseja buscar no banco de tarefas: ");
        String titulo = scanf.nextLine();
        titulo = titulo.toLowerCase();
        ArrayList<Tarefa> tarefas = arquivoTarefas.listar(titulo);
        for (int i = 0; i < tarefas.size(); i++) {
          System.out.println("Tarefa " + (i + 1) + ":\n" + "Nome da Tarefa: " + tarefas.get(i).getNome() + "\n"
              + "Data de Inicio: "
              + tarefas.get(i).getInicio() + "\n" + "Data de Fim: " + tarefas.get(i).getFim() + "\n" +
              "Status: " + tarefas.get(i).getStatus() + "\n" + "Prioridade: " + tarefas.get(i).getPrioridade() + "\n");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    /* Interface Atualizando Tarefa */
    public void atualizarTarefa() throws Exception {
      String termo, input;
      int numeroTarefa = -1;
      Tarefa t = new Tarefa(), old;
      ArrayList<Tarefa> tarefas = null;
      try {
        // Evitando Buffer
        scanf.nextLine();
        while (tarefas == null || tarefas.size() == 0) {
          System.out.println("Digite o termo que deseja pesquisar no banco de tarefas: ");
          termo = scanf.nextLine();
          tarefas = listarTarefas(termo);
          if (tarefas == null || tarefas.size() == 0) {
            System.out.println("Erro ao buscar tarefas, tente novamente com um termo diferente");
          }
        }
        while (numeroTarefa < 0 || numeroTarefa > tarefas.size()) {
          System.out.println(
              "Digite o número da Tarefa que deseja atualizar\nObs: digite 0 para cancelar (favor ignorar a mensagem de erro)");
          numeroTarefa = scanf.nextInt();
          if (numeroTarefa < 0 || numeroTarefa > tarefas.size()) {
            System.out.println("Tarefa não encontrada, tente novamente");
          }
        }
        old = tarefas.get(numeroTarefa - 1);
        System.out.println("Tarefa Selecionada: " + old.getNome());

        // Evitando Buffer
        scanf.nextLine();
        System.out.println("Digite seu novo nome");
        t.setNome(scanf.nextLine());

        // Evitando Buffer
        scanf.nextLine();

        // Scanneando a Data de Inicio
        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (data == null) {
          System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setInicio(data);
          }
        }

        // Evitando Buffer
        scanf.nextLine();

        data = null;
        // Scanneando a Data Final
        while (data == null) {
          System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setFim(data);
          }
        }

        // Scanneando o Status da Tarefa
        System.out.println("Digite os Status da tarefa (0 para não iniciado, 1 para em andamento e 2 para finalizado)");
        t.setStatus((byte) scanf.nextInt());

        // Scanneando a Prioridade da Tarefa
        System.out.println("Digite a prioridade da nvoa Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanf.nextInt());

        if (arquivoTarefas.update(old, t)) {
          System.out.println("Tarefa atualizada com sucesso");
        } else {
          System.out.println("Erro ao atualizar a tarefa");
        }

        t = null;

      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    /* Interface de Criação da Tarefa */
    public void criarTarefa() throws Exception {

      Tarefa t = new Tarefa();
      String input;
      try {
        /* Evitando Buffer */
        scanf.nextLine();

        /* Scanneando o Nome da Tarefa */
        System.out.println("Digite o nome da tarefa");
        t.setNome(scanf.nextLine());

        System.out.println("Digite o índice da categoria que desseja adicionar esta tarefa");
        System.out.println();
        arquivoCategorias.listar();
        System.out.println();
        t.setIdCategoria(scanf.nextInt());

        // Evitando Buffer
        scanf.nextLine();

        // Definindo rotulos
        int newRotulo = 1;
        ArrayList<Rotulo> rotulos = new ArrayList<>();
        ArrayList<Integer> posRotulosLista = new ArrayList<>();
        System.out.println("Deseja adicionar alguma rotulo ? (1 para sim, 0 para não)");
        newRotulo = scanf.nextInt();
        while (newRotulo == 1) {
          System.out.println("Digite o índice da rotulo que deseja adicionar a esta tarefa");
          System.out.println();
          rotulos = arquivoRotulo.listar();
          System.out.println();
          posRotulosLista.add(scanf.nextInt() - 1);
          System.out.println("Deseja adicionar mais rotulos? (1 para sim, 0 para não)");
          newRotulo = scanf.nextInt();
        }
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 0; i < posRotulosLista.size(); i++) {
          aux.add(rotulos.get(posRotulosLista.get(i)).getId());
        }
        t.setIdRotulos(aux);

        /* Scanneando a Data de Inicio */
        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        scanf.nextLine();
        while (data == null) {
          System.out.println("Digite a data de inicio (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setInicio(data);
          }
        }

        data = null;
        /* Scanneando a Data Final */
        while (data == null) {
          System.out.println("Digite a data do Fim (No formato dd/MM/yyyy)");
          input = scanf.nextLine();
          try {
            data = LocalDate.parse(input, formatter);
          } catch (Exception e) {
            System.out.println("Data inválida, favor utilizar o formato (dd/MM/yyyy)");
            data = null;
          }
          if (data != null) {
            t.setFim(data);
          }
        }

        /* Scanneando o Status da Tarefa */
        System.out.println("Digite os Status da tarefa (0 para não iniciado, 1 para em andamento e 2 para finalizado)");
        t.setStatus((byte) scanf.nextInt());

        /* Scanneando a Prioridade da Tarefa */
        System.out.println("Digite a prioridade da Tarefa (Um numero inteiro)");
        t.setPrioridade((byte) scanf.nextInt());

        arquivoTarefas.create(t);

        t = null;

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // Interface de Adição de Rotulo
    public void atualizarRotulo() throws Exception {
      String termo;
      int numeroTarefa = -1;
      Tarefa old = new Tarefa();
      ArrayList<Tarefa> tarefas = null;
      try {
        // Evitando Buffer
        scanf.nextLine();
        while (tarefas == null || tarefas.size() == 0) {
          System.out.println("Digite o termo que deseja pesquisar no banco de tarefas: ");
          termo = scanf.nextLine();
          tarefas = listarTarefas(termo);
          // System.out.println("Tarefas: " + tarefas.size());
          if (tarefas == null || tarefas.size() == 0) {
            System.out.println("Erro ao buscar tarefas, tente novamente com um termo diferente");
          }
        }
        while (numeroTarefa < 0 || numeroTarefa > tarefas.size()) {
          System.out.println(
              "Digite o número da Tarefa que deseja atualizar\nObs: digite 0 para cancelar (favor ignorar a mensagem de erro)");
          numeroTarefa = scanf.nextInt();
          if (numeroTarefa < 0 || numeroTarefa > tarefas.size()) {
            System.out.println("Tarefa não encontrada, tente novamente");
          } else if (numeroTarefa == 0) {
            return;
          }
        }
        old = tarefas.get(numeroTarefa - 1);
        System.out.println("Tarefa Selecionada: " + old.getNome());
      } catch (Exception e) {
        e.printStackTrace();
      }

      // Definindo rotulos
      int newRotulo = 1;
      ArrayList<Rotulo> rotulos = new ArrayList<>();
      ArrayList<Integer> posRotulosLista = new ArrayList<>();
      System.out.println("Deseja remover rotulos ? (1 para sim, 0 para não)");
      newRotulo = scanf.nextInt();
      while (newRotulo == 1) {
        System.out.println("Digite o índice da rotulo que deseja remover dessa tarefa");
        System.out.println();
        rotulos = arquivoRotulo.listar();
        System.out.println();
        posRotulosLista.add(scanf.nextInt() - 1);
        System.out.println("Deseja remover mais rotulos? (1 para sim, 0 para não)");
        newRotulo = scanf.nextInt();
      }
      ArrayList<Integer> removed = new ArrayList<>();
      for (int i = 0; i < posRotulosLista.size(); i++) {
        removed.add(rotulos.get(posRotulosLista.get(i)).getId());
      }
      posRotulosLista.clear();
      System.out.println("Deseja adicionar rotulos ? (1 para sim, 0 para não)");
      newRotulo = scanf.nextInt();
      while (newRotulo == 1) {
        System.out.println("Digite o índice da rotulo que deseja adicionar dessa tarefa");
        System.out.println();
        rotulos = arquivoRotulo.listar();
        System.out.println();
        posRotulosLista.add(scanf.nextInt() - 1);
        System.out.println("Deseja adicionar mais rotulos? (1 para sim, 0 para não)");
        newRotulo = scanf.nextInt();
      }
      ArrayList<Integer> added = new ArrayList<>();
      for (int i = 0; i < posRotulosLista.size(); i++) {
        added.add(rotulos.get(posRotulosLista.get(i)).getId());
      }

      if (arquivoTarefas.updateRotulos(old, removed, added)) {
        System.out.println("Rotulos atualizadas com sucesso");
      } else {
        System.out.println("Erro ao atualizar as rotulos");
      }
    }
  }

  public static class CrudCategorias {
    public static ArquivoCategorias categoria;

    Scanner scanf = new Scanner(System.in);

    public void iniciarCategoria() throws Exception {
      categoria = new ArquivoCategorias();
      int resposta = 0;
      System.out.println("---CATEGORIAS---");

      System.out.println("1) Incluir..................");
      System.out.println("2) Buscar...................");
      System.out.println("3) Alterar..................");
      System.out.println("4) Excluir..................");
      System.out.println("5) Retornar ao Menu Anterior");

      resposta = scanf.nextInt();

      switch (resposta) {
        case 1:
          criarCategoria();
          break;
        case 2:
          listarCategoria();
          break;
        case 3:
          atualizarCategoria();
          break;
        case 4:
          deletarCategoria();
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Inválida");
          break;
      }
    }

    public void criarCategoria() throws Exception {
      try {
        /* Limpar o buffer */
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria a ser Criada");
        categoria.create(scanf.nextLine());
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      System.out.println("Criado com sucesso");
      System.out.println();
      categoria.listar();
    }

    public void listarCategoria() throws Exception {
      String nomeCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria que deseja listar as tarefas");
        System.out.println();
        categoria.listar();

        nomeCategoria = scanf.nextLine();

        ArrayList<Tarefa> t = categoria.read(nomeCategoria);

        for (int i = 0; i < t.size(); i++) {
          System.out.println("\n" + "Nome da Tarefa: " + t.get(i).getNome() + "\n" + "Data de Inicio: "
              + t.get(i).getInicio() + "\n" + "Data de Fim: " + t.get(i).getFim() + "\n" +
              "Status: " + t.get(i).getStatus() + "\n" + "Prioridade: " + t.get(i).getPrioridade() + "\n");
        }

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    public void atualizarCategoria() throws Exception {
      String nomeCategoria, novaCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da Categoria que deseja atualizar");
        System.out.println();
        categoria.listar();
        nomeCategoria = scanf.nextLine();

        System.out.println("Digite o nome da nova categoria");
        novaCategoria = scanf.nextLine();

        categoria.update(nomeCategoria, novaCategoria);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      System.out.println("Atualizado com sucesso");
    }

    public void deletarCategoria() throws Exception {
      String nomeCategoria;
      try {
        scanf.nextLine();
        System.out.println("Digite o número da Categoria que deseja deletar");
        System.out.println();
        ArrayList<Categoria> categorias = categoria.listar();
        int index = scanf.nextInt();
        while (index < 0 || index > categorias.size()) {
          System.out.println("Digite um índice válido");
          index = scanf.nextInt();
        }
        categoria.listar();

        nomeCategoria = scanf.nextLine();

        if (categoria.delete(nomeCategoria)) {
          System.out.println("Deletado com sucesso");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static class CrudRotulos {
    public static ArquivoRotulo arqRotulo;

    Scanner scanf = new Scanner(System.in);

    public void iniciarRotulo() throws Exception {
      arqRotulo = new ArquivoRotulo();
      int resposta = 0;
      System.out.println("---ETIQUETAS---");

      System.out.println("1) Incluir..................");
      System.out.println("2) Buscar...................");
      System.out.println("3) Alterar..................");
      System.out.println("4) Excluir..................");
      System.out.println("5) Retornar ao Menu Anterior");

      resposta = scanf.nextInt();

      switch (resposta) {
        case 1:
          criarRotulo();
          break;
        case 2:
          listarRotulo();
          break;
        case 3:
          atualizarRotulo();
          break;
        case 4:
          deletarRotulo();
          break;
        case 5:
          break;
        default:
          System.out.println("Opção Inválida");
          break;
      }
    }

    public void criarRotulo() throws Exception {
      try {
        /* Limpar o buffer */
        scanf.nextLine();
        System.out.println("Digite o nome da Rotulo a ser Criada");
        arqRotulo.create(scanf.nextLine());
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      System.out.println("Criado com sucesso");
      System.out.println();
      arqRotulo.listar();
    }

    public void listarRotulo() throws Exception {
      String nomeRotulo;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da rotulo que deseja listar as tarefas");
        System.out.println();
        arqRotulo.listar();

        nomeRotulo = scanf.nextLine();

        ArrayList<Tarefa> t = arqRotulo.read(nomeRotulo);

        for (int i = 0; i < t.size(); i++) {
          System.out.println("\n" + "Nome da Tarefa: " + t.get(i).getNome() + "\n" + "Data de Inicio: "
              + t.get(i).getInicio() + "\n" + "Data de Fim: " + t.get(i).getFim() + "\n" +
              "Status: " + t.get(i).getStatus() + "\n" + "Prioridade: " + t.get(i).getPrioridade() + "\n");
        }

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    public void atualizarRotulo() throws Exception {
      String nomeRotulo, novaRotulo;
      try {
        scanf.nextLine();
        System.out.println("Digite o nome da rotulo que deseja atualizar");
        System.out.println();
        arqRotulo.listar();
        nomeRotulo = scanf.nextLine();

        System.out.println("Digite o nome da nova rotulo");
        novaRotulo = scanf.nextLine();

        arqRotulo.update(nomeRotulo, novaRotulo);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      System.out.println("Atualizado com sucesso");
    }

    public void deletarRotulo() throws Exception {
      String nomeRotulo;
      try {
        scanf.nextLine();
        System.out.println(
            "Digite o índice da rotulo que deseja deletar\n Obs: digite 0 para cancelar (favor ignorar a mensagem de erro)");
        System.out.println();
        ArrayList<Rotulo> rotulos = arqRotulo.listar();
        int index = scanf.nextInt();
        while (index < 0 || index > rotulos.size()) {
          System.out.println("Digite um índice válido");
          index = scanf.nextInt();
        }
        nomeRotulo = rotulos.get(index - 1).getNome();
        if (arqRotulo.delete(nomeRotulo)) {
          System.out.println("Deletado com sucesso");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
