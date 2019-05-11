package de.fh_zwickau.oose.zuul.view;

import de.fh_zwickau.oose.zuul.model.NPC;

/**
 * Class representing the npc's view (that is, the npc's model)
 * @author salavat
 *
 */
public class NPCView extends MovingEntityView {

 	public NPCView(NPC npc){
		super(npc, 1);
		relocate();
		
	}

 	public String getDialogText(){
 		return ((NPC)entity).getDialogText();
 	}

 	public NPC getNps(){
 		return (NPC)entity;
 	}

 	/**
 	 * sets the direction of the npc to the given coordinate
 	 * @param x1
 	 * @param y1
 	 */
 	public void setNpsDirectionToPlayer(double x1, double y1){
 		double degree;
		double x2 = entity.getX();
		double y2 = entity.getY();
		degree = Math.atan2((y2-y1), (x2-x1));
		degree = Math.toDegrees(degree);
		entity.setDirectionsToFalse();
		setDirection(degree);

	}

 	/**
 	 * sets the direction of the npc depending on the transmitted angle
 	 * @param degree
 	 */
 	private void setDirection(double degree){
 		if(degree>=-45 && degree<=45 ){
			entity.getDirections()[3] = true;
		}else if(degree > 45 && degree<=135){
			entity.getDirections()[0] = true;
		}else if((degree >135 && degree<=180) || (degree<=-135 && degree>=-180)){
			entity.getDirections()[2] = true;
		}else if((degree >-135 && degree<-45) || (degree<=-135 && degree>-180)){
			entity.getDirections()[1] = true;
		}
 	}


}
