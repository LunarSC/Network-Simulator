package assignment1;

import javafx.scene.image.ImageView;

public class routerImage extends ImageView {
    protected String MAC;

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String hexAddress) {
        if(hexAddress.length() != 8) {
            MAC = hexAddress;
        }
    }

}
