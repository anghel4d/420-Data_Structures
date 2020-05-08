package ca.qc.johnabbott.cs406;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ThreeMensMorris implements Game, Copyable<ThreeMensMorris>{

    // The game table containing current positions.
    final int TABLESIZE = 3;
    Token[][] gameTable = new Token[TABLESIZE][TABLESIZE];

    // The starting token of the game.
    final Token starter = Token.WHITE;

    // The current token of the game.
    Token currentToken;

    // Keeping track of the number of turns made.
    final int MAXTURNS = 3;
    Map<Token, Integer> turnsMade;

    // Constructor
    public ThreeMensMorris() {
        currentToken = starter;
        turnsMade = new HashMap<>();
        turnsMade.put(Token.WHITE, 0);
        turnsMade.put(Token.BLACK, 0);
        for(Token[] row : gameTable){
            Arrays.fill(row, Token.NONE);
        }
    }

    public ThreeMensMorris(char[] tableState) {
        this();

        int row = -1;
        for (int i = 0; i < tableState.length; i++){
            int col = i % TABLESIZE;
            if(col == 0){
                row += 1;
            }

            char currentChar = tableState[i];

            switch (currentChar){
                case '○':
                    gameTable[row][col] = Token.WHITE;      // Set the token on the table.
                    int tmp = turnsMade.get(Token.WHITE);   // Count the number of turns
                    turnsMade.replace(Token.WHITE, tmp + 1);
                    currentToken = Token.BLACK;             // White went so it's Black's turn next.
                    break;
                case '●':
                    gameTable[row][col] = Token.BLACK;      // Same as above.
                    tmp = turnsMade.get(Token.BLACK);
                    turnsMade.replace(Token.BLACK, tmp + 1);
                    currentToken = Token.WHITE;             // Black went so it's White's turn next.
                    break;
                default:
                    gameTable[row][col] = Token.NONE;
                    break;
            }
        }
    }

    // Methods
    @Override
    public boolean play(int file, int rank) {
        // Convert rank and file to array indexes.
        file = file - 1;
        rank = rank - 1;

        // Don't allow a move to occur if all plays have been made OR the game table position is occupied.
        if(turnsMade.get(Token.WHITE) >= MAXTURNS && turnsMade.get(Token.BLACK) >= MAXTURNS
            || gameTable[file][rank] != Token.NONE || this.winner() != Token.NONE) {
            return false;
        }

        gameTable[file][rank] = currentToken;
        int tmp = turnsMade.get(currentToken) + 1;
        turnsMade.replace(currentToken, tmp);
        currentToken = currentToken.opposite();

        return true;
    }

    // Rank = Row
    private boolean sameRank() {
        boolean isRankSame = true;
        for(int i = 0; i < TABLESIZE; i++){
            isRankSame = true;
            Token first = gameTable[i][0];
            for(int j = 1; j < TABLESIZE; j++){
                if(!(first.equals(gameTable[i][j]))){
                    isRankSame = false;
                    break;
                }
            }
            if(isRankSame) { break; }
        }
        return isRankSame;
    }



    // File = Column
    private boolean sameFile() {
        boolean isFileSame = true;
        for(int i = 0; i < TABLESIZE; i++) {
            isFileSame = true;
            Token first = gameTable[0][i];
            for(int j = 1; j < TABLESIZE; j++) {
                if(!(first.equals(gameTable[j][i]))) {
                    isFileSame = false;
                    break;
                }
            }
            if(isFileSame) { break; }
        }
        return isFileSame;
    }

    private boolean sameDiagonally() {
        if(currentToken == gameTable[0][0] && currentToken == gameTable[1][1] && currentToken == gameTable[2][2]
        || currentToken == gameTable[0][2] && currentToken == gameTable[1][1] && currentToken == gameTable[2][0]) {
            return true;
        }

        return false;
    }

    @Override
    public Token winner() {
        if(sameDiagonally() || sameRank() || sameFile()) {
            return currentToken;
        }

        return Token.NONE;
    }

    public char[] contentChars() {
        char[] content = new char[TABLESIZE * TABLESIZE];
        int count = 0;
        for(int row = 0; row < TABLESIZE; row++) {
            for(int col = 0; col < TABLESIZE; col++) {
                content[count] = gameTable[row][col].get();
                count++;
            }
        }
        return content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Table Contents
        sb.append('[');
        sb.append(contentChars());
        sb.append(']');
        sb.append(' ');

        // Current Turn
        sb.append("turn=");
        sb.append(currentToken);
        sb.append(' ');

        // Winner
        sb.append("winner=");
        sb.append(this.winner().name());

        return sb.toString();
    }

    @Override
    public ThreeMensMorris copy() {
        return new ThreeMensMorris(contentChars());
    }

    // Comparison Operators
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThreeMensMorris that = (ThreeMensMorris) o;

        if (!Arrays.deepEquals(gameTable, that.gameTable)) return false;
        if (starter != that.starter) return false;
        return currentToken == that.currentToken;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(gameTable);
        result = 31 * result + (starter != null ? starter.hashCode() : 0);
        result = 31 * result + (currentToken != null ? currentToken.hashCode() : 0);
        return result;
    }
}
