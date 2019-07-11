using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour {

	public float jump;
	private Rigidbody2D rb2d;

    void Start() {
		rb2d = GetComponent<Rigidbody2D> ();
    }
	
	void Update () {
		if (Input.GetKeyDown (KeyCode.Space)) {
			// the game is autoscroll
			// therefore, x position is always the same
			rb2d.velocity = new Vector2 (rb2d.velocity.x, jump);
		}
	}

	private void OnTriggerEnter2D(Collider2D collider){
		if (collider.tag == "Gem") {
            // TODO: score should be increased
            // once the gem is collected, destroy the object
            Destroy (collider.gameObject);
			//collider.gameObject.SetActive (false);
		}
	}
}
