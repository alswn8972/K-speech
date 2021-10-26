using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class player : MonoBehaviour
{

    private Animator anim;
    // Start is called before the first frame update
    void Start()
    {
        anim=GetComponent<Animator>();
    }
    
    public float Speed;
    // Update is called once per frame
    void Update()
    {
        float X=Input.GetAxisRaw("Horizontal");
        float Y=Input.GetAxisRaw("Vertical");
        
        if(X !=0 || Y !=0)
            anim.SetBool("isMove",true);
        else
            anim.SetBool("isMove",false);

        anim.SetFloat("MoveX",X);
        anim.SetFloat("MoveY",Y);

        
        transform.Translate(new Vector2(X,Y)*Time.deltaTime*Speed);
    }

}
