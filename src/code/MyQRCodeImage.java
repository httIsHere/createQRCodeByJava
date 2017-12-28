package code;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class MyQRCodeImage {
	
	BufferedImage bufferedImage;
	
	public MyQRCodeImage (BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	// get the image's height
	public int getHeight() {
		return bufferedImage.getHeight();
	}
	
	// get the image's px
	public int getPixel(int arg0, int arg1) {
		return bufferedImage.getRGB(arg0, arg1);
	}
	
	// get the image's width
	public int getWidth() {
		return bufferedImage.getWidth();
	}
}
