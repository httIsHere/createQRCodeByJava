package codeLogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodewithLogo {
	private static final int QRCOLOR = 0xFF000000; // black
	private static final int BGCOLOR = 0xFFFFFFFF; // white
	
	public static void main(String[] args) throws WriterException {
		try {
			getLogoQRCode("http://www.musicren.com/qydt/qydt_QRList_Web.html?openid=ocMBCuL5McBv7V1_2n2fnBVpme9s", "create new qrcode");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * create qrcode with logo
	 * @param qrPic
	 * @param logoPic
	 * 
	 */
	public static String getLogoQRCode(String qrUrl, String message) {
		//logo pic path
		String filePath = Class.class.getClass().getResource("/").getPath() + "resource/images/logoImages/logo2.jpg";
		System.out.println(filePath);
		
		//qrcode content
		String content = qrUrl;
		
		try {
			QRCodewithLogo code = new QRCodewithLogo();
			BufferedImage bim = code.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 400, 400, code.getDecodeHintType());
			return code.addLogo_QRCode(bim, new File(filePath), new LogoConfig(), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * add logo on qrcode
	 * @param qrPic
	 * @param logoPic
	 */	
	public String addLogo_QRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig, String message) {
		try {
			/*
			 * read qrcode and create draw object
			 */
			BufferedImage image = bim;
			Graphics2D g = image.createGraphics();
			
			/*
			 * read logo pic
			 */
			BufferedImage logo = ImageIO.read(logoPic);
			
			/*
			 * set the size of logo, about 20% of qrcode
			 */
			int widthLogo = logo.getWidth(null) > image.getWidth() * 3 / 10 ? (image.getWidth() * 3 / 10) : logo.getWidth(null);
			int heightLogo = logo.getHeight(null) > image.getHeight() * 3 / 10 ? (image.getHeight() * 3 / 10) : logo.getHeight(null);
			
			/*
			 * let logo on the center
			 */
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - heightLogo) / 2;
			
			/*
			 * if logo on the right bottom
			 * int x = (image.getWidth() - widthLogo);
			 * int y = (image.getHeight() - heightLogo);
			 */
			
			//draw
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.dispose();
			
			//add some message (not too long)
			if(message != null && !message.equals("")) {
				BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D outg = outImage.createGraphics();
				//draw qrcode on a new canvas
				outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
				//write message on the new canvas
				outg.setColor(Color.BLACK);
				outg.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 30));//font family, font weight, font size
				int strWidth = outg.getFontMetrics().stringWidth(message);
				if(strWidth > 399) {
					//if message is too long, it should linefeed
					String message1 = message.substring(0, message.length() / 2);
					String message2 = message.substring(message.length() / 2, message.length());
					int strWidth1 = outg.getFontMetrics().stringWidth(message1);
					int strWidth2 = outg.getFontMetrics().stringWidth(message2);
					outg.drawString(message1, 200 - strWidth1 / 2, image.getHeight() + (outImage.getHeight() - image.getHeight()) / 2 + 12);
					
					BufferedImage outImage2 = new BufferedImage(400, 405, BufferedImage.TYPE_4BYTE_ABGR);
					Graphics2D outg2 = outImage2.createGraphics();
					outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
					outg2.setColor(Color.BLACK);
					outg2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 30));//font family, font weight, font size
					outg2.drawString(message2, 200 - strWidth2 / 2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight())/2 + 5);
					outg2.dispose();
					
					outImage2.flush();
					outImage = outImage2;
				} else {
					outg.drawString(message, 200  - strWidth/2 , image.getHeight() + (outImage.getHeight() - image.getHeight())/2 + 12);
				}
				outg.dispose();
				outImage.flush();
				image = outImage;
			}
			logo.flush();
			image.flush();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.flush();
			ImageIO.write(image, "png", baos);
			
			ImageIO.write(image, "png", new File("E:\\httishere\\myCodeImage\\Logo_" + System.currentTimeMillis() + ".png"));
			
			//get image base64 string
			//String imageBase64QRCode = Base64.encode(baos.toByteArray());
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * int qrcode
	 * @param bm
	 * @return
	 */
	public BufferedImage fileToBufferedImage(BitMatrix bm) {
		BufferedImage image = null;
		try {
			int w = bm.getWidth(), h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			
			for(int x = 0; x < w; x ++) {
				for(int y = 0; y < h; y ++) {
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
	
	/*
	 * create qrcode bufferedImage image
	 * @param content
	 * @param barcodeFormat
	 * @param width
	 * @param height
	 * @param hints
	 * @return
	 */
	public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height,
			Map<EncodeHintType, Object> hints){
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bMatrix = null;
		BufferedImage image = null;
		try {
			multiFormatWriter = new MultiFormatWriter();
			//the params: content > barcodeFormat > width > height > hints
			bMatrix = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
			int w = bMatrix.getWidth();
			int h = bMatrix.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			
			//use qrcode data create Bitmap image, set black(0xFF000000) and white(0xFFFFFFFF)
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bMatrix.get(x, y) ? QRCOLOR : BGCOLOR);
				}
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	/*
	 * set qrcode format params
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<EncodeHintType, Object> getDecodeHintType() {
		//hints
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		//set qrcode error class
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		//set encode way
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 0);
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);		
		
		return hints;
	}
	
	// logo config
	static class LogoConfig {
		//logo default border color
		public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
		//logo default border width
		public static final int DEFAULT_BORDER = 2;
		//logo's size is 1/5 of image
		public static final int DEFAULT_LOGOPART = 5;
		
		private final int border = DEFAULT_BORDER;
		private final Color borderColor;
		private final int logoPart;
		
		/*
		 * create a default config with on color and off color,
		 * generating normal black-on-white barcodes.
		 */
		public LogoConfig() {
			this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
		}

		public LogoConfig(Color defaultBordercolor, int defaultLogopart) {
			this.borderColor = defaultBordercolor;
			this.logoPart = defaultLogopart;
		}
		
		public Color getBorderColor() {
			return this.borderColor;
		}
		
		public int getBorder() {
			return this.border;
		}
		
		public int getLogoPart() {
			return this.logoPart;
		}
		
	}
}
