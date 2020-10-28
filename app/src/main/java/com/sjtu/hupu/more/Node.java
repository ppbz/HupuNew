package com.sjtu.hupu.more;

public class Node {
    public Node left;
    public Node right;
    public float x,y;
    private String name;
    public Node(String name) {
        this.name = name;
        x = 500f;
        y = 500f;
    }
}
