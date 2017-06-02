package com.example.kaios.runcar2;

/**
 * Created by kaios on 6/1/2017.
 */

public class Model_Diem {

    private String Ten;
    private int Diem;

    public Model_Diem(String Ten, int Diem ) {
        this.Ten=Ten;
        this.Diem=Diem;
    }


    public String getTen() {
        return Ten;
    }

    public int getDiem() {
        return Diem;
    }

    public void setTen(String Ten){
        this.Ten=Ten;
    }

    public void setDiem(int Diem){
        this.Diem=Diem;
    }
}