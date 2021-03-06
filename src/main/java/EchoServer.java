import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static BufferedReader reader;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(4004)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = server.accept();
            System.out.println("Клиент подключился");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readUTF();
                        if (message.equalsIgnoreCase("exit")) {
                            System.out.println("Клиент завершил сеанс");
                            System.exit(0);
                        }
                        System.out.println("Клиент: " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                String message = reader.readLine();
                out.writeUTF(message);
            }

        } catch (IOException e) {
            System.err.println("Ошибка подключения");
            e.printStackTrace();
        }

    }
}
