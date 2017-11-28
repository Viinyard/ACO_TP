package client;


import commands.PlayBackCommand;
import commands.SelectionCommand;
import commands.StartRecordCommand;
import commands.StopRecordCommand;
import invoker.IHM;
import invoker.Invoker;
import memento.CopyMemento;
import memento.CutMemento;
import memento.DeleteMemento;
import memento.Gardian;
import memento.InsertMemento;
import memento.PasteMemento;
import receiver.Moteur;
import receiver.MoteurImpl;

public class Client {

	
	public static void main(String[] args) {
		new Client();
	}
	
	public Client() {
		Moteur engine = new MoteurImpl();
		Invoker invoker = new IHM(System.in); // Typé IHM à cause de l'observer
		((MoteurImpl) engine).addObserver((IHM) invoker);
		//V1
//		ihm.addCommand("V", new PasteCommand(m));
//		ihm.addCommand("C", new CopyCommand(m));
//		ihm.addCommand("X", new CutCommand(m));
//		ihm.addCommand("I", new InsertCommand(m, ihm));
//		ihm.addCommand("S", new SelectionCommand(m, ihm));
//		ihm.addCommand("D", new DeleteCommand(m));
		
		//V2
		Gardian gardian = new Gardian();
		invoker.addCommand("V", new PasteMemento(engine, gardian));
		invoker.addCommand("C", new CopyMemento(engine, gardian));
		invoker.addCommand("X", new CutMemento(engine, gardian));
		invoker.addCommand("I", new InsertMemento(engine, invoker, gardian));
		invoker.addCommand("S", new SelectionCommand(engine, invoker));
		invoker.addCommand("D", new DeleteMemento(engine, gardian));
		
		invoker.addCommand("R", new StartRecordCommand(gardian));
		invoker.addCommand("Q", new StopRecordCommand(gardian));
		invoker.addCommand("P", new PlayBackCommand(gardian));
		
		//v3
		
		((IHM) invoker).beginLoop();
	}

}