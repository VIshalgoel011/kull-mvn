package com.kull;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.media.*;
import javax.media.format.*;
import javax.media.util.*;
import javax.media.control.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import jmapps.util.*;

import com.kull.BaseApplet;
import com.sun.image.codec.jpeg.*;
import javax.media.protocol.DataSource;
import java.applet.AppletContext;


public class WebCamApplet extends BaseApplet implements ActionListener {
    public static Player player = null;
    private CaptureDeviceInfo di = null;
    private MediaLocator ml = null;
    String insid = null;
    String taxpayerid = null;
    String affID = null;
    String affname = null;
    String wfid = null;
    String username = null;
    String aid = null;
    String tid = null;
    /**
     * 按钮
     */
    private JButton capture = null;
    /**
     * 按钮
     */
    private JButton save = null;
    private JTextField num = null;
    private Buffer buf = null;
    private Image img = null;
    // private VideoFormat vf = null;
    private BufferToImage btoi = null;
    private ImagePanel imgpanel = null;
    /**
     * 选取x,y,width,height默值
     */
    private int rectX;
    private int rectY;
    private int rectWidth = 320;
    private int rectHeight = 240;
    private int imgWidth = 320;
    private int imgHeight = 240;

    /**
     * 默媳募
     */
    private String fname = "工作流附件程序";
    public void init() {
        insid = this.getParameter("insid");
        taxpayerid = this.getParameter("taxpayerid");
        affID = this.getParameter("affID");
        affname = this.getParameter("affname");
        wfid = this.getParameter("wfid");
        username = this.getParameter("username");
        aid = this.getParameter("aid");
        tid = this.getParameter("tid");

        setLayout(new BorderLayout());
        //setSize(320, 240);
        imgpanel = new ImagePanel();
        
        capture = new JButton("拍照");
        capture.addActionListener(this);
        save = new JButton("上传该附件");
        save.addActionListener(this);
        //num = new JTextField();
        String str1 = "vfw:Logitech USB Video Camera:0";
        String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
        //从驱动管理器中获取驱动,一般使用MicroOS默认的驱动，str1为罗技的摄像头驱动
        di = CaptureDeviceManager.getDevice(str2);
        //获得本地媒体源
        ml = di.getLocator();
        try {
            //通过获取的本地源建立播放者对象
            DataSource dataSource;
            //player对象可以使用Manager.createRealizedPlayer(di.getLocator())直接构造，
            
            player = Manager.createRealizedPlayer(di.getLocator());
            //dataSource = JMFUtils.createCaptureDataSource(null, null, str2, di.getFormats()[4]);
            //如果自行设置格式应当使用DataSource对象构建，其中DataSource对象位置为javax.media.protocol.DataSource;
            //player = Manager.createRealizedPlayer(dataSource);
            //播放者开始播放
            player.start();
            Panel panelx2 = new Panel(new GridLayout(1, 2));
            Component comp;
            if ((comp = player.getVisualComponent()) != null) {
                //增加播放源在窗口上
                panelx2.add(comp);
                //comp.setSize(new Dimension(320, 240));
            }
            Panel panelx = new Panel(new GridLayout(1, 5));
            Panel panel1 = new Panel(new BorderLayout());
            panelx.add(new Panel());
            panelx.add(capture);
            panelx.add(new Panel());
            panel1.add(new Label("222222---"+username), BorderLayout.WEST);
            //panel1.add(num, BorderLayout.CENTER);
            panelx.add(save);
            panelx.add(new Panel());
            panel1.add(panelx2, BorderLayout.NORTH);
            panel1.add(panelx, BorderLayout.SOUTH);
            add(panel1, BorderLayout.CENTER);
            panelx2.add(imgpanel);
            //add(panelx2,BorderLayout.NORTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    @Override
	public void destroy() {
		// TODO Auto-generated method stub
		playerclose();
	}



	public static void main(String[] args) throws ClassNotFoundException {
        try {
            URL url = new URL(
                    "http://webserver:7001/Workflow/servlet/WebCang");
            URLConnection urlConn = url.openConnection();
            urlConn.setUseCaches(false);
            urlConn.setDefaultUseCaches(false);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 乇头
     *
     */
    public static void playerclose() {
        player.close();
        player.deallocate();
    }
    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        JComponent c = (JComponent) e.getSource();
        if (c == capture) { // 抓取当前frame
            FrameGrabbingControl fgc = (FrameGrabbingControl) player
                                       .getControl(
                    "javax.media.control.FrameGrabbingControl");
            buf = fgc.grabFrame(); // 转化流格式
            btoi = new BufferToImage((VideoFormat) buf.getFormat());
            img = btoi.createImage(buf); // 显示抓取图片
            imgpanel.setImage(img); // 设置imgpanel图片属性
        } else if (c == save) {
            if (img != null) {
                //fname = !num.getText().equals("") ? num.getText() : "temp";
                fname = "test";
                    saveJPG(img, "c:\\load\\WorkflowPhoto\\" , fname + ".jpg");
                //WebCangFile.saveJPG(img,"f:/"+ fname + ".jpg");
            }
        }
    }
//为图片增加声明
    public void creatMark(String imagePath, String userName) {
        ImageIcon imgIcon = new ImageIcon(imagePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null);
        int height = theImg.getHeight(null) + 30;
        try {
            BufferedImage bimage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_BGR);
            Graphics2D g = bimage.createGraphics();
            // 设置填充背景为白色
            g.setBackground(Color.DARK_GRAY);
            g.clearRect(0, 0, width, height);
            // 设置字体颜色
            g.setColor(Color.white);
            // 绘制指定图像的当前可用部分
            g.drawImage(theImg, 0, 15, null);
            SimpleDateFormat formatter_time = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");
            String up_time = formatter_time.format(new java.util.Date());
            // 使用该图形上下文的当前字体和颜色，绘制由指定的字符串给出的文本
            g.drawString(("本电子图片由" + userName + "于:" + up_time +
                          "上传，版权归属国家税务局所有."), 10, 15); // 添加文字
            // 撤消该图形的上下文并释放它所使用的任何系统资源。在 dispose 方法被调用后， Graphics 对象将不能被使用
            g.dispose();
            // 图片输出
            FileOutputStream image_out = new FileOutputStream(imagePath); // "f:/deploy/Workflow/aa.jpg"
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(image_out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(50f, true);
            encoder.encode(bimage, param);
            image_out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
      * 将byte数组转换为表示16进制值的字符串
      * 和public static String hexStr2ByteArr(String strIn)
      * 互为可逆的转换过程
      * @param bytes 需要转换的byte数组
      * @return 转换后的字符串
      * @throws Exception 本方法不处理任何异常，所有异常全部抛出
      * @author <a href="mailto:c_r_zhu@hotmail.com">PeterZhu</a>
      */
     public static final String encodeHex(byte[] bytes) {
                 StringBuffer buf = new StringBuffer(bytes.length * 2);
                 int i;
                 for (i = 0; i < bytes.length; i++) {
                         if (((int) bytes[i] & 0xff) < 0x10) {
                                 buf.append("0");
                         }
                         buf.append(Long.toString((int) bytes[i] & 0xff, 16));
                 }
                 return buf.toString();
         }
     /**
      * 将表示16进制值的字符串转换为byte数组，
      * 和public static String encodeHex(byte[] arrB)
      * 互为可逆的转换过程
      * @param strIn 需要转换的字符串
      * @return 转换后的byte数组
      * @throws Exception 本方法不处理任何异常，所有异常全部抛出
      * @author <a href="mailto:c_r_zhu@hotmail.com">PeterZhu</a>
      */
     public static byte[] hexStr2ByteArr(String strIn)
         throws Exception
     {
         byte[] arrB = strIn.getBytes();
         int iLen = arrB.length;
   //两个字符表示一个字节，所以字节数组长度是字符串长度除以2
         byte[] arrOut = new byte[iLen / 2];
         for (int i = 0; i < iLen; i = i + 2)
         {
             String strTmp = new String(arrB, i, 2);
             arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
         }
         return arrOut;
     }

    /**
     * 图
     *
     * @param img
     * @param s
     */
    public void saveJPG(Image img, String s,String filename) {
        BufferedImage bi = (BufferedImage) createImage(img.getWidth(null),
                img.getHeight(null));
       File filedir = new File(s);
        if (!filedir.exists()) {
                 filedir.mkdirs(); //新建目录
        }
        System.out.println("ghf----" + img.getWidth(null) + "    hgf----" +
                           img.getHeight(null));
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, null, null);
        //图像输出,保存部分
        File f = new File(s+filename);
        if (f.exists()){
            f.delete();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
        } catch (java.io.FileNotFoundException io) {
            System.out.println("File Not Found");
        }
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
        param.setQuality(1f, false);
        encoder.setJPEGEncodeParam(param);
        try {
            encoder.encode(bi);
            out.close();
            creatMark(s+filename, username);
        } catch (java.io.IOException io) {
            System.out.println("IOException");
        }
        /*AppletContext ac = getAppletContext();
        try {
            ac.showDocument(new URL(
                    "http://webserver:7001/Workflow/custom/public/affiliatedInfo_upload_main.jsp?"
                    + "ls_workflow_type=affupload&"
                    + "ls_wfid=b120&"
                    + "li_insid=134020000006559&"
                    + "ls_taxpayerID=340211790123101&"
                    + "ls_affID=FB-0001&"
                    + "ls_affName=aaaaaaaaaaaaa&"
                    + "path=" + s));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }*/
    String filestr ="" ;
    File file = new File(s+filename);
    FileInputStream fin = null;
    try {
        fin = new FileInputStream(file);
    } catch (FileNotFoundException ex) {
    }
    byte[] byt = new byte[4096];
    int bb;
    try {
        while ((bb = fin.read(byt, 0, 4096)) != -1) {
            filestr = filestr + encodeHex(byt);
        }
    } catch (IOException ex1) {
    }
        try {
            java.net.URL url = new java.net.URL(
                    "http://webserver:7001/Workflow/servlet/WebCang_servlet?insid="
                    + insid +"&affID="
                    + affID +"&affName="
                    + affname +"&filestr="
                    +filestr);
            java.net.URLConnection con = url.openConnection();
            con.setUseCaches(true);
            con.setDoOutput(true);
            con.setDoInput(true);
            //con.setRequestProperty("Content-type", "application/octest-stream");
            /*FileInputStream fin = null;
            File file = new File(s);
            fin = new FileInputStream(file);
            System.out.println("fin.available()------------"+fin.available());
            DataOutputStream dataout = new DataOutputStream(con.getOutputStream());
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = fin.read(buffer, 0, 8192)) != -1) {
                dataout.write(buffer, 0, bytesRead); //将文件写入Stream
            }*/
     DataOutputStream dataout = new DataOutputStream(con.getOutputStream());
            dataout.flush();
            dataout.close();
            System.out.println(dataout.size());
            DataInputStream in = new DataInputStream(con.getInputStream());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    class ImagePanel extends Panel 
    { 
      public Image myimg = null; 
       
      public ImagePanel() 
      { 
        setLayout(null); 
        setSize(320,240); 
      } 
       
      public void setImage(Image img) 
      { 
        this.myimg = img; 
        repaint(); 
      } 
       
      public void paint(Graphics g) 
      { 
        if (myimg != null) 
        { 
          g.drawImage(myimg, 0, 0, this); 
        } 
         
      } 
    }
    
}