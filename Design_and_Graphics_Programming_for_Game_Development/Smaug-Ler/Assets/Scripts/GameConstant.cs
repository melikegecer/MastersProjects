using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Melike Gecer
 * 
 * Game Constants should be held in here.
 * 
 */

public class GameConstant : MonoBehaviour {

	public static float speed;
	private float speedTimer;
	private float speedIncRate		= 3f;

	void Start () {
		speed = -1.5f;
		speedTimer = 0f;
	}
	
	void Update () {
		speedTimer += Time.deltaTime;

		if (speedTimer >= speedIncRate) {
			speed -= 0.1f;
			speedTimer = 0f;
		}
	}
}
