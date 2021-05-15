package Models;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String data;
    private List<Node> children;
    private Node parent;

    public Node(String data) {
        this.data = data;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public void addChildren(Node child){
        List<Node> children = getChildren();
        children.add(child);
        setChildren(children);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
