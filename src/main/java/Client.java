import java.io.*;
import java.net.Socket;

public class Client {
    private static DataInputStream in;
    private static DataOutputStream out;
    private static BufferedReader reader;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4004)) {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите 'exit', если хотите отключиться!");

            new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readUTF();
                        System.out.println("Сервер: " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                String message = reader.readLine();
                out.writeUTF(message);

                if (message.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка подключения");
            e.printStackTrace();
        }
    }
}

