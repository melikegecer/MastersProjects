using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour {

    /*private void Awake()
    {
        AudioListener.pause = true;
    }*/

    // Start the game
    public void Play()
    {
        /*if(SceneManager.GetActiveScene().name == "menu")
        {
            SceneManager.LoadScene("test");
        } else
        {
            SceneManager.LoadScene("menu");
        }*/

        SceneManager.LoadScene("background");
        AudioListener.pause = false;
    }

    // Exit the game (only works on builded version)
    public void Quit()
    {
        Application.Quit();
    }

}
