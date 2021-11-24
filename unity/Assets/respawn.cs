using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class respawn : MonoBehaviour
{

    public GameObject[] charPrefabs;
    public GameObject player;

    // Start is called before the first frame update
    void Start()
    {
        //player=Instantiate(charPrefabs[(int)DataMgr.instance.currentChara]);
        //player.transform.position=transform.position;
    }

}
