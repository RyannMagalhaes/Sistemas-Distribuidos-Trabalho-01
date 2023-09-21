import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 2001);
        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());

        Mensagem msg1 = new Mensagem("0200","000000010000","120925","0512","040104","401231021845","1" );
        String mensagem = msg1.buildarMensagem();
        saida.writeUTF(mensagem);
        System.out.println("Requisição Concluida: "+entrada.readUTF());
        socket.close();
    }
}
