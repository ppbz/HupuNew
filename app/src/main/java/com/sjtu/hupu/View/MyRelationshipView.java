package com.sjtu.hupu.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.sjtu.hupu.R;
import com.sjtu.hupu.more.Node;

import java.util.ArrayDeque;

public class MyRelationshipView extends View {

    private Node root;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    ArrayDeque<Node> ad = new ArrayDeque<>();


    public MyRelationshipView(Context context) {
        super(context);
    }

    public MyRelationshipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelationshipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);

        if(root != null) {
            ad.add(root);
            root.x = (float) getWidth()/2;
            root.y = (float) getHeight()/2;
            while(!ad.isEmpty()) {
                Node node = ad.removeFirst();
                canvas.drawCircle(node.x,node.y,40,paint);
                if(node.left != null) {
                    ad.add(node.left);
                    Node left = node.left;
                    left.x = node.x - 100f;
                    left.y = node.y - 100f;
                    canvas.drawLine(node.x,node.y,left.x,left.y,paint);
                }
                if(node.right != null) {
                    ad.add(node.right);
                    Node right = node.right;
                    right.x = node.x + 100f;
                    right.y = node.y - 100f;
                    canvas.drawLine(node.x,node.y,right.x,right.y,paint);
                }
            }
        }
    }

    public void addData(Node node){
        this.root = node;
    }

}
