package jgame.entity;

import java.awt.Color;
import java.awt.Graphics;

import jgame.Scene;
import jgame.Window;
import jgame.components.BoxCollider;
import jgame.sprite.Sprite;

/**
 * This class represents a game object.
 * @author David Almeida
 * @see jgame.entity.Entity
 * @since 1.0
 */
public class GameObject extends Entity implements Cloneable{

	private Sprite sprite;
	private float xD,yD;
	private boolean moveOnceX,moveOnceY;

	/**
	 * 
	 * @param sprite the sprite representing the game object
	 * @since 1.0
	 */
	public GameObject(Sprite sprite) {
		super();
		this.sprite = sprite;
	}

	/**
	 * 
	 * @param sprite the sprite representing the game object
	 * @param x the initial x of the game object
	 * @param y the initial y of the game object
	 * @param layer the layer of the game object. A game object will overlap any game object 
	 * that has a lower layer
	 * @since 1.0
	 */
	public GameObject(Sprite sprite, int x, int y,int layer) {
		this(sprite);
		position.x = x;
		position.y = y;
		this.layer = layer;
	}


	/**
	 * @return the layer
	 * @since 1.0
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * @param newSprite the sprite to set
	 * @since 1.0
	 */
	public void setSprite(Sprite newSprite) {
		this.sprite = newSprite;
	}
	
	/**
	 * @param move along the x axis
	 * @since 1.2
	 */
	public void moveX(float x) {
		this.xD = x;
		moveOnceX = true;
	}
	
	/**
	 * @param move along the y axis
	 * @since 1.2
	 */
	public void moveY(float y) {
		this.yD = y;
		moveOnceY= true;
	}


	/**
	 * @param moves continuously in the x-axis
	 * @since 1.0
	 */
	public void moveXContinuously(float x) {
		this.xD = x;
	}


	/**
	 * @param moves continuously in the x-axis
	 * @since 1.0
	 */
	public void moveYContinuously(float y) {
		this.yD = y;
	}


	/**
	 * Draws the game object in the scene
	 * @param g the graphics of the scene
	 * @since 1.0
	 */
	public void draw(Graphics g){
		if(xD != 0 || yD != 0) {
			move();
			if(moveOnceX)
				xD = 0;
			if(moveOnceY)
				yD = 0;
			
		}
		Camera camera = Camera.getInstance();
		g.setColor(Color.WHITE);
		if(sprite != null)
			g.drawImage(sprite.getNextSpriteSequence(),
					(int) (position.x - camera.position.x)
					,(int) (position.y - camera.position.y), null);
		if(name.equals("player")) {
			g.drawString("Player " + position.x + " " + position.y  + " " 
					+ (position.x + getSprite().getSpriteWidth()) + " "
					+ (position.y + getSprite().getSpriteHeight()),0,50);
			BoxCollider c = (BoxCollider) this.getComponent("BoxCollider");

			g.drawString("Box Collider " + c.minPoint.x+ " " + c.minPoint.x + " " + 
					c.maxPoint.x + " " + c.maxPoint.y ,0,100);

		}
	}

	/**
	 * Moves the game object in the current scene that is being displayed
	 * @since 1.0
	 */
	private void move(){
		//update components to update their position based on the game object 

		this.notifyObservers();
		BoxCollider c = (BoxCollider) this.getComponent("BoxCollider");
		if(c != null) {
			this.notifyObservers(xD,yD);

		}

		Scene scene = Window.getInstance().getScene();

		position.x += xD;
		if(position.x < 0)
			position.x = 0;
		else if(position.x > scene.getWidth() - sprite.getSpriteWidth())
			position.x = scene.getWidth() - sprite.getSpriteWidth();


		position.y += yD;
		if(position.y < 0)
			position.y = 0;
		else if(position.y > scene.getHeight() - sprite.getSpriteHeight())
			position.y = scene.getHeight() - sprite.getSpriteHeight();

	}


	/**
	 * 
	 * @return the sprite of the game object
	 * @since 1.0
	 */
	public Sprite getSprite(){
		return sprite;
	}
	
	@Override
	public Object clone() {
		GameObject other = null;
		other = (GameObject) super.clone();
		if(this.sprite != null)
			other.sprite = (Sprite) this.sprite.clone();
		return other;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sprite == null) ? 0 : sprite.hashCode());
		result = prime * result + Float.floatToIntBits(xD);
		result = prime * result + Float.floatToIntBits(yD);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
