package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BoardDatumResponse implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
 @SerializedName("board")
    @Expose
    private String board;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
}
