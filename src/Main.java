import java.util.Scanner;


public class Main {
        public static void main(String[] args) throws Exception {
            //new mainGUI();
            User user = null;
            int choice = 0;
            String username;
            String password;
            boolean check=false;
            Scanner in = new Scanner(System.in);
            System.out.println("Welcome to GreenBox. ");
            while(!check) {
                System.out.println("What would you like to do?");
                System.out.println("1.Login");
                System.out.println("2.Sign-Up");

                try{
                    choice = Integer.parseInt(in.nextLine());
                }catch(NumberFormatException e){
                    System.out.println("An error occurred");
                }

                switch (choice) {
                    case 1:
                        System.out.print("Username: ");
                        username=in.nextLine();
                        System.out.print("Password: ");
                        password=in.nextLine();
                        check= User.verifyAccount(username,password);
                        if(check){
                            user=new User(username,password);
                            user.setID(username);
                        }
                        break;
                    case 2:
                        System.out.print("Create a Username(No Spaces): ");
                        username=in.nextLine();
                        System.out.print("Create a Password(No Spaces): ");
                        password=in.nextLine();
                        User.createAccount(username,password);
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

            while(true){
                System.out.println("What would you like to do?");
                System.out.println("1. Rent a Movie.");
                System.out.println("2. Return Movie.");
                System.out.println("3. Edit Profile.");
                System.out.println("4. Exit Greenbox.");
                choice=0;
                try{
                    choice = Integer.parseInt(in.nextLine());
                }catch(NumberFormatException e){
                System.out.println("An error occurred");
                }

                switch(choice){
                    case 1:
                        System.out.println("Enter Movie Name:");
                        String movieSearch=in.nextLine();
                        MovieRequest temp=new MovieRequest();
                        movieSearch=temp.movieListRequest(movieSearch);
                        User.updateAccount(movieSearch,user.getID());
                       break;
                    case 2:
                        System.out.println("The following movies you have rented are as listed: ");
                        user.viewMovies(user.getID());

                        break;
                    case 3:
                        System.out.println("Edit here: ");
                        break;
                    case 4:
                        System.out.println("Come back soon!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        }
    }
