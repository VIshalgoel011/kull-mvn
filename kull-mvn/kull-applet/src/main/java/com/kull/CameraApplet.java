package com.kull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;



public class CameraApplet extends BaseApplet {

	private JPanel jContentPane = null;
	private JButton btnCapture=null,btnSave;
	 
	Player player = null;
	private Buffer buf = null;
	 private BufferToImage btoi = null;
	 private Image img = null;
	 
	
	public CameraApplet() {
	super();
	}
	 
	public void init() {
	this.setSize(320, 240);
	this.setContentPane(getJContentPane());
	this.setName("Camera");
	this.btnCapture=new JButton("采集");
	this.btnCapture.addMouseListener(new CaptureMouseListener());
	}

	public class CaptureMouseListener implements java.awt.event.MouseListener{

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		     FrameGrabbingControl fgc = (FrameGrabbingControl) player
                     .getControl(
            "javax.media.control.FrameGrabbingControl");
       buf = fgc.grabFrame(); // 转化流格式
btoi = new BufferToImage((VideoFormat) buf.getFormat());
img = btoi.createImage(buf); // 显示抓取图片
//imgpanel.setImage(img); // 设置imgpanel图片属性
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * ハードウエアリストを取得
	 */
	private CaptureDeviceInfo[] getDevices() {
	 Vector devices = CaptureDeviceManager.getDeviceList(null);
	 CaptureDeviceInfo[] info = new CaptureDeviceInfo[devices.size()];
	 for (int i = 0; i < devices.size(); i++) {
	 info[i] = (CaptureDeviceInfo) devices.get(i);
	 }
	 return info;
	}

	/**
	 * ビデオ設備を取得
	 */
	private CaptureDeviceInfo[] getVideoDevices() {
	CaptureDeviceInfo[] info = getDevices();
	CaptureDeviceInfo[] videoDevInfo;
	Vector<CaptureDeviceInfo> vc = new Vector<CaptureDeviceInfo>();
	for (int i = 0; i < info.length; i++) {
	Format[] fmt = info[i].getFormats();
	for (int j = 0; j < fmt.length; j++) {
	if (fmt[j] instanceof VideoFormat) {
	vc.add(info[i]);
	}
	break;
	}
	}
	videoDevInfo = new CaptureDeviceInfo[vc.size()];
	for (int i = 0; i < vc.size(); i++) {
	videoDevInfo[i] = (CaptureDeviceInfo) vc.get(i);
	}

	return videoDevInfo;
	}

	/**
	 * Cameraを表示
	 */
	private JPanel getJContentPane() {
	if (jContentPane == null) {
	BorderLayout borderLayout = new BorderLayout();
	jContentPane = new JPanel();
	jContentPane.setLayout(borderLayout);
	  
	MediaLocator ml = null;
	
	try {
	// １つビデオを取得
	ml = getVideoDevices()[0].getLocator();
	// Playerを構造
	player = Manager.createRealizedPlayer(ml);
	player.start();
	Component comp = player.getVisualComponent();
    
	if (comp != null) {
	// 画面に表示
	jContentPane.add(comp, BorderLayout.EAST);
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	return jContentPane;
	}

	
	

}
