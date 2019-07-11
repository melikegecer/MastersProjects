using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class GameOverScores : MonoBehaviour
{

    public Text latestScoreText;
    public Text highScoreText;

    void Start()
    {
        latestScoreText.text = "Score: " + PlayerPrefs.GetFloat("latestScore"); ;
        highScoreText.text = "High Score: " + PlayerPrefs.GetFloat("highscore");
    }
}
