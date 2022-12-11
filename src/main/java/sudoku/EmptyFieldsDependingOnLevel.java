package sudoku;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class EmptyFieldsDependingOnLevel {

    private Set<Point> cords = new HashSet<Point>();

    public SudokuBoard readyBoard(SudokuBoard board, Level level) {
        Random random = new Random();

        while (cords.size() != level.howManyFieldsDelete()) {
            Point point = new Point();
            point.x = random.nextInt(9);
            point.y = random.nextInt(9);
            cords.add(point);
        }

        for (Point point : cords) {
            board.set(point.x, point.y, 0);
        }

        return board;
    }

}
