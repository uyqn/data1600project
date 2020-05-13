package listManager;

import users.User;

import java.util.ArrayList;

public class UserList {
    private static ArrayList<User> members = new ArrayList<>();

    public static void add(User user) throws IllegalArgumentException{
        for (User member : members) {
            if (user.getUsername().equals(member.getUsername())) {
                throw new IllegalArgumentException("A user with the username " + member.getUsername() + " already " +
                        "exists \nplease pick a new username");
            }
        }
        members.add(user);
    }
    public static void remove(User user){
        members.remove(user);
    }
    public static ArrayList<User> getMembers() {
        return members;
    }
}
