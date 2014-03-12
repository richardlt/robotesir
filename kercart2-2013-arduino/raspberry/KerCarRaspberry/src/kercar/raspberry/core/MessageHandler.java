package kercar.raspberry.core;

import kercar.comAPI.CMDMissionMessage;
import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDTurnMessage;
import kercar.comAPI.CMDPhotoMessage;
import kercar.comAPI.IMessage;
import kercar.comAPI.Message;
import kercar.raspberry.arduino.message.GetAngle;
import kercar.raspberry.arduino.message.GetPos;
import kercar.raspberry.arduino.message.IArduinoMessage;

public class MessageHandler {
	private IIA ia;
	
	public MessageHandler(IIA ia) {
		this.ia = ia;
	}

	public void handle(IMessage message)
	{
		System.out.println("Message received (Handler)");
		this.ia.setBlocked(false);
		if (message.getType() == Message.CMD_MOVE)
		{
			Core.Log("MessageHandler : CMD_MOVE");
			CMDMoveMessage move = new CMDMoveMessage((Message)message);
			if(move.isBackward()){
				System.out.println("MessageHandler : BACKWARD");
				Core.Log("MessageHandler : BACKWARD");
				this.ia.stopMission();
				this.ia.backward(move.getSpeed());
			}
			else{
				System.out.println("Going forward (Handler)");
				Core.Log("MessageHandler : FORWARD");
				this.ia.stopMission();
				this.ia.forward(move.getSpeed());
			}
		}
		else if (message.getType() == Message.CMD_TURN)
		{
			Core.Log("MessageHandler : CMD_TURN");
			System.out.println("MessageHandler : CMD_TURN");
			CMDTurnMessage turn = new CMDTurnMessage((Message)message);
			this.ia.stopMission();
			if (turn.isTurningRight())
			{
				Core.Log("RIGHT");
				this.ia.turnRight();
			}
			else {
				Core.Log("LEFT");
				this.ia.turnLeft();
			}			
		}
		else if (message.getType() == Message.CMD_STOP)
		{		
			Core.Log("MessageHandler : CMD_STOP");
			System.out.println("MessageHandler : CMD_STOP");
			this.ia.stopMission();
			this.ia.stopKercar();
		}
		else if (message.getType() == Message.CMD_MISSION) {
			Core.Log("MessageHandler : CMD_MISSION");
			System.out.println("MessageHandler : CMD_MISSION");
			this.ia.stopMission();
//			this.ia.stopKercar();
			CMDMissionMessage mission = new CMDMissionMessage((Message)message);
			this.ia.launchMission(mission.getCoordinates(), mission.getMailAddress(), 75, mission.getPhoto());
		}
		else if (message.getType() == Message.CMD_PHOTO) {
			Core.Log("MessageHandler : CMD_PHOTO");
			System.out.println("MessageHandler : CMD_PHOTO");
			CMDPhotoMessage msg = new CMDPhotoMessage((Message)message);
			this.ia.takePhoto(msg.getMailAddress());
		}
	}
	
	public void handle(IArduinoMessage message) {
		Core.Log("MessageHandler : Message from Arduino");
		System.out.println("MessageHandler : Message from Arduino");
		System.out.println("ID " + message.getID());
		if(message.getID() == IArduinoMessage.RECEIVE_BLOCK) {
			Core.Log("MessageHandler : RECEIVE_BLOCK");
			System.out.println("MessageHandler : RECEIVE_BLOCK");
			this.ia.stopMission();
			this.ia.setBlocked(message.getParam(0) == 1);
			//this.ia.getServletQueue().add(new Stop());
		} 
		else if(message.getID() == IArduinoMessage.RECEIVE_ANGLE) {
			Core.Log("MessageHandler : RECEIVE_ANGLE");
			System.out.println("MessageHandler : RECEIVE_ANGLE");
		//	GetAngle angle = (GetAngle) message;
			this.ia.setAngle(message.getParam(0));
		}
		else if(message.getID() == IArduinoMessage.RECEIVE_POS) {
			Core.Log("MessageHandler : RECEIVE_POS");
			System.out.println("MessageHandler : RECEIVE_POS");
			
			this.ia.setLongitude(message.getParam(0));
			this.ia.setLatitude(message.getParam(1));
		} else if(message.getID() == IArduinoMessage.OK) {
			Core.Log("MessageHandler : OK");
			System.out.println("MessageHandler : OK");
		} 
	}
}
