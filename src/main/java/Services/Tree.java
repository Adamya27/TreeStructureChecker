package Services;

import Models.Node;

import java.util.*;

public class Tree {
    enum Errors { E1, E2, E3, E4, E5}
    private List<Node> nodes;
    private Errors error;

    public Tree() {
        this.nodes = new ArrayList<>();
        this.error = null;
    }

    public void addRelation(String parent, String child){
//        System.out.println("adding relation " + parent + " -> " + child);
        Node parentNode = null, childNode = null;
        List<Node> nodes = getNodes();
        for(Node node: nodes){
            if(node.getData().equals(parent)){
                parentNode = node;
            }
            if(node.getData().equals(child)){
                childNode = node;
            }
        }

        if(Objects.isNull(parentNode)){
            parentNode = new Node(parent);
            nodes.add(parentNode);
        }
        if(Objects.isNull(childNode)){
            childNode = new Node(child);
            nodes.add(childNode);
        }

        List<Node> childrenOfParent = parentNode.getChildren();
        if(checkIfNodeAlreadyPresent(childrenOfParent, childNode)){
            setError(Errors.E2);
            return;
        }
        parentNode.addChildren(childNode);
        Node parentOfChild = childNode.getParent();
        if(!Objects.isNull(parentOfChild)){
            setError(Errors.E5);
            return;
        }

        childNode.setParent(parentNode);
    }

    public boolean checkIfNodeAlreadyPresent(List<Node> childrenList, Node child){

        for(Node childElement : childrenList){
            if(childElement.getData() == child.getData()){
                return true;
            }
        }

        return false;
    }

    public void checkErrorsInTree(){
        if(!Objects.isNull(getError())){
            return ;
        }

        checkForMoreThanTwoChildren();
        if(!Objects.isNull(getError())){
            return ;
        }

        checkForMultipleRoots();
        if(!Objects.isNull(getError())){
            return;
        }

        checkForCycles();
        if(!Objects.isNull(getError())){
            return;
        }
    }

    public void checkForMoreThanTwoChildren(){
        List<Node> nodeList = getNodes();
        for(Node node: nodeList){
            if(node.getChildren().size() > 2){
                setError(Errors.E1);
                return;
            }
        }
    }

    public void checkForMultipleRoots(){
        int countRoots = 0;
        List<Node> nodeList = getNodes();
        for(Node node:nodeList){
            Node parentOfCurrentNode = node.getParent();
            if(Objects.isNull(parentOfCurrentNode)){
                countRoots++;
            }
        }
        if(countRoots > 1){
            setError(Errors.E4);
        }
    }

    public void checkForCycles(){
        List<Node> nodes = getNodes();
        List<Node> roots = findRoots(nodes);
        if(roots.size() == 0){
            setError(Errors.E3);
            return;
        }

        Queue<Node> q = new LinkedList<>();
        for(Node root: roots){
            q.add(root);
            HashMap<Node,Boolean> visited = new HashMap<>();

            while(!q.isEmpty()){
                Node frontElement = q.peek();
                q.remove();
                visited.put(frontElement,true);
                List<Node> children = frontElement.getChildren();
                for (Node child: children){
//                    System.out.println(visited.get(child));
//                    System.out.println(Objects.isNull(visited.get(child)));
                    if(!Objects.isNull(visited.get(child))){
                        setError(Errors.E3);
                        return;
                    }
                    else{
                        q.add(child);
                    }
                }

            }
        }
    }

    public String getStatusMessage(){
        checkErrorsInTree();

        Errors error = getError();
        String message;
        if(Objects.isNull(error)){
            return "Success";
        }

        switch (error){
            case E1:
                message = "E1 : More than 2 children";
                break;
            case E2: message = "E2: Duplicate Tuples";
                break;
            case E3: message = "E3: Cycle present";
                break;
            case E4: message = "E4: Multiple roots";
                break;
            case E5: message = "E5: Multiple parents";
                break;
            default: message = " Unknown Error";
        }

        return message;
    }

    public List<Node> findRoots(List<Node> nodes){
        List<Node> roots = new ArrayList<>();
        for(Node node: nodes){
            Node parentNode = node.getParent();
            if(Objects.isNull(parentNode)){
                roots.add(node);
            }
        }

        return roots;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }
}
