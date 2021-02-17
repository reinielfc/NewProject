package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.StringTokenizer;

import business.User;
import java.io.FileNotFoundException;

/**
 *
 * @author dev
 */
public class UserDB {

    /**
     *
     * @param user
     * @param filepath
     * @return
     * @throws IOException
     */
    public static long insert(User user, String filepath) throws IOException {
        FileWriter fw = new FileWriter(filepath, true);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            StringJoiner sj = new StringJoiner("|");
            
            sj.add(user.getUsername());
            sj.add(user.getPassword());
            sj.add(user.getEmail());
            sj.add(user.getFirstName());
            sj.add(user.getLastName());
            
            bw.write(sj.toString());
            bw.newLine();
        }

        return 0;
    }

    /**
     *
     * @param username
     * @param password
     * @param filepath
     * @return
     * @throws IOException
     */
    public static User getUser(String username, String password, String filepath) throws IOException {
        FileReader fr = new FileReader(filepath);
        try (BufferedReader br = new BufferedReader(fr)) {
            String line = br.readLine();
            
            while (line != null) {
                StringTokenizer t = new StringTokenizer(line, "|");
                
                String DBUsername = t.nextToken();
                if (DBUsername.equals(username)) {
                    String DBPassword = t.nextToken();
                    
                    if (DBPassword.equals(password)) {
                        String email = t.nextToken();
                        String firstName = t.nextToken();
                        String lastName = t.nextToken();
                        User u = new User();
                        
                        u.setUsername(username);
                        u.setPassword(password);
                        u.setEmail(email);
                        u.setFirstName(firstName);
                        u.setLastName(lastName);
                        
                        br.close();
                        return u;
                    }
                }
                line = br.readLine();
            }
        }
        return null;
    }

    /**
     *
     * @param username
     * @param filepath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean hasUser(String username, String filepath) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(filepath);
        try (BufferedReader br = new BufferedReader(fr)) {
            String line = br.readLine();
            
            while (line != null) {
                StringTokenizer t = new StringTokenizer(line, "|");
                
                String DBUsername = t.nextToken();
                if (DBUsername.equals(username)) {
                    br.close();
                    return true;
                }
                line = br.readLine();
            }
        }

        return false;
    }

}
