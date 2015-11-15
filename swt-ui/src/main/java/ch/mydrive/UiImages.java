package ch.mydrive;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kirill on 14.11.2015.
 */
public class UiImages {
    public static final UiImages INSTANCE = new UiImages();

    public static final String ICON = "/app-logo.gif";
    public static final String CLOUD = "/cloud.gif";

    private Device display;

    public Image get(String img) {
        try (InputStream stream = UiImages.class.getResourceAsStream(img)) {
            ImageData source = new ImageData(stream);
            ImageData mask = source.getTransparencyMask();
            Image image = new Image(display, source, mask);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDisplay(Device display) {
        this.display = display;
    }
}
