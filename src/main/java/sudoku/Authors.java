package sudoku;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"1","Szymon Wydmuch"},
                {"2","Michalina Wysocka"}
        };
    }
}
