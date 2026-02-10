package br.com.isiflix.calcserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import io.isiflix.calc.Request;
import io.isiflix.calc.Response;

public class ServerClass {
	public static void main(String[] args) {
		try {
			// step 1
			ServerSocket serverSocket = new ServerSocket(8350);
			// step 2
			while (true) {
				// step 2.1
				Socket socket = serverSocket.accept();
				System.out.println("New connection!!");

				// step 2.2
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Request req = (Request)in.readObject();
				
				System.out.println(req);
				// step 2.3
				Response rep = new Response();
				// step 2.4 - handling data				
				switch(req.getOper()) {
				case "+": // step 2.5
					rep.setStatus("Ok");
					rep.setValue(req.getOp1()+ req.getOp2());
					break;
				case "-": // step 2.5
					rep.setStatus("Ok");
					rep.setValue(req.getOp1()- req.getOp2());
					break;
				case "*": // step 2.5
					rep.setStatus("Ok");
					rep.setValue(req.getOp1()* req.getOp2());
					break;
				case "^": // step 2.5
					// step 2.5
					rep.setValue(Math.pow(req.getOp1(), req.getOp2()));
				    break;
				case "sqrt":
					// step 2.5
					if (req.getOp1() >= 0) {  
						rep.setStatus("Ok");
						rep.setValue(Math.sqrt(req.getOp1()));
					}
					// step 2.6
					else {  
						rep.setStatus("Invalid");
					}
					break;
				case "/":
					// step 2.5
					if (req.getOp2() != 0) {
						rep.setStatus("Ok");
						rep.setValue(req.getOp1() / req.getOp2());
					}
					// step 2.6
					else {
						rep.setStatus("Invalid");
					}
					break;
				default:
					// step 2.7
					rep.setStatus("Unsupported");
				}
					
				// step 2.8
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(rep);
				socket.close();				
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
