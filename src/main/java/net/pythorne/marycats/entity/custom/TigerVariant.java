package net.pythorne.marycats.entity.custom;

import java.util.Arrays;
import java.util.Comparator;

public enum TigerVariant {
    ORANGE(0),
    WHITE(1);

    private static final TigerVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(TigerVariant::getId)).toArray(TigerVariant[]::new);
    private final int id;

    TigerVariant(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static TigerVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }
}

