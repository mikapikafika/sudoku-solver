package sudoku;

public enum Level {
    EASY(20),
    MEDIUM(30),
    HARD(40);

    final int howManyFields;

    Level(int fieldsNumber) {
        this.howManyFields = fieldsNumber;
    }

    public int howManyFieldsDelete() {
        return howManyFields;
    }
}