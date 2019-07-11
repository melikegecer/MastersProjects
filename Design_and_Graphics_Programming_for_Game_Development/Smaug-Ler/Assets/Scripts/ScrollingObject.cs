using System.Collections;
using System.Collections.Generic;
using UnityEngine;
 
/*
 * Melike Gecer
 * 
 * Some objects need to be scrolled through the game.
 * This class should be added to these type of objects.
 * 
 * Knights, collectibles and obstacles. 
 * 
 */

public class ScrollingObject : MonoBehaviour {
	private Rigidbody2D rb2d;

	void Start () {
		rb2d = GetComponent<Rigidbody2D>();
		//rb2d.velocity = new Vector2 (-1.5f, 0);
		rb2d.velocity = new Vector2 (GameConstant.speed, 0);
	}

	void Update() {
		// if game ends
		// scrolling should be stopped
		// rb2d.velocity = Vector2.zero;
	}
}