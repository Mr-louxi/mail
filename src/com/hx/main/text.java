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
			// ��ȡ��Ƶ������
			AudioInputStream audioInputStream;
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("music/8.wav"));
			// ��ȡ��Ƶ�������
			AudioFormat audioFormat = audioInputStream.getFormat();
			// ������������
			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, audioFormat,
					AudioSystem.NOT_SPECIFIED);
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
					.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();

			/*
			 * ���������ж�ȡ���ݷ��͵�������
			 */
			int count;
			byte tempBuffer[] = new byte[1024];
			while ((count = audioInputStream.read(tempBuffer, 0,
					tempBuffer.length)) != -1) {
				if (count > 0) {
					sourceDataLine.write(tempBuffer, 0, count);
				}
			}

			// ������ݻ���,���ر�����
			sourceDataLine.drain();
			sourceDataLine.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
