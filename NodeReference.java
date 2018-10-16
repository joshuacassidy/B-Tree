/**
 * Created by Josh on 15/10/2018.
 */
public class NodeReference {

    private Node reference;

    public NodeReference(Node reference) {
        this.reference = reference;
    }

    public Node getReference() {
        return reference;
    }

    public void setReference(Node reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "NodeReference{" +
                "reference=" + reference +
                '}';
    }
}
