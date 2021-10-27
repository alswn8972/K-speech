using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class player : MonoBehaviour
{
    public float Speed;
    bool isHorizonMove;
    private Animator anim;
    Rigidbody2D rigid;
    //바라보는 방향
    Vector3 dirVec;
    GameObject scanObject;

    // Start is called before the first frame update
    void Start()
    {
        anim=GetComponent<Animator>();
        rigid=GetComponent<Rigidbody2D>();
    }
    
    
    // Update is called once per frame
    void Update()
    {
        float X=Input.GetAxisRaw("Horizontal");
        float Y=Input.GetAxisRaw("Vertical");
        

        bool hDown=Input.GetButtonDown("Horizontal");
        bool vDown=Input.GetButtonDown("Vertical");
        bool hUp=Input.GetButtonUp("Horizontal");
        bool vUp=Input.GetButtonUp("Vertical");

        if(hDown)
            isHorizonMove=true;
        else if(vDown)
            isHorizonMove=false;
        else if(hUp || vUp)
            isHorizonMove = X != 0;
        if(X !=0 || Y !=0)
            anim.SetBool("isMove",true);
        else
            anim.SetBool("isMove",false);

        anim.SetFloat("MoveX",X);
        anim.SetFloat("MoveY",Y);

        //Direction
        if(vDown && Y==1)
            dirVec=Vector3.up;
        else if(vDown && Y==-1)
            dirVec=Vector3.down;
        else if(hDown && X==-1)
            dirVec=Vector3.left;
        else if(hDown && X==1)
            dirVec=Vector3.right;
        
        //
        if(Input.GetButtonDown("Jump") && scanObject !=null){
            Debug.Log("this is : "+ scanObject.name);
        }

        Vector2 moveVec= isHorizonMove ? new Vector2(X,0) : new Vector2(0,Y);
        transform.Translate(moveVec*Time.deltaTime*Speed);
    }
    void FixedUpdate(){

        Debug.DrawRay(rigid.position,dirVec*0.7f,new Color(0,1,0));
        RaycastHit2D rayHit=Physics2D.Raycast(rigid.position,dirVec,0.7f,LayerMask.GetMask("Object"));

        if(rayHit.collider !=null )
        {
            //Debug.Log("this is : not null");
            scanObject=rayHit.collider.gameObject;
        }else{
            
            scanObject=null;
        }
    }
}
