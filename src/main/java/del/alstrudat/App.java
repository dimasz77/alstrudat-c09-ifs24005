package del.alstrudat;

import java.util.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        // Cari angka N, lewati jika ada baris kosong di awal
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) break; 
        }
        
        if (line == null) return;

        int n = Integer.parseInt(line); // Membaca jumlah perintah
        Program program = new Program(10);

        for (int i = 0; i < n; i++) {
            String commandLine = br.readLine();
            if (commandLine == null) break;
            
            StringTokenizer st = new StringTokenizer(commandLine);
            if (!st.hasMoreTokens()) { // Jika ada baris kosong di tengah-tengah input, jangan error
                i--; // Jangan hitung loop ini
                continue;
            }
            
            String cmd = st.nextToken();

            switch (cmd) {
                case "ADD":
                    program.add(st.nextToken(), Integer.parseInt(st.nextToken()), out);
                    break;
                case "UPDATE":
                    program.update(st.nextToken(), Integer.parseInt(st.nextToken()), out);
                    break;
                case "KILL":
                    program.kill(st.nextToken(), out);
                    break;
                case "SERVE":
                    program.serve(out);
                    break;
                case "PRINT":
                    program.printHeap(out);
                    break;
            }
        }
        out.flush();
        out.close();
    }
}