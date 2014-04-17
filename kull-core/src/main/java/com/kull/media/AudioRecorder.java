package com.kull.media;

import java.io.*;

import javax.sound.sampled.*;

public class AudioRecorder extends Thread {
	static TargetDataLine m_targetdataline;  
	  
    // 透過TargetDataLine介面(繼承自DataLine)與音效卡溝通 target目標  
  
    // 產生AudioFileFormat.Type類別的變數m_targetType Format格式  
    static AudioFileFormat.Type m_targetType;  
  
    // 產生AudioInputStream類別的變數m_audioInputStream stream流  
    static AudioInputStream m_audioInputStream;  
  
    static File m_outputFile;// 產生File類別的變數 m_outputFile  
  
    static ByteArrayOutputStream bos = new ByteArrayOutputStream();  
  
    static byte[] buf;  
  
    static boolean m_bRecording;// 後面需用到布林函數 True,False  
  
    private AudioRecorder(TargetDataLine line,  
            AudioFileFormat.Type targetType, File file) {  
        m_targetdataline = line;  
        m_audioInputStream = new AudioInputStream(line);  
        m_targetType = targetType;  
        m_outputFile = file;  
    }  
  
    public static void startRecording(String Filename) {  
       
        File outputFile = new File(Filename);  
  
        // 我們一開始先在主程式裡指定聲音檔的檔名為  
        // JDKAudioRecorder.wav  
        // String Filename = "JDKAudioRecord.wav ";  
        // 接著指定存檔的資料夾,預設存在相同的資料夾  
        // File outputFile = new File(Filename);  
  
        AudioFormat audioFormat = null;  
  
        // audioFormat = new  
        // AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100.0F, 16, 2, 4,  
        // 44100.0F, false);  
        audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F,  
                8, 1, 1, 44100F, false);  
        // 再來設定和取得音效檔的屬性  
        // audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,  
        // 44100.0F, 16, 2, 4, 44100.0F, false);  
  
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,  
                audioFormat);  
        TargetDataLine targetDataLine = null;  
  
        // 然後透過TargetDataLine介面(繼承自DataLine)與音效卡溝通  
        // DataLine.Info info = new DataLine.Info(TargetDataLine.class,  
        // audioFormat);  
        // 接著做例外處理,當聲音裝置出錯或其他因素導致錄音功能無法被執行時,程式將被終止  
  
        try {  
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);  
            targetDataLine.open(audioFormat);// try{ }可能發生例外的敘述  
  
        } catch (LineUnavailableException e)// catch{ }處理方法  
  
        {  
            System.out.println("無法錄音,錄音失敗 ");  
            e.printStackTrace();  
            System.exit(-1);  
        }  
  
        AudioFileFormat.Type targetType = AudioFileFormat.Type.AU;  
        AudioRecorder recorder = null;  
  
        recorder = new AudioRecorder(targetDataLine, targetType,  
                outputFile);  
        recorder.start();  
    }  
  
    public void start() {  
        m_targetdataline.start();  
        super.start();  
        System.out.println("recording...");  
    }  
  
    public static void stopRecording() {  
        m_targetdataline.stop();  
        m_targetdataline.close();  
        m_bRecording = false;  
        buf = bos.toByteArray();  
        System.out.println("stopped.");  
    }  
  
    public void run() {  
        try {  
            // AudioSystem.write(m_audioInputStream, m_targetType,  
            // m_outputFile);  
            AudioSystem.write(m_audioInputStream, m_targetType, bos);  
            System.out.println("after   write() ");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
     public static void main(String args[]) {  
        AudioRecorder.startRecording("d://test.wav");  
    }  
}
