
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.io.DataInputStream;

public class Rotulo implements Registro {

    Arquivo estiquetas;

    private int id;

    // Atributos da classe Categoria
    private String nome;

    // Métodos Set
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    // Fim Métodos Set

    // Métodos Get
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    // Fim Métodos Get

    // Método toByteArray
    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(this.id);
            dos.writeUTF(this.nome);
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Construtores

    public Rotulo(String nome) {
        this.nome = nome;
        this.id = -1;
    }

    public Rotulo() {
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
        return s;
    }

    private String getArgumentList() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("id: ").append(Integer.toString(this.id)).append(", ");
        sb.append("nome: ").append(nome).append(", ");
        return sb.toString();
    }

}
