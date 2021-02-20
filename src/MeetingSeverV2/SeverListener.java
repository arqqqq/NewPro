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
		//获取滤镜类型
		String command=e.getActionCommand();
		System.out.println("动作监听器接收到的动作类型为："+command);
		if("反差色".equals(command)) {
			MeetSever.changeStyle(1);
		}else if("原画".equals(command)) {
			MeetSever.changeStyle(0);
		}
	}

}
