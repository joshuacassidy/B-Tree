/**
 * Created by Josh on 15/10/2018.
 */
public class Main {

    public static void main(String[] args) {
        Btree btree1 = new Btree(5);
        int[] arr1 = {1, 12, 8, 2, 25, 6, 14, 28, 17, 7, 52, 16, 48, 68, 3, 26, 29, 53, 55, 45};
        for (int i: arr1) {
            btree1.insert(i);
        }
        System.out.println("Btree: ");
        btree1.display();
        System.out.println();

        Btree btree2 = new Btree(3);
        int[] arr2 = {78, 52, 81, 40, 33, 90, 85, 20, 38};
        for (int i: arr2) {
            btree2.insert(i);
        }
        System.out.println("Btree: ");
        btree2.display();
        System.out.println();
        System.out.println();
    }

}
