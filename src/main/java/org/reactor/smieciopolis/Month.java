package org.reactor.smieciopolis;

public enum Month {
    
    JANUARY(1, 5, 0, 0),
    FEBRUARY(1, 5, 2, 1),
    MARCH(1, 5, 4, 2),
    APRIL(1, 5, 6, 3),
    MAY(1, 5, 8, 4),
    JUNE(1, 5, 10, 5),
    JULY(6, 11, 0, 6),
    AUGUST(6, 11, 2, 7),
    SEPTEMBER(6, 11, 4, 8),
    OCTOBER(6, 11, 6, 9),
    NOVEMBER(6, 11, 8, 10),
    DECEMBER(6, 11, 10, 11);
    
    private final int minRowValue;
    private final int maxRowValue;
    private final int col;
    private final int monthValue;

    public static Month byRowAndCol(int row, int col) {
        for (Month month : Month.values()) {
            if (month.rowMatches(row) && month.colMatches(col)) {
                return month;
            }
        }
        return null;
    }

    private boolean rowMatches(int row) {
        return row >= minRowValue && row <= maxRowValue;
    }

    private boolean colMatches(int col) {
        return col == this.col || col == this.col + 1;
    }

    Month(int minRowValue, int maxRowValue, int col, int monthValue) {
        this.minRowValue = minRowValue;
        this.maxRowValue = maxRowValue;
        this.col = col;
        this.monthValue = monthValue;
    }

    public int getMonthValue() {
        return monthValue;
    }
}
