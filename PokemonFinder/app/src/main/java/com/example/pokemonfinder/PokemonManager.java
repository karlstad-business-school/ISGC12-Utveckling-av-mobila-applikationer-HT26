package com.example.pokemonfinder;

import android.provider.Telephony;

import java.util.ArrayList;

public class PokemonManager {
    public static PokemonManager instance = new PokemonManager();
    private ArrayList<Pokemon> pokemons;

    private enum Test{
        T
    }

    private PokemonManager(){}

    public void add(Pokemon p){
        if(pokemons == null){
            pokemons = new ArrayList<Pokemon>();
        }
        for(int i = 0; i < pokemons.size(); i++){
            if(pokemons.get(i).getName() == p.getName()){
                return;
            }
        }
        pokemons.add(p);
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
