/**
 * Created by Josh on 15/10/2018.
 */
public class IntReference {

    private int reference;

    public IntReference(int reference) {
        this.reference = reference;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "IntReference{" +
                "reference=" + reference +
                '}';
    }
}
