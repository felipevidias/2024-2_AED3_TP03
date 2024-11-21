# TP02 - Índices Indiretos e Relacionamento 1:N

## Relatório de Desenvolvimento do Trabalho Prático 2
### Ana Cristina, Felipe Vilhena, Kenia Teixeira, Lucas Gabriel

---

## Resumo

Este projeto implementa um sistema CRUD com suporte para índices indiretos e relacionamento 1:N entre tarefas e categorias, garantindo integridade e eficiência no acesso aos dados. Utilizamos estruturas de dados avançadas, como a árvore B+, para realizar buscas eficientes e manter a consistência entre as entidades relacionadas, permitindo a recuperação e manipulação de dados de forma otimizada.

---

## Descrição Geral

O sistema é um CRUD aprimorado que implementa índices indiretos e um relacionamento 1:N entre as classes Tarefa e Categoria. A implementação inclui estruturas como árvore B+ e hash extensível para otimização de buscas e garantia de integridade referencial.

---

## Descrição das Classes e Métodos

## Classe Tarefa

A classe Tarefa representa uma tarefa com atributos essenciais que podem ser associados a uma Categoria através de um identificador de categoria, garantindo o relacionamento 1
.
Atributos:

    int id: ID único da tarefa.
    String nome: Nome da tarefa.
    String descricao: Descrição da tarefa.
    int idCategoria: ID da categoria à qual a tarefa pertence (chave estrangeira).

Métodos:

    toBytes(): Serializa o objeto Tarefa para um array de bytes, facilitando o armazenamento em arquivo.
    fromBytes(byte[] bytes): Reconstrói o objeto Tarefa a partir de um array de bytes.
    exibirTarefa(): Exibe as informações da tarefa, incluindo o nome da categoria vinculada, se disponível.

## Classe Categoria

A classe Categoria representa uma categoria de tarefas, permitindo a organização e o agrupamento das tarefas associadas.
Atributos:

    int id: ID único da categoria.
    String nome: Nome da categoria.

Métodos:

    toBytes(): Serializa o objeto Categoria para um array de bytes.
    fromBytes(byte[] bytes): Reconstrói o objeto Categoria a partir de um array de bytes.
    exibirCategoria(): Exibe as informações da categoria.

## Classe ArquivoCategorias

A classe ArquivoCategorias lida com as operações de entrada e saída para armazenar e recuperar categorias de um arquivo, utilizando hash extensível para organização eficiente.
Atributos:

    HashExtensivel<ParNomeId> hashCategorias: Índice indireto para acesso rápido às categorias pelo nome.

Métodos:

    criar(Categoria categoria): Cria uma nova categoria e a adiciona ao arquivo.
    ler(int id): Lê uma categoria específica pelo ID.
    atualizar(Categoria categoria): Atualiza uma categoria existente.
    remover(int id): Remove uma categoria pelo ID e atualiza o índice.

## Classe ArquivoTarefas

A classe ArquivoTarefas gerencia a entrada e saída de dados para as tarefas, implementando um índice para gerenciar a relação com categorias.
Atributos:

    HashExtensivel<ParIDcIDt> hashTarefas: Índice indireto de tarefas para buscas rápidas.
    ArvoreBMais arvoreCategorias: Relacionamento 1
    entre categorias e tarefas.

Métodos:

    criar(Tarefa tarefa): Adiciona uma nova tarefa ao arquivo.
    ler(int id): Lê uma tarefa específica pelo ID.
    atualizar(Tarefa tarefa): Atualiza uma tarefa existente.
    remover(int id): Remove uma tarefa pelo ID e gerencia o índice.

## Classe ArvoreBMais

A classe ArvoreBMais implementa a árvore B+ para organizar e indexar o relacionamento entre categorias e tarefas.
Atributos:

    Pagina raiz: Raiz da árvore B+, que gerencia o relacionamento hierárquico entre as entidades.

Métodos:

    inserir(int chave, int valor): Insere uma nova chave e valor na árvore B+.
    remover(int chave): Remove uma chave específica da árvore B+.
    buscar(int chave): Busca uma chave específica na árvore B+.

## Classe HashExtensivel

A classe HashExtensivel implementa um hash extensível para otimizar a busca de registros de forma dinâmica e eficiente.
Atributos:

    Diretorio diretorio: Diretório de buckets para armazenar os dados.
    Cesto[] cestos: Array de cestos que armazena dados em buckets.

Métodos:

    inserir(int chave, int valor): Insere um par chave-valor no hash.
    buscar(int chave): Busca um valor específico pelo hash da chave.
    remover(int chave): Remove um valor específico do hash.

## Classe Main

A classe Main serve como ponto de entrada do programa e controla a interface de usuário e as operações CRUD.
Métodos:

    main(String[] args): Inicializa o sistema, carregando as classes de arquivo e controlando as operações do usuário.

---

## Experiência de Desenvolvimento

Durante o desenvolvimento deste trabalho prático, implementamos todos os requisitos especificados e aplicamos estruturas de dados para garantir a eficiência e integridade do sistema. As principais dificuldades incluíram a implementação correta da árvore B+ e a integração da hash extensível, especialmente no tratamento de remoções e na validação do relacionamento entre tarefas e categorias. Apesar desses desafios, o sistema está completo e funcionando conforme esperado, com todos os testes de integridade e operações CRUD implementados e testados.

---

## Checklist

- O CRUD (com índice direto) de categorias foi implementado? **Sim**
- Há um índice indireto de nomes para as categorias? **Sim**
- O atributo de ID de categoria, como chave estrangeira, foi criado na classe Tarefa? **Sim**
- Há uma árvore B+ que registre o relacionamento 1:N entre tarefas e categorias? **Sim**
- É possível listar as tarefas de uma categoria? **Sim**
- A remoção de categorias checa se há alguma tarefa vinculada a ela? **Sim**
- A inclusão da categoria em uma tarefa se limita às categorias existentes? **Sim**
- O trabalho está funcionando corretamente? **Sim**
- O trabalho está completo? **Sim**
- O trabalho é original e não a cópia de um trabalho de outro grupo? **Sim**

---

