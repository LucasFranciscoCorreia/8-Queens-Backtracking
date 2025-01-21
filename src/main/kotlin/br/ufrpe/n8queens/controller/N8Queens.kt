package br.ufrpe.n8queens.controller

import br.ufrpe.n8queens.model.Row
import kotlin.math.abs
import kotlin.math.max

/**
 * The N8Queens class provides methods to solve the 8-queens problem using backtracking.
 *
 * The 8-queens problem is a classic puzzle where the goal is to place eight queens on an 8x8 chessboard
 * such that no two queens threaten each other. This class contains methods to check if a queen can be
 * safely placed on the board and to initiate the search for a solution.
 */
class N8Queens {
    /**
     * Companion object containing utility functions for solving the 8-queens problem.
     */
    companion object {
        /**
         * Checks if placing a queen at the given row and column is safe.
         *
         * This function determines if a queen can be placed on the board at the specified row and column
         * without being attacked by any other queens already placed on the board.
         *
         * @param root The head of the linked list representing the rows where queens are already placed.
         * @param row The row index where the queen is to be placed.
         * @param col The column index where the queen is to be placed.
         * @return `true` if it is safe to place the queen at the specified position, `false` otherwise.
         */
        fun isSafe(root: Row, row: Int, col: Int): Boolean {
            var i = 1;
            var node: Row? = root;
            while(node != null){
                if(node.j == -1) {
                    break
                }
                if(node.j == col || abs(node.j - col) == abs(i - row)){
                    return false;
                }
                i++;
                node = node.next;
            }
            return true;
        }
        /**
         * Initiates the search for a solution to the 8-queens problem starting from the given root row.
         *
         * @param root The root row node from which the search begins.
         * @return The depth level if a solution is found, otherwise -1.
         */
        fun search(root: Row): Int{
            var found = false;

            /**
             * Recursively attempts to place one queen on the board using backtracking.
             *
             * @param node The current row node being processed.
             * @param depth The current depth level in the recursion, representing the row number.
             * @return The depth level if a solution is found, otherwise -1.
             */
            fun start(node: Row, depth: Int): Int{
                if(node.next != null) {
                    val ret = start(node.next!!, depth+1)
                    if(found) return ret;
                    if(depth == 1 && node.j == 8){
                        node.j = -1;
                    }
                    node.next = null;
                };
                if(depth == 8){
                    for(j in max(node.j+1, 1)..8){
                        if(isSafe(root, depth, j)){
                            node.j = j;
                            found = true;
                            break;
                        }
                    }
                    return depth;
                }
                for(j in max(1, node.j+1)..8){
                    if(isSafe(root, depth, j)){
                        node.j = j;
                        node.next = Row();
                        found = true;
                        return depth;
                    }
                }
                return -1;
            }
            return start(root, 1);
        }
    }

}