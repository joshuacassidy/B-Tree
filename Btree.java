public class Btree implements ITree {

    private final int ORDER;
    private final int MAX;
    private final int MIN;
    private Node root;



    public Btree(int ORDER) {
        this.root = null;
        this.ORDER = ORDER;
        this.MAX = ORDER -1;
        MIN = (int) Math.ceil( (double) ORDER/2) -1;
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
        searchNode(x, node, intRef);

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
    public void delete(int x) {
        if (root == null) {
            System.out.println("tree is empty");
            return;
        }

        delete(x, root);

        if (root != null && root.numKeys ==0) {
            root = root.child[0];
        }

    }

    private void delete(int x, Node node) {
        IntReference n = new IntReference(0);
        if(searchNode(x, node, n)) {
            if (node.child[n.getReference()] == null) { // p is a leaf node
                deleteByShift(node, n.getReference());
                return;
            } else { // deleting a non-leaf node
                Node nonLeaf = node.child[n.getReference()];
                while (nonLeaf.child[0] != null) {
                    nonLeaf = nonLeaf.child[0];
                }
                node.key[n.getReference()] = nonLeaf.key[1];
                delete(nonLeaf.key[1], node.child[n.getReference()]);
            }
        } else { /* key not found in node */
            if (node.child[n.getReference()] == null) { /* p is a leaf node */
                System.out.println("Value " + x + " not present in the tree");
                return;
            } else { /* p is a non leaf node */
                delete(x, node.child[n.getReference()]);
            }
        }

        if (node.child[n.getReference()].numKeys < MIN) {
            restore(node, n.getReference());
        }

    }

    private void deleteByShift(Node node, int n) {
        for (int i = n + 1; i <= node.numKeys; i++) {
            node.key[i -1] = node.key[i];
            node.child[i-1] = node.child[i];
        }
        node.numKeys--;
    }

    // called when node.child[n] becomes underflow
    private void restore(Node node, int n) {
        if (n != 0 && node.child[n-1].numKeys > MIN) {
            bottomLeft(node, n);
        } else if (n != node.numKeys && node.child[n+1].numKeys > MIN) {
            bottomRight(node, n);
        } else {
            if (n != 0) {
                combine(node, n);
            } else {
                combine(node, n+1);
            }
        }
    }

    private void bottomLeft(Node node, int n) {
        Node underflowNode = node.child[n];
        Node leftSibling = node.child[n-1];

        underflowNode.numKeys++;
        // shift all the keys and children in underflow node one positon right
        for (int i = underflowNode.numKeys; i > 0; i--) {
            underflowNode.key[i+1] = underflowNode.key[i];
            underflowNode.child[i+1] = underflowNode.child[i];

        }
        underflowNode.child[1] = underflowNode.child[0];

        // move the separator key from the parent node p to underflow node
        underflowNode.key[1] = node.key[n];

        // move the right most key of the node left sibling to the parent node p
        node.key[n] = leftSibling.key[leftSibling.numKeys];

        // right most child of left sibling become left child of underflow node
        underflowNode.child[0] = leftSibling.child[leftSibling.numKeys];

        leftSibling.numKeys--;

    }

    private void bottomRight(Node node, int n) {
        Node underflowNode = node.child[n];
        Node rightSibling = node.child[n+1];

        // move the separator key from the parentnode p to underflow node
        underflowNode.numKeys++;
        underflowNode.key[underflowNode.numKeys] = node.key[n + 1];

        // left most child of rightsibling becomes the rightmost child of underflownode
        underflowNode.child[underflowNode.numKeys] = rightSibling.child[0];

        // move the leftmost key from rightsibling to parent node
        node.key[n + 1] = rightSibling.key[1];
        rightSibling.numKeys--;

        // shift all the keys and children of rightsibling one position left
        rightSibling.child[0] = rightSibling.child[1];

        for (int i = 1; i <= rightSibling.key[i + 1]; i++) {
            rightSibling.key[i] = rightSibling.key[i+1];
            rightSibling.child[i] = rightSibling.child[i + 1];
        }

    }

    private void combine(Node node, int m) {
        Node node1 = node.child[m -1];
        Node node2 = node.child[m];

        node1.numKeys++;

        // move the separator key from the parent node p to nodeA
        node1.key[node1.numKeys] = node.key[m];

        // shift the keys and children that are after the separator key in node p one postion left
        int i;
        for (i = m; i < node.numKeys; i++) {
            node.key[i] = node.key[i+1];
            node.child[i] = node.child[i + 1];
        }

        node.numKeys--;

        // leftmost child of nodeb becomes right most child of nodeA
        node1.child[node1.numKeys] = node2.child[0];

        // insert all the keys and children of nodeb at the end of nodeA
        for (i = 1; i <= node2.numKeys; i++) {
            node1.numKeys++;
            node1.key[node1.numKeys] = node2.key[i];
            node1.child[node1.numKeys] = node2.child[i];
        }

    }

    @Override
    public boolean search(int x) {
        if (search(x, root) == null) {
            return false;
        }
        return true;
    }

    public Node search(int x, Node node) {
        if (node == null) {
            return null;
        }

        IntReference n = new IntReference(0);

        if(searchNode(x, node, n)) {
            return node;
        }

        return search(x, node.child[n.getReference()]);


    }

    public boolean searchNode(int x, Node node, IntReference n) {

        if(x < node.key[1]) {
            n.setReference(0);
            return false;
        }

        n.setReference(node.numKeys);

        while ((x < node.key[n.getReference()]) && n.getReference() > 1) {
            n.setReference(n.getReference()-1);
        }

        return x == node.key[n.getReference()];

    }


    public void inorder() {
        inorder(root);
    }

    private void inorder(Node node) {

        if (node == null) {
            return;
        }
        int i;
        for (i = 0; i < node.numKeys; i++) {
            inorder(node.child[i]);
            System.out.println(node.key[i+1] + " ");
        }
        inorder(node.child[i]);

    }

}

