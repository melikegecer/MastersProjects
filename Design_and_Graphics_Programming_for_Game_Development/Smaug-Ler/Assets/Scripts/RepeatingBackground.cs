using UnityEngine;
using System.Collections;

/*
 * Melike Gecer
 * 
 * The game is auto-scroll. Therefore, the backgrounds should be replaced
 * as the scene comes to the end of current backgroud.
 * 
 */

public class RepeatingBackground : MonoBehaviour 
{

	private BoxCollider2D groundCollider;
	private float groundHorizontalLength;
	private float minX;
	//public float speed = 1.5f;

	void Start() {
		SpriteRenderer sp = GetComponent<SpriteRenderer> ();
		groundHorizontalLength = sp.size.x * transform.localScale.x;
		minX = transform.position.x - groundHorizontalLength;
	}

	private void Update() {
		Vector2 position = transform.position;
		//position.x -= speed * Time.deltaTime;
		position.x -= Mathf.Abs(GameConstant.speed) * Time.deltaTime;
		transform.position = position;
		if (transform.position.x <= minX) {
			RepositionBackground ();
		}
	}

	private void RepositionBackground() {
		Vector2 groundOffSet = new Vector2(groundHorizontalLength, 0);
		transform.position = (Vector2) transform.position + groundOffSet;
	}
}