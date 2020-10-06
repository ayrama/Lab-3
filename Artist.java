import java.io.*;
/*Node class*/
public class Artist {
    public String name;
    public Artist next;
    public Artist(String name) {
        this.name = name;
    }
}
/* The List TopStreamingArtists is composed of a series of artist names
Linklist class */
class TopStreamingArtists {
    private Artist first;
    public void TopStreamingArtists(){
        first = null;
    }
    /* method that checks if linklist is empty
    return boolean true ot false */
    public boolean isEmpty(){
        return (first == null);
    }
    /* method creats new Node with given String argument.
    It adds new Nodes in ascending order.
    @param name is A String name of the Artist */
    public void insert(String name){
        //if new Node then inserted Node becomes a first one
        Artist newArtist = new Artist(name);
        if(first == null) {
            first = newArtist;
        }
        //else if new Node.name value is lower than first Node
        //then new Node is inseted in front of the first
        //and becomes a first one in the Linklist
        else if(first.name.compareToIgnoreCase(newArtist.name) > 0) {
            Artist temp = first;
            first = newArtist;
            newArtist.next = temp;
        }
        //in other cases method finds correct place in the list (ascending order)
        //for new Node
        else{
            Artist current = first;
            while(current.name != newArtist.name){
                {
                    if (current.next == null) {
                        current.next = newArtist;
                        break;
                    }
                    else if(current.next.name.equals(newArtist.name)){
                        break;
                    } else if(current.next.name.compareToIgnoreCase(newArtist.name) > 0) {
                        Artist temp = current.next;
                        current.next = newArtist;
                        newArtist.next = temp;
                        break;
                    }
                    current = current.next;
                }
            }
        }
    }
    /* this method displays all Nodes in the created linkList
    param@ pathName is the name of downloaded .csv file */
    public void displayList(String pathName) throws FileNotFoundException {
        //date is taken from the name of downloaded .csv file and inserted
        //in the name of new created .txt file
        pathName = pathName.substring(pathName.lastIndexOf('.')-11, pathName.lastIndexOf('.'));
        pathName = "ArtistsSorted-WeekOf" + pathName + ".txt";

        //PrintWriter method for creating and storing processed data in the .txt file
        PrintWriter pr = new PrintWriter(pathName);
        int number = 1;
        pr.println("Downloaded list of artists is sorted in ascending order:");
        Artist current = first;
        while(current != null) {
            //print format to organize the list of Artists
            pr.printf("%-5d %-5s\n", number++, current.name);
            current = current.next;
        }
        pr.close();
    }
}
/* class with main method */
class ArtistList{
    /* name method */
    public static void main(String[] args) {
        String path = "C:\\Users\\ayram\\Desktop\\Java.IntelliJ projects\\Lab 3\\regional-us-weekly-2020-09-25--2020-10-02.csv";
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            // first 2 lines are description before the table
            //that's why I've read them and have not stored in an array
            reader.readLine();
            reader.readLine();
            //creating of the Linklist
            TopStreamingArtists artistNames = new TopStreamingArtists();
            //instead of String.split() method I have taken a subscript
            // of the read String line between 3rd and 2nd commas from the end
            //between these commas the name of the Artist is always placed
            //in addition I removes char '"'
            while((line = reader.readLine()) != null){
                int last = line.lastIndexOf(',');
                last = line.lastIndexOf(',', last-1);
                int first = line.lastIndexOf(',',last-1);
                line = line.substring(first+1, last);
                if(line.charAt(0) == '"') {
                    line = line.substring(1, line.length()-1);
                }
                artistNames.insert(line);
            }
            //displaying created Linklist in the .txt file
            artistNames.displayList(path);
        } catch (IOException ep) {
            ep.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No such file exists.");
        }
    }
}