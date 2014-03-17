package tree.model;

import java.util.LinkedList;
import java.util.List;

import main.Main;

public class ForceXPosition {
	static private class ForceNode{
		public List<Person> nodePerson;
		
		public List<ForceNode> connections = new LinkedList<ForceNode>();
		
		public List<ForceNode> sameGeneration;
		
		public int lastForce = 0;
		
		public int getWidth(){
			return nodePerson.size() * 2;
		}
		
		public void setX(int x){
			int size = nodePerson.size();
			x = x - size/2;
			
			for(Person person : nodePerson){
				person.setXPosition(x, false);
				x += 2;
			}
		}
		
		public int getX(){
			return nodePerson.get(0).getXPosition() + nodePerson.size()/2;
		}
		
		public int getForce(ForceNode x){
			int diff = x.getX() - this.getX();
			if(diff < 0)
				diff = 0 - diff;
			
			if(sameGeneration.contains(x)){
				
				if( diff < (x.getWidth()/2 + this.getWidth()/2 + 2)){
					return 100;
				}
				else{
					if(x.getX() > this.getX())
						return (diff - (x.getWidth()/2 + this.getWidth()/2 + 2));
					else
						return -(diff - (x.getWidth()/2 + this.getWidth()/2 + 2));
				}
			}
			if(connections.contains(x)){
				return x.getX() - this.getX(); 
			}
			return 0;
		}
		
		
	}
	
	
	
	/**
	 * this algorithm uses a force based approach for sorting the x position of the graph
	 * this algorithm should be called after the y sorting is finished
	 * @param top
	 */
	static public void forceXPositioning(){
		List<Person> inTree = Main.getMainNode().getPersonsReference();
		List<ForceNode> nodes = new LinkedList<ForceNode>();
		
		//first create a force tree
		int maxgen = 1;
		for(Person person : inTree){
			if(person.getGeneration() > maxgen)
				maxgen = person.getGeneration();
		}
		for(int i=1; i< maxgen+1; i++){
			List<ForceNode> nodeOfThisGen = new LinkedList<ForceNode>();
			
			for(Person person : inTree){
				if( person.getGeneration() == i){
					boolean added = false;
					for(ForceNode node : nodeOfThisGen){
						added = added || node.nodePerson.contains(person);
					}
					if(!added){
					List<Person> partners = Utils.getPartnerConnections(person);
					partners.add(person);
					ForceNode node = new ForceNode();
					node.nodePerson = partners;
					
					nodeOfThisGen.add(node);
					}
				}
				
			}
			for(ForceNode node : nodeOfThisGen){
				node.sameGeneration = nodeOfThisGen;
			}
			nodes.addAll(nodeOfThisGen);
		}
		
		for(ForceNode node : nodes){
			for(ForceNode innerNode : nodes){
				for(Person person : node.nodePerson){
					for(Person child : person.getChildren()){
						if(innerNode.nodePerson.contains(child))
							node.connections.add(innerNode);
					}
				}
			}
		}
		
		for(int i=0; i< 1; i++){
			for(ForceNode node : nodes){
				node.lastForce = 0;
				for(ForceNode con : node.connections){
					node.lastForce  += node.getForce(con);
				}
			}
			for(ForceNode node : nodes){
				int move =(int) (node.lastForce * 0.001);
				if(node.lastForce > 10 && move == 0){
					move = 2;
				}
				
				if(node.lastForce < -10 && move == 0){
					move = -2;
				}
				node.setX(node.getX()+move);
			}
			
		}
		
		
	}

}
