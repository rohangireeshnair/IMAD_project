package com.example.rypta.imagen;

import android.graphics.Bitmap;

public class Profile
{
    Bitmap id;

    public Bitmap getId() {
        return id;
    }

    public void setId(Bitmap id) {
        this.id = id;
    }

    public Profile(Bitmap i)
    {
        id=i;
    }

}
