import java.net.Socket;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamStreamer;


public class MjpegStreamingExample {

	public static void main(String[] args) throws InterruptedException {
		Webcam w = Webcam.getDefault();
		new WebcamStreamer(9999, w, 100, true);
		do {
			Thread.sleep(5000);
		} while (true);
	}
}
