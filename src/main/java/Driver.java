import Services.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Driver {
    public static void main(String args[]) throws FileNotFoundException {

        File file = new File("/Users/b0212222/Downloads/TreeStructureChecker/src/main/resources/input");
        Scanner sc = new Scanner(file);

        System.out.println("*********** Welcome to Tree Validator ***********");

        Tree tree = new Tree();

        while(sc.hasNext()){
            String input = sc.nextLine();
            String[] inputData = input.split(" ");
            tree.addRelation(inputData[0], inputData[1]);
            if(!Objects.isNull(tree.getError())){
                break;
            }
        }

        System.out.println(tree.getStatusMessage());

    }
}
