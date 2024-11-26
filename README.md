# TP03 - Buscas e Relacionamento N:N

## Relatório de Desenvolvimento do Trabalho Prático 3
### Ana Cristina, Felipe Vilhena, Kenia Teixeira, Lucas Gabriel

---

## Resumo

Este projeto implementa um sistema avançado de gerenciamento de tarefas, focado em buscas eficientes e na categorização de tarefas por rótulos.  
Utilizamos um relacionamento **N:N** entre tarefas e rótulos, implementado com estruturas como **Árvores B+** e **listas invertidas**. O sistema também inclui um índice invertido baseado em **TFxIDF** para garantir relevância nas buscas.

---

## Descrição Geral

O sistema permite:  
- **Busca eficiente por tarefas** utilizando palavras-chave.  
- **Organização de tarefas por categorias e rótulos** (relacionamento N:N).  
- Manipulação de rótulos com operações CRUD.  

Estruturas avançadas, como **Árvores B+**, **listas invertidas** e um **hash extensível**, garantem **desempenho elevado** e **consistência dos dados**.

**Dificuldades encontradas:**  
- Implementação do índice invertido, principalmente no tratamento de **stop words** e cálculo de **TFxIDF**.  
- Gerenciamento da adição/remoção de vínculos entre tarefas e rótulos.  

---

## **Descrição das Classes e Métodos**

### **Classe ArquivoEtiqueta**

Gerencia o CRUD de rótulos, utilizando uma **Árvore B+** para indexação.  

#### Atributos:
- `ArvoreBMais<ParEtiquetaId> arvoreB`: Indexa etiquetas por nome.

#### Métodos:
- `create(String nomeEtiqueta)`: Cria uma nova etiqueta.  
- `read(String nomeEtiqueta)`: Lê as tarefas associadas a um rótulo.  
- `delete(String nomeEtiqueta)`: Remove uma etiqueta.  
- `listar()`: Lista todas as etiquetas armazenadas.  

---

### **Classe ArquivoTarefas**

Gerencia as tarefas e permite buscas eficientes.  

#### Atributos:
- `HashExtensivel<ParIDcIDt> hashTarefas`: Índice para buscas rápidas.  
- `ArvoreBMais arvoreCategorias`: Relaciona categorias e tarefas.  
- `StopWords stopWords`: Filtra palavras irrelevantes das buscas.  

#### Métodos:
- `criar(Tarefa tarefa)`: Adiciona uma nova tarefa.  
- `read(ParNomeId parNomeId)`: Busca tarefas por nome e ID da categoria.  
- `read(ParEtiquetaId parEtiquetaId)`: Busca tarefas associadas a um rótulo.  
- `update(Tarefa tarefa, Tarefa update)`: Atualiza uma tarefa.  
- `delete(int id)`: Remove uma tarefa e atualiza os índices.  
- `updateEtiquetas(Tarefa tarefa, ArrayList<Integer> removed, ArrayList<Integer> added)`: Atualiza os rótulos de uma tarefa.  

---

### **Classe ArvoreBMais**

Implementa uma **Árvore B+** para indexação.  

#### Métodos:
- `inserir(int chave, int valor)`: Insere um par chave-valor.  
- `remover(int chave)`: Remove uma chave.  
- `buscar(int chave)`: Busca uma chave.  

---

### **Classe Categoria**

Representa uma categoria de tarefas.  

#### Atributos:
- `int id`: ID da categoria.  
- `String nome`: Nome da categoria.  

#### Métodos:
- `toBytes()`: Serializa o objeto.  
- `fromBytes(byte[] bytes)`: Desserializa o objeto.  
- `exibirCategoria()`: Exibe informações da categoria.  

---

### **Classe ListaInvertida**

Gerencia a lista invertida para buscas eficientes por palavras.  

#### Atributos:
- `String nomeArquivoDicionario`: Armazena o dicionário de chaves.  
- `String nomeArquivoBlocos`: Armazena os blocos de dados.  

#### Métodos:
- `create(String chave, ElementoLista e)`: Adiciona um elemento à lista.  
- `read(String chave)`: Retorna os elementos associados a uma chave.  
- `delete(String chave, int id)`: Remove um elemento da lista.  
- `print()`: Exibe todas as listas invertidas.  

---

### **Classe StopWords**

Filtra palavras irrelevantes e gerencia a lista invertida.  

#### Métodos:
- `stopWordsCheck(String titulo)`: Remove as stop words de um título.  
- `wordsCounter(...)`: Conta a frequência de palavras relevantes.  
- `inserir(String titulo, int id)`: Insere dados no índice invertido.  

---

### **Classe Tarefa**

Representa uma tarefa no sistema.  

#### Atributos:
- `int id`: ID único da tarefa.  
- `String nome`: Nome da tarefa.  
- `String descricao`: Descrição da tarefa.  
- `int idCategoria`: ID da categoria associada.  

#### Métodos:
- `toBytes()`: Serializa o objeto.  
- `fromBytes(byte[] bytes)`: Desserializa o objeto.  
- `exibirTarefa()`: Exibe os detalhes da tarefa.  

---

## **Checklist**

- O índice invertido foi implementado com a classe `ListaInvertida`? **Sim**  
- O CRUD de rótulos foi implementado? **Sim**  
- As Árvores B+ são usadas para gerenciar rótulos? **Sim**  
- É possível buscar tarefas por palavras-chave? **Sim**  
- É possível buscar tarefas por rótulos? **Sim**  
- O trabalho está completo e original? **Sim**  
