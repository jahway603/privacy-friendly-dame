/*
 This file is part of Privacy Friendly Dame.

 Privacy Friendly Dame is free software:
 you can redistribute it and/or modify it under the terms of the
 GNU General Public License as published by the Free Software Foundation,
 either version 3 of the License, or any later version.

 Privacy Friendly Dame is distributed in the hope
 that it will be useful, but WITHOUT ANY WARRANTY; without even
 the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Privacy Friendly App Example. If not, see <http://www.gnu.org/licenses/>.
 */

package org.secuso.privacyfriendlydame.game;

import java.util.ArrayList;

/**
 * This class models a game of checkers. It keeps track of the board and the active player.
 */
public class CheckersGame {
    static final int NONE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    static final int KINGED = 3;

    // checkers game state
    private Board gameBoard;
    private int turn;
    private ArrayList<Piece> capturedWhitePieces, capturedBlackPieces;


    /**
     * Default constructor which creates a new game with a default board setup and black as the first player
     */
    public CheckersGame() {
        gameBoard = new Board();
        turn = CheckersGame.BLACK;
        capturedWhitePieces = new ArrayList<>();
        capturedBlackPieces = new ArrayList<>();
    }

    /**
     * Changes the current player to black if white is active and vice versa
     */
    private void advanceTurn() {
        if (turn == CheckersGame.WHITE) {
            turn = CheckersGame.BLACK;
        } else {
            turn = CheckersGame.WHITE;
        }
    }

    /**
     * Returns the board of the current game
     * @return board of the current game
     */
    public Board getBoard() {
        return this.gameBoard;
    }

    private ArrayList<Piece> getCapturedPiecesForMove(Move move) {
        ArrayList<Piece> pieces = new ArrayList<>();

        for (Position p: move.capturePositions)
            pieces.add(getBoard().getPiece(p));

        return pieces;
    }

    /**
     * Returns the longest move which can be executed for a pair of start- and end-positions
     * @param start start-position of desired move
     * @param end end-position of desired move
     * @return move with most capturePositions for a pair of start- and end-positions
     */
    public Move getLongestMove(Position start, Position end) {
        Move longest = null;
        Move moveset[] = getMoves();
        for (Move move : moveset) {
            if (move.start().equals(start) && move.end().equals(end)) {
                if (longest == null ||
                        longest.capturePositions.size() < move.capturePositions.size())
                    longest = move;
            }
        }
        return longest;
    }

    /**
     * Generates an array of allowed moves for the current player
     * @return array of allowed moves for the current player
     */
    public Move[] getMoves() {
        return gameBoard.getMoves(turn);
    }

    /**
     * Executes a move on the board and passes the turn to the next player
     * @param move move to execute
     */
    public void makeMove(Move move) {
        gameBoard.makeMove(move);
        if (whoseTurn() == WHITE)
            capturedBlackPieces.addAll(getCapturedPiecesForMove(move));
        else
            capturedWhitePieces.addAll(getCapturedPiecesForMove(move));
        advanceTurn();
    }

    /**
     * Resets the board state and sets the current player to black
     */
    public void restart() {
        gameBoard = new Board();
        turn = CheckersGame.BLACK;
    }

    /**
     * Returns the ID of the current player
     * @return ID of the current player
     */
    public int whoseTurn() {
        return turn;
    }
}