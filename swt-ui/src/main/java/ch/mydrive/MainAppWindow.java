package ch.mydrive;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

/**
 * Created by kirill on 14.11.2015.
 */
public class MainAppWindow {

    private static final Logger LOG = Logger.getLogger(MainAppWindow.class);

    private Display display;
    private Shell shell;
    private Group dndGroup;

    public Shell open(Display display) {
        this.display = display;
        shell = new Shell(display);
        shell.setImage(UiImages.INSTANCE.get(UiImages.ICON));
        shell.setText(UiStrings.getResourceString("main.title"));
        createShellContents();
        shell.setMinimumSize(450, 350);
        shell.setSize(450, 350);
        shell.pack();
        shell.open();
        LOG.trace("MainAppWindow Shell Opened");
        return shell;
    }

    private void createShellContents() {
        {
            FormLayout layout = new FormLayout();
            shell.setLayout(layout);
        }

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
            layoutData.top = new FormAttachment(60);
            layoutData.bottom = new FormAttachment(75);
            hint.setLayoutData(layoutData);
        }
        setupDragAndDrop();

        Label status = new Label(shell, SWT.BORDER);
        {
            FormData layoutData = new FormData();
            layoutData.left = new FormAttachment(0);
            layoutData.right = new FormAttachment(100);
            layoutData.bottom = new FormAttachment(100);
            status.setLayoutData(layoutData);
        }
        status.setText(UiStrings.getResourceString("main.status.ready"));

        createMenubar();
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

        MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeHelpMenu.setText(UiStrings.getResourceString("main.menu.help"));

        Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeHelpMenu.setMenu(helpMenu);

        MenuItem aboutItem = new MenuItem(helpMenu, SWT.PUSH);
        aboutItem.setText(UiStrings.getResourceString("main.menu.help.about"));
    }

    private void setupDragAndDrop() {
        DropTarget dropTarget = new DropTarget(dndGroup, DND.DROP_MOVE);
        dropTarget.setTransfer(new FileTransfer[]{FileTransfer.getInstance()});
        dropTarget.addDropListener(new DropTargetListener() {

            @Override
            public void dragEnter(DropTargetEvent event) {

            }

            @Override
            public void dragLeave(DropTargetEvent event) {

            }

            @Override
            public void dragOperationChanged(DropTargetEvent event) {

            }

            @Override
            public void dragOver(DropTargetEvent event) {

            }

            @Override
            public void drop(DropTargetEvent event) {
                LOG.info(">> Drop");
                LOG.info(event.toString());
            }

            @Override
            public void dropAccept(DropTargetEvent event) {

            }
        });
    }

    public void close() {

    }
}
