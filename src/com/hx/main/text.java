package com.hx.main;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class text {
	public static void main(String[] args) {
		try {
			// 获取音频输入流
			AudioInputStream audioInputStream;
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("music/8.wav"));
			// 获取音频编码对象
			AudioFormat audioFormat = audioInputStream.getFormat();
			// 设置数据输入
			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, audioFormat,
					AudioSystem.NOT_SPECIFIED);
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
					.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();

			/*
			 * 从输入流中读取数据发送到混音器
			 */
			int count;
			byte tempBuffer[] = new byte[1024];
			while ((count = audioInputStream.read(tempBuffer, 0,
					tempBuffer.length)) != -1) {
				if (count > 0) {
					sourceDataLine.write(tempBuffer, 0, count);
				}
			}

			// 清空数据缓冲,并关闭输入
			sourceDataLine.drain();
			sourceDataLine.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
