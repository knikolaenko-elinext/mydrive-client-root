package ch.mydrive;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

    public static void main(String[] args) {
        Display display = new Display();

        UiImages.INSTANCE.setDisplay(display);

        MainAppWindow application = new MainAppWindow();

        Shell shell = application.open(display);
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        application.close();
        display.dispose();
    }
}