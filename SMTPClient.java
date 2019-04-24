package com.tju.edu.p03;

import java.net.*;
import java.util.Scanner;

import sun.misc.BASE64Encoder;

import java.io.*;

	/*
	 * 用来实现邮件客户端的主要功能
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
	
	//发送邮件的函数
	public void sendMail() {
		try {
			//建立与SMTP服务器的套接字，SMTP端口号为25
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
			
			//用来回显服务器的回应消息，后同
			System.out.println(reader.readLine());
			
			//握手
			writter.println("HELO QQ");
			System.out.println(reader.readLine());
			
			//登录
			writter.println("AUTH LOGIN");
			System.out.println(reader.readLine());
			
			//输入用户名（带>>的为命令行）
			System.out.println(">>Input username:");
			user = scanner.nextLine();
			writter.println(new BASE64Encoder().encode(user.getBytes()));//SMTP用户名密码使用BASE64加密
			System.out.println(reader.readLine());
			
			//输入密码
			System.out.println(">>Input password:");
			pass = scanner.nextLine();
			writter.println(new BASE64Encoder().encode(pass.getBytes()));
			System.out.println(reader.readLine());
			
			//输入发送方邮箱号
			System.out.println(">>Input sender:");
			sender = scanner.nextLine();
			writter.println("MAIL FROM:<" + sender + ">");
			System.out.println(reader.readLine());
			
			//输入接收方邮箱号
			System.out.println(">>Input receiver:");
			receiver = scanner.nextLine();
			writter.println("RCPT TO:<" + receiver + ">");
			System.out.println(reader.readLine());
			
			//输入邮件内容
			writter.println("data");
			System.out.println(reader.readLine());
			
			//输入主题
			System.out.println(">>Input subject:");
			subject = scanner.nextLine();
			writter.println("SUBJECT:" + subject);
			
			//输入邮件中显示的发件人和收件人
			writter.println("FROM:" + sender);
			writter.println("TO:" + receiver);
			
			//指示解读邮件的文件类型和编码
			writter.println("Content-Type: text/plain; charset=\"utf-8\"");
			writter.println();
			
			//输入内容
			System.out.println(">>Input content:");
			content = scanner.nextLine();
			writter.println(content);
			
			//内容以单行"."作为结束标志
			writter.println(".");
			System.out.println(reader.readLine());
			
			//关闭连接
			writter.println("QUIT");
			System.out.println(reader.readLine());
			
			scanner.close();
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//接收邮件方法
	public void receiveMail() {
		try {
			//连接POP3服务器
			Socket socket = new Socket("pop.163.com", 110);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 100000);
			PrintWriter writter = new PrintWriter(socket.getOutputStream(), true);
			Scanner scanner = new Scanner(System.in);
			
			String user = null;
			String pass = null;
			
			//回显服务器的回应，下同
			System.out.println(reader.readLine());
			
			//输入用户名，POP3服务不用使用BASE64加密
			System.out.println(">>Input username:");
			user = scanner.nextLine();
			writter.println("user " + user);
			System.out.println(reader.readLine());
			
			//输入密码
			System.out.println(">>Input password:");
			pass = scanner.nextLine();
			writter.println("pass " + pass);
			System.out.println(reader.readLine());
			
			//循环输入指令
			while (true) {
				String command = null;
				System.out.println(">>Waiting command...");
				command = scanner.nextLine();
				
				//如果指令为quit，中断连接
				if (command.equals("quit")) {
					writter.println(command);
					break;
				//否则，循环接收回应，POP3的list\retr命令回应以单行"."作为结束标志
				}else if (command.split(" ")[0].equals("list") || command.split(" ")[0].equals("retr")){
					writter.println(command);
					String temp = null;
					while (!(temp = reader.readLine()).equals(".")) {
						System.out.println(temp);
					}
					System.out.println(temp);
				//dele命令只有一行回复
				}else {
					writter.println(command);
					System.out.println(reader.readLine());
				}
			}
			
			//退出循环再接收一次回应
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
