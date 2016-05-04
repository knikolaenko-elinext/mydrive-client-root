package ch.mydrive;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ch.mydrive.core.CoreModule;

public class Main {

    public static void main(String[] args) {
    	createInjector();
    	
        Display display = new Display();

        UiImages.INSTANCE.setDisplay(display);

        MainAppWindow application = new MainAppWindow();

        Shell shell = application.open(display);
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
    
    private static Injector createInjector(){
    	Injector injector = Guice.createInjector(new CoreModule());
    	return injector;
    }
}