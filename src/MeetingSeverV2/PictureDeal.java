package MeetingSeverV2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PictureDeal {

	//����ɫ�˾�Ч��
	public BufferedImage FanChaSe(BufferedImage buff) {
		//���������bufferImage�Ķ������RGBֵȡ��
		BufferedImage newBuff=new BufferedImage(buff.getWidth(), buff.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);		
		int[] r=new int[buff.getWidth()*buff.getHeight()];
		int[] rgb=buff.getRGB(0, 0, buff.getWidth(), buff.getHeight(), r, 0, 0);		
		newBuff.setRGB(0, 0, buff.getWidth(), buff.getHeight(), rgb, 0, 0);
		return newBuff;		
	}
	//�����˾�Ч��
	
}
