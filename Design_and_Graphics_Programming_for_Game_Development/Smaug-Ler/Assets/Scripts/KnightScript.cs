using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KnightScript : MonoBehaviour {

	private Rigidbody2D myRigidbody;
    private Animator myAnimator;

	private Vector2 direction;

	[SerializeField]
	private float knightspeed;

	// Use this for initialization
	void Start () {
		myRigidbody = GetComponent<Rigidbody2D> ();
		direction = Vector2.left;
        myAnimator = GetComponent<Animator>();
	}
	
	// Update is called once per frame
	void FixedUpdate() {
		HandleMovement ();
	}

	public void Initialize(Vector2 direction) {
		this.direction = direction;
	}

	private void HandleMovement() {
		myRigidbody.velocity = Vector2.left; //x=-1 , y =0
		myRigidbody.velocity = knightspeed * direction;
//		myRigidbody.velocity = 3 * direction;
	}

    private void OnTriggerEnter2D(Collider2D collider)
    {
        if (collider.tag == "Fireball")
        {
            myAnimator.SetTrigger("knightdeath");
            Destroy(collider.gameObject);
            Destroy(this.gameObject,0.5f);
        }

    }

    void OnBecameInvisible() {
       // myAnimator.SetTrigger("knightdeath");
	//	Destroy (gameObject);
	}


}
