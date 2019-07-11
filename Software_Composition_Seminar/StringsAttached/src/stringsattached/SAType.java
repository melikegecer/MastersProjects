package stringsattached;

/**
 *
 * @author Melike Geçer
 */
public class SAType {

    private int typeID;
    private String typeName;

    public SAType(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public int getTypeID() {
        return typeID;
    }

    public String getTypeName() {
        return typeName;
    }

}
