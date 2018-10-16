/**
 * Created by Josh on 15/10/2018.
 */
public class Btree implements ITree {

    private final int ORDER;
    private final int MAX;
    private Node root;



    public Btree(int ORDER) {
        this.root = null;
        this.ORDER = ORDER;
        this.MAX = ORDER -1;
    }

    // set references for the node to be inserted
    public void setReferences(int x, Node node, IntReference n) {

        if(x < node.getKey()[1]) {
            n.setReference(0);
        } else {

            n.setReference(node.getNumKeys());
            while ((x < node.getKey()[n.getReference()]) && n.getReference() > 1) {
                n.setReference(n.getReference() - 1);
            }

        }
    }

    @Override
    public void insert(int x) {
        IntReference indexKey = new IntReference(0);
        NodeReference indexKeyChild = new NodeReference(null);

        // determines if root needs to be spilt
        boolean split = insert(x, root, indexKey, indexKeyChild);

        // new root needs to be added
        if (split) {
            Node temp = new Node(ORDER);
            temp.setChildByIndex(0, root);
            root = temp;

            //set default root properties
            root.setNumKeys(1);
            root.setKeyByIndex(1, indexKey.getReference());
            root.setChildByIndex(1, indexKeyChild.getReference());
        }

    }

    private boolean insert(int x, Node node, IntReference indexKey, NodeReference indexKeyChild) {
        if (node == null) {
            indexKey.setReference(x);
            indexKeyChild.setReference(null);

            return true;
        }

        IntReference intRef = new IntReference(0);
        setReferences(x, node, intRef);

        boolean needSplit = insert(x, node.getChild()[intRef.getReference()], indexKey, indexKeyChild);

        if (needSplit) {

            if (node.getNumKeys() < MAX) {
                shiftInsert(node, intRef.getReference(), indexKey.getReference(), indexKeyChild.getReference());
                return false;
            } else {
                split(node, intRef.getReference(), indexKey, indexKeyChild);
                return true;
            }
        }

        return false;

    }

    // basic insert on array just moves the keys around
    private void shiftInsert(Node node, int n, int indexKey, Node indexKeyChild) {
        for (int i = node.getNumKeys(); i > n ; i--) {
            node.setKeyByIndex(i+1, node.getKey()[i]);
            node.setChildByIndex(i+1, node.getChild()[i]);
        }

        node.setKeyByIndex(n + 1, indexKey);
        node.setChildByIndex(n + 1, indexKeyChild);
        node.setNumKeys(node.getNumKeys()+1);
    }


    private void split(Node node, int n, IntReference indexKey, NodeReference indexKeyChild) {

        int i, j;
        int lastKey;
        Node lastChild;

        if(n == MAX) {
            lastKey = indexKey.getReference();
            lastChild = indexKeyChild.getReference();
        } else {
            lastKey = node.getKey()[MAX];
            lastChild = node.getChild()[MAX];

            for (i = node.getNumKeys()-1; i > n; i--) {
                node.setKeyByIndex(i+1, node.getKey()[i]);
                node.setChildByIndex(i+1, node.getChild()[i]);
            }
            node.setKeyByIndex(i+1, indexKey.getReference());
            node.setChildByIndex(i+1, indexKeyChild.getReference());

        }

        int medianIndex = (ORDER +1) /2;
        int medianKey = node.getKey()[medianIndex];
        Node newNode = new Node(ORDER);
        newNode.setNumKeys(ORDER - medianIndex);
        newNode.setChildByIndex(0, node.getChild()[medianIndex]);

        for (i=1, j=medianIndex+1; j <= MAX; i++, j++) {
            newNode.setKeyByIndex(i, node.getKey()[j]);
            newNode.setChildByIndex(i, node.getChild()[j]);
        }

        newNode.setKeyByIndex(i, lastKey);
        newNode.setChildByIndex(i, lastChild);

        node.setNumKeys(medianIndex -1);

        indexKey.setReference(medianKey);
        indexKeyChild.setReference(newNode);


    }

    public void display() {
        display(root, 0);
    }

    private void display(Node node, int spaces) {

        if(node != null) {
            for (int i = 1; i <= spaces; i++) {
                System.out.print(" ");
            }
            for (int i = 1; i <= node.getNumKeys(); i++) {
                System.out.print(node.getKey()[i] + " ");
            }
            System.out.println("");

            for (int i = 0; i <= node.getNumKeys(); i++) {
                display(node.getChild()[i], spaces + 15);
            }

        }

    }

    @Override
    public boolean search(int x) {
        //TODO
        return false;
    }

    @Override
    public int delete(int x) {
        //TODO
        return 0;
    }
}
