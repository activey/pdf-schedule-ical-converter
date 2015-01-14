package org.reactor.smieciopolis;

public enum TrashType {
    
    BLACK("CZARNY", 1, 7),
    YELLOW("ŻÓŁTY", 2, 8),
    GREEN("ZIELONY", 3, 9),
    BIO("BIO", 4, 10),
    LARGE("WLG", 5, 11);

    public static TrashType byRow(int rowId) {
        for (TrashType type: TrashType.values()){
            for (int id : type.rowIds) {
                if (id == rowId) {
                    return type;
                }
            }
        }
        return null;
    }
    
    private final String label;
    private final int[] rowIds;
    
    TrashType(String label, int... rowIds) {
        this.label = label;
        this.rowIds = rowIds;
    }

    public String getLabel() {
        return label;
    }
}
