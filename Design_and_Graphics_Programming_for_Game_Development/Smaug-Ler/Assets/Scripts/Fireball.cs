using System.Collections;
using System.Collections.Generic;
using UnityEngine;
[RequireComponent(typeof(Rigidbody2D))]

public class Fireball : MonoBehaviour {
	[SerializeField]
	private float speed;

    public AudioClip knightDeath;
    private AudioSource source;

    void Awake()
    {
        source = GetComponent<AudioSource>();
    }

    private Rigidbody2D myRigidbody;

	private Vector2 direction;

	// to change score as a knight dies
	private ScoreManager scoreManager;

	// Use this for initialization
	void Start () {
		myRigidbody = GetComponent<Rigidbody2D>();
		//always through to the right
		direction = Vector2.right;

		// to change score as a gem is collected.
		scoreManager = FindObjectOfType<ScoreManager> ();
	}

	void FixedUpdate()
	{
		myRigidbody.velocity = direction * speed;
	}
	public void Initialize(Vector2 direction)
	{
		this.direction = direction;
	}

	void OnBecameInvisible()
	{
		Destroy (gameObject);
	}

	private void OnTriggerEnter2D(Collider2D collider){
		if (collider.tag == "Knight") {
            //source.PlayOneShot(knightDeath);
            scoreManager.scoreCount += 15;
            if (knightDeath)               //And here
                AudioSource.PlayClipAtPoint(knightDeath, Camera.main.transform.position, 0.5f);
        }

	}
}
