using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class DragonScript : MonoBehaviour {

	private static DragonScript instance;
	public float jump;

    bool isGrounded = true;
    public float isGroundedRayLength = 0.1f;
    public LayerMask layerMaskForGrounded;

    public AudioClip fireSound;
    private AudioSource source;
    private float timeStamp;
    public AudioClip gem;
    public AudioClip hit;

    private void Awake()
    {
        source = GetComponent<AudioSource>();
        timeStamp = Time.time + 1.0f;
    }

    // to change score as a gem is collected
    private ScoreManager scoreManager;

	public static DragonScript Instance
	{
		get
		{
			if (instance == null) 
			{
				instance = GameObject.FindObjectOfType<DragonScript>();
			}
			return instance;
		}
	}

	private Animator myAnimator; 

	[SerializeField]
	private Transform FireballPos;

	[SerializeField]
	private GameObject FireballPrefab;

	[SerializeField]
	private int health;

	public GameObject Heart1, Heart2, Heart3, gameover;

	//public abstract bool IsDead{ get;}

	public Rigidbody2D MyRigidbody{ get; set;}

    //public bool Attack { get; set;}
    // Use this for initialization
    void Start () {
		MyRigidbody = GetComponent<Rigidbody2D>();
		myAnimator = GetComponent<Animator>();

        // to change score as a gem is collected.
        scoreManager = FindObjectOfType<ScoreManager> ();
	}
	
	// Update is called once per frame
	void FixedUpdate () {
        HandleInput();	

		switch (health) {
		case 3:
			Heart1.gameObject.SetActive (true);
			Heart2.gameObject.SetActive (true);
			Heart3.gameObject.SetActive (true);
			break;

		case 2:
			Heart1.gameObject.SetActive (true);
			Heart2.gameObject.SetActive (true);
			Heart3.gameObject.SetActive (false);
			break;

		case 1:
			Heart1.gameObject.SetActive (true);
			Heart2.gameObject.SetActive (false);
			Heart3.gameObject.SetActive (false);
			break;

		case 0:
			Heart1.gameObject.SetActive (false);
			Heart2.gameObject.SetActive (false);
			Heart3.gameObject.SetActive (false);
			Time.timeScale = 0;
			break;
		}

	}

    private void HandleInput()
	{
		if (Input.GetKeyDown (KeyCode.LeftShift)) {
			myAnimator.SetTrigger("DragonAttack");
			MyRigidbody.velocity = Vector2.zero;
		}
		if (Input.GetKeyDown (KeyCode.V)) 
		{
			myAnimator.SetTrigger ("attack");
            //DragonAttack (0);
            if(timeStamp <= Time.time)
            {
                source.PlayOneShot(fireSound);
                timeStamp = Time.time + 1.0f;
            }
        }
			
		if (Input.GetKeyDown (KeyCode.Space)) {
            // the game is autoscroll
            // therefore, x position is always the same
            if (isGrounded)
            {
                myAnimator.SetTrigger("jump");
                MyRigidbody.velocity = new Vector2(MyRigidbody.velocity.x, jump);
            }
        }
	//	if (!this.myAnimator.GetCurrentAnimatorStateInfo (0).IsTag ("Attack")) {
		//do something i fit is in the state of attack!
	//	}
	}

	//actually it is throwing fire function
	public void DragonAttack (int value)
	{
		GameObject tmp = Instantiate(FireballPrefab, FireballPos.position , Quaternion.identity);
		tmp.GetComponent<Fireball> ().Initialize (Vector2.right);
    }

	private void OnTriggerEnter2D(Collider2D collider){
        // once the gem is collected, destroy the object and add score.
        // if the obstacle's collider is triggered, then decrease score.
		if (collider.tag == "RedGem") {
            AudioSource.PlayClipAtPoint(gem, Camera.main.transform.position, 0.25f);
            Destroy (collider.gameObject);
			scoreManager.scoreCount += 10;
		} else if (collider.tag == "PurpleGem") {
            AudioSource.PlayClipAtPoint(gem, Camera.main.transform.position, 0.25f);
            Destroy(collider.gameObject);
            scoreManager.scoreCount += 5;
		} else if (collider.tag == "GreenGem") {
            AudioSource.PlayClipAtPoint(gem, Camera.main.transform.position, 0.25f);
            Destroy(collider.gameObject);
            scoreManager.scoreCount += 15;
		} else if (collider.tag == "Obstacle") {
			scoreManager.scoreCount -= 5;
		}
		if (collider.tag == "Knight") {
            Debug.Log("knight", gameObject);
            AudioSource bla = collider.GetComponent<AudioSource>();
            bla.PlayOneShot(hit);
            DragonTakeDamage ();
		}
		if (collider.tag == "Obstacle") {
            AudioSource bla = collider.GetComponent<AudioSource>();
            bla.PlayOneShot(hit);
            DragonTakeDamage ();
		}
        if (collider.name == "background")
        {
            Debug.Log("name", gameObject);
        }
        if (collider.tag == "test")
        {
            Debug.Log("tag", gameObject);
        }
    }

    public bool IsDead{
		get{ 
			return health <= 0;
		}
	}//isdead

	public void DragonTakeDamage(){
		health -= 1;
		if (!IsDead) {
            myAnimator.SetTrigger ("die");//mean damage :D 
		} else {
			//myAnimator.SetTrigger ("die");
            SceneManager.LoadScene("gameOver");
            //Destroy (gameObject);
		}
	}
}
