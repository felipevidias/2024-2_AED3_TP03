
import java.time.LocalDate;
import java.util.ArrayList;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Array;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Tarefa implements Registro {
    private int id;

    // Chave Estrangeira
    private int idCategoria;
    private ArrayList<Integer> idRotulos;

    // Atributos da classe Tarefa
    private String nome;
    private LocalDate inicio;
    private LocalDate fim;
    private Byte status;
    private Byte prioridade;

    // Métodos Set's
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public void setPrioridade(Byte prioridade) {
        this.prioridade = prioridade;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setIdRotulos(ArrayList<Integer> idRotulos) {
        this.idRotulos = idRotulos;
    }
    // Fim Métodos Set's

    // Métodos Get's
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public LocalDate getInicio() {
        return this.inicio;
    }

    public LocalDate getFim() {
        return this.fim;
    }

    public Byte getStatus() {
        return this.status;
    }

    public Byte getPrioridade() {
        return this.prioridade;
    }

    public int getIDCategoria() {
        return this.idCategoria;
    }

    public ArrayList<Integer> getIDRotulos() {
        return this.idRotulos;
    }
    // Fim Métodos Get's

    // Método toByteArray
    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(this.id);
            dos.writeUTF(this.nome);
            dos.writeInt((int) this.inicio.toEpochDay());
            dos.writeInt((int) this.fim.toEpochDay());
            dos.writeByte(this.status);
            dos.writeByte(this.prioridade);
            dos.writeInt(this.idCategoria);
            dos.writeInt(this.idRotulos.size());
            for (int i = 0; i < this.idRotulos.size(); i++) {
                dos.writeInt(this.idRotulos.get(i));
            }
        } catch (Exception e) {
            System.out.println("Deu bobs ao converter Tarefa para array de byte");
            System.out.println(e.getMessage());
        }
        return baos.toByteArray();
    }

    // Método fromByteArray
    public void fromByteArray(byte[] array) {
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bais);
        try {
            this.id = dis.readInt();
            this.nome = dis.readUTF();
            this.inicio = LocalDate.ofEpochDay(dis.readInt());
            this.fim = LocalDate.ofEpochDay(dis.readInt());
            this.status = dis.readByte();
            this.prioridade = dis.readByte();
            this.idCategoria = dis.readInt();
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                this.idRotulos.add(dis.readInt());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Construtores
    public Tarefa(String nome, LocalDate inicio, LocalDate fim, byte status, byte prioridade) {
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
        this.prioridade = prioridade;
        this.idCategoria = -1;
        this.idRotulos = new ArrayList<Integer>();
    }

    public Tarefa() {
        this.id = -1;
        this.inicio = null;
        this.fim = null;
        this.status = -1;
        this.prioridade = -1;
        this.idCategoria = -1;
        this.idRotulos = new ArrayList<Integer>();
    }

    // Fim Construtores

    @Override
    public String toString() {
        return getArgumentList();
    }

    // Método toString
    private String getArgumentAsLines() {
        String s = "";
        s += Integer.toString(this.id);
        s += "\n";
        s += this.nome;
        s += "\n";
        s += this.inicio;
        s += "\n";
        s += this.fim;
        s += "\n";
        s += Byte.toString(this.status);
        s += "\n";
        s += Byte.toString(this.prioridade);
        s += "\n";
        return s;
    }

    private String getArgumentList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id:........... ").append(Integer.toString(this.id)).append("\n");
        sb.append("Nome:......... ").append(nome).append("\n");
        sb.append("Inicio:....... ").append(inicio).append("\n");
        sb.append("Fim:.......... ").append(fim).append("\n");
        sb.append("Status:....... ").append(status).append("\n");
        sb.append("Prioridade:... ").append(prioridade).append("\n");
        return sb.toString();
    }

}
