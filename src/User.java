import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private String ID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String username) {
        try {
            String check;
            FileReader fileReader = new FileReader("userData.csv");
            BufferedReader buffReader = new BufferedReader(fileReader);
            while ((check = buffReader.readLine()) != null) {
                String[] existingUser = check.split(" ", 3);
                if (existingUser[0].equals(username)) {
                    this.ID = existingUser[2];
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static void viewMovies(String userID){
        String movieID;
        List<String> rentedMovieID = new ArrayList<>();
        List<String> rentedMovies = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(userID+".csv");
            BufferedReader buffReader = new BufferedReader(fileReader);
            while((movieID = buffReader.readLine())!=null){
                rentedMovieID.add(movieID);
                rentedMovies.add(MovieRequest.movieIDRequest(movieID));
            }
            buffReader.close();
            fileReader.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }


        for(int i=0;i<rentedMovies.size();i++){
            System.out.println(i+1+"."+rentedMovies.get(i));
        }
        System.out.println("Which movie would you like to return? ");
        int choice = 0;
        Scanner in = new Scanner(System.in);
        try{
            choice = Integer.parseInt(in.nextLine());
        }catch(NumberFormatException e){
            System.out.println("An error occurred");
        }
        rentedMovieID.remove(choice-1);
        try {
            FileWriter writer = new FileWriter(userID+".csv", false);
            for(int i=0;i<rentedMovieID.size();i++){
                writer.write(rentedMovieID.get(i));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public static boolean verifyAccount(String username, String password){
        try {
            String check;
            FileReader fileReader = new FileReader("userData.csv");
            BufferedReader buffReader = new BufferedReader(fileReader);
            while((check = buffReader.readLine())!=null){
                //System.out.println(check);
                String[] existingUser=check.split(" ",3);
                //System.out.println(existingUser[0]);
                //System.out.println(existingUser[2]);
                if(existingUser[0].equals(username)){
                    if(existingUser[1].equals(password)){
                        System.out.println("Welcome to GreenBox "+username);
                        return true;
                    }
                    else{
                        System.out.println("Username or Password is Incorrect");
                        return false;
                    }
                }

            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Username or Password is Incorrect");
        return false;
    }



    public static boolean checkFile(String user){
        String check;
        try {
            FileReader fileReader = new FileReader("userData.csv");
            BufferedReader buffReader = new BufferedReader(fileReader);
            while((check = buffReader.readLine())!=null){
                //System.out.println(check);
                String[] existingUser=check.split(" ",3);
                //System.out.println(existingUser[0]);
                //System.out.println(existingUser[2]);
                if(existingUser[0].equals(user)){
                    System.out.println("Name is taken please try again!");
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void updateAccount(String movieID, String userID){
        try{
            File userFile = new File(userID+".csv");
            boolean exists = userFile.exists();
            if(!exists){
                userFile.createNewFile();
            }
        }catch(IOException e){
            System.out.println("An error occurred. User data file does not exist!");
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter(userID+".csv",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(movieID);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void createAccount(String username, String password){
        try{
            File userFile = new File("userData.csv");
            boolean exists = userFile.exists();
            if(!exists){
                userFile.createNewFile();
            }
        }catch(IOException e){
            System.out.println("An error occurred. User data file does not exist!");
            e.printStackTrace();
        }
        int id = (int)(Math.random()*(99999-10000+1)+10000);
        if(User.checkFile(username)){
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter("userData.csv",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(username+" "+password+" "+id);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
