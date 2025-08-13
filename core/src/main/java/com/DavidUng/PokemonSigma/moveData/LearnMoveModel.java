package com.DavidUng.PokemonSigma.moveData;

public class LearnMoveModel {
    private int id;
    private String method;
    public LearnMoveModel() {}
    public LearnMoveModel(int id, String method) {
        this.id = id;
        this.method = method;
    }

    @Override
    public String toString() {
        return "LearnMoveModel{id=" + id + ", method='" + method + "'}";
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
