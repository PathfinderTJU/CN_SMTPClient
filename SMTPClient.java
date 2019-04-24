package com.tju.edu.p03;

import java.net.*;
import java.util.Scanner;

import sun.misc.BASE64Encoder;

import java.io.*;

	/*
	 * ����ʵ���ʼ��ͻ��˵���Ҫ����
	 */
public class SMTPClient {
	
	private String receiver = null;
	private String sender = null;
	private String user = null;
	private String pass = null;
	private String subject = null;
	private String content = null;
	
	public SMTPClient() {
		
	}
	
	//�����ʼ��ĺ���
	public void sendMail() {
		try {
			//������SMTP���������׽��֣�SMTP�˿ں�Ϊ25
			Socket socket = new Socket("smtp.qq.com", 25);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writter = new PrintWriter(socket.getOutputStream(), true);
			Scanner scanner = new Scanner(System.in);
			
			String receiver = null;
			String sender = null;
			String user = null;
			String pass = null;
			String subject = null;
			String content = null;
			
			//�������Է������Ļ�Ӧ��Ϣ����ͬ
			System.out.println(reader.readLine());
			
			//����
			writter.println("HELO QQ");
			System.out.println(reader.readLine());
			
			//��¼
			writter.println("AUTH LOGIN");
			System.out.println(reader.readLine());
			
			//�����û�������>>��Ϊ�����У�
			System.out.println(">>Input username:");
			user = scanner.nextLine();
			writter.println(new BASE64Encoder().encode(user.getBytes()));//SMTP�û�������ʹ��BASE64����
			System.out.println(reader.readLine());
			
			//��������
			System.out.println(">>Input password:");
			pass = scanner.nextLine();
			writter.println(new BASE64Encoder().encode(pass.getBytes()));
			System.out.println(reader.readLine());
			
			//���뷢�ͷ������
			System.out.println(">>Input sender:");
			sender = scanner.nextLine();
			writter.println("MAIL FROM:<" + sender + ">");
			System.out.println(reader.readLine());
			
			//������շ������
			System.out.println(">>Input receiver:");
			receiver = scanner.nextLine();
			writter.println("RCPT TO:<" + receiver + ">");
			System.out.println(reader.readLine());
			
			//�����ʼ�����
			writter.println("data");
			System.out.println(reader.readLine());
			
			//��������
			System.out.println(">>Input subject:");
			subject = scanner.nextLine();
			writter.println("SUBJECT:" + subject);
			
			//�����ʼ�����ʾ�ķ����˺��ռ���
			writter.println("FROM:" + sender);
			writter.println("TO:" + receiver);
			
			//ָʾ����ʼ����ļ����ͺͱ���
			writter.println("Content-Type: text/plain; charset=\"utf-8\"");
			writter.println();
			
			//��������
			System.out.println(">>Input content:");
			content = scanner.nextLine();
			writter.println(content);
			
			//�����Ե���"."��Ϊ������־
			writter.println(".");
			System.out.println(reader.readLine());
			
			//�ر�����
			writter.println("QUIT");
			System.out.println(reader.readLine());
			
			scanner.close();
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//�����ʼ�����
	public void receiveMail() {
		try {
			//����POP3������
			Socket socket = new Socket("pop.163.com", 110);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 100000);
			PrintWriter writter = new PrintWriter(socket.getOutputStream(), true);
			Scanner scanner = new Scanner(System.in);
			
			String user = null;
			String pass = null;
			
			//���Է������Ļ�Ӧ����ͬ
			System.out.println(reader.readLine());
			
			//�����û�����POP3������ʹ��BASE64����
			System.out.println(">>Input username:");
			user = scanner.nextLine();
			writter.println("user " + user);
			System.out.println(reader.readLine());
			
			//��������
			System.out.println(">>Input password:");
			pass = scanner.nextLine();
			writter.println("pass " + pass);
			System.out.println(reader.readLine());
			
			//ѭ������ָ��
			while (true) {
				String command = null;
				System.out.println(">>Waiting command...");
				command = scanner.nextLine();
				
				//���ָ��Ϊquit���ж�����
				if (command.equals("quit")) {
					writter.println(command);
					break;
				//����ѭ�����ջ�Ӧ��POP3��list\retr�����Ӧ�Ե���"."��Ϊ������־
				}else if (command.split(" ")[0].equals("list") || command.split(" ")[0].equals("retr")){
					writter.println(command);
					String temp = null;
					while (!(temp = reader.readLine()).equals(".")) {
						System.out.println(temp);
					}
					System.out.println(temp);
				//dele����ֻ��һ�лظ�
				}else {
					writter.println(command);
					System.out.println(reader.readLine());
				}
			}
			
			//�˳�ѭ���ٽ���һ�λ�Ӧ
			String s = reader.readLine();
			System.out.println(s);
			scanner.close();
			socket.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
