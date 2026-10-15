package com.example.pokemonfinder;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataManager {
    public static DataManager instance = new DataManager();
    private final String FILENAME = "pokemons.txt";

    private DataManager() {
    }


    public void writeToFile(Context context, ArrayList<Pokemon> pokemons) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pokemons);

            oos.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public ArrayList<Pokemon> readFromFile(Context context) {
        ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();

        try{
            FileInputStream fs = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fs);

            pokemons = (ArrayList<Pokemon>) ois.readObject();

            fs.close();
            ois.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return pokemons;
    }

}
