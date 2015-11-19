package ch.mydrive;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Created by elinext-kirill on 16.11.15.
 */
public class AboutWindow {

    private Shell shell;

    public Shell open(Shell parent) {
        shell = new Shell(parent);
        shell.setImage(UiImages.INSTANCE.get(UiImages.ICON));
        shell.setText(UiStrings.getResourceString("about.title"));
        shell.setMinimumSize(350, 250);
        shell.setSize(350, 250);
        shell.pack();
        shell.open();
        return shell;
    }
}
