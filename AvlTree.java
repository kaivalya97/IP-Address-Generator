import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;

/*Dynamic IP Address Generator by Group 26: Aaditya Shah, Kaivalya Shah, Mohit Vachhani*/

class AvlTree 
{
  protected AvlNode root;  
  public int nodes;
  
  public void insert(int k, String name) 
  {
    AvlNode n = new AvlNode(k, name);
    insertAvl(this.root,n);
	nodes++;
  }
  
  public void insertAvl(AvlNode p, AvlNode q)
  {
    if(p==null) 
    {
      this.root=q;
    } 
    else 
    {
      if(q.key<p.key) 
      {
        if(p.left==null) 
        {
          p.left = q;
          q.parent = p;
          recursiveBalance(p);
        } 
        else 
        {
          insertAvl(p.left,q);
        }
      } 
      else if(q.key>p.key) 
      {
        if(p.right==null) 
        {
          p.right = q;
          q.parent = p;
          recursiveBalance(p);
        } 
        else 
        {
          insertAvl(p.right,q);
        }
      }
    }
  }
  
  public void recursiveBalance(AvlNode cur) 
  {
    setBalance(cur);
    int balance = cur.balance;
    if(balance==-2)
    {
      if(height(cur.left.left)>=height(cur.left.right))
      {
        cur = rotateRight(cur);
      }
      else
      {
        cur = doubleRotateLeftRight(cur);
      }
    }
    else if(balance==2)
    {
      if(height(cur.right.right)>=height(cur.right.left))
      {
        cur = rotateLeft(cur);
      }
      else
      {
        cur = doubleRotateRightLeft(cur);
      }
    }
    if(cur.parent!=null)
    {
      recursiveBalance(cur.parent);
    }
    else
    {
      this.root = cur;
    }
  }
  
  public void remove(int k)
  {
    removeAvl(this.root,k);
	nodes--;
  }
  
  public void removeAvl(AvlNode p,int q)
  {
    if(p==null)
    {
      return;
    }
    else
    {
      if(p.key>q)
      {
        removeAvl(p.left,q);
      }
      else if(p.key<q)
      {
        removeAvl(p.right,q);
      }
      else if(p.key==q)
      {
        removeFoundNode(p);
      }
    }
  }
  
  public void removeFoundNode(AvlNode q)
  {
    AvlNode r;
    if(q.left==null || q.right==null)
    {
      if(q.parent==null)
      {
        this.root=null;
        q=null;
        return;
      }
      r = q;
    }
    else
    {
      r = successor(q);
      q.key = r.key;
    }
    AvlNode p;
    if(r.left!=null)
    {
      p = r.left;
    }
    else
    {
      p = r.right;
    }
    if(p!=null)
    {
      p.parent = r.parent;
    }
    if(r.parent==null)
    {
      this.root = p;
    }
    else
    {
      if(r==r.parent.left)
      {
        r.parent.left=p;
      }
      else
      {
        r.parent.right = p;
      }
      recursiveBalance(r.parent);
    }
    r = null;
  }
  
  public AvlNode rotateLeft(AvlNode n)
  {
    AvlNode v = n.right;
    v.parent = n.parent;
    n.right = v.left;
    if(n.right!=null)
    {
      n.right.parent=n;
    }
    v.left = n;
    n.parent = v;
    if(v.parent!=null)
    {
      if(v.parent.right==n)
      {
        v.parent.right = v;
      }
      else if(v.parent.left==n)
      {
        v.parent.left = v;
      }
    }
    setBalance(n);
    setBalance(v);
    return v;
  }
  
  public AvlNode rotateRight(AvlNode n)
  {
    AvlNode v = n.left;
    v.parent = n.parent;
    n.left = v.right;
    if(n.left!=null)
    {
      n.left.parent=n;
    }
    v.right = n;
    n.parent = v;
    if(v.parent!=null)
    {
      if(v.parent.right==n)
      {
        v.parent.right = v;
      }
      else if(v.parent.left==n)
      {
        v.parent.left = v;
      }
    }
    setBalance(n);
    setBalance(v);
    return v;
  }
  public AvlNode doubleRotateLeftRight(AvlNode u)
  {
    u.left = rotateLeft(u.left);
    return rotateRight(u);
  }
  
  public AvlNode doubleRotateRightLeft(AvlNode u)
  {
    u.right = rotateRight(u.right);
    return rotateLeft(u);
  }
  public AvlNode successor(AvlNode q)
  {
    if(q.right!=null) 
    {
      AvlNode r = q.right;
      while(r.left!=null) 
      {
        r = r.left;
      }
      return r;
    } 
    else 
    {
      AvlNode p = q.parent;
      while(p!=null && q==p.right) 
      {
        q = p;
        p = q.parent;
      }
      return p;
    }
  }
  
  private int height(AvlNode cur)
  {
    if(cur==null)
    {
      return -1;
    }
    if(cur.left==null && cur.right==null)
    {
      return 0;
    }
    else if(cur.left==null)
    {
      return 1+height(cur.right);
    }
    else if(cur.right==null)
    {
      return 1+height(cur.left);
    } 
    else
    {
      return 1+maximum(height(cur.left),height(cur.right));
    }
  }
  
  private int maximum(int a, int b)
  {
    if(a>=b)
    {
      return a;
    }
    else
    {
      return b;
    }
  }
  
  private void setBalance(AvlNode cur)
  {
    cur.balance = height(cur.right)-height(cur.left);
  }
  
  public void writetofile(String dept)
  {
	GUI.deleteFile(new File(dept+"Tree"));
    preorder(root,dept);
  }
  
  public void preorder(AvlNode r, String dept)
  {
    if (r != null)
    {
      	String filename = new String(dept+"Tree");
		try
		{
			FileWriter fw=new FileWriter(filename,true);
			BufferedWriter br=new BufferedWriter(fw);
			br.append(r.key +","+r.username+";");
			br.close();
      		preorder(r.left,dept);
      		preorder(r.right,dept);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
  }
  
    public boolean search(String x)
    {
        return (search(root,x));
    }
    public boolean search(AvlNode r,String x)
    {
      boolean flag = false;
      if (r != null)
      {
        flag = false;
        if(x.equals(r.username))
        {
          flag=true;
          return true;
        } 
        preorder(r.left,x);
        preorder(r.right,x);
      }
      return flag;
    }
}