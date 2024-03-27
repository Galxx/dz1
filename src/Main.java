import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String data = "";
        Scanner in = new Scanner(System.in);
        OperationHelper.readFromFile();
        OperationHelper.showMainMenu();
        while(true){
            boolean exit = OperationHelper.HandleOperation(data);
            if(exit){
                return;
            }else{
                data = in.nextLine();
            }
        }
        

    }
}