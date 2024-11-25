# TP03 - Buscas e Relacionamento N:N

## Relatório de Desenvolvimento do Trabalho Prático 3
### Ana Cristina, Felipe Vilhena, Kenia Teixeira, Lucas Gabriel

---

## Descrição de classes e métodos 

## Classe ArquivoEtiqueta

A classe ArquivoEtiqueta representa o arquivo de etiquetas e suas operações CRUD.

Atributos: 

ArvoreBMais<ParEtiquetaId> arvoreB: Árvore B+ para indexação de etiquetas por nome.

Métodos: 
create(String nomeEtiqueta): Cria uma nova etiqueta.
read(String nomeEtiqueta): Lê as tarefas associadas a uma etiqueta específica.
delete(String nomeEtiqueta): Deleta uma etiqueta pelo nome.
listar(): Lista todas as etiquetas armazenadas.

---

## Classe Arquivo Tarefas

A classe ArquivoTarefas gerencia a entrada e saída de dados para as tarefas, utilizando estruturas como Árvore B+ e lista invertida para indexação e busca eficiente.

- Atributos: 
HashExtensivel<ParIDcIDt> hashTarefas: Índice indireto de tarefas para buscas rápidas.
ArvoreBMais arvoreCategorias: Relacionamento 1 entre categorias e tarefas.
StopWords stopWords: Lista invertida para busca de tarefas por palavras-chave.

- Métodos: 

criar(Tarefa tarefa): Adiciona uma nova tarefa ao arquivo.
read(ParNomeId parNomeId): Lê tarefas associadas a um par de nome e ID de categoria.
read(ParEtiquetaId parEtiquetaId): Lê tarefas associadas a uma etiqueta específica.
update(Tarefa tarefa, Tarefa update): Atualiza uma tarefa existente.
delete(int id): Remove uma tarefa pelo ID e gerencia o índice.
updateEtiquetas(Tarefa tarefa, ArrayList<Integer> removed, ArrayList<Integer> added): Atualiza as etiquetas associadas a uma tarefa.

---

## Classe ArvoreBMais

A classe ArvoreBMais implementa a árvore B+ para organizar e indexar o relacionamento entre categorias e tarefas. 

- Atributos:
Pagina raiz: Raiz da árvore B+, que gerencia o relacionamento hierárquico entre as entidades.

- Métodos: 
inserir(int chave, int valor): Insere uma nova chave e valor na árvore B+.
remover(int chave): Remove uma chave específica da árvore B+.
buscar(int chave): Busca uma chave específica na árvore B+.

---

## Classe Categoria
A classe Categoria representa uma categoria de tarefas, permitindo a organização e o agrupamento das tarefas associadas. Atributos:

int id: ID único da categoria.
String nome: Nome da categoria.

- Métodos:

toBytes(): Serializa o objeto Categoria para um array de bytes.
fromBytes(byte[] bytes): Reconstrói o objeto Categoria a partir de um array de bytes.
exibirCategoria(): Exibe as informações da categoria.

---

## Classe ElementoLista
A classe ElementoLista representa um elemento com identificador único e uma frequência associada, permitindo comparações e clonagem

- Atributos:
int id: Identificador único
float frequencia: Valor associado à frequência do elemento

- Metodos:
clone(): Cria e retorna uma cópia do objeto atual
compareTo(ElementoLista outro): Compara o objeto atual com outro elemento com base no identificador

---

## Classe Etiqueta
A classe Etiqueta implementa a interface Registro e fornece métodos para a serialização e desserialização dos seus objetos

- Atributos: 
int id: Identificador único
String nome: Nome da etiqueta

- Metodos:
toByteArray(): Converte o objeto em um array de bytes
fromByteArray(byte[] array): Reconstrói o objeto a partir de um array de bytes

---

## Classe ListaInvertida
A classe ListaInvertida é uma estrutura para armazenar e gerenciar listas invertidas em arquivos binários, facilitando a associação entre chaves e listas de dados

- Atributos:
String nomeArquivoDicionario: Onde o dicionário de chaves é armazenado
String nomeArquivoBlocos: Onde os blocos de dados associados às chaves são armazenados
RandomAccessFile arqDicionario: Referência ao arquivo de dicionário
RandomAccessFile arqBlocos: Referência ao arquivo de blocos
int quantidadeDadosPorBloco:Número máximo de dados em cada bloco

- Metodos: 
create(String chave, ElementoLista e): Adiciona um elemento à lista associada à chave
read(String chave): Retorna os elementos associados à chave.
delete(String chave, int id): Remove um elemento de uma lista associada à chave
print(): Exibe todas as listas invertidas.

---

## Classe interna Bloco
A classe Bloco é usada para representar os blocos de dados associados às chaves

- Atributos do Bloco: 
short quantidade: quantidade de dados presentes na lista
short quantidadeMaxima: Número máximo de elementos permitidos
ElementoLista[] elementos: Lista de elementos armazenados no bloco
long proximo: Ponteiro para o próximo bloco da mesma chave
short bytesPorBloco: Tamanho fixo do cesto em bytes

- Metodos do bloco: 
create(ElementoLista e): Insere um elemento no bloco.
test(int id): Verifica se um elemento com o ID especificado está presente.
delete(int id): Remove um elemento do bloco.

---

## Classe ParEtiquetaId
A classe ParEtiquetaId representa um par de valores (Nome, Id) utilizados como um registro em uma estrutura de árvore B+ para armazenar e organizar de forma eficiente.

- Atributos: 
String nome: Armazena o nome associado ao par de dados
int id: Armazena um identificador único para o par
Short TAMANHO: Define o tamanho fixo do registro

- Métodos:
byte[] toByteArray(): Converte o objeto em um array de bytes
void fromByteArray(byte[] ba): Reconstrói um objeto ParEtiquetaId a partir de um array de bytes
transforma(String str): Remove acentos e converte o texto para minúsculas

---

## Classe ParIDEndereco

A classe ParIDEndereco implementa um registro que associa um ID a um endereço, sendo projetada para uso em sistemas de hash extensível. Seu objetivo é permitir uma organização eficiente de registros fixos, com funcionalidades de serialização e desserialização para armazenamento e recuperação de dados.

- Atributos:
Int id: Representa a chave do registro.
Long endereco: Armazena o valor associado à chave.
Short TAMANHO: Define o tamanho fixo do registro em bytes (12 bytes: 4 para o ID e 8 para o endereço).

- Métodos:
ParIDEndereco(): Construtor padrão que inicializa o ID e o endereço com valores inválidos (-1).
ParIDEndereco(id, endereco): Construtor parametrizado que permite definir o ID e o endereço durante a criação do objeto.
hashCode(): Calcula e retorna o hash do registro com base no ID.
toString(): Retorna uma representação textual do registro no formato (id;endereco).
toByteArray(): Serializa o objeto em um array de bytes para armazenamento ou transmissão.
fromByteArray(array): Desserializa um array de bytes e inicializa o objeto com os valores contidos nele.

---
## Classe ParIDEtiquetacID

A classe ParIDEtiquetacID representa uma conexão entre os IDs de categorias e IDs de tarefas. Ela é utilizada para mapear a relação entre essas duas entidades, permitindo a manipulação e armazenamento eficientes desses pares de IDs. A classe implementa métodos de comparação, clonagem, serialização e desserialização para facilitar o uso em estruturas de dados como árvores B+.

- Atributos:

Int id1: Representa o ID de uma categoria.
Int id2: Representa o ID de uma tarefa.
Short TAMANHO: Define o tamanho fixo do registro em bytes (8 bytes: 4 para cada ID).

-Métodos:
ParIDEtiquetacID(): Construtor padrão que inicializa os IDs com valores inválidos (-1).
ParIDEtiquetacID(int id1): Construtor que inicializa o ID de categoria (id1) e o ID de tarefa com valor inválido (-1).
ParIDEtiquetacID(int id1, int id2): Construtor que inicializa os dois IDs com os valores fornecidos.
toByteArray(): Serializa o objeto para um array de bytes.
fromByteArray(byte[] array): Desserializa um array de bytes e inicializa os valores do objeto com os dados contidos no array.

---

## Classe ParIDcIDt

A classe ParIDcIDt representa uma conexão entre os IDs de categorias e IDs de tarefas. Ela é usada para associar esses dois tipos de IDs e oferece funcionalidades para manipulação, comparação, serialização e desserialização de registros. A classe implementa os métodos necessários para trabalhar em estruturas de dados como árvores B+.

-Atributos:
Int id1: Representa o ID de uma categoria.
Int id2: Representa o ID de uma tarefa.
Short TAMANHO: Define o tamanho fixo do registro em bytes (8 bytes: 4 para cada ID).

-Métodos:
ParIDcIDt(): Construtor padrão que inicializa os IDs com valores inválidos (-1).
ParIDcIDt(int id1): Construtor que inicializa o ID de categoria (id1) e o ID de tarefa com valor inválido (-1).
ParIDcIDt(int id1, int id2): Construtor que inicializa os dois IDs com os valores fornecidos.
toByteArray(): Serializa o objeto para um array de bytes.
fromByteArray(byte[] array): Desserializa um array de bytes e inicializa os valores do objeto com os dados contidos no array.

---

## Classe ParNomeId

A classe ParNomeId representa uma associação entre um nome e um ID, sendo útil em sistemas que precisam de indexação ou mapeamento de entidades por nome. A classe oferece funcionalidades para manipulação de nomes e IDs, além de realizar a serialização e desserialização desses dados.

-Atributos:
String nome: Representa o nome associado ao ID.
Int id: Representa o ID do registro.
Short TAMANHO: Define o tamanho fixo do registro (30 bytes, sendo 26 bytes reservados para o nome e 4 bytes para o ID).

-Métodos:
ParNomeId(): Construtor padrão que inicializa o nome com uma string vazia e o ID com valor inválido (-1).
ParNomeId(String n): Construtor que inicializa o nome com o valor fornecido e o ID com valor inválido (-1).
ParNomeId(String n, int i): Construtor que inicializa o nome e o ID com os valores fornecidos. Lança uma exceção se o nome tiver mais de 26 caracteres.
toByteArray(): Serializa o objeto para um array de bytes. O nome é ajustado para ocupar 26 bytes, preenchendo com espaços se necessário.
fromByteArray(byte[] array): Desserializa um array de bytes e inicializa os valores do objeto com os dados contidos no array.
transforma(String str): Método auxiliar que remove acentos e converte o nome para minúsculas para normalizar as comparações.

---

## Interface Registro

A interface Registro define os métodos essenciais para qualquer classe que represente um registro de dados, especificando operações para manipulação do ID e para a serialização e desserialização dos dados do registro.

-Métodos:
toByteArray(): Serializa o objeto em um array de bytes.
fromByteArray(byte[] array): Desserializa um array de bytes e inicializa o objeto com os dados contidos no array.

--- 

## Interface RegistroArvoreBMais

A interface RegistroArvoreBMais define os métodos que devem ser implementados pelos objetos que serão armazenados na árvore B+. Esses métodos permitem a manipulação e comparação dos elementos dentro da árvore, além de permitir a clonagem e serialização dos dados.

-Métodos:
toByteArray(): Converte o objeto em um vetor de bytes para armazenamento ou transmissão.
fromByteArray(byte[] ba): Constrói o objeto a partir de um vetor de bytes.
compareTo(T obj): Compara o objeto atual com outro objeto do mesmo tipo.

---

## Interface RegistroHashExtensivel

A interface RegistroHashExtensivel define os métodos necessários para os objetos que serão inseridos em uma tabela hash extensível. Esses métodos garantem a manipulação correta e eficiente dos registros na tabela hash, além de permitir a conversão entre objetos e seus formatos binários.

-Métodos:
hashCode(): Retorna a chave numérica que será usada no diretório da tabela hash.
toByteArray(): Converte o objeto em um vetor de bytes para armazenamento ou transmissão.
fromByteArray(byte[] ba): Constrói o objeto a partir de um vetor de bytes

---

## Classe StopWords
A classe StopWords é responsável por gerenciar uma lista de palavras irrelevantes (stop words) e realizar a filtragem dessas palavras em títulos fornecidos. Ela também realiza a contagem da frequência das palavras restantes e insere os dados em uma lista invertida.

-Atributos:
ArrayList<String> stopWords: Lista que contém as stop words.
ListaInvertida lista: Lista invertida que armazena os elementos com base nas palavras filtradas.

-Métodos:
StopWords(): Construtor que inicializa a lista de stop words lendo-as de um arquivo chamado "stopWordsList.txt". Também inicializa a lista invertida a partir de arquivos de dados, criando o diretório "dados" se necessário.
stopWordsCheck(String titulo): Recebe um título como entrada, converte o título para minúsculas, divide-o em palavras e remove as palavras presentes na lista de stop words. Retorna um vetor de palavras filtradas.
wordsCounter(ArrayList<ElementoLista> elementos, String[] chaves, int idChave): Conta a frequência de cada palavra no vetor chaves, ignorando palavras repetidas e removendo as stop words. Adiciona a frequência de cada palavra à lista de elementos elementos.
inserir(String titulo, int id): Insere um título e seu respectivo ID na lista invertida. Primeiro, o título é processado para remover as stop words e as frequências das palavras restantes são calculadas. Depois, os dados são inseridos na lista invertida.
referencia(String[] args): Método principal utilizado para testar a classe. Permite ao usuário inserir títulos e associá-los a IDs, armazenando as informações na lista invertida. Também exibe um menu interativo para realizar operações como inserção, busca, exclusão, visualização e manipulação de entidades na lista invertida.

---

## Classe Tarefa
A classe Tarefa representa uma tarefa no sistema, com informações como nome, datas de início e fim, status, prioridade, além de associações a categorias e etiquetas. Ela implementa a interface Registro, oferecendo métodos para conversão de objetos em arrays de bytes e vice-versa.

-Atributos:
int id: ID único da tarefa.
String nome: Nome da tarefa.
String descricao: Descrição da tarefa.
int idCategoria: ID da categoria à qual a tarefa pertence (chave estrangeira).

-Métodos:
toBytes(): Serializa o objeto Tarefa para um array de bytes, facilitando o armazenamento em arquivo.
fromBytes(byte[] bytes): Reconstrói o objeto Tarefa a partir de um array de bytes.
exibirTarefa(): Exibe as informações da tarefa, incluindo o nome da categoria vinculada, se disponível.

---

## Resumo 

Este projeto implementa um sistema que permite buscas eficientes por tarefas e a categorização delas com rótulos, utilizando um relacionamento N:N entre tarefas e rótulos. As buscas e os relacionamentos foram otimizados com o uso de árvores B+ e listas invertidas, garantindo alta performance e integridade dos dados. 

## Descrição Geral

Este projeto desenvolve um sistema para gerenciamento de tarefas, com foco na implementação de buscas eficientes e na organização por rótulos. Foi criado um índice invertido que permite buscas por palavras utilizando a métrica TFxIDF, a qual ordena os resultados de forma relevante. Também foi implementado um relacionamento N:N entre tarefas e rótulos, além de um CRUD para rótulos, permitindo a manutenção das categorias utilizadas. Para garantir eficiência e consistência nos acessos aos dados, foram empregadas estruturas avançadas como árvores B+ e listas invertidas.

Experiência de Desenvolvimento: 
Durante o desenvolvimento deste trabalho prático, implementamos todos os requisitos especificados, utilizando estruturas de dados avançadas para assegurar a eficiência e integridade do sistema. As maiores dificuldades envolveram a implementação do índice invertido, especialmente no tratamento de stop words, no cálculo de TFxIDF e na integração com o sistema de buscas. Além disso, houve desafios ao gerenciar a adição e remoção de vínculos entre tarefas e rótulos.

## Checklist

- O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida? Sim
- O CRUD de rótulos foi implementado? Sim
- No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+? Sim
- É possível buscar tarefas por palavras usando o índice invertido? Sim
- É possível buscar tarefas por rótulos usando uma árvore B+? Sim
- O trabalho está completo? Sim
- O trabalho é original e não a cópia de um trabalho de um colega? Sim
