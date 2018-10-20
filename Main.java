public class Main {

    public static void main(String[] args) {
        Btree btree = new Btree(5);

        for (int i = 1; i < 13; i++) {
            btree.insert(i);
        }

        System.out.println("\nInorder: ");
        btree.inorder();
        System.out.println();
        System.out.println("BTree: ");
        btree.display();

        btree.delete(8);

        System.out.println("BTree: ");
        btree.display();

        btree.delete(2);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(1);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(3);
        System.out.println("BTree: ");
        btree.display();


        btree.delete(4);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(11);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(7);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(5);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(6);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(9);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(10);
        System.out.println("BTree: ");
        btree.display();

        btree.delete(12);
        System.out.println("BTree: ");
        btree.display();

    }

}
