package MeetingSeverV2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeverListener implements ActionListener {

	//
	private MeetingSever_Two MeetSever;
	public SeverListener(MeetingSever_Two MeetSever) {
		// TODO Auto-generated constructor stub
		this.MeetSever=MeetSever;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//��ȡ�˾�����
		String command=e.getActionCommand();
		System.out.println("�������������յ��Ķ�������Ϊ��"+command);
		if("����ɫ".equals(command)) {
			MeetSever.changeStyle(1);
		}else if("ԭ��".equals(command)) {
			MeetSever.changeStyle(0);
		}
	}

}
