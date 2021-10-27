using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;


public class Scene1 : MonoBehaviour
{

    int sceneIndex;
    // Start is called before the first frame update
    void Start()
    {
        sceneIndex=SceneManager.GetActiveScene().buildIndex;
    }
    public void NextScene(){
        SceneManager.LoadScene(sceneIndex+1);
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}
