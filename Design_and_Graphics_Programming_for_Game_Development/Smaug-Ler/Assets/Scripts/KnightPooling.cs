using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Melike Gecer
 * 
 * Knight Pooling is implemented to generate Knights through the 
 * game play.
 * 
 */

public class KnightPooling : MonoBehaviour {

	private int knightPoolSize 			= 6;
	public GameObject knightPrefab;
	private GameObject[] knightList;
	private int knightCounter 			= 0;
	private float spawnRate 			= 2f;
	private float timeSinceLastSpawned;
	private Vector2 objectPoolPosition 	= new Vector2 (-15f, -25f);

	void Start () {
		timeSinceLastSpawned = 0f;
		knightList = new GameObject[knightPoolSize];
		generateKnights ();
	}

	void Update () {
		timeSinceLastSpawned += Time.deltaTime;

		if (timeSinceLastSpawned >= spawnRate) {
			timeSinceLastSpawned = 0f;
			knightList [knightCounter].transform.position = new Vector2 (13f, -2.5f);
			knightList [knightCounter].SetActive (true);
			knightCounter++;

			if (knightCounter >= knightPoolSize) {
				knightCounter = 0;
				generateKnights ();
			}

			spawnRate = Random.Range (5f, 10f);
		}
	}

	private void generateKnights() {
		for (int i = 0; i < knightPoolSize; i++) {
			knightList [i] = (GameObject)Instantiate (knightPrefab, objectPoolPosition, Quaternion.identity);
			knightList [i].SetActive (false);
		}
	}
}