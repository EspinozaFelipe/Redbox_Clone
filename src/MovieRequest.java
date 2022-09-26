import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;



public class MovieRequest {
    public static String movieIDRequest(String ID) throws Exception {
        Movie movie;
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("http://www.omdbapi.com/?apikey=3dccca49&i="+ID+"&type=movie"))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        movie=gson.fromJson(getResponse.body(),Movie.class);
        return movie.getTitle()+"("+movie.getYear()+")";
    }
    public String movieListRequest(String movieSearch) throws Exception{
        movieSearch=movieSearch.replaceAll(" ", "_").toLowerCase();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("http://www.omdbapi.com/?apikey=3dccca49&s="+movieSearch+"&type=movie"))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        MoviesList moviesList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        moviesList=gson.fromJson(getResponse.body(),MoviesList.class);
        if(moviesList.getResponse().equals("False")){
            System.out.println("Too many results please try again.");
            return "";
        }
        Movie[] returnResults= moviesList.getSearch();
        for(int i=0;i<returnResults.length;i++){
            System.out.print(i+1+".");
            System.out.println(returnResults[i].getTitle()+" ("+returnResults[i].getYear()+")");
        }
        System.out.println("What movie would you like to rent out? (1-"+returnResults.length+")");
        int choice=0;
        String selection;
        Scanner in = new Scanner(System.in);
        try{
            choice = Integer.parseInt(in.nextLine());
        }catch(NumberFormatException e){
            System.out.println("An error occurred");
        }
        selection=returnResults[choice-1].getImdbID();
        return selection;
    }
}
