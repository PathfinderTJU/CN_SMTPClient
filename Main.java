package com.tju.edu.p03;

import java.util.Scanner;

	/*
	 * ������
	 */
public class Main {
	public static void main(String args[]) {
		//ʹ��Scanner��ӿ���̨���ж�������
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please choose SMTP/POP3:");
		String serviceType = scanner.nextLine();
		
		//���ݲ���ѡ���������
		if (serviceType.equals("SMTP")) {
			SMTPClient client = new SMTPClient();
			client.sendMail();
		}else if (serviceType.equals("POP3")) {
			SMTPClient client = new SMTPClient();
			client.receiveMail();
		}else {
			System.out.println("Connect failed: Illegal Arguments.");
		}
		
		scanner.close();
	}
}
