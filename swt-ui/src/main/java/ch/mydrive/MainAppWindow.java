package ch.mydrive;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

/**
 * Created by kirill on 14.11.2015.
 */
public class MainAppWindow {

    private static final Logger LOG = Logger.getLogger(MainAppWindow.class);

    private Display display;
    private Shell shell;
    private Group dndGroup;
    private TrayItem trayItem;
    private Label status;

    public Shell open(Display display) {
        this.display = display;
        shell = new Shell(display);
        shell.setImage(UiImages.INSTANCE.get(UiImages.ICON));
        shell.setText(UiStrings.getResourceString("main.title"));
        createShellContents();
        shell.setMinimumSize(450, 350);
        shell.setMaximized(AppProps.INSTANCE.getBool("main.maximized", false));
        if (!shell.getMaximized()){
			shell.setSize(AppProps.INSTANCE.getInt("main.width", 450), AppProps.INSTANCE.getInt("main.height", 350));
			shell.setLocation(AppProps.INSTANCE.getInt("main.x", 50), AppProps.INSTANCE.getInt("main.y", 50));
	    }
		shell.open();
        shell.addShellListener(new ShellAdapter() {
            @Override
            public void shellIconified(ShellEvent e) {
                minimizeToTray();
            }
        });
        // Save new size	 
		shell.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				AppProps.INSTANCE.setInt("main.width", shell.getSize().x);
				AppProps.INSTANCE.setInt("main.height", shell.getSize().y);
				AppProps.INSTANCE.setBool("main.maximized", shell.getMaximized());
			}
		});
		// Save new position
		shell.addListener(SWT.Move, new Listener() {
			public void handleEvent(Event e) {
				AppProps.INSTANCE.setInt("main.x", shell.getLocation().x);
				AppProps.INSTANCE.setInt("main.y", shell.getLocation().y);
			}
		});
        LOG.trace("MainAppWindow Shell Opened");
        return shell;
    }


    private void createShellContents() {
        {
            FormLayout layout = new FormLayout();
            shell.setLayout(layout);
        }
        createMenubar();
        createDragAndDropArea();
        setupDragAndDrop();
        createStatusBar();
        createTrayIcon();
    }

    private void createTrayIcon() {
        Tray tray = display.getSystemTray();
        if (tray == null) {
            return;
        }
        trayItem = new TrayItem(tray, SWT.NONE);
        trayItem.setImage(UiImages.INSTANCE.get(UiImages.ICON));
        trayItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                toggleMinimizedState();
            }
        });

        final Menu trayMenu = new Menu(shell, SWT.POP_UP);
        MenuItem exitMenu = new MenuItem(trayMenu, SWT.PUSH);
        exitMenu.setText(UiStrings.getResourceString("main.tray.menu.exit"));
        exitMenu.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                close();
            }
        });

        trayItem.addListener(SWT.MenuDetect, new Listener() {
            public void handleEvent(Event event) {
                trayMenu.setVisible(true);
            }
        });
    }



    private void createMenubar() {
        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);

        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText(UiStrings.getResourceString("main.menu.file"));

        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);

        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.setText(UiStrings.getResourceString("main.menu.file.exit"));
        exitItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                close();
            }
        });

        MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeHelpMenu.setText(UiStrings.getResourceString("main.menu.help"));

        Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeHelpMenu.setMenu(helpMenu);

        MenuItem aboutItem = new MenuItem(helpMenu, SWT.PUSH);
        aboutItem.setText(UiStrings.getResourceString("main.menu.help.about"));
        aboutItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                AboutWindow aboutWindow = new AboutWindow();
                aboutWindow.open(shell);
            }
        });
    }


    private void createDragAndDropArea() {
        dndGroup = new Group(shell, SWT.NONE);
        {
            FormData layoutData = new FormData();
            layoutData.left = new FormAttachment(10);
            layoutData.right = new FormAttachment(90);
            layoutData.top = new FormAttachment(10);
            layoutData.bottom = new FormAttachment(80);
            dndGroup.setLayoutData(layoutData);
        }

        {
            FormLayout layout = new FormLayout();
            dndGroup.setLayout(layout);
        }

        Label cloud = new Label(dndGroup, SWT.NONE);
        cloud.setImage(UiImages.INSTANCE.get(UiImages.CLOUD));
        cloud.setAlignment(SWT.CENTER);
        {
            FormData layoutData = new FormData();
            layoutData.left = new FormAttachment(5);
            layoutData.right = new FormAttachment(95);
            layoutData.top = new FormAttachment(0);
            layoutData.bottom = new FormAttachment(60);
            cloud.setLayoutData(layoutData);
        }

        Label hint = new Label(dndGroup, SWT.NONE);
        hint.setText(UiStrings.getResourceString("main.dnd.hint"));
        hint.setAlignment(SWT.CENTER);
        {
            FormData layoutData = new FormData();
            layoutData.left = new FormAttachment(5);
            layoutData.right = new FormAttachment(95);
            layoutData.bottom = new FormAttachment(70);
            hint.setLayoutData(layoutData);
        }

        Button selectFilesBtn = new Button(dndGroup, SWT.PUSH);
        selectFilesBtn.setText(UiStrings.getResourceString("main.dnd.btn"));
        selectFilesBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
                fileDialog.setText(UiStrings.getResourceString("main.selectFilesDialog.title"));
                fileDialog.open();
                LOG.info(">> Select");               
                String[] files = fileDialog.getFileNames();                
                StringBuilder sb = new StringBuilder("Select: ");
                for (String file: files){
                	sb.append(fileDialog.getFilterPath()).append(File.separator).append(file).append(";");
                }
                status.setText(sb.toString());
            }
        });
        {
            FormData layoutData = new FormData();
            layoutData.left = new FormAttachment(25);
            layoutData.right = new FormAttachment(75);
            layoutData.bottom = new FormAttachment(90);
            selectFilesBtn.setLayoutData(layoutData);
        }
    }

    private void setupDragAndDrop() {
        DropTarget dropTarget = new DropTarget(dndGroup, DND.DROP_MOVE);
        dropTarget.setTransfer(new FileTransfer[]{FileTransfer.getInstance()});
        dropTarget.addDropListener(new DropTargetAdapter(){
            @Override
            public void drop(DropTargetEvent event) {
                LOG.info(">> Drop");
                LOG.info(event.toString());
                String[] files = (String[]) event.data;                
                StringBuilder sb = new StringBuilder("Drop: ");
                for (String file: files){
                	sb.append(file).append(";");
                }
                status.setText(sb.toString());
            }
        });
    }

    private void createStatusBar() {
        status = new Label(shell, SWT.BORDER);
        {
            FormData layoutData = new FormData();
            layoutData.left = new FormAttachment(0);
            layoutData.right = new FormAttachment(100);
            layoutData.bottom = new FormAttachment(100);
            status.setLayoutData(layoutData);
        }
        status.setText(UiStrings.getResourceString("main.status.ready"));
    }

    private void minimizeToTray() {
        shell.setVisible(false);
    }

    private void restoreFromTray() {
        shell.setVisible(true);
        shell.setActive();
        shell.setMinimized(false);
    }

    private void toggleMinimizedState() {
        if (shell.isVisible()){
            minimizeToTray();
        } else {
            restoreFromTray();
        }
    }

    public void close() {
        shell.close();
    }
}
