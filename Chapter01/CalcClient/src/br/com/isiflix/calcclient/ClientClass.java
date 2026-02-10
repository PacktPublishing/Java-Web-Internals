package br.com.isiflix.calcclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import io.isiflix.calc.Request;
import io.isiflix.calc.Response;

public class ClientClass {
	public static void main(String[] args) {
		try {
			// step 1
			Scanner scanner = new Scanner(System.in);
			String oper;
			Double op1, op2=null;
			oper = scanner.nextLine();
			op1 = Double.parseDouble(scanner.nextLine());
			// step 1.1
			switch(oper) {
			case "+":
			case "-":
			case "*":
			case "/":
			case "^":
				op2 = Double.parseDouble(scanner.nextLine());
				break;
			}
			// step 2
			Request req = new Request(op1, op2, oper);
			// step 3
			Socket socket = new Socket("localhost", 8350);
			System.out.println("Connecting on server");
			// step 4
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(req);
			
			// step 5
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Response rep = (Response)in.readObject();
			// step 6
			System.out.println(rep);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
