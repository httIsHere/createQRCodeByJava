package code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRCode {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Qrcode qrcode = new Qrcode();
		
		//error class£¨L, M, H)
		qrcode.setQrcodeErrorCorrect('M');
		
		//N-number, A-alpha(a-Z), B-others
		qrcode.setQrcodeEncodeMode('B');
		
		//version--can input
		int version = 7;
		qrcode.setQrcodeVersion(version);
		
		//the data in qrcode--can input
		String qrData = "my message";
		
		//set the pixel of qrcode
		int width = 67 + 12 * (7 - 1);
		int height = 67 + 12 * (7 - 1);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//draw
		Graphics2D gs = bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		//clear canvas content
		gs.clearRect(0,  0, width, height);
		
		//set offset, if no offset, it maybe will be wrong
		int pixoff = 2;
		
		byte[] data = qrData.getBytes("gb2312");
		if(data.length > 0 && data.length < 120) {
			boolean[][] s = qrcode.calQrcode(data);
			for(int i = 0; i < s.length; i++) {
				for(int j = 0; j < s.length; j++) {
					if(s[i][j]) {
						gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
					}
				}
			}
		}
		
		gs.dispose();
		bufferedImage.flush();
		ImageIO.write(bufferedImage, "png", new File("E:\\httishere\\myCodeImage\\" + System.currentTimeMillis() + ".png"));
		
	}

}
