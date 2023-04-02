package attribute.square;

import error.LogicError;

public class GameBoardSquareCoordinates {
    int row;
    int column;
    String standardNotation;

    public static GameBoardSquareCoordinates of(int row, int column) {
        return new GameBoardSquareCoordinates(row, column);
    }

    public static GameBoardSquareCoordinates of(String standardNotation) {
        Coordinates coordinates = calculateCoordinates(standardNotation);
        int coordinatesRow = coordinates.getRow();
        int coordinatesColumn = coordinates.getColumn();

        return new GameBoardSquareCoordinates(coordinatesRow, coordinatesColumn, standardNotation);
    }

    private GameBoardSquareCoordinates(int row, int column) {
        this(row, column, calculateStandardNotation(row, column));
    }

    private GameBoardSquareCoordinates(int row, int column, String standardNotation) {
        this.row = row;
        this.column = column;
        this.standardNotation = standardNotation;
    }

    public static String calculateStandardNotation(int row, int column) {
        final short asciiSymbolStartIndex = 65;
        final short asciiSymbolEndIndex = 90;

        int currentAsciiSymbolIndex = column + asciiSymbolStartIndex;

        if (currentAsciiSymbolIndex > asciiSymbolEndIndex) {
            throw new LogicError("Column must not be more than 25");
        }

        char columnChar = (char) currentAsciiSymbolIndex;

        return String.valueOf(columnChar) + row;
    }

    public static Coordinates calculateCoordinates(String standardNotation) {
        int standardNotationLength = standardNotation.length();

        String rowString = null;
        String columnNameString = null;

        for (int i = 0; i < standardNotationLength; i++) {
            char currentChar = standardNotation.charAt(i);

            if (Character.isDigit(currentChar)) {
                columnNameString = standardNotation.substring(0, i);
                rowString = standardNotation.substring(i, standardNotationLength);
                break;
            }
        }

        if (rowString == null) {
            throw new LogicError("Row number must contain at least 1 character");
        } else if (columnNameString.length() != 1) {
            throw new LogicError("Column name must contain only 1 character");
        }

        int rowNum = Integer.parseInt(rowString);
        int columnNum = Character.getNumericValue(columnNameString.charAt(0));

        return Coordinates.of(rowNum, columnNum);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getStandardNotation() {
        return standardNotation;
    }

    public static final class Coordinates {
        private final int row;
        private final int column;

        public static Coordinates of(int row, int column) {
            return new Coordinates(row, column);
        }

        private Coordinates(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}
