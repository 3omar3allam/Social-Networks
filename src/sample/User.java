package sample;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import static sample.usefulFunctions.*;

enum gender{male,female}

public class User {


    static ArrayList<User> allUsersID;   // Vector of all users sorted by ID
    static ArrayList<User> allUsersName;   // Vector of all users sorted by Name
    static int noUsers;

    private int ID ;                               //Unique ID for each user
    private gender Gender;
    private LocalDate birthDate;
    static int newID=0;
    static Queue<Integer>  availableIDs;  //m3mlthash int 3shan tala3 error :D
    private String FirstName;
    private String LastName;
    private String UserName;              //Unique username for login
    private ArrayList<User> Friends;     //List of all friends of the user
    private ArrayList<Integer> MutualFriendsNo; //list carries no of mutual friends with each friend, with the same index order
    private LinkedList<Post> Posts;
    private LinkedList<Group> Groups;

    //******************* Constructors******************//
    public User(String userName,String firstName,String lastName, String gender, LocalDate birthDate)throws Exception {
        if(!firstName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("first name"));
        if(!lastName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("last name"));
        FirstName = firstName;
        LastName = lastName;
        if(!userName.matches("[\\w]+")) throw new UsernameException("username should contain only word characters");
        UserName = userName;
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        this.birthDate = LocalDate.of(birthDate.getYear(),birthDate.getMonth(),birthDate.getDayOfMonth());
        if(this.getAge()<8) throw new AgeException("8 years is the minimum age allowed");
        if(gender.equals("male"))  Gender = sample.gender.male;
        else Gender = sample.gender.female;
        addToList(this); //implemented in usefulFunctions class
        Posts = new LinkedList<>();
        Friends = new ArrayList<>();
        Groups = new LinkedList<>();
        newID++;
        noUsers++;
    }

    public User(String userName, String firstName,String lastName)throws Exception{
        this.UserName=userName;
        if(!firstName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("first name"));
        if(!lastName.matches("\\w[(\\w|\\s)]*")) throw new NameException("Invalid ",new Throwable("last name"));
        FirstName = firstName;
        LastName = lastName;
        addToList(this);
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        newID++;
        noUsers++;
        Posts = new LinkedList<>();
        Friends = new ArrayList<>();
        Groups = new LinkedList<>();
    }

    //*******************Copy Constructor******************//
    /*
    public User(User copyUser) throws Exception //w da kaman by3mel eh ?? :D
    {
        if(!availableIDs.isEmpty())ID=availableIDs.remove();
        else ID=newID;
        UserName=copyUser.getUserName();
        FirstName=copyUser.getFirstName();
        LastName=copyUser.getLastName();
        noFriends=copyUser.getNoFriends();
        noPosts = copyUser.getNoPosts();
        noGroups = copyUser.getNoGroups();
        addToList(this);
        Posts = new LinkedList<>(copyUser.getPosts());
        Friends = new ArrayList<>(copyUser.getFriends());
        Groups = new LinkedList<>(copyUser.getGroups());
        newID++;
        noUsers++;
    }
*/

    ///////////////////////////////////////////////////////

    @Override
    public String toString(){
        StringBuilder buffer = new StringBuilder("\""+UserName+"\"");

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

    public void setUserName(String name) {
        UserName = name;
    }

    public String getUserName() {
        return UserName;
    }

    public ArrayList<User> getFriends() {
        return Friends;
    }

    public void setFriends(ArrayList<User> friends) {
        Friends = friends;
        ensureSize(MutualFriendsNo,friends.size());
    }

    public int getNoFriends() {
        return Friends.size();
    }
    public gender getGender(){return Gender;}
    public String getGender_string(){
        if(this.Gender == gender.male) return "Male";
        else return "Female";
    }
    public void setGender(gender g){Gender=g;}
    public LocalDate getBirthDate(){return birthDate;}
    public void setBirthDate(LocalDate d){birthDate=d;}
    public int getAge(){
        return Period.between(birthDate,LocalDate.now()).getYears();
    }
    public ArrayList<Integer> getMutualFriendsNo(){return MutualFriendsNo;}
    public void setMutualFriendsNo(ArrayList<Integer> no){MutualFriendsNo=no;}
    ////////*********other functions***********//////////////

    public void addFriend(User user) throws Exception {
        if (user != this) {
            if (!this.Friends.contains(user)) {
                int index = name_index(Friends, 0, Friends.size(), user);
                Friends.add(index, user);
            }
            if (!user.getFriends().contains(this)) {
                int index = name_index(user.Friends, 0, user.Friends.size(), user);
                user.getFriends().add(index, this);
            }
        }
    }

    public void addFriendSpecial(User user) throws Exception {
        if(!this.Friends.contains(user)&& (this!=user)) {
            int index = name_index(Friends,0,Friends.size(),user);
            Friends.add(index,user);
            index = name_index(user.Friends,0,user.Friends.size(),this);
            user.getFriends().add(index,this);
            ensureSize(MutualFriendsNo,user.getNoFriends());
            MutualFriendsNo.add(index,MutualFriendsNo(this,user));
        }
    }

    public void deleteFriend(User user)
    {
        if (Friends.contains(user)) {
            int index=Friends.indexOf(user);
            Friends.remove(index);
            MutualFriendsNo.remove(index);
            index=user.getFriends().indexOf(this);
            user.getFriends().remove(this);
        }
    }
    public Post addPost(String content){
        Post post = new Post(content);
        if(Posts == null) Posts = new LinkedList<>();
        Posts.add(post);
        post.setOwner(this);
        return post;
    }

    public void addPost(Post post){
        if(Posts == null) Posts = new LinkedList<>();
        Posts.add(post);
        post.setOwner(this);
    }


    public void deletePost(Post post){
        Posts.remove(post);
    }

    public void likePost(Post post){
        if(!post.getLikers().contains(this)) {
            post.getLikers().add(this);
        }
    }

    public void unlikePost(Post post) {
        post.getLikers().remove(this);
    }


    public int getNoGroups() {
        return Groups.size();
    }

    public int getNoPosts() {
        return Posts.size();
    }


    public LinkedList<Post> getPosts() {
        return Posts;
    }

    public LinkedList<Group> getGroups() {
        return Groups;
    }

    public void setGroups(LinkedList<Group> groups) {
        Groups = groups;
    }

    public boolean isFriend(User user)
    {
        return userNameBinarySearch(Friends,0,Friends.size(),user.getUserName()) !=-1;
    }

    public Post getMostLiked(){
        try{
            int no_posts = Posts.size();
            if(no_posts == 0) return null;
            Post max_post = new Post(" ");
            for(Post post : Posts){
                if(post.getLikes() >= max_post.getLikes()) max_post = post;
            }
            return max_post;
        }catch(Exception ex){
            return null;
        }
    }

    public void delete()
    {
        int id = ID;
        allUsersID.remove(id);
        allUsersName.remove(this);
        availableIDs.add(id);
        FirstName=null;
        LastName=null;
        UserName=null;
        Gender=null;
        birthDate=null;
        Posts.clear();
        Posts = null;
        noUsers--;
        for(int i = 0 ; i< Friends.size();i++) {
            try{
                this.deleteFriend(Friends.get(i));
                i--;
            }catch(Exception ignored){}
        }
        for (int i = 0; i < Groups.size();i++){
            try {
                Group group = Groups.get(i);
                if(group.getAdmin()==this) group.delete();
                else group.removeMember(this);
                i--;
            }catch(Exception ignored){}
        }
        Groups.clear();
        Groups = null;
        Friends = null;
        MutualFriendsNo=null;
    }
}