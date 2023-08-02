public class blindSearch {
    private static long startTime = System.currentTimeMillis(); //variable to print the runtime
    static int boardLength = 8;
    static int[][] chessBoard = new int[boardLength][boardLength];
    static int initialX = 4; 
    static int initialY = 3;
    static int moveCount = 0;
    static boolean flag = false; //flag for printing the initial chess board

    public static void main(String[] args) {
    	InitializeChessBoard();
        SolveTour(); //driver function
    }

    static void PrintChessBoard(int board[][]) {  //prints the chess board with solution or the steps
        int side = 8;
    	System.out.print("\r");
        System.out.println(" \ta \tb \tc \td \te \tf \tg \th \n");
        for (int x = 0; x < boardLength; x++) {
        	System.out.print(side + "|");
            for (int y = 0; y < boardLength; y++) {
                System.out.print("\t" + board[x][y]);
            }
            side--;
            System.out.println();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\nRuntime: " + (endTime - startTime) + " milliseconds");
    }

    static void PrintInitialBoard(int board[][]) { //prints the initial board with the starting point
        int side = 8;
    	System.out.println("Initial Position: ");
        System.out.println();
        System.out.println(" \ta \tb \tc \td \te \tf \tg \th \n");
        for (int x = 0; x < boardLength; x++) {
        	System.out.print(side + "|");
            for (int y = 0; y < boardLength; y++) {
            	if(board[x][y] == -1) {
            		System.out.print("\tXX");
            	}
            	else System.out.print("\t" + board[x][y]);
            }
            side--;
            System.out.println();
        }
    }

    static void InitializeChessBoard() { //puts -1 value in the initial board to enable backtracking
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                chessBoard[i][j] = -1;
            }
        }
    }

    static boolean SolveTour() { //solves the knights tour problem
    	
    //-------------------------------MOVEMENT PATTERNS-----------------------------------//
    /* This section of the code contains various movement pattern priority, 
     * just remove the comment tag before moveX and moveY in order to use the movement pattern
     * note that some patterns are faster than others and others may take weeks to finish
     * default is set to clockwise as it provides a more blind search*/
    	
    	int moveX[]={ 2, 1, -1, -2, -2, -1, 1, 2 }; //clockwise (bottom first)
        int moveY[]={ -1, -2, -2, -1, 1, 2, 2, 1 };
    	
    	//int moveX[] = {-2, -1, 1, 2, 2, 1, -1, -2}; //clockwise (top first)
        //int moveY[] = {1, 2, 2, 1, -1, -2, -2, -1};
        
        //int moveX[] = {2, 1, -1, -2, -2, -1, 1, 2}; //counter clockwise
        //int moveY[] = {1, 2, 2, 1, -1, -2, -2, -1};

        //int moveX[] = {1, 1, 2, 2, -1, -1, -2, -2}; //Warnsdorf movement pattern (fastest)
        //int moveY[] = {2, -2, 1, -1, 2, -2, 1, -1};

        //int moveX[] = {-2, -2, -1, -1, 1, 1, 2, 2}; //alternating sides
        //int moveY[] = {-1, 1, -2, 2, -2, 2, -1, 1};

        //int moveX[] = {-2, -2, -1, -1, 1, 1, 2, 2}; //alternating sides 2
        //int moveY[] = {-1, 1, -2, 2, 2, -2, -1, 1};
        
        //int moveX[] = {-1, -1, -2, -2, 2, 2, 1, 1 }; //alternating sides 3
        //int moveY[] = {-2, 2, -1, 1, -1, 1, 2, -2 };

        chessBoard[initialX][initialY] = 0; //place the start counter at the initial position

        if (!flag) {
            PrintInitialBoard(chessBoard);
            flag = true;
        }
        if (!MoveKnightRecursively(initialX, initialY, 1, chessBoard, moveX, moveY)) { //this checks if a solution to the knights tour is found
            System.out.println("No solution found");
            return false;
        } else {
            PrintChessBoard(chessBoard);
        }

        return true; //meaning that the tour is successful
    }

    static boolean MoveKnightRecursively(int x, int y, int moveCount, int[][] board, int[] MoveX, int[] MoveY) {
        int nextX, nextY;

        if (moveCount == boardLength * boardLength) { //checks of the movement count matches with the board dimentions
            System.out.println("\nBoard is fully occupied");
            System.out.println("Final Position:");
            return true;
        }
        
        /*Given a set of movement patterns the loop below check whether the next move is available*/
        for (int i = 0; i < 8; i++) {
            nextX = x + MoveX[i]; //adds the next pair of movement direction 
            nextY = y + MoveY[i];
            if (nextX >= 0 && nextY >= 0 && nextX < boardLength && nextY < boardLength && board[nextX][nextY] == -1) { //this checks if the cell is already visited by the knight
                board[nextX][nextY] = moveCount; //places the current move number in the cell

                if (MoveKnightRecursively(nextX, nextY, moveCount + 1, board, MoveX, MoveY)) { //checks if the next move is valid
                    return true;
                } else { //it backtracks and find another possible move
                    board[nextX][nextY] = -1; //sets back to -1 meaning the cell is not yet visited
                }
            }

        }
        return false; //returns false if no solution is found.
    }

}