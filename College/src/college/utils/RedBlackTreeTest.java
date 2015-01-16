package college.utils;

import java.util.Scanner;

public class RedBlackTreeTest {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
        RedBlackTree rbt = new RedBlackTree(); 
        
        System.out.println("Red Black Tree Test\n");          
        char ch;
        /*  Perform tree operations  */
        do    
        {
            System.out.println("\nRed Black Tree Operations\n");
            System.out.println("1. Insert ");
            System.out.println("2. Search");
            System.out.println("3. Count nodes");
            System.out.println("4. Check empty");
            System.out.println("5. Clear tree");
 
            int choice = scan.nextInt();            
            switch (choice)
            {
            case 1 : 
                System.out.println("Enter integer element to insert");
                rbt.insert( scan.nextInt() );                     
                break;                          
            case 2 : 
                System.out.println("Enter integer element to search");
                System.out.println("Search result : "+ rbt.search( scan.nextInt() ));
                break;                                          
            case 3 : 
                System.out.println("Nodes = "+ rbt.countNodes());
                break;     
            case 4 : 
                System.out.println("Empty status = "+ rbt.isEmpty());
                break;     
            case 5 : 
                System.out.println("\nTree Cleared");
                rbt.makeEmpty();
                break;         
            default : 
                System.out.println("Wrong Entry \n ");
                break;    
            }
            /*  Display tree  */
            System.out.print("\nPost order : ");
            rbt.postorder();
            System.out.print("\nPre order : ");
            rbt.preorder();
            System.out.print("\nIn order : ");
            rbt.inorder(); 
 
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);                        
        } while (ch == 'Y'|| ch == 'y');  

	}

}
