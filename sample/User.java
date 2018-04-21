package sample;


import java.io.BufferedReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import static sample.usefulFunctions.*;

public class User {


    public static ArrayList<User> allUsersID;   // Vector of all users sorted by ID
    public static ArrayList<User> allUsersName;   // Vector of all users sorted by Name
    private int ID ;                               //Unique ID for each user
    public static int currentID=-1;
    public static Queue<Integer>  availableIDs;   //m3mlthash int 3shan tala3 error :D
    // ^^ wenta btinitializeha mat3melsh = new Queue<> .. e3mel = new ArrayList<>() aw new LinkedList<>()  <allam>
    private String FirstName;
    private String LastName;
    private String userName;              //Unique username for login
    private LinkedList<User> Friends;     //List of all friends of the user
    private int noFriends;

    //******************* Constructors******************//
    public User(String name,String username, LinkedList<User> friends, int noFriends) throws Exception {
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid First/Last Name");
        }
        userName=username;
        Friends = friends;
        this.noFriends = noFriends;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User(String name) throws Exception {
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid First/Last Name");
        }
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}

    }

    public User(String userName, String name) throws Exception {
        this.userName=userName;
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid First/Last Name");
        }
        noFriends=0;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User(String userName, String name, LinkedList<User> friends) throws Exception {
        this.userName=userName;
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid First/Last Name");
        }
        noFriends=friends.size();
        Friends = friends;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
    }

    public User() {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else {ID=currentID; currentID++;}
        FirstName=null;
        LastName=null;
        noFriends=0;
    }

    @Override
    public String toString(){
        StringBuilder buffer= new StringBuilder(ID + "," + userName + "," + FirstName + "," + LastName);
        for (int i = 0; i <noFriends ; i++) {
            buffer.append(',').append(Friends.get(i).ID);
        }
        return buffer.toString();
    }

    ////////////****** getters and setters*************////////////
    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }

    public String getName(){
        return FirstName + " " + LastName;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setFirstName(String name) {
        FirstName = name;
    }
    public void setLastName(String name){
        LastName = name;
    }

    public void setName(String name) throws Exception {
        StringTokenizer nameToken = new StringTokenizer(name);
        try{
            FirstName = nameToken.nextToken(" ");
            LastName = nameToken.nextToken();
        }catch(Exception ex){
            throw new Exception("Invalid Name");
        }
    }

    public LinkedList<User> getFriends() {
        return Friends;
    }

    public void setFriends(LinkedList<User> friends) {
        Friends = friends;
        noFriends=friends.size();
    }

    public int getNoFriends() {
        return noFriends;
    }


    ////////*********other functions***********//////////////
    public void addFriend(User user)
    {
        Friends.add(user);
        noFriends++;
    }
    public void deleteFriend(User user)
    {
        if (Friends.contains(user)) {
            Friends.remove(user);
            noFriends--;
        }
    }

    public boolean isFriend(User user)
    {
        return Friends.contains(user);

    }
}