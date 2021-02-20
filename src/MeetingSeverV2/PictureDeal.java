package MeetingSeverV2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PictureDeal {

	//反差色滤镜效果
	public BufferedImage FanChaSe(BufferedImage buff) {
		//即对输入的bufferImage的对象进行RGB值取反
		BufferedImage newBuff=new BufferedImage(buff.getWidth(), buff.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);		
		int[] r=new int[buff.getWidth()*buff.getHeight()];
		int[] rgb=buff.getRGB(0, 0, buff.getWidth(), buff.getHeight(), r, 0, 0);		
		newBuff.setRGB(0, 0, buff.getWidth(), buff.getHeight(), rgb, 0, 0);
		return newBuff;		
	}
	//熔铸滤镜效果
	
}
