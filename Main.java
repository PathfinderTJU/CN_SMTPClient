package com.tju.edu.p03;

import java.util.Scanner;

	/*
	 * 主函数
	 */
public class Main {
	public static void main(String args[]) {
		//使用Scanner类从控制台按行读入数据
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please choose SMTP/POP3:");
		String serviceType = scanner.nextLine();
		
		//根据参数选择服务类型
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
