using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Melike Gecer
 * 
 * Object Pooling is implemented to control generating obstacles
 * and collectibles.
 * 
 * Obstacles; Create, Wagon
 * Collectibles; Red Gem, Purple Gem, Green Gem
 * 
 */

public class ObjectPooling : MonoBehaviour {

	// Pool sizes
	private int gemPoolSize 			= 10;
	private int obstaclePoolSize 		= 5;

	// Red Gem variables
	public GameObject redGemPrefab;
	private GameObject[] redGemList;
	private int redGemCounter 			= 0;

	// Purple Gem variables
	public GameObject purpleGemPrefab;
	private GameObject[] purpleGemList;
	private int purpleGemCounter 		= 0;

	// Green Gem variables
	public GameObject greenGemPrefab;
	private GameObject[] greenGemList;
	private int greenGemCounter 		= 0;

	// Create obstacle variables
	public GameObject createPrefab;
	private GameObject[] createObstacleList;
	private int createObstacleCounter 	= 0;

	// Wagon obstacle variables
	public GameObject wagonPrefab;
	private GameObject[] wagonObstacleList;
	private int wagonObstacleCounter 	= 0;

	// Spawn values
	private float spawnRate 			= 3f;
	private float timeSinceLastSpawned;

	// Other variables
	private Vector2 objectPoolPosition = new Vector2 (-15f, -25f);

	// Collectible Maps
	int currentMapX = 0;
	int currentMapY = 0;
	char[,] map = {
		{'p','p','p','r','p','c','p','r','p','p'}, 
		{'p','w','p','p','r','p','p','p','r','p'}, 
		{'p','p','p','c','p','p','p','c','p','r'}
	};

	void Start () {
		timeSinceLastSpawned = 0f;

		// Declare object lists
		redGemList = new GameObject[gemPoolSize];
		purpleGemList = new GameObject[gemPoolSize];
		greenGemList = new GameObject[gemPoolSize];
		wagonObstacleList = new GameObject[obstaclePoolSize];
		createObstacleList = new GameObject[obstaclePoolSize];

		// Generate object lists
		generateRedGems ();
		generatePurpleGems ();
		generateGreenGems ();
		generateWagonObstacles ();
		generateCreateObstacles ();
	}
	
	void Update () {
		timeSinceLastSpawned += Time.deltaTime;

		if (timeSinceLastSpawned >= spawnRate) {
			timeSinceLastSpawned = 0f;
			switch (map[currentMapX, currentMapY]) {
			case 'r':
				redGemList [redGemCounter].transform.position = new Vector2 (13f, -3.2f);
				redGemCounter++;
				break;
			case 'p':
				purpleGemList [purpleGemCounter].transform.position = new Vector2 (13f, -3.2f);
				purpleGemCounter++;
				break;
			case 'c':
				createObstacleList [createObstacleCounter].transform.position = new Vector2 (13f, -3.2f);
				greenGemList [greenGemCounter].transform.position = new Vector2 (13f, -1.25f);
				greenGemCounter++;
				createObstacleCounter++;
				break;
			case 'w':
				wagonObstacleList [wagonObstacleCounter].transform.position = new Vector2 (13f, -1.7f);
				greenGemList [greenGemCounter].transform.position = new Vector2 (13f, 1f);
				greenGemCounter++;
				wagonObstacleCounter++;
				break;
			}

			currentMapY++;

			if (redGemCounter >= gemPoolSize) {
				redGemCounter = 0;
				generateRedGems ();
			}

			if (purpleGemCounter >= gemPoolSize) {
				purpleGemCounter = 0;
				generatePurpleGems ();
			}

			if (greenGemCounter >= gemPoolSize) {
				greenGemCounter = 0;
				generateGreenGems ();
			}

			if (wagonObstacleCounter >= obstaclePoolSize) {
				wagonObstacleCounter = 0;
			}

			if (createObstacleCounter >= obstaclePoolSize) {
				createObstacleCounter = 0;
			}

			if (currentMapY >= map.GetLength(1)) {
				currentMapY = 0;
				currentMapX = Random.Range (0, 2);
			}
		}
	}

	private void generateRedGems() {
		for (int i = 0; i < gemPoolSize; i++) {
			redGemList [i] = (GameObject)Instantiate (redGemPrefab, objectPoolPosition, Quaternion.identity);
		}
	}

	private void generatePurpleGems() {
		for (int i = 0; i < gemPoolSize; i++) {
			purpleGemList [i] = (GameObject)Instantiate (purpleGemPrefab, objectPoolPosition, Quaternion.identity);
		}
	}

	private void generateGreenGems() {
		for (int i = 0; i < gemPoolSize; i++) {
			greenGemList [i] = (GameObject)Instantiate (greenGemPrefab, objectPoolPosition, Quaternion.identity);
		}
	}

	private void generateWagonObstacles() {
		for (int i = 0; i < obstaclePoolSize; i++) {
			wagonObstacleList [i] = (GameObject)Instantiate (wagonPrefab, objectPoolPosition, Quaternion.identity);
		}
	}

	private void generateCreateObstacles() {
		for (int i = 0; i < obstaclePoolSize; i++) {
			createObstacleList [i] = (GameObject)Instantiate (createPrefab, objectPoolPosition, Quaternion.identity);
		}
	}

}