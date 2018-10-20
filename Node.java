import java.util.Arrays;

public class Node {

    public int[] key;
    public Node[] child;
    public int numKeys;

    public Node(int size) {
        this.numKeys = 0;
        // zeroth element never used just move onto next one
        key = new int[size];
        child = new Node[size];
    }

    public int[] getKey() {
        return key;
    }

    public void setKey(int[] key) {
        this.key = key;
    }

    public void setChildByIndex(int index, Node child) {
        this.child[index] = child;
    }

    public void setKeyByIndex(int index, int key) {
        this.key[index] = key;
    }

    public Node[] getChild() {
        return child;
    }

    public void setChild(Node[] child) {
        this.child = child;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + Arrays.toString(key) +
                ", child=" + Arrays.toString(child) +
                ", numKeys=" + numKeys +
                '}';
    }
}
