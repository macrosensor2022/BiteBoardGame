public class BiteBoardGameModel {

    private boolean[][] board;
    private int rows, cols;
    private int currentPlayer = 1;
    private boolean gameOver = false;
    private int winner = 0;

    public BiteBoardGameModel(int rows, int cols) {
        reset(rows, cols);
    }

    // Reset the entire game board
    public void reset(int rows, int cols) {
        this.rows = Math.max(1, rows);
        this.cols = Math.max(1, cols);

        board = new boolean[this.rows][this.cols];

        // Fill with chocolate
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                board[r][c] = true;
            }
        }

        currentPlayer = 1;
        gameOver = false;
        winner = 0;
    }

    public boolean isValidMove(int row, int col) {
        if (gameOver) return false;
        if (row < 0 || row >= rows || col < 0 || col >= cols) return false;
        return board[row][col];
    }

    // Removes clicked square and all squares above/right
    public boolean biteSquares(int row, int col) {
        if (!isValidMove(row, col)) return false;

        for (int r = row; r < rows; r++) {
            for (int c = col; c < cols; c++) {
                board[r][c] = false;
            }
        }

        // Toxic bottom-left square eaten → player loses immediately
        if (!board[0][0]) {
            gameOver = true;
            winner = (currentPlayer == 1) ? 2 : 1;
            return true;
        }

        // Check if ANY squares remain
        boolean anyLeft = false;
        for (int r = 0; r < rows && !anyLeft; r++) {
            for (int c = 0; c < cols && !anyLeft; c++) {
                if (board[r][c]) anyLeft = true;
            }
        }

        // No moves left → last player wins
        if (!anyLeft) {
            gameOver = true;
            winner = currentPlayer;
        }

        return true;
    }

    public void switchPlayer() {
        if (!gameOver) {
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }
    }

    public int getCurrentPlayer() { return currentPlayer; }
    public boolean isGameOver() { return gameOver; }
    public int getWinner() { return winner; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public boolean isCellAvailable(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return false;
        return board[row][col];
    }
}
