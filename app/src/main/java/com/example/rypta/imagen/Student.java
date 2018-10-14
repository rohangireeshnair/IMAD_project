package com.example.rypta.imagen;

import android.graphics.Bitmap;

public class Student
{
    Bitmap profilepic;
    String username;
    String name;

    public Student(Bitmap profilepic, String username, String name) {
        this.profilepic = profilepic;
        this.username = username;
        this.name = name;
    }

    public Bitmap getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(Bitmap profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

}
