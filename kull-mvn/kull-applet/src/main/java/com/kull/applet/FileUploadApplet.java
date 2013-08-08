package com.kull.applet;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.HashMap;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;






public abstract class FileUploadApplet extends BaseApplet {

	
	private String[] _fileSrcs;
	private String[] _fileNames;
	private String[] _strValues;
	private String[] _strNames;
	private String _action; 
	
	JButton _btnUpload;
	JLabel _lbResult;
	
	@Override
	public void init() { 
		// TODO Auto-generated method stub
		super.init();
		this.setLayout(new BorderLayout());
        this.setParameter();
		_btnUpload=new JButton(this.getParameter("btnText"));
		_btnUpload.addMouseListener(new BtnUploadHandler());
		_btnUpload.setVisible(true);
		this.add(_btnUpload);
		
	}
	
    private void setParameter(){
		this._fileSrcs=this.getParameter("fileSrcs").split(",");
		this._fileNames=this.getParameter("fileNames").split(",");
		this._strNames=this.getParameter("strNames").split(",");
		this._strValues=this.getParameter("strValues").split(",");
	    this._action=this.getParameter("action");
    }

    protected abstract void doHandleResult(String context);
    
	private final class BtnUploadHandler implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			String btnText=_btnUpload.getText();
            _btnUpload.setEnabled(false);
			_btnUpload.setText("上传中....");
           
            Map<String, String>  nvs=new HashMap<String, String>();
			for(int i=0;i<_strNames.length;i++){
				nvs.put(_strNames[i], _strValues[i]);
			}
			Map<String, String> nfs=new HashMap<String, String>();
			for(int i=0;i<_fileNames.length;i++){
				//File f=new File(_fileSrcs[i]);
				nfs.put(_fileNames[i], _fileSrcs[i]);
			}
			System.err.println("sss");
			String context=post(_action, nvs, nfs);
			doHandleResult(context);
			_btnUpload.setText(btnText);
			_btnUpload.setEnabled(true);
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
}
