using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SelectChar : MonoBehaviour
{
    public Character character;
    SpriteRenderer sr;
    Animator anim;
    public SelectChar[] chars;
    // Start is called before the first frame update
    void Start()
    {
        anim = GetComponent<Animator>();
        sr = GetComponent<SpriteRenderer>();
        if(DataManager.instance.currentChara==character) OnSelect();
        else OnDeSelect();
    }
    private void OnMouseUpAsButton(){
        
        DataManager.instance.currentChara=character;
        OnSelect();
        for(int i=0;i<chars.Length;i++){
            if(chars[i]!=this)chars[i].OnDeSelect();
        }
    }
    void OnDeSelect()
    {
        anim.SetBool("run", false); // 애니메이션 전환하기 위한 파라미터
        sr.color=new Color(0.5f,0.5f,0.5f); // 어둡게 만들어줌.
    }
    void OnSelect()
    {
        anim.SetBool("run", true);
        sr.color=new Color(1f,1f,1f); // 밝게 만들어줌.
    }
}
