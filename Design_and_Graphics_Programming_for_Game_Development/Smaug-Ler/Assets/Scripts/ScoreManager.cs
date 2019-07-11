using UnityEngine;
using System.Collections;
using UnityEngine.UI;

/*
 * Melike Gecer
 * 
 * Score management to calculate current score and 
 * keep the high score of the game.
 * 
 */

public class ScoreManager : MonoBehaviour {

	public Text scoreText;
	public Text highScoreText;

	public float scoreCount;

	public bool scoreIncreasing;

	public float pointsPerSecond;
	private float pointsPerSecondRate = 1f;
	private float timeSinceLastSpawned;

	void Start () {
		timeSinceLastSpawned = 0f;
	}
	
	void Update () {
		timeSinceLastSpawned += Time.deltaTime;

		if (timeSinceLastSpawned >= pointsPerSecondRate) {
			timeSinceLastSpawned = 0f;

			scoreCount += pointsPerSecond;
		}

		scoreText.text = "Score: " + scoreCount;
        highScoreText.text = "High Score: " + PlayerPrefs.GetFloat("highscore");
        PlayerPrefs.SetFloat("latestScore", scoreCount);
        PlayerPrefs.Save();

        if (scoreCount > PlayerPrefs.GetFloat("highscore")) {
			PlayerPrefs.SetFloat ("highscore", scoreCount);
			PlayerPrefs.Save();
		}
	}
}
